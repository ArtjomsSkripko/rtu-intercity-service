package intercity.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import intercity.enumeration.PassengerTypeEnum;
import intercity.enumeration.TransportTypeEnum;

public class IntercityOffer {

    private String id;
    private Integer numberOfTickets;
    private String companyName;
    private PassengerTypeEnum passenger;
    private TransportTypeEnum transportType;
    private String destCity;
    private String depCity;
    private Integer numberOfLuggage;
    private String placeType;
    private BigDecimal discount;
    private BigDecimal originalPrice;
    private BigDecimal priceWithDiscount;
    private BigDecimal taxRate;
    private ZonedDateTime depTime;

    public IntercityOffer() {
        // default constructor
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

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

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getDepTime() {
        return depTime;
    }

    public void setDepTime(ZonedDateTime depTime) {
        this.depTime = depTime;
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

        IntercityOffer castOther = (IntercityOffer) other;

        return new EqualsBuilder()
                .append(id, castOther.id)
                .append(numberOfTickets, castOther.numberOfTickets)
                .append(companyName, castOther.companyName)
                .append(passenger, castOther.passenger)
                .append(taxRate, castOther.taxRate)
                .append(transportType, castOther.transportType)
                .append(destCity, castOther.destCity)
                .append(depCity, castOther.depCity)
                .append(numberOfLuggage, castOther.numberOfLuggage)
                .append(placeType, castOther.placeType)
                .append(discount, castOther.discount)
                .append(originalPrice, castOther.originalPrice)
                .append(priceWithDiscount, castOther.priceWithDiscount)
                .append(depTime, castOther.depTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(numberOfTickets)
                .append(companyName)
                .append(passenger)
                .append(taxRate)
                .append(transportType)
                .append(destCity)
                .append(depCity)
                .append(numberOfLuggage)
                .append(placeType)
                .append(discount)
                .append(originalPrice)
                .append(priceWithDiscount)
                .append(depTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("numberOfTickets", numberOfTickets)
                .append("companyName", companyName)
                .append("passenger", passenger)
                .append("taxRate", taxRate)
                .append("transportType", transportType)
                .append("destCity", destCity)
                .append("depCity", depCity)
                .append("numberOfLuggage", numberOfLuggage)
                .append("placeType", placeType)
                .append("discount", discount)
                .append("originalPrice", originalPrice)
                .append("priceWithDiscount", priceWithDiscount)
                .append("depTime", depTime)
                .toString();
    }
}
