package orderPackage;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class OrderCreate extends Client {
    private static final String PATH_CREATE_ORDER = "/api/v1/orders";
    private static final String PATH_CANCEL_ORDER = "/api/v1/orders/cancel";


    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH_CREATE_ORDER)
                .then();
    }
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .spec(getSpec())
                .body(track)
                .when()
                .post(PATH_CANCEL_ORDER)
                .then();
    }
}
