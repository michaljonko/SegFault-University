package events.segfault.gdansk2019.stub.price;

import events.segfault.gdansk2019.stub.Beer;
import java.math.RoundingMode;
import lombok.NonNull;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public final class DiscountService {

    private DiscountService() {
        throw new UnsupportedOperationException();
    }

    public static Money calculateDiscount(@NonNull Beer beer, int amount) throws DiscountException {
        if (beer.getBarcode().endsWith("1") && amount >= 4) {
            // 10% discount for beers with '1' at the end of barcode & amount >= 4
            return beer.getPrice().multipliedBy(0.1d, RoundingMode.HALF_DOWN).multipliedBy(amount);
        } else if (beer.getBarcode().endsWith("2")) {
            // throw service exception for beers with '2' at the end of barcode
            throw new DiscountException();
        } else {
            // no discount
            return Money.zero(CurrencyUnit.EUR);
        }
    }
}
