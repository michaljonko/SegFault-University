package events.segfault.gdansk2019.stub.price;

import events.segfault.gdansk2019.stub.Beer;
import lombok.NonNull;

public final class DiscountService {

    private DiscountService() {
        throw new UnsupportedOperationException();
    }

    public static double calculateDiscount(@NonNull Beer beer, int amount) throws DiscountException {
        if (beer.getBarcode().endsWith("1") && amount >= 4) {
            // 10% discount for beers with '1' at the end of barcode & amount >= 4
            return (beer.getPrice() * 0.1d) * amount;
        } else if (beer.getBarcode().endsWith("2")) {
            // throw service exception for beers with '2' at the end of barcode
            throw new DiscountException();
        } else {
            // no discount
            return 0d;
        }
    }
}
