package events.segfault.gdansk2019.stub;

import lombok.NonNull;
import org.joda.money.Money;

import java.util.function.BiFunction;

public final class TotalPriceCalculator implements BiFunction<Beer, Integer, Money> {

    private final BiFunction<Beer, Integer, Money> discountCalculator;

    public TotalPriceCalculator(@NonNull BiFunction<Beer, Integer, Money> discountCalculator) {
        this.discountCalculator = discountCalculator;
    }

    @Override
    public Money apply(Beer beer, Integer amount) {
        Money totalPrice = beer.getPrice().multipliedBy(amount);
        Money discount = discountCalculator.apply(beer, amount);
        return totalPrice.minus(discount);
    }
}
