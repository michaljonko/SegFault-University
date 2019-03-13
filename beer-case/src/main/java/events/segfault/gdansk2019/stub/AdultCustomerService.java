package events.segfault.gdansk2019.stub;

import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
import static java.time.ZoneOffset.UTC;

@AllArgsConstructor
public final class AdultCustomerService implements Function1<String, Either<Throwable, Customer>> {

    private final transient Clock clock;
    private final transient CustomerService customerService;

    @Override
    public Either<Throwable, Customer> apply(String customerId) {
        return Try.of(() -> customerService.getCustomerById(customerId))
                .filter(this::isAdult)
                .mapFailure(
                        Case($(instanceOf(NoSuchElementException.class)), NotAdultException::new))
                .toEither();
    }

    private boolean isAdult(Customer customer) {
        return customer.getBirthDate().isBefore(LocalDateTime.now(clock).minusYears(18).toInstant(UTC));
    }
}
