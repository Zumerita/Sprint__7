package courierPackage;

public class CourierCreate {
    public static Courier getCourier() {
        return new Courier("Rit", "12345", "Margarita");
    }

    public static Courier getCourierWithLogin() {
        return new Courier("Rit", "", "");
    }

    public static Courier getCourierWithPassword() {
        return new Courier("", "test", "");
    }

    public static Courier getCourierWithSimilarLogin() {
        return new Courier("Rit", "12345", "Margarita");
    }
}