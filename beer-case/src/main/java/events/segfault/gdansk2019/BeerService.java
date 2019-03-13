package events.segfault.gdansk2019;

import static java.time.ZoneOffset.UTC;

import events.segfault.gdansk2019.stub.Beer;
import events.segfault.gdansk2019.stub.NotAdultException;
import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import events.segfault.gdansk2019.stub.price.DiscountException;
import events.segfault.gdansk2019.stub.price.DiscountService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Slf4j
public final class BeerService {

    private final CustomerService customerService;
    private final BiFunction<Beer, Integer, Money> discountCalculator;
    private final Clock clock;

    public BeerService(@NonNull CustomerService customerService) {
        this(customerService, new DiscountCalculator(), Clock.systemUTC());
    }

    BeerService(@NonNull CustomerService customerService,
            BiFunction<Beer, Integer, Money> discountCalculator,
            Clock clock) {
        this.customerService = customerService;
        this.discountCalculator = discountCalculator;
        this.clock = clock;
    }

    /**
     * It should: - check if user has permission to buy an alcohol drinks - if not then we cannot sell beer - check &
     * calculate discount for final price - calculate final price & charge the customer
     */
    public boolean buyBeers(String customerId, Beer beer, int amount) {
        Customer customer = customerService.getCustomerById(customerId);
        if (isAdult(customer)) {
            Money totalPrice = calculateTotalPriceIncludingDiscount(beer, amount);
            customerService.buy(customer, totalPrice);
            return true;
        } else {
            throw new NotAdultException();
        }
    }

    private Money calculateTotalPriceIncludingDiscount(Beer beer, int amount) {
        Money totalPrice = beer.getPrice().multipliedBy(amount);
        Money discountPrice = discountCalculator.apply(beer, amount);
        totalPrice = totalPrice.minus(discountPrice);
        return totalPrice;
    }

    private boolean isAdult(Customer customer) {
        return customer.getBirthDate().isBefore(LocalDateTime.now(clock).minusYears(18).toInstant(UTC));
    }

    private static final class DiscountCalculator implements BiFunction<Beer, Integer, Money> {

        private static final Money ZERO_PRICE = Money.zero(CurrencyUnit.EUR);

        @Override
        public Money apply(Beer beer, Integer amount) {
            try {
                return DiscountService.calculateDiscount(beer, amount);
            } catch (DiscountException e) {
                log.error("Problem with discount calculation.", e);
            }
            return ZERO_PRICE;
        }

    }
}
