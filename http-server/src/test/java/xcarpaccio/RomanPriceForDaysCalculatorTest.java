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

        assertThat(romanPriceForDays, is(3.2));

    }

    @Test
    public void test_5()  {
        Integer days = 5;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 4.2));

    }

    public void test_empty_string()  {
        Integer days = 0;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 0.0));

    }

    @Test
    public void test_null()  {
        Integer days = null;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 0.0));

    }

    @Test
    public void test_7()  {
        Integer days = 7;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 6.2));

    }

    @Test
    public void test_9()  {
        Integer days = 9;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 7.4));

    }

    @Test
    public void test_14()  {
        Integer days = 14;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 11.6));

    }

    @Test
    public void test_39()  {
        Integer days = 39;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 32.6));

    }

    @Test
    public void test_41()  {
        Integer days = 41;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 34.6));

    }

    @Test
    public void test_77()  {
        Integer days = 77;

        Double romanPriceForDays = priceCalculator.getRomanPriceForDays(days);

        assertThat(romanPriceForDays, is( 65.0));

    }


}