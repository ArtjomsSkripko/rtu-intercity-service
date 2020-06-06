package intercity.rest

import intercity.exception.UnauthorizedException
import intercity.mapper.IntercityMapper
import intercity.rest.model.IntercityOfferRequestDTO
import intercity.service.IntercityService
import spock.lang.Specification

class IntercityControllerSpockTest extends Specification {

    IntercityService service
    IntercityMapper mapper
    IntercityOfferController controller

    def setup() {
        service = Mock(IntercityService.class)
        mapper = Mock(IntercityMapper.class)
        controller = new IntercityOfferController(service, mapper)
    }

    void "call create offer unauthorized"() {
        given:
        IntercityOfferRequestDTO request = new IntercityOfferRequestDTO()
        when:
        controller.createRegionalOffers(request)
        then:
        thrown(UnauthorizedException.class)
    }
}
