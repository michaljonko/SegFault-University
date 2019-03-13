package events.segfault.gdansk2019;

import events.segfault.gdansk2019.stub.Beer;
import events.segfault.gdansk2019.stub.customer.CustomerDataExternalService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public final class Main {

    public static void main(String[] args) {
        BeerService beerService = new BeerService(new CustomerDataExternalService());
        beerService.buyBeers("5", new Beer("Special", "123", Money.of(CurrencyUnit.EUR, 1.99d)), 5);
    }
}
