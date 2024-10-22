package lab3.part_a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import java.lang.NullPointerException;

public class MovieTicketPriceCalculatorTests {

    /**
     * For all tests that require valid times and ages, they are as follows
     *      startMatineeTime = 8am
     *      endMatineeTime = 12pm
     *      maxChildAge = 13
     *      minSeniorAge = 60
     */
    private static final LocalTime validStartMatineeTime = LocalTime.of(8, 0);
    private static final LocalTime validEndMatineeTime = LocalTime.of(12, 0);
    private static final Integer validMaxChildAge = 13; 
    private static final Integer validMinSeniorAge = 60;    
    private static final MovieTicketPriceCalculator validCalculator = new MovieTicketPriceCalculator(validStartMatineeTime, validEndMatineeTime, validMaxChildAge, validMinSeniorAge);

    private static final Integer validAdultAge = 40;
    private static final Integer validSeniorAge = 70;
    private static final Integer validChildAge = 8;


    /**
     * Tests: When startMatineeTime is null an exception is thrown
     */
    @Test
    void calculatorTest_NullStartMatineeTime() {
        assertThrows(NullPointerException.class , () -> new MovieTicketPriceCalculator(null, validEndMatineeTime, validMaxChildAge, validMinSeniorAge));
    }

    /**
     * Tests: When endMatineeTime is null an exception is thrown
     */
    @Test
    void calculatorTest_NullEndMatineeTime() {
        assertThrows(NullPointerException.class , () -> new MovieTicketPriceCalculator(validStartMatineeTime, null, validMaxChildAge, validMinSeniorAge));

    }

    /**
     * Tests: When endMatineeTime is before startMatineeTime an exception is thrown
     */
    @Test
    void calculatorTest_EndMatineeIsBeforeStartMatinee() {
        assertThrows(IllegalArgumentException.class, () -> new MovieTicketPriceCalculator(validEndMatineeTime, validStartMatineeTime, validMaxChildAge, validMinSeniorAge));
    }

    /**
     * Tests: That the expected discount is applied
     */
    @Test
    void computeDiscountTest_Adult() {
        assertEquals(0, validCalculator.computeDiscount(validAdultAge));
    }

    /**
     * Tests: That the expected discount is applied
     */
    @Test
    void computeDiscountTest_Senior() {
        assertEquals(400, validCalculator.computeDiscount(validSeniorAge));
    }

    /**
     * Tests: That the expected discount is applied
     */
    @Test
    void computeDiscountTest_Child() {
        assertEquals(300, validCalculator.computeDiscount(validChildAge));
    }

    /**
     * The rest of the computeDiscount Tests are edge cases
     */

    /**
     * This should throw an error but doesn't as a result i will make it fail the test case
     */
    @Test
    void computeDiscountTest_NegativeAge() {
        assertFalse(0 == validCalculator.computeDiscount(-30));

    }
    /**
     * Tests: Invalid age applies no discount
     */
    @Test
    void computeDiscountTest_ZeroAge() {
        assertFalse(0 == validCalculator.computeDiscount(0));

    }
    
    /**
     * Tests: That adult price and matinee discounts are applied
     */
    @Test
    void computePriceTest_Adult_AtMatineeStart() {
        assertEquals(2400, validCalculator.computePrice(LocalTime.of(8, 0), validAdultAge));
    }


    /**
     * Tests: That child price and matinee discounts are applied
     */
    @Test
    void computePriceTest_Child_DuringMatinee() {
        assertEquals(2100, validCalculator.computePrice(LocalTime.of(9, 0), validChildAge));

    }

    /**
     * Tests: That senior price is applied and matinee discounts are not applied
     */
    @Test
    void computePriceTest_Senior_AtMatineeEnd() {
        assertEquals(2300, validCalculator.computePrice(LocalTime.of(12, 0), validSeniorAge));

    }

    /**
     * Tests: That adult price are applied
     */
    @Test
    void computePriceTest_Adult_AfterMatinee() {
        assertEquals(2700, validCalculator.computePrice(LocalTime.of(17, 0), validAdultAge));

    }

    /**
     * These are edge cases
     */

    @Test
    void computePriceTest_InvalidAge() {
        assertFalse(2700 == validCalculator.computePrice(LocalTime.of(17, 0), -8));

    }



    
}
