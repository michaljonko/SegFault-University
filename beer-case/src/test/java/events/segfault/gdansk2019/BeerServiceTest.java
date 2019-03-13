package events.segfault.gdansk2019;

import events.segfault.gdansk2019.stub.Beer;
import events.segfault.gdansk2019.stub.NotAdultException;
import events.segfault.gdansk2019.stub.customer.Customer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private Function<String, Optional<Customer>> adultCustomerService;
    @Mock
    private BiFunction<Beer, Integer, Money> priceCalculator;
    @Mock
    private BiFunction<Customer, Money, Boolean> buyBeerFunction;
    private BeerService beerService;

    @Before
    public void setUp() {
        beerService = new BeerService(adultCustomerService, priceCalculator, buyBeerFunction);
    }

    @Test
    public void shouldChargeCustomerAccount() {
        Money beerPrice = Money.of(CurrencyUnit.EUR, 1);
        Beer beer = new Beer("Specjal", "123", beerPrice);
        Customer customer = new Customer(Instant.parse("2000-01-03T00:00:00.00Z"), "Stefan");

        given(adultCustomerService.apply("666")).willReturn(Optional.of(customer));
        given(priceCalculator.apply(beer, 1)).willReturn(beerPrice);
        given(buyBeerFunction.apply(customer, beerPrice)).willReturn(true);

        boolean charged = beerService.buyBeers("666", beer, 1);

        assertThat(charged).isTrue();
    }

    @Test
    public void shouldNotChargeCustomerAccount() {
        Money beerPrice = Money.of(CurrencyUnit.EUR, 1);
        Beer beer = new Beer("Specjal", "123", beerPrice);
        Customer customer = new Customer(Instant.parse("2000-01-03T00:00:00.00Z"), "Stefan");

        given(adultCustomerService.apply("666")).willReturn(Optional.of(customer));
        given(priceCalculator.apply(beer, 1)).willReturn(beerPrice);
        given(buyBeerFunction.apply(customer, beerPrice)).willReturn(false);

        boolean charged = beerService.buyBeers("666", beer, 1);

        assertThat(charged).isFalse();
    }

    @Test
    public void shouldThrowExceptionForNotAdultCustomer() {
        Money beerPrice = Money.of(CurrencyUnit.EUR, 1);
        Beer beer = new Beer("Specjal", "123", beerPrice);

        given(adultCustomerService.apply("666")).willReturn(Optional.empty());

        expectedException.expect(NotAdultException.class);

        beerService.buyBeers("666", beer, 1);
    }
}