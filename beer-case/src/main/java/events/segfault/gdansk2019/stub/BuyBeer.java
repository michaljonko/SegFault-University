package events.segfault.gdansk2019.stub;

import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import io.vavr.Function2;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.joda.money.Money;

@AllArgsConstructor
public final class BuyBeer implements Function2<Customer, Money, Either<Throwable, Money>> {

    private final transient CustomerService customerService;

    @Override
    public Either<Throwable, Money> apply(Customer customer, Money money) {
        return Try.run(() -> customerService.buy(customer, money.getAmount().doubleValue()))
                .map(ignored -> money)
                .toEither();
    }
}
