package orderPackage;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderGet extends Client {
    private static final String PATH_GET_ORDER = "api/v1/orders";

    public ValidatableResponse getOrder(){
        return given()
                .spec(getSpec())
                .get(PATH_GET_ORDER)
                .then();
    }
}
