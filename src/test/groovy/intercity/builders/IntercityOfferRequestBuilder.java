package intercity.builders;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import intercity.enumeration.PassengerTypeEnum;
import intercity.enumeration.TransportTypeEnum;
import intercity.model.IntercityOfferRequest;

public class IntercityOfferRequestBuilder {

    private IntercityOfferRequest template;

    public IntercityOfferRequestBuilder() {
        this.template = new IntercityOfferRequest();
    }

    public IntercityOfferRequestBuilder depCity(String depCity) {
        template.setDepCity(depCity);
        return this;
    }

    public IntercityOfferRequestBuilder destCity(String destCity) {
        template.setDepCity(destCity);
        return this;
    }

    public IntercityOfferRequestBuilder depTime(ZonedDateTime depTime) {
        template.setDepartureTime(depTime);
        return this;
    }

    public IntercityOfferRequestBuilder numberOfLuggage(Integer numberOfLuggage) {
        template.setNumberOfLuggage(numberOfLuggage);
        return this;
    }

    public IntercityOfferRequestBuilder passenger(PassengerTypeEnum passenger) {
        template.setPassenger(passenger);
        return this;
    }

    public IntercityOfferRequestBuilder placeType(String placeType) {
        template.setPlaceType(placeType);
        return this;
    }

    public IntercityOfferRequestBuilder transportType(TransportTypeEnum transportType) {
        template.setTransportType(transportType);
        return this;
    }

    public IntercityOfferRequestBuilder companyName(String companyName) {
        template.setCompanyName(companyName);
        return this;
    }

    public IntercityOfferRequestBuilder withDefaults() {
        return this
                .depCity("RIGA")
                .destCity("DAUGAVPILS")
                .depTime(ZonedDateTime.of(2020, 5, 5, 12, 0, 0, 0, ZoneId.systemDefault()))
                .passenger(PassengerTypeEnum.ADULT)
                .placeType("ECO")
                .numberOfLuggage(0)
                .transportType(TransportTypeEnum.BUS)
                .companyName("RIGAS_SATIKSME")
                ;
    }

    public IntercityOfferRequest build() {
        return template;
    }
}
