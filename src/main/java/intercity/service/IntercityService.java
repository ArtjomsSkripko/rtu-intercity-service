package intercity.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import intercity.authorization.UserToken;
import intercity.enumeration.PassengerTypeEnum;
import intercity.model.IntercityOffer;
import intercity.model.IntercityOfferRequest;
import intercity.repository.IntercityRepository;

import static intercity.service.UserRoleEnum.COMPANY_USER;
import static intercity.service.UserRoleEnum.CONDUCTOR;

@Service
public class IntercityService {

    private IntercityRepository repository;

    @Autowired
    public IntercityService(IntercityRepository repository) {
        this.repository = repository;
    }

    public List<IntercityOffer> createOffers(IntercityOfferRequest request, UserToken token) {

        BigDecimal totalDiscount = repository.getApplicationSpecificDiscount(token.getApplicationType(), token.getApplicationName());

        BigDecimal companyUserSpecificDiscount = new BigDecimal(1.0);
        if (COMPANY_USER.name().equals(token.getUserRole())) {
            companyUserSpecificDiscount = repository.getCompanyUserSpecificDiscount(token.getCompanyName());
        }

        if (CONDUCTOR.name().equals(token.getUserRole())) {
            totalDiscount = totalDiscount.multiply(new BigDecimal(1.3));
        }
        List<IntercityOffer> offers = repository.fetchOffersData(request);
        for (IntercityOffer offer : offers) {
            offer.setPassenger(request.getPassenger());
            BigDecimal originalPrice = offer.getOriginalPrice();

            String companyName = offer.getCompanyName();
            Pair<PassengerTypeEnum, BigDecimal> passengerSpecificDiscount
                    = repository.getPassengerSpecificDiscount(request.getPassenger(), companyName);

            totalDiscount = totalDiscount.multiply(passengerSpecificDiscount.getRight());

            BigDecimal priceWithDiscount = originalPrice.multiply(totalDiscount);
            if (StringUtils.isNotEmpty(token.getCompanyName()) && companyName.equals(token.getCompanyName())) {
                priceWithDiscount = priceWithDiscount.multiply(companyUserSpecificDiscount);
                totalDiscount = totalDiscount.multiply(companyUserSpecificDiscount);
            }

            offer.setDiscount(totalDiscount.setScale(2, BigDecimal.ROUND_HALF_EVEN));
            priceWithDiscount = priceWithDiscount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            offer.setPriceWithDiscount(priceWithDiscount);
        }

        return offers;
    }
}
