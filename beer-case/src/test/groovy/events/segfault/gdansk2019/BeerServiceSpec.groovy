package events.segfault.gdansk2019

import events.segfault.gdansk2019.stub.Beer
import events.segfault.gdansk2019.stub.customer.Customer
import events.segfault.gdansk2019.stub.customer.CustomerService
import org.joda.money.Money
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Clock
import java.time.Instant
import java.util.function.BiFunction

import static java.time.ZoneOffset.UTC
import static org.joda.money.CurrencyUnit.EUR

class BeerServiceSpec extends Specification {

    @Shared def beer = new Beer("Specjal", "123", Money.of(EUR, 10));
    @Shared def adultCustomer = new Customer(Instant.parse("2000-01-01T00:00:00.00Z"), "Stefan");
    @Shared def childCustomer = new Customer(Instant.parse("2013-01-01T00:00:00.00Z"), "Stefan");
    def clock = Clock.fixed(Instant.parse("2020-01-01T00:00:00.00Z"), UTC)
    def discountCalculator = Mock(BiFunction)
    def customerService = Mock(CustomerService)
    def beerService = new BeerService(customerService, discountCalculator, clock)

    @Unroll
    def 'if customr is born at #customer.birthDate then sell = #expectedResult'() {
        given:
        def discount = Money.of(EUR, discountValue)
        discountCalculator.apply(beer, 1) >> discount
        customerService.getCustomerById("777") >> adultCustomer

        when:
        def result = beerService.buyBeers("777", beer, 1)

        then:
        result == true;

        where:
        customer      | discountValue || expectedResult
        adultCustomer | 0             || true
        childCustomer | 0             || false
    }
}
