package airlinecompany2server.airlinecompany2server.endpoint.message.request;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "SearchAirportsRequest")
public class SearchAirportsRequest {

    @XmlElement(name = "SearchFilter", required = true)
    protected String searchFilter;  

    public SearchAirportsRequest(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public String getSearchFilter() {
        return searchFilter;
    }
}


