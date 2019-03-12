package events.segfault.gdansk2019;

import events.segfault.gdansk2019.stub.Beer;
import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceTest {

    @Mock
    private CustomerService customerService;
    @InjectMocks
    private BeerService beerService;


    @Test
    public void shouldChargeCustomerAccountWithPriceIncludingDiscountFor5Beers() {
        final double expectedPriceWithDiscount = 10d;
        Customer customer = new Customer(Instant.parse("2000-01-03T00:00:00.00Z"), "Stefan");
        Beer beer = new Beer("Specjal", "41", 1.99d);
        int beersAmount = 5;

        given(customerService.getCustomerById("666")).willReturn(customer);

        beerService.buyBeers("666", beer, beersAmount);

        then(customerService).should().buy(customer, expectedPriceWithDiscount);
    }

    @Test
    public void shouldNotSellBeerForTooYoungCustomer() {

    }

    @Test
    public void shouldChargeCustomerAccountWithPriceWithoutDiscountFor2Beers() {

    }

    @Test
    public void shouldChargeCustomerAccountWithPriceWithoutDiscountWhenDiscountServiceIsUnavailable() {

    }
}