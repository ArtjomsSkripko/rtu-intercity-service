package intercity.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import intercity.enumeration.PassengerTypeEnum;
import intercity.exception.OfferNotFoundException;
import intercity.model.IntercityOffer;
import intercity.model.IntercityOfferRequest;

@Repository
public class IntercityRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(IntercityRepository.class);

    private NamedParameterJdbcOperations namedJdbcTemplate;
    private OfferExtractor offerExtractor = new OfferExtractor();
    public static String COMPANY_NAME_COLUMN = "company_name";
    public static String PLACE_TYPE_COLUMN = "place_type";
    public static String NUMBER_OF_TICKETS = "ticket_num";
    public static String TRANSPORT_TYPE_COLUMN = "transport_type";
    public static String DEP_CITY_COLUMN = "dep_city";
    public static String DEST_CITY_COLUMN = "dest_city";
    public static String DEP_TIME_COLUMN = "dep_time";
    public static String DEP_TIME_TILL = "dep_time_till";
    public static String TAX_RATE_COLUMN = "tax_rate";
    public static String PRICE_WITH_VAT_COLUMN = "price";

    @Autowired
    public IntercityRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate) {
        this.namedJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Pair<PassengerTypeEnum, BigDecimal> getPassengerSpecificDiscount(PassengerTypeEnum passengerType, String companyName) {
        String sqlQuery = "select discount from intercity_discount where passenger_type = '"
                + passengerType.name() + "' and company_name = '" + companyName + "'";
        Double foundDiscount = null;
        try {
            foundDiscount = namedJdbcTemplate.queryForObject(sqlQuery, new HashMap<>(), Double.class);
        } catch (Exception e) {
            LOGGER.error("No passenger discount found");
        }

        return Pair.of(passengerType, new BigDecimal(foundDiscount == null ? 1.0 : foundDiscount));
    }

    public BigDecimal getApplicationSpecificDiscount(String applicationType, String applicationName) {
        String sqlQuery = "select discount from application_discount where app_type = '"
                + applicationType + "' and app_name = '" + applicationName + "'";
        Double foundDiscount = null;
        try {
            foundDiscount = namedJdbcTemplate.queryForObject(sqlQuery, new HashMap<>(), Double.class);
        } catch (Exception e) {
            LOGGER.error("No application discount found");
        }

        return foundDiscount != null ? new BigDecimal(foundDiscount) : new BigDecimal(1.0);
    }

    public BigDecimal getCompanyUserSpecificDiscount(String companyName) {
        String sqlQuery = "select discount from company_discount where company = '" + companyName + "'";
        Double foundDiscount = null;
        try {
            foundDiscount = namedJdbcTemplate.queryForObject(sqlQuery, new HashMap<>(), Double.class);
        } catch (Exception e) {
            LOGGER.error("No company discount found");
        }

        return foundDiscount != null ? new BigDecimal(foundDiscount) : new BigDecimal(1.0);
    }

    public List<IntercityOffer> fetchOffersData(IntercityOfferRequest request) {
        StringBuilder queryBuilder = new StringBuilder("select * from intercity o");

        List<String> filterCriteria = new ArrayList<>();
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        if(request.getCompanyName() != null) {
            parameters.addValue(COMPANY_NAME_COLUMN, request.getCompanyName());
            filterCriteria.add(createCriteriaString("o", COMPANY_NAME_COLUMN));
        }

        parameters.addValue(PLACE_TYPE_COLUMN, request.getPlaceType());
        filterCriteria.add(createCriteriaString("o", PLACE_TYPE_COLUMN));

        parameters.addValue(TRANSPORT_TYPE_COLUMN, request.getTransportType().name());
        filterCriteria.add(createCriteriaString("o", TRANSPORT_TYPE_COLUMN));

        parameters.addValue(DEP_CITY_COLUMN, request.getDepCity());
        filterCriteria.add(createCriteriaString("o", DEP_CITY_COLUMN));

        parameters.addValue(DEST_CITY_COLUMN, request.getDestCity());
        filterCriteria.add(createCriteriaString("o", DEST_CITY_COLUMN));

        parameters.addValue(DEP_TIME_COLUMN, Timestamp.from(request.getDepartureTime().toInstant()));
        filterCriteria.add("o." + DEP_TIME_COLUMN + " >= :" + DEP_TIME_COLUMN);

        parameters.addValue(DEP_TIME_TILL, Timestamp.from(request.getDepartureTime().plusDays(1).toInstant()));
        filterCriteria.add("o." + DEP_TIME_COLUMN + " <= :" + DEP_TIME_TILL);
        parameters.addValue(NUMBER_OF_TICKETS, request.getNumberOfTickets());
        filterCriteria.add("o." + NUMBER_OF_TICKETS + " >= :" + NUMBER_OF_TICKETS);
        if (!CollectionUtils.isEmpty(filterCriteria)) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(String.join(" AND ", filterCriteria));
        }

        List<IntercityOffer> response = namedJdbcTemplate.query(queryBuilder.toString(), parameters, offerExtractor);

        if (CollectionUtils.isEmpty(response)) {
            throw new OfferNotFoundException("No offers found for current parameter");
        }

        response.forEach(offer -> {
            Integer numberOfTickets = request.getNumberOfTickets();
            offer.setNumberOfTickets(numberOfTickets);
            BigDecimal originalPrice = offer.getOriginalPrice();
            offer.setOriginalPrice(originalPrice.multiply(new BigDecimal(numberOfTickets)));
        });

        return response;
    }

    private String createCriteriaString(String table, String column) {
        return table + "." + column + " = :" + column;
    }
}
