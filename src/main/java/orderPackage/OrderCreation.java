package orderPackage;

import java.util.List;

public class OrderCreation {
    public static Order getOrderWithOneColor(){
        return new Order(
                "Rita",
                "Dem",
                "Spasskaya, 1 apt.",
                "4",
                "+7 925 308 15 07",
                "5",
                "2024-07-29",
                "no comment",
                List.of("Black")
        );
    }
    public static Order getOrderWithTwoColor() {
        return new Order(
                "Rita",
                "Dem",
                "Spasskaya, 1 apt.",
                "Сокольники",
                "+7 925 308 15 07",
                "5",
                "2024-07-29",
                "no comment",
                List.of("Black", "Grey")
        );
    }
    public static Order getOrderWithoutColor() {
        return new Order(
                "Rita",
                "Dem",
                "4",
                "+7 925 308 15 07",
                "5",
                "2024-07-29",
                "no comment",
                List.of()
        );
    }
}