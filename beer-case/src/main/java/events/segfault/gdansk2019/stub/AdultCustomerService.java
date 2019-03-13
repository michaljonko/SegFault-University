package events.segfault.gdansk2019.stub;

import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import lombok.NonNull;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

import static java.time.ZoneOffset.UTC;

public final class AdultCustomerService implements Function<String, Optional<Customer>> {

    private final Clock clock;
    private final CustomerService customerService;

    public AdultCustomerService(@NonNull Clock clock, @NonNull CustomerService customerService) {
        this.clock = clock;
        this.customerService = customerService;
    }

    @Override
    public Optional<Customer> apply(String customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer.getBirthDate().isBefore(LocalDateTime.now(clock).minusYears(18).toInstant(UTC))) {
            return Optional.of(customer);
        }
        return Optional.empty();
    }
}
