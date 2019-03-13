package events.segfault.gdansk2019.stub

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import spock.lang.Specification
import spock.lang.Unroll

class DiscountCalculatorSpec extends Specification {

    @Unroll
    def "discount for #amount beer(s) #beer should be #expectedDiscount"() {
        setup:
        def calculator = new DiscountCalculator()

        when:
        def discount = calculator.apply(beer, amount)

        then:
        discount == expectedDiscount

        where:
        beer                                                      || amount || expectedDiscount
        new Beer("Specek", "123", Money.of(CurrencyUnit.EUR, 1d)) || 1      || Money.of(CurrencyUnit.EUR, 0)
        new Beer("Amber", "111", Money.of(CurrencyUnit.EUR, 1d))  || 1      || Money.of(CurrencyUnit.EUR, 0)
        new Beer("EB", "111", Money.of(CurrencyUnit.EUR, 1d))     || 4      || Money.of(CurrencyUnit.EUR, 0.4)
    }
}