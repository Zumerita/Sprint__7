package ordersPackage;

import io.restassured.response.ValidatableResponse;
import orderPackage.Order;
import orderPackage.OrderCreate;
import orderPackage.OrderCreation;
import orderPackage.OrderGet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class GetOrderTest {
    private OrderCreate orderCreate;
    private OrderGet orderList;
    private int track1;
    private int track2;
    private int track3;

    @Before
    public void setOrderCreate() {
        orderCreate = new OrderCreate();
        orderList = new OrderGet();
    }

    @Test
    public void createOrderWithDifferentColor() {
        ValidatableResponse responseCreateOrderWithOneColor = orderCreate.createOrder(OrderCreation.getOrderWithOneColor());
        ValidatableResponse responseCreateOrderWithTwoColor = orderCreate.createOrder(OrderCreation.getOrderWithTwoColor());
        ValidatableResponse responseOrderWithoutColor = orderCreate.createOrder(OrderCreation.getOrderWithoutColor());
        track1 = responseCreateOrderWithOneColor.extract().path("track");
        track2 = responseCreateOrderWithTwoColor.extract().path("track");
        track3 = responseOrderWithoutColor.extract().path("track");
        ValidatableResponse response = orderList.getOrder();
        List<Order> orderList = response.extract().path("orders");
        assertTrue(response.extract().statusCode() == 200
                && orderList.size() != 0);
    }

    @After
    public void cleanUp() {
        orderCreate.cancelOrder(track1);
        orderCreate.cancelOrder(track2);
        orderCreate.cancelOrder(track3);
    }
}
