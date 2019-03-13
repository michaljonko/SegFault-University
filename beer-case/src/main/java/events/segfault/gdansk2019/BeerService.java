package events.segfault.gdansk2019;

import events.segfault.gdansk2019.stub.*;
import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;

import java.time.Clock;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
public final class BeerService {

    private final Function<String, Optional<Customer>> adultCustomerService;
    private final BiFunction<Beer, Integer, Money> priceCalculator;
    private final BiFunction<Customer, Money, Boolean> buyBeerFunction;

    public BeerService(CustomerService customerService) {
        this(new AdultCustomerService(Clock.systemUTC(), customerService),
                new TotalPriceCalculator(new DiscountCalculator()),
                new BuyBeer(customerService));
    }

    BeerService(Function<String, Optional<Customer>> adultCustomerService,
                BiFunction<Beer, Integer, Money> priceCalculator,
                BiFunction<Customer, Money, Boolean> buyBeerFunction) {
        this.adultCustomerService = adultCustomerService;
        this.priceCalculator = priceCalculator;
        this.buyBeerFunction = buyBeerFunction;
    }

    /**
     * It should:
     * - check if user has permission to buy an alcohol drinks - if not then we cannot sell beer
     * - check & calculate discount for final price
     * - calculate final price & charge the customer
     */
    public boolean buyBeers(String customerId, Beer beer, int amount) {
        return adultCustomerService.apply(customerId)
                .map(customer -> {
                    Money finalPrice = priceCalculator.apply(beer, amount);
                    return buyBeerFunction.apply(customer, finalPrice);
                })
                .orElseThrow(NotAdultException::new);
    }
}
