package events.segfault.gdansk2019.stub;

import lombok.Value;
import org.joda.money.Money;

@Value
public class Beer {

    private String name;
    private String barcode;
    private Money price;
}
