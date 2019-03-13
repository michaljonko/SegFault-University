package events.segfault.gdansk2019.stub;

import events.segfault.gdansk2019.stub.customer.Customer;
import events.segfault.gdansk2019.stub.customer.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AdultCustomerServiceTest {

    private final Clock clock = Clock.fixed(Instant.parse("2019-03-14T10:00:00.00Z"), ZoneId.systemDefault());
    @Mock
    private CustomerService customerService;
    private Function<String, Optional<Customer>> adultCustomerService;

    @Before
    public void setUp() {
        adultCustomerService = new AdultCustomerService(clock, customerService);
    }

    @Test
    public void shouldReturnAdultCustomer() {
        String customerId = "777";
        Customer customer = new Customer(Instant.parse("2000-03-14T10:00:00.00Z"), "Janusz");

        given(customerService.getCustomerById(customerId)).willReturn(customer);

        Optional<Customer> adultCustomer = adultCustomerService.apply(customerId);

        assertThat(adultCustomer).contains(customer);
    }

    @Test
    public void shouldNotReturnCustomerBecauseOfTooYoung() {
        String customerId = "777";
        Customer customer = new Customer(Instant.parse("2010-03-14T10:00:00.00Z"), "Janusz");

        given(customerService.getCustomerById(customerId)).willReturn(customer);

        Optional<Customer> adultCustomer = adultCustomerService.apply(customerId);

        assertThat(adultCustomer).isEmpty();
    }
}