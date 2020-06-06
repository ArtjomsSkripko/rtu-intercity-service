package configs.steps.jbehave;

import java.time.ZonedDateTime;

import configs.steps.SpringIntegrationTest;
import intercity.rest.model.IntercityOfferRequestDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.http.HttpStatus;

public class JBehaveSteps extends SpringIntegrationTest {

    private IntercityOfferRequestDTO offerRequest = new IntercityOfferRequestDTO();
    private Response response = null;

    @Given("offer request for trip from $depCity to $destCity")
    public void createOfferRequestWithDepAndDestCities(String depCity, String destCity) {
        offerRequest.setDepCity(depCity);
        offerRequest.setDestCity(destCity);
    }

    @Given("add passenger type $passengerType")
    public void addPassenger(String passengerType) {
        offerRequest.setPassenger(passengerType);
    }

    @Given("add company name $companyName")
    public void addCompanyName(String companyName) {
        offerRequest.setCompanyName(companyName);
    }

    @Given("add place type $placeType")
    public void addPlaceType(String placeType) {
        offerRequest.setPlaceType(placeType);
    }

    @Given("set number of tickets to $numberOfTickets")
    public void addNumberOfTickets(Integer numberOfTickets) {
        offerRequest.setNumberOfTickets(numberOfTickets);
    }

    @Given("set number of luggage to $numberOfLuggage")
    public void addNumberOfLuggage(Integer numberOfLuggage) {
        offerRequest.setNumberOfLuggage(numberOfLuggage);
    }

    @When("call create offers")
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

    @Given("add transport type $transportType")
    public void addTransportType(String transportType) {
        offerRequest.setTransportType(transportType);
    }

    @Given("^set departure time to (today|after 1 hour|tomorrow|after 5 days)")
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

    @Then("response status is $responseStatus")
    public void responseStatusIs(String responseStatus) {
        response.then().statusCode(HttpStatus.valueOf(responseStatus).value());
    }

    @Then("response has $ticketCount offer")
    public void responseHasTicket(int ticketCount) {
        response.then().body("ticketId", Matchers.hasSize(ticketCount));
    }

    @Then("$ticketNumber offer has route from $depCity to $arrCity$")
    public void ticketHasRoute(int ticketNumber, String depCity, String arrCity) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".depCity", Matchers.equalTo(depCity));
        response.then().body(pathForRequestedTicket + ".destCity", Matchers.equalTo(arrCity));
    }

    @Then("$ticketNumber offer id is not null")
    public void offerIdAssertions(int ticketNumber) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".id", Matchers.notNullValue());
    }

    @Then("$ticketNumber offer has discount value $discount")
    public void discountAssertions(int ticketNumber, String discount) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".discount", Matchers.equalTo(discount));
    }

}
