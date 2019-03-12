package events.segfault.gdansk2019.stub.customer;

import events.segfault.gdansk2019.stub.Beer;

import java.time.Instant;

public final class CustomerDataExternalService implements CustomerService {

    @Override
    public Customer getCustomerById(String customerId) {
        if (customerId.endsWith("1") || customerId.endsWith("4")) {
            return new Customer(Instant.parse("2008-01-03T00:00:00.00Z"), "Michał Mleko-pod-nosem");
        } else if (customerId.endsWith("2")) {
            throw new NoCustomerFoundException();
        } else if (customerId.endsWith("3")) {
            return new Customer(Instant.parse("2000-12-03T00:00:00.00Z"), "Piotr Dorosły");
        } else {
            return new Customer(Instant.parse("1983-12-03T00:00:00.00Z"), "Krzysztof Stary");
        }
    }

    @Override
    public void buy(Customer customer, Beer beer, double totalPrice) {
        if (customer.getFullName().startsWith("P")) {
            throw new CustomerServiceException();
        }
    }
}
