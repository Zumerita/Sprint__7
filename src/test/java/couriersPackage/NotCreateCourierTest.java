package couriersPackage;

import courierPackage.*;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

public class NotCreateCourierTest {
    private Courier courier;
    private final Courier courierWithLoginOnly = CourierCreate.getCourierWithLogin();
    private final Courier courierWithPasswordOnly = CourierCreate.getCourierWithPassword();
    private final Courier courierWithSimilarLogin = CourierCreate.getCourierWithSimilarLogin();
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courier = CourierCreate.getCourier();
        courierClient = new CourierClient();
    }

    @Test
    public void createTwoSimilarCourierOk() {
        ValidatableResponse responseCreateFirst = courierClient.create(courier);
        ValidatableResponse responseCreateFirstDouble = courierClient.create(courier);
        ValidatableResponse responseLoginFirst = courierClient.login(InputData.from(courier));
        id = responseLoginFirst.extract().path("id");
        assertTrue(responseCreateFirst.extract().statusCode() == 201
                && responseCreateFirstDouble.extract().statusCode() == 409);
    }

    @Test
    public void notCreateCourierWithLoginOnly() {
        ValidatableResponse responseCreate = courierClient.create(courierWithLoginOnly);
        assertTrue(responseCreate.extract().statusCode() == 400
                && responseCreate.extract().path("message").equals("Недостаточно данных для создания учетной записи"));

    }

    @Test
    public void notCreateCourierWithPasswordOnly() {
        ValidatableResponse responseCreate = courierClient.create(courierWithPasswordOnly);
        assertTrue(responseCreate.extract().statusCode() == 400
                && responseCreate.extract().path("message").equals("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void notCreateCourierWithSimilarLogin() {
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        ValidatableResponse responseCreate = courierClient.create(courierWithSimilarLogin);
        assertTrue(responseCreate.extract().statusCode() == 409
                && responseCreate.extract().path("message").equals("Этот логин уже используется. Попробуйте другой.")
                && courier.getLogin().equals(courierWithSimilarLogin.getLogin()));
    }

    @Test
    public void notLoginCourierWithIncorrectLogin() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        courier.setLogin(courier.getLogin() + "InCorrect");
        ValidatableResponse responseLoginIncorrect = courierClient.login(InputData.from(courier));
        assertTrue(responseCreate.extract().statusCode() == 201
                && responseLoginIncorrect.extract().statusCode() == 404
                && responseLoginIncorrect.extract().path("message").equals("Учетная запись не найдена"));
    }
    @Test
    public void notLoginCourierWithIncorrectPassword() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        courier.setPassword(courier.getPassword() + "InCorrect");
        ValidatableResponse responseLoginIncorrect = courierClient.login(InputData.from(courier));
        String w = responseLogin.extract().path("message");
        assertTrue(responseCreate.extract().statusCode() == 201
                && responseLoginIncorrect.extract().statusCode() == 404
                && responseLoginIncorrect.extract().path("message").equals("Учетная запись не найдена"));
    }

    @Test
    public void notLoginCourierWithOutPassword() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        ValidatableResponse responseLoginIncorrect = courierClient.login(InputDataLogin.fromLogin(courier));
        assertTrue(responseCreate.extract().statusCode() == 201
                && responseLoginIncorrect.extract().statusCode() == 400
                && responseLoginIncorrect.extract().path("message").equals("Недостаточно данных для входа"));
    }

    @Test
    public void notLoginCourierWithOutLogin() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        ValidatableResponse responseLoginIncorrect = courierClient.login(InputDataPassword.fromPassword(courier));
        assertTrue(responseCreate.extract().statusCode() == 201
                && responseLoginIncorrect.extract().statusCode() == 400
                && responseLoginIncorrect.extract().path("message").equals("Недостаточно данных для входа"));
    }

    @Test
    public void notLoginNotExistCourierInBase() {
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        assertTrue(responseLogin.extract().statusCode() == 404
                && responseLogin.extract().path("message").equals("Учетная запись не найдена"));
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }
}
