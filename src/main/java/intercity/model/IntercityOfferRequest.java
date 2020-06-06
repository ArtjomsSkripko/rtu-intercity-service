package intercity.model;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import intercity.enumeration.PassengerTypeEnum;
import intercity.enumeration.TransportTypeEnum;

public class IntercityOfferRequest {

    private Integer numberOfTickets;
    private String companyName;
    private PassengerTypeEnum passenger;
    private TransportTypeEnum transportType;
    private String destCity;
    private String depCity;
    private Integer numberOfLuggage;
    private String placeType;
    private ZonedDateTime departureTime;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public PassengerTypeEnum getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerTypeEnum passenger) {
        this.passenger = passenger;
    }


    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public TransportTypeEnum getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportTypeEnum transportType) {
        this.transportType = transportType;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public Integer getNumberOfLuggage() {
        return numberOfLuggage;
    }

    public void setNumberOfLuggage(Integer numberOfLuggage) {
        this.numberOfLuggage = numberOfLuggage;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /* Equals & HashCode */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        IntercityOfferRequest castOther = (IntercityOfferRequest) other;

        return new EqualsBuilder()
                .append(numberOfTickets, castOther.numberOfTickets)
                .append(companyName, castOther.companyName)
                .append(passenger, castOther.passenger)
                .append(transportType, castOther.transportType)
                .append(destCity, castOther.destCity)
                .append(numberOfLuggage, castOther.numberOfLuggage)
                .append(placeType, castOther.placeType)
                .append(depCity, castOther.depCity)
                .append(departureTime, castOther.departureTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(numberOfTickets)
                .append(companyName)
                .append(passenger)
                .append(transportType)
                .append(destCity)
                .append(numberOfLuggage)
                .append(placeType)
                .append(depCity)
                .append(departureTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("numberOfTickets", numberOfTickets)
                .append("companyName", companyName)
                .append("transportType", transportType)
                .append("destCity", destCity)
                .append("numberOfLuggage", numberOfLuggage)
                .append("placeType", placeType)
                .append("depCity", depCity)
                .append("departureTime", departureTime)
                .toString();
    }
}
