package configs.steps;

import java.time.ZonedDateTime;

import intercity.rest.model.IntercityOfferRequestDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;


public class OfferStepDefs extends SpringIntegrationTest{

    private IntercityOfferRequestDTO offerRequest = new IntercityOfferRequestDTO();
    private Response response = null;

    @Given("^offer request for trip from (.+) to (.+)$")
    public void createOfferRequestWithDepAndDestCities(String depCity, String destCity) {
        offerRequest.setDepCity(depCity);
        offerRequest.setDestCity(destCity);
    }

    @And("^add passenger type (.+)$")
    public void addPassenger(String passengerType) {
        offerRequest.setPassenger(passengerType);
    }

    @And("^add company name (.+)$")
    public void addCompanyName(String companyName) {
        offerRequest.setCompanyName(companyName);
    }

    @And("^add place type (.+)$")
    public void addPlaceType(String placeType) {
        offerRequest.setPlaceType(placeType);
    }

    @And("^set number of tickets to (\\d+)$")
    public void addNumberOfTickets(Integer numberOfTickets) {
        offerRequest.setNumberOfTickets(numberOfTickets);
    }

    @And("^set number of luggage to (\\d+)$")
    public void addNumberOfLuggage(Integer numberOfLuggage) {
        offerRequest.setNumberOfLuggage(numberOfLuggage);
    }

    @When("^call create offers$")
    public void callCreateOffers() {
        response = RestAssured.given()
                .when()
                .contentType("application/json")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRlc3RVc2VyMSIsInVzZXJfaWQiOiIxMjM0MTI1IiwiY29udHJhY3RfbW9kZWwiOiJjb250cmFjdF8xIiwicm9sZSI6IlJFR1VMQVJfVVNFUiIsInRvdWNocG9pbnQiOiJXRUIifQ.vwqvyEYcuZdlmt65QfjwhhZeURNEigCWqfJvGS-kWZA")
                .body(offerRequest)
                .port(8084)
                .post("/v1/intercity/");
        response.then().log().all();
    }

    @And("^add transport type (.+)$")
    public void addTransportType(String transportType) {
        offerRequest.setTransportType(transportType);
    }

    @And("^set departure time to (today|after 1 hour|tomorrow|after 5 days)$")
    public void setDepTime(String depTime) {
        ZonedDateTime departureTime;
        switch (depTime) {
            case "today":
                departureTime = ZonedDateTime.now();
                break;
            case "after 1 hour":
                departureTime = ZonedDateTime.now().plusHours(1);
                break;
            case "tomorrow":
                departureTime = ZonedDateTime.now().plusDays(1);
                break;
            default:
                departureTime = ZonedDateTime.now().plusDays(5);
                break;
        }

        offerRequest.setDepartureTime(departureTime);
    }

    @Then("^response status is (.+)$")
    public void responseStatusIs(String responseStatus) {
        response.then().statusCode(HttpStatus.valueOf(responseStatus).value());
    }

    @And("^response has (\\d+) offers?$")
    public void responseHasTicket(int ticketCount) {
        response.then().body("ticketId", Matchers.hasSize(ticketCount));
    }

    @And("^(\\d+) offer has route from (.+) to (.+)$")
    public void ticketHasRoute(int ticketNumber, String depCity, String arrCity) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".depCity", Matchers.equalTo(depCity));
        response.then().body(pathForRequestedTicket + ".destCity", Matchers.equalTo(arrCity));
    }

    @And("^(\\d+) offer id is not null$")
    public void offerIdAssertions(int ticketNumber) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".id", Matchers.notNullValue());
    }

    @And("^(\\d+) offer has discount value (.+)$")
    public void discountAssertions(int ticketNumber, String discount) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".discount", Matchers.equalTo(discount));
    }
}
