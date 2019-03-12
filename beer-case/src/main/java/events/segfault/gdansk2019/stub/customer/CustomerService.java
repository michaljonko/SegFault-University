package events.segfault.gdansk2019.stub.customer;

import events.segfault.gdansk2019.stub.Beer;

public interface CustomerService {

    Customer getCustomerById(String customerId);

    void buy(Customer customer, Beer beer, double price);
}
