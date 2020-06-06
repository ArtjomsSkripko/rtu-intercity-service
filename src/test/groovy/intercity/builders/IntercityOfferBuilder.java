package intercity.builders;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import intercity.enumeration.PassengerTypeEnum;
import intercity.enumeration.TransportTypeEnum;
import intercity.model.IntercityOffer;
import intercity.rest.model.IntercityOfferDTO;

public class IntercityOfferBuilder {

    private IntercityOffer template;

    public IntercityOfferBuilder() {
        this.template = new IntercityOffer();
    }

    public IntercityOfferBuilder depCity(String depCity) {
        template.setDepCity(depCity);
        return this;
    }

    public IntercityOfferBuilder destCity(String destCity) {
        template.setDepCity(destCity);
        return this;
    }

    public IntercityOfferBuilder depTime(ZonedDateTime depTime) {
        template.setDepTime(depTime);
        return this;
    }

    public IntercityOfferBuilder numberOfLuggage(Integer numberOfLuggage) {
        template.setNumberOfLuggage(numberOfLuggage);
        return this;
    }

    public IntercityOfferBuilder passenger(PassengerTypeEnum passenger) {
        template.setPassenger(passenger);
        return this;
    }

    public IntercityOfferBuilder placeType(String placeType) {
        template.setPlaceType(placeType);
        return this;
    }

    public IntercityOfferBuilder priceWithDiscount(BigDecimal priceWithDiscount) {
        template.setPriceWithDiscount(priceWithDiscount);
        return this;
    }

    public IntercityOfferBuilder originalPrice(BigDecimal originalPrice) {
        template.setOriginalPrice(originalPrice);
        return this;
    }

    public IntercityOfferBuilder taxRate(BigDecimal taxRate) {
        template.setTaxRate(taxRate);
        return this;
    }

    public IntercityOfferBuilder discount(BigDecimal discount) {
        template.setDiscount(discount);
        return this;
    }

    public IntercityOfferBuilder transportType(TransportTypeEnum transportType) {
        template.setTransportType(transportType);
        return this;
    }

    public IntercityOfferBuilder companyName(String companyName) {
        template.setCompanyName(companyName);
        return this;
    }

    public IntercityOfferBuilder id(String id) {
        template.setId(id);
        return this;
    }

    public IntercityOfferBuilder withDefaults() {
        return this
                .depCity("RIGA")
                .destCity("DAUGAVPILS")
                .depTime(ZonedDateTime.of(2020, 5, 5, 12, 0, 0, 0, ZoneId.systemDefault()))
                .passenger(PassengerTypeEnum.ADULT)
                .placeType("ECO")
                .numberOfLuggage(0)
                .taxRate(new BigDecimal(23.00))
                .originalPrice(new BigDecimal(10.10))
                .priceWithDiscount(new BigDecimal(10.10))
                .discount(new BigDecimal(1.0))
                .transportType(TransportTypeEnum.BUS)
                .companyName("RIGAS_SATIKSME")
                .id("offerId1")
                ;
    }
    public IntercityOffer build() {
        return template;
    }
}
