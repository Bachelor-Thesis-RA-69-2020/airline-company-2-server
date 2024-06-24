package airlinecompany2server.airlinecompany2server.model;

public class TicketPricing {
    private Float economyClassPrice;

    private Integer economyClassTicketCount;

    private Float businessClassPrice;

    private Integer businessClassTicketCount;

    private Float firstClassPrice;

    private Integer firstClassTicketCount;

    public TicketPricing(Float economyClassPrice, Integer economyClassTicketCount, Float businessClassPrice,
            Integer businessClassTicketCount, Float firstClassPrice, Integer firstClassTicketCount) {
        this.economyClassPrice = economyClassPrice;
        this.economyClassTicketCount = economyClassTicketCount;
        this.businessClassPrice = businessClassPrice;
        this.businessClassTicketCount = businessClassTicketCount;
        this.firstClassPrice = firstClassPrice;
        this.firstClassTicketCount = firstClassTicketCount;
    }

    public Float getEconomyClassPrice() {
        return economyClassPrice;
    }

    public Integer getEconomyClassTicketCount() {
        return economyClassTicketCount;
    }

    public Float getBusinessClassPrice() {
        return businessClassPrice;
    }

    public Integer getBusinessClassTicketCount() {
        return businessClassTicketCount;
    }

    public Float getFirstClassPrice() {
        return firstClassPrice;
    }

    public Integer getFirstClassTicketCount() {
        return firstClassTicketCount;
    }
}
