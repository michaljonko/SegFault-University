package events.segfault.gdansk2019;

import events.segfault.gdansk2019.stub.Beer;
import events.segfault.gdansk2019.stub.NotAdultException;
import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerDataExternalService;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import events.segfault.gdansk2019.stub.price.DiscountException;
import events.segfault.gdansk2019.stub.price.DiscountService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

@Slf4j
public final class BeerService {

    private final CustomerService customerService;

    public BeerService() {
        this(new CustomerDataExternalService());
    }

    BeerService(@NonNull CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * It should:
     * - check if user has permission to buy an alcohol drinks - if not then we cannot sell beer
     * - check & calculate discount for final price
     * - calculate final price & charge the customer
     */
    public void buyBeers(String customerId, Beer beer, int amount) {
        Customer customer = customerService.getCustomerById(customerId);

        if (isAdultCustomer(customer)) {
            double currentPrice = beer.getPrice() * amount;
            try {
                currentPrice -= DiscountService.calculateDiscount(beer, amount);
            } catch (DiscountException e) {
                log.error("Problem with discount calculation.", e);
            }
            customerService.buy(customer, currentPrice);
        } else {
            throw new NotAdultException();
        }
    }

    private boolean isAdultCustomer(Customer customer) {
        return customer.getBirthDate().isBefore(LocalDateTime.now().minusYears(18).toInstant(UTC));
    }
}
