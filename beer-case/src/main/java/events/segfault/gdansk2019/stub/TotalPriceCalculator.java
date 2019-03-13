package events.segfault.gdansk2019.stub;

import events.segfault.gdansk2019.stub.price.DiscountService;
import io.vavr.CheckedFunction2;
import io.vavr.Function2;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.joda.money.Money;

@AllArgsConstructor
public final class TotalPriceCalculator implements Function2<Beer, Integer, Money> {

    private final CheckedFunction2<Beer, Integer, Money> calculateDiscount;

    public TotalPriceCalculator() {
        this(DiscountService::calculateDiscount);
    }

    @Override
    public Money apply(Beer beer, Integer amount) {
        Money totalPrice = beer.getPrice().multipliedBy(amount);
        return Try.of(() -> calculateDiscount.apply(beer, amount))
                .map(totalPrice::minus)
                .getOrElse(totalPrice);
    }
}


