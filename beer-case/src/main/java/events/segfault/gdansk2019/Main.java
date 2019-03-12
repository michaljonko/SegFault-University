package events.segfault.gdansk2019;

import events.segfault.gdansk2019.stub.Beer;

public final class Main {

    public static void main(String[] args) {
        BeerService beerService = new BeerService();
        beerService.buyBeers("5", new Beer("Special", "1", 1.99d), 5);
    }
}
