package intercity.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import intercity.builders.IntercityOfferBuilder;
import intercity.builders.IntercityOfferRequestBuilder;
import intercity.model.IntercityOffer;
import intercity.model.IntercityOfferRequest;
import intercity.repository.IntercityRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import intercity.authorization.UserToken;
import intercity.builders.UserTokenBuilder;
import intercity.enumeration.PassengerTypeEnum;
import intercity.exception.OfferNotFoundException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static intercity.service.UserRoleEnum.CONDUCTOR;

public class IntercityServiceTest {

    private static IntercityService service;
    private static IntercityRepository repository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @BeforeClass
    public static void initiate() {
        repository = mock(IntercityRepository.class);
        service = new IntercityService(repository);
    }

    @Before
    public void setup(){
        reset(repository);
    }

    @Test
    public void testGetOffersForRegularUser() {
        IntercityOfferRequest request = new IntercityOfferRequestBuilder().withDefaults().build();
        UserToken token = new UserTokenBuilder().withDefaults().build();

        Mockito.when(repository.getApplicationSpecificDiscount(anyString(), anyString()))
                .thenReturn(new BigDecimal(0.9));
        Mockito.when(repository.getPassengerSpecificDiscount(any(PassengerTypeEnum.class), anyString()))
                .thenReturn(Pair.of(PassengerTypeEnum.SCHOLAR, new BigDecimal(0.5)));
        Mockito.when(repository.fetchOffersData(any()))
                .thenReturn(Collections.singletonList(new IntercityOfferBuilder().withDefaults().build()));

        List<IntercityOffer> results = service.createOffers(request, token);

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(results.get(0).getDiscount(),
                new BigDecimal(0.45).setScale(2, BigDecimal.ROUND_HALF_EVEN));

    }

    @Test(expected = OfferNotFoundException.class)
    public void testGetOffersForRegularUserWhenOfferNotFoundException() {
        IntercityOfferRequest request = new IntercityOfferRequestBuilder().withDefaults().build();
        UserToken token = new UserTokenBuilder().withDefaults().build();

        Mockito.when(repository.getApplicationSpecificDiscount(anyString(), anyString()))
                .thenReturn(new BigDecimal(0.9));
        Mockito.when(repository.getPassengerSpecificDiscount(any(PassengerTypeEnum.class), anyString()))
                .thenReturn(Pair.of(PassengerTypeEnum.SCHOLAR, new BigDecimal(0.5)));
        Mockito.when(repository.fetchOffersData(any()))
                .thenThrow(new OfferNotFoundException("offer not found"));

        service.createOffers(request, token);
    }

    @Test
    public void testGetOffersForConductorUser() {
        IntercityOfferRequest request = new IntercityOfferRequestBuilder().withDefaults().build();
        UserToken token = new UserTokenBuilder().withDefaults().role(CONDUCTOR.name()).build();
        IntercityOffer offer = new IntercityOfferBuilder().withDefaults().build();

        Mockito.when(repository.getApplicationSpecificDiscount(anyString(), anyString())).thenReturn(new BigDecimal(0.9));
        Mockito.when(repository.getPassengerSpecificDiscount(any(PassengerTypeEnum.class), anyString()))
                .thenReturn(Pair.of(PassengerTypeEnum.SCHOLAR, new BigDecimal(0.5)));
        Mockito.when(repository.fetchOffersData(any())).thenReturn(Collections.singletonList(offer));

        List<IntercityOffer> results = service.createOffers(request, token);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(results.get(0).getDiscount(), new BigDecimal(0.59).setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }
}
