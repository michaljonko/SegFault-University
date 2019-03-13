package events.segfault.gdansk2019.stub.customer;

public interface CustomerService {

    Customer getCustomerById(String customerId);

    void buy(Customer customer, double price);
}
