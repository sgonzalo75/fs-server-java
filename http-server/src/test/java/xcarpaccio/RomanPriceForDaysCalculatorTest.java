package xcarpaccio;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by User on 06.12.2016.
 */
public class RomanPriceForDaysCalculatorTest {

    RomanPriceForDaysCalculator priceCalculator = new RomanPriceForDaysCalculator();

    @Test
    public void test_1()  {
        Integer days = 1;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is(1.0));

    }

    @Test
    public void test_3()  {
        Integer days = 3;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is(3.0));

    }

    @Test
    public void test_4()  {
        Integer days = 4;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is(3.4));

    }

    @Test
    public void test_5()  {
        Integer days = 5;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 4.4));

    }

    @Test
    public void test_7()  {
        Integer days = 7;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 6.4));

    }

}