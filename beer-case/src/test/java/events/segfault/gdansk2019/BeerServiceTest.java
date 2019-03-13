package events.segfault.gdansk2019;

import events.segfault.gdansk2019.stub.Beer;
import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import java.time.Clock;
import java.time.ZoneOffset;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.money.CurrencyUnit.EUR;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private BiFunction<Beer, Integer, Money> discountCalculator;
    @Mock
    private CustomerService customerService;

    private BeerService beerService;

    @Before
    public void setUp() {
        beerService = new BeerService(customerService, discountCalculator, Clock.fixed(Instant.parse("2020-01-03T00:00:00.00Z"), UTC));
    }

    @Test
    public void shouldChargeCustomerAccount() {
        Money discountPrice = Money.of(EUR, 0);
        Money beerPrice = Money.of(EUR, 10);
        Beer beer = new Beer("Specjal", "123", beerPrice);
        Customer customer = new Customer(Instant.parse("2000-01-03T00:00:00.00Z"), "Stefan");

        given(discountCalculator.apply(beer, 1)).willReturn(discountPrice);
        given(customerService.getCustomerById("777")).willReturn(customer);

        boolean result = beerService.buyBeers("777", beer, 1);

        then(customerService).should().buy(customer, Money.of(EUR, 10));
        assertThat(result).isTrue();
    }
}