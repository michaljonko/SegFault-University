package events.segfault.gdansk2019.stub;

import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import lombok.NonNull;
import org.joda.money.Money;

import java.util.function.BiFunction;

public final class BuyBeer implements BiFunction<Customer, Money, Boolean> {

    private final CustomerService customerService;

    public BuyBeer(@NonNull CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Boolean apply(Customer customer, Money money) {
        try {
            customerService.buy(customer, money.getAmount().doubleValue());
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
