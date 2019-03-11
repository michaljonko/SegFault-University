package events.segfault.gdansk2019;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FinalPriceCalculator {

    public void calculate(Integer items, Double itemPrice, String state, double vipDiscount) {
        double totalPrice = items * itemPrice;
        double discountRate;
        double taxRate;
        if (totalPrice < 1_000) {
            discountRate = 0.0;
        } else if (totalPrice < 5_000) {
            discountRate = 0.03;
        } else if (totalPrice < 7_000) {
            discountRate = 0.05;
        } else if (totalPrice < 10_000) {
            discountRate = 0.07;
        } else if (totalPrice < 50_000) {
            discountRate = 0.1;
        } else {
            discountRate = 0.15;
        }
        if ("PO".equals(state)) {
            taxRate = 0.0685;
        } else if ("ZP".equals(state)) {
            taxRate = 0.0800;
        } else if ("WM".equals(state)) {
            taxRate = 0.0625;
        } else if ("KP".equals(state)) {
            taxRate = 0.04;
        } else {
            taxRate = 0.0825;
        }
        double discountPrice = (1 - discountRate) * totalPrice;
        double reducedPrice = (1 + taxRate) * discountPrice;
        double finalPrice = (1 - vipDiscount) * reducedPrice;
        log.info("Final price = {}", finalPrice);
    }
}
