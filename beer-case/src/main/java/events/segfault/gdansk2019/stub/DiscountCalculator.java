package events.segfault.gdansk2019.stub;

import events.segfault.gdansk2019.stub.price.DiscountException;
import events.segfault.gdansk2019.stub.price.DiscountService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.function.BiFunction;

@Slf4j
public final class DiscountCalculator implements BiFunction<Beer, Integer, Money> {

    private static final Money ZERO_PRICE = Money.zero(CurrencyUnit.EUR);

    @Override
    public Money apply(Beer beer, Integer amount) {
        try {
            return DiscountService.calculateDiscount(beer, amount);
        } catch (DiscountException e) {
            log.error("Problem with discount calculation.", e);
        }
        return ZERO_PRICE;
    }

}
