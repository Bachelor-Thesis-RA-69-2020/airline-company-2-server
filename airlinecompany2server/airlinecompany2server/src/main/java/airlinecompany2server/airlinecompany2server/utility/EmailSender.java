package airlinecompany2server.airlinecompany2server.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Component
public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTicketsEmail(String to, byte[] pdf) {
        String title = "Ticket purchase confirmation";
        String htmlContent = "";

        String filePath = "src/main/resources/templates/bookingEmailTemplate.html";
        try {
            htmlContent = readHtmlTemplate(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(htmlContent, true);

            String logoPath = "src/main/resources/images/logo.png";
            FileSystemResource image = new FileSystemResource(new File(logoPath));
            helper.addInline("logo.png", image);

            DataSource pdfDataSource = new ByteArrayDataSource(pdf, "application/pdf");
            helper.addAttachment("tickets.pdf", pdfDataSource);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String readHtmlTemplate(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
