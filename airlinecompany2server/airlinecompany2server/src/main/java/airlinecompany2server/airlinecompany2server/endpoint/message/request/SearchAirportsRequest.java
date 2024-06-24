package airlinecompany2server.airlinecompany2server.endpoint.message.request;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "searchFilter"
})
@XmlRootElement(name = "SearchAirportsRequest", namespace = "http://localhost:8082/")
public class SearchAirportsRequest {

    @XmlElement(name = "SearchFilter", required = true)
    protected String searchFilter;

    public SearchAirportsRequest() {
    }   

    public SearchAirportsRequest(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearch(String searchFilter) {
        this.searchFilter = searchFilter;
    }
}


