package intercity.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import intercity.enumeration.TransportTypeEnum;
import intercity.model.IntercityOffer;

import static intercity.repository.IntercityRepository.DEP_CITY_COLUMN;
import static intercity.repository.IntercityRepository.COMPANY_NAME_COLUMN;
import static intercity.repository.IntercityRepository.DEP_TIME_COLUMN;
import static intercity.repository.IntercityRepository.DEST_CITY_COLUMN;
import static intercity.repository.IntercityRepository.PLACE_TYPE_COLUMN;
import static intercity.repository.IntercityRepository.PRICE_WITH_VAT_COLUMN;
import static intercity.repository.IntercityRepository.TAX_RATE_COLUMN;
import static intercity.repository.IntercityRepository.TRANSPORT_TYPE_COLUMN;

public class OfferExtractor implements ResultSetExtractor<List<IntercityOffer>> {

    @Override
    public List<IntercityOffer> extractData(ResultSet rs) throws SQLException {
        List<IntercityOffer> list = new ArrayList<>();
        while(rs.next()) {
            IntercityOffer offer = new IntercityOffer();
            offer.setId(UUID.randomUUID().toString());
            offer.setDepCity(rs.getString(DEP_CITY_COLUMN));
            offer.setDestCity(rs.getString(DEST_CITY_COLUMN));
            offer.setCompanyName(rs.getString(COMPANY_NAME_COLUMN));
            offer.setTaxRate(rs.getBigDecimal(TAX_RATE_COLUMN));
            offer.setOriginalPrice(rs.getBigDecimal(PRICE_WITH_VAT_COLUMN));
            offer.setTransportType(TransportTypeEnum.valueOf(rs.getString(TRANSPORT_TYPE_COLUMN)));
            offer.setPlaceType(rs.getString(PLACE_TYPE_COLUMN));
            offer.setDepTime(ZonedDateTime.ofInstant(rs.getTimestamp(DEP_TIME_COLUMN).toInstant(), ZoneId.systemDefault()));
            list.add(offer);
        }

        return list;
    }
}
