package intercity.mapper;

import java.util.stream.Collectors;

import intercity.rest.model.IntercityOfferDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import intercity.enumeration.OfferTypeEnum;
import intercity.enumeration.PassengerTypeEnum;
import intercity.enumeration.TransportTypeEnum;
import intercity.model.IntercityOffer;
import intercity.model.IntercityOfferRequest;
import intercity.rest.model.IntercityOfferRequestDTO;

@Component
public class IntercityMapper {

    public IntercityOfferDTO toDTOOffer(IntercityOffer offer) {
        IntercityOfferDTO dto = new IntercityOfferDTO();

        dto.setCompanyName(offer.getCompanyName());
        dto.setId(offer.getId());
        dto.setDepCity(offer.getDepCity());
        dto.setDestCity(offer.getDestCity());
        dto.setTransportType(offer.getTransportType().name());
        dto.setNumberOfTickets(offer.getNumberOfTickets());
        dto.setNumberOfLuggage(offer.getNumberOfLuggage());
        dto.setPassenger(offer.getPassenger().name());
        dto.setTaxRate(offer.getTaxRate().toString());
        dto.setDiscount(offer.getDiscount().toString());
        dto.setOriginalPrice(offer.getOriginalPrice().toString());
        dto.setPriceWithDiscount(offer.getPriceWithDiscount().toString());
        dto.setPlaceType(offer.getPlaceType());
        dto.setDepTime(offer.getDepTime());

        return dto;
    }

    public IntercityOfferRequest toDomainRequest(IntercityOfferRequestDTO requestDTO) {
        IntercityOfferRequest request = new IntercityOfferRequest();

        request.setCompanyName(requestDTO.getCompanyName());
        request.setDepartureTime(requestDTO.getDepartureTime());
        request.setDepCity(requestDTO.getDepCity());
        request.setDestCity(requestDTO.getDestCity());
        request.setTransportType(TransportTypeEnum.valueOf(requestDTO.getTransportType()));
        request.setNumberOfTickets(requestDTO.getNumberOfTickets());
        request.setNumberOfLuggage(requestDTO.getNumberOfLuggage());
        request.setPassenger(PassengerTypeEnum.valueOf(requestDTO.getPassenger()));
        request.setPlaceType(requestDTO.getPlaceType());

        return request;
    }


}
