package events.segfault.gdansk2019;

import events.segfault.gdansk2019.stub.Beer;
import events.segfault.gdansk2019.stub.customer.CustomerDataExternalService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Slf4j
public final class Main {

    public static void main(String[] args) {
        BeerService beerService = new BeerService(new CustomerDataExternalService());
        log.info("Charged: {}", beerService.buyBeers("5", new Beer("Special", "123", Money.of(CurrencyUnit.EUR, 1.99d)), 5));
        log.info("Charged: {}", beerService.buyBeers("1234", new Beer("EB", "1233", Money.of(CurrencyUnit.EUR, 1.99d)), 5));
        log.info("Charged: {}", beerService.buyBeers("123", new Beer("Amber", "1232", Money.of(CurrencyUnit.EUR, 1.99d)), 5));
        log.info("Charged: {}", beerService.buyBeers("12", new Beer("Brok", "1234", Money.of(CurrencyUnit.EUR, 1.99d)), 5));
    }
}
