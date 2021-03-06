package intercity.rest;

import intercity.mapper.IntercityMapper;
import intercity.rest.model.IntercityOfferRequestDTO;
import intercity.service.IntercityService;
import org.junit.BeforeClass;
import org.junit.Test;
import intercity.exception.UnauthorizedException;

import static org.mockito.Mockito.mock;

public class IntercityControllerTest {

    private static IntercityService service;
    private static IntercityMapper mapper;
    private static IntercityOfferController controller;

    @BeforeClass
    public static void initiate() {
        mapper = mock(IntercityMapper.class);
        service = mock(IntercityService.class);
        controller = new IntercityOfferController(service, mapper);
    }

    @Test(expected = UnauthorizedException.class)
    public void testGetOffersUnauthorized() {
        IntercityOfferRequestDTO request = new IntercityOfferRequestDTO();
        controller.createRegionalOffers(request);
    }

}
