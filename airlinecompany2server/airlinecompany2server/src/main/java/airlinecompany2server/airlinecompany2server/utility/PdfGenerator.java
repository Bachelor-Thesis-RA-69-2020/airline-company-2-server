package airlinecompany2server.airlinecompany2server.utility;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import airlinecompany2server.airlinecompany2server.model.Booking;
import airlinecompany2server.airlinecompany2server.model.Flight;

public class PdfGenerator {
    public byte[] generateTicketsPDF(Flight flight, String flightClass, List<Booking> bookings, List<String> ticketCodes) {
        String filePath = "src/main/resources/templates/ticketPDFTemplate.html";

        HtmlTemplate htmlTemplate = new HtmlTemplate(filePath);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");

        String formattedTakeOffTime = flight.getTakeoffTime().format(formatter);
        String formattedLandingTime = flight.getLandingTime().format(formatter);

        htmlTemplate.setBaseValue("originIATA", flight.getDepartureAirport().getIataCode());
        htmlTemplate.setBaseValue("destinationIATA", flight.getArrivalAirport().getIataCode());
        htmlTemplate.setBaseValue("origin", flight.getDepartureAirport().getAirportName());
        htmlTemplate.setBaseValue("destination", flight.getArrivalAirport().getAirportName());
        htmlTemplate.setBaseValue("departure", formattedTakeOffTime);
        htmlTemplate.setBaseValue("arrival", formattedLandingTime);
        htmlTemplate.setBaseValue("flightNumber", flight.getCode());
        htmlTemplate.setBaseValue("flightClass", flightClass);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos)) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            PdfMerger merger = new PdfMerger(pdfDoc);

            for (int i = 0; i < bookings.size(); i++) {
                htmlTemplate.reset();

                String ticketCode = ticketCodes.get(i);
                Booking booking = bookings.get(i);

                formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                String formattedBirthDate = booking.getBirthDate().format(formatter);

                htmlTemplate.setValue("ticketCode", ticketCode);
                htmlTemplate.setValue("name", booking.getFullName());
                htmlTemplate.setValue("dateOfBirth", formattedBirthDate);
                htmlTemplate.setValue("passportNumber", booking.getPassportId());

                String htmlContent = htmlTemplate.getHtml();

                ByteArrayOutputStream tempBaos = new ByteArrayOutputStream();
                HtmlConverter.convertToPdf(htmlContent, tempBaos);

                PdfDocument tempDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(tempBaos.toByteArray())));
                merger.merge(tempDoc, 1, tempDoc.getNumberOfPages());
                tempDoc.close();
            }

            pdfDoc.close();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
