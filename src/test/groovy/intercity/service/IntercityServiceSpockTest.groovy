package intercity.service

import intercity.authorization.UserToken
import intercity.builders.IntercityOfferBuilder
import intercity.builders.IntercityOfferRequestBuilder
import intercity.builders.UserTokenBuilder
import intercity.exception.OfferNotFoundException
import intercity.model.IntercityOffer
import intercity.model.IntercityOfferRequest
import intercity.repository.IntercityRepository
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class IntercityServiceSpockTest extends Specification {

    IntercityService service
    IntercityRepository repository

    def setup() {
        repository = Mock(IntercityRepository.class)
        service = new IntercityService(repository)
    }

    void "create offers as regular user"() {
        given:
        IntercityOfferRequest request = new IntercityOfferRequestBuilder().withDefaults().build()
        UserToken token = new UserTokenBuilder().withDefaults().build()
        and:
        repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >> new BigDecimal(0.9)
        and:
        repository.getPassengerSpecificDiscount(request.passenger, request.companyName) >> Pair.of(request.passenger, new BigDecimal(0.5))
        and:
        IntercityOffer offer = new IntercityOfferBuilder().withDefaults().build()
        repository.fetchOffersData(request) >> [offer]
        when:
        List<IntercityOffer> results = service.createOffers(request, token)
        then:
        results != null
        results.get(0).discount == 0.45
        1 * repository.getPassengerSpecificDiscount(request.passenger, request.companyName) >>
                Pair.of(request.passenger, new BigDecimal(0.5))
        1 * repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >>
                new BigDecimal(0.9)
    }

    void "create offers as regular user with offer not found error"() {
        given:
        IntercityOfferRequest request = new IntercityOfferRequestBuilder().withDefaults().build()
        UserToken token = new UserTokenBuilder().withDefaults().build()
        and:
        repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >> new BigDecimal(0.9)
        and:
        repository.getPassengerSpecificDiscount(request.passenger, request.companyName) >> Pair.of(request.passenger, new BigDecimal(0.5))
        and:
        repository.fetchOffersData(request) >> { IntercityOfferRequest offerRequest -> throw new OfferNotFoundException("offer not found")}
        when:
        service.createOffers(request, token)
        then:
        thrown(OfferNotFoundException)
    }

    void "create offers as conductor user"() {
        given:
        IntercityOfferRequest request = new IntercityOfferRequestBuilder().withDefaults().build()
        UserToken token = new UserTokenBuilder().withDefaults().role(UserRoleEnum.CONDUCTOR.name()).build()
        and:
        repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >> new BigDecimal(0.9)
        and:
        repository.getPassengerSpecificDiscount(request.passenger, request.companyName) >> Pair.of(request.passenger, new BigDecimal(0.5))
        and:
        IntercityOffer offer = new IntercityOfferBuilder().withDefaults().build()
        IntercityOffer offer2 = new IntercityOfferBuilder().withDefaults().build()
        repository.fetchOffersData(request) >> [offer,offer2]
        when:
        List<IntercityOffer> results = service.createOffers(request, token)
        then:
        results != null
        results.size() == 2
        notThrown(OfferNotFoundException.class)
        verifyAll {
            results[0].discount == 0.59
            results[1].discount == 0.29
        }
        2 * repository.getPassengerSpecificDiscount(request.passenger, request.companyName) >>
                Pair.of(request.passenger, new BigDecimal(0.5))
        1 * repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >>
                new BigDecimal(0.9)
    }
}
