package events.segfault.gdansk2019.stub.customer;

import events.segfault.gdansk2019.stub.Beer;
import org.joda.money.Money;

public interface CustomerService {

    Customer getCustomerById(String customerId);

    void buy(Customer customer, Money price);
}
