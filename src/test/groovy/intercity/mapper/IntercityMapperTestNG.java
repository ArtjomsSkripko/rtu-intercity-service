package intercity.mapper;

import intercity.builders.IntercityOfferBuilder;
import intercity.builders.IntercityOfferRequestDTOBuilder;
import intercity.model.IntercityOffer;
import intercity.model.IntercityOfferRequest;
import intercity.rest.model.IntercityOfferDTO;
import intercity.rest.model.IntercityOfferRequestDTO;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IntercityMapperTestNG {

    private IntercityMapper mapper = new IntercityMapper();

    @Test
    public void testToOfferDTO() {
        IntercityOffer offer = new IntercityOfferBuilder().withDefaults().build();

        IntercityOfferDTO result = mapper.toDTOOffer(offer);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getDepCity(), offer.getDepCity());
        Assert.assertEquals(result.getDepTime(), offer.getDepTime());
        Assert.assertEquals(result.getDestCity(), offer.getDestCity());
        Assert.assertEquals(result.getCompanyName(), offer.getCompanyName());
        Assert.assertEquals(result.getId(), offer.getId());
        Assert.assertEquals(result.getNumberOfTickets(), offer.getNumberOfTickets());
    }

    @Test
    public void testToDomainRequest() {
        IntercityOfferRequestDTO requestDTO = new IntercityOfferRequestDTOBuilder().withDefaults().build();

        IntercityOfferRequest result = mapper.toDomainRequest(requestDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getDepCity(), requestDTO.getDepCity());
        Assert.assertEquals(result.getDepartureTime(), requestDTO.getDepartureTime());
        Assert.assertEquals(result.getDestCity(), requestDTO.getDestCity());
        Assert.assertEquals(result.getCompanyName(), requestDTO.getCompanyName());
        Assert.assertEquals(result.getNumberOfTickets(), requestDTO.getNumberOfTickets());
    }

}
