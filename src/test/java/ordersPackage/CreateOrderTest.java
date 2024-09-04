package ordersPackage;

import io.restassured.response.ValidatableResponse;
import orderPackage.Order;
import orderPackage.OrderCreate;
import orderPackage.OrderCreation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderCreate orderCreate;
    private final Order order;
    private final int statusCode;
    private int track;

    public CreateOrderTest(Order order, int statusCode) {
        this.order = order;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {OrderCreation.getOrderWithOneColor(), SC_CREATED},
                {OrderCreation.getOrderWithTwoColor(), SC_CREATED},
                {OrderCreation.getOrderWithoutColor(), SC_CREATED}
        };
    }

    @Before
    public void setOrderCreate() {
        orderCreate = new OrderCreate();
    }

    @Test
    public void createOrderWithDifferentColour() {
        ValidatableResponse responseCreateOrder = orderCreate.createOrder(order);
        track = responseCreateOrder.extract().path("track");
        int actualStatusCode = responseCreateOrder.extract().statusCode();
        assertTrue(statusCode == actualStatusCode
                && track != 0);
    }

    @After
    public void cleanUp() {
        orderCreate.cancelOrder(track);
    }
}