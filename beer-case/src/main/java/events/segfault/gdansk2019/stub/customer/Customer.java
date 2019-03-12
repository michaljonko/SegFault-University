package events.segfault.gdansk2019.stub.customer;

import lombok.Value;

import java.time.Instant;

@Value
public final class Customer {

    private Instant birthDate;
    private String fullName;
}
