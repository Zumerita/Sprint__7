package couriersPackage;

import courierPackage.Courier;
import courierPackage.CourierClient;
import courierPackage.CourierCreate;
import courierPackage.InputData;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class CreateCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courier = CourierCreate.getCourier();
        courierClient = new CourierClient();
    }

    @Test
    public void createCourier201(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        assertEquals(SC_CREATED, responseCreate.extract().statusCode());
    }

    @Test
    public void createCourierOk(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        assertTrue(responseCreate.extract().path("ok"));
    }

    @Test
    public void createCourierLogin() {
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        assertEquals(SC_OK, responseLogin.extract().statusCode());
    }

    @Test
    public void createCourierWithLoginAndPassword(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        int actualStatusCode = responseCreate.extract().statusCode();
        assertFalse(courier.getLogin().isEmpty() || courier.getPassword().isEmpty()
                || actualStatusCode != 201);
    }

    @Test
    public void createCourierWithLoginAndPasswordForLogin(){
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        int actualStatusCode = responseLogin.extract().statusCode();
        assertFalse(courier.getLogin().isEmpty() || courier.getPassword().isEmpty()
                || actualStatusCode != 200);
    }

    @Test
    public void createCourierLoginGetId() {
        id = 0;
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(InputData.from(courier));
        id = responseLogin.extract().path("id");
        assertTrue(responseLogin.extract().statusCode() == 200 && id != 0);
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

}
