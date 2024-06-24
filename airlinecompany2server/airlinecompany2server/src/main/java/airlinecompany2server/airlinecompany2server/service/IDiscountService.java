package airlinecompany2server.airlinecompany2server.service;

import airlinecompany2server.airlinecompany2server.model.Discount;

public interface IDiscountService {
    String create(String flightCode, Discount discount);
}
