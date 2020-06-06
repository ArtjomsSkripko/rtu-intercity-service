package intercity.rest;

import java.util.List;
import java.util.stream.Collectors;

import intercity.rest.model.IntercityOfferDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import intercity.authorization.UserToken;
import intercity.authorization.Utils;
import intercity.exception.UnauthorizedException;
import intercity.mapper.IntercityMapper;
import intercity.model.IntercityOffer;
import intercity.rest.model.IntercityOfferRequestDTO;
import intercity.service.IntercityService;

@RestController
@Validated
@Api(protocols = "http, https")
@RequestMapping(value = "v1/intercity")
public class IntercityOfferController {

    private IntercityService intercityService;
    private IntercityMapper mapper;

    @Autowired
    public IntercityOfferController(IntercityService intercityService, IntercityMapper mapper) {
        this.intercityService = intercityService;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(
            value = "Generates intercity offers",
            notes = "Generates intercity offers based on input criteria.",
            tags = {"Intercity offers"}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "InvalidRequestError, code 350: Invalid request"),
            @ApiResponse(code = 500, message = "SomeError")
    })
    public List<IntercityOfferDTO> createRegionalOffers(@RequestBody @Validated IntercityOfferRequestDTO requestDto) {
        UserToken token = Utils.getServiceUser();
        if (token == null) {
           throw new UnauthorizedException("current user is not authorized to fetch offers");
        }
        List<IntercityOffer> offers = intercityService.createOffers(mapper.toDomainRequest(requestDto), Utils.getServiceUser());
        return offers.stream().map(o -> mapper.toDTOOffer(o)).collect(Collectors.toList());
    }
}
