package events.segfault.gdansk2019;

import events.segfault.gdansk2019.stub.AdultCustomerService;
import events.segfault.gdansk2019.stub.Beer;
import events.segfault.gdansk2019.stub.BuyBeer;
import events.segfault.gdansk2019.stub.TotalPriceCalculator;
import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;

import java.time.Clock;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class BeerService {

    private final Function1<String, Either<Throwable, Customer>> customerService;
    private final Function2<Beer, Integer, Money> totalPriceCalculator;
    private final Function2<Customer, Money, Either<Throwable, Money>> buyBeer;

    public BeerService(CustomerService customerService) {
        this(new AdultCustomerService(Clock.systemUTC(), customerService),
                new TotalPriceCalculator(),
                new BuyBeer(customerService));
    }

    /**
     * It should:
     * - check if user has permission to buy an alcohol drinks - if not then we cannot sell beer
     * - check & calculate discount for final price
     * - calculate final price & charge the customer
     */
    public Either<Throwable, Money> buyBeers(String customerId, Beer beer, int amount) {
        return customerService.apply(customerId)
                .map(buyBeer::apply)
                .flatMap(chargeFunction -> chargeFunction.apply(totalPriceCalculator.apply(beer, amount)));
    }
}
