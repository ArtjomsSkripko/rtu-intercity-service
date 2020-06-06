package intercity.mapper

import intercity.builders.IntercityOfferBuilder
import intercity.builders.IntercityOfferRequestDTOBuilder
import intercity.model.IntercityOffer
import intercity.model.IntercityOfferRequest
import intercity.rest.model.IntercityOfferDTO
import intercity.rest.model.IntercityOfferRequestDTO
import spock.lang.Specification

class IntercityMapperTestSpock extends Specification {

    private IntercityMapper mapper = new IntercityMapper()

    void "testing toOfferDTO method"() {
        given:
        IntercityOffer offer = new IntercityOfferBuilder().withDefaults().build()

        when:
        IntercityOfferDTO result = mapper.toDTOOffer(offer)

        then:
        result != null

        result.getDepCity() == offer.getDepCity()
        result.getDepTime() == offer.getDepTime()
        result.getDestCity() == offer.getDestCity()
        result.getCompanyName() == offer.getCompanyName()
        result.getId() == offer.getId()
        result.getNumberOfTickets() == offer.getNumberOfTickets()
    }

    void "testing ToDomainRequest method"() {
        given:
        IntercityOfferRequestDTO requestDTO = new IntercityOfferRequestDTOBuilder().withDefaults().build()

        when:
        IntercityOfferRequest result = mapper.toDomainRequest(requestDTO)

        then:
        result != null
        result.getDepCity() == requestDTO.getDepCity()
        result.getDepartureTime() == requestDTO.getDepartureTime()
        result.getDestCity() == requestDTO.getDestCity()
        result.getCompanyName() == requestDTO.getCompanyName()
        result.getNumberOfTickets() == requestDTO.getNumberOfTickets()
    }
}
