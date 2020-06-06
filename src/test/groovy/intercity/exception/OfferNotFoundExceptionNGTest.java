package intercity.exception;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class OfferNotFoundExceptionNGTest {

    @Test
    public void testConstructorWithMessage() {
        String msg = "No offer found in DB";
        OfferNotFoundException offerNotFoundException = new OfferNotFoundException(msg);
        assertEquals(offerNotFoundException.getMessage(), msg);
    }
}
