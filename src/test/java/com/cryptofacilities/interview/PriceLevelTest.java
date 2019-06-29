package com.cryptofacilities.interview;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PriceLevelTest {

    PriceLevel priceLevel;
    String instrument = "Instrument1";
    Side side = Side.buy;
    long price = 333;
    Order order8;
    Order order9;
    Order order10;

    /**
     * Add different Orders
     * Add new Price level
     */
    @Before
    public void initialize() {
        LinkedList<Order> orders = new LinkedList<Order>();
        order8 = new Order("orderId8", instrument, side, price, 10);
        order9 = new Order("orderId9", instrument, side, price, 5);
        order10 = new Order("orderId10", instrument, side, price, 80);

        orders.add(order8);
        orders.add(order9);
        orders.add(order10);

        priceLevel = new PriceLevel(orders, instrument, side, price);
    }

    /**
     * Test add Order
     */
    @Test
    public void testAddOrder() {
        Order order11 = new Order("orderId11", instrument, side, price, 2);
        priceLevel.addOrder(order11);

        int expectedSize = 4;
        int actualSize = priceLevel.getOrders().size();
        assertEquals("Size is 4", expectedSize, actualSize);

        Order expectedOrder = new Order(order11);
        Order actualOrder = priceLevel.getOrders().get(3);
        assertEquals("11th order is order11", expectedOrder, actualOrder);
    }

    /**
     * Test Modify Order
     */
    @Test
    public void testModifyOrder() {
        priceLevel.modifyOrder("orderId9", 100);

        long expectedQty = 100;
        long actualQty = priceLevel.getOrderById("orderId9").getQuantity();
        assertEquals("New quantity is 100", expectedQty, actualQty);

        String expectedLastOrderId = "orderId9";
        String actualLastOrderId = priceLevel.getOrders().getLast().getOrderId();
        assertEquals("Last order: ", expectedLastOrderId, actualLastOrderId);
    }

    /**
     * Test Delete Order
     */
    @Test
    public void testDeleteOrder() {
        priceLevel.deleteOrder(order10.getOrderId());

        int expectedSize = 2;
        int actualSize = priceLevel.getOrders().size();
        assertEquals("Size is 2", expectedSize, actualSize);

        Order expectedOrder = null;
        Order actualOrder = priceLevel.getOrderById(order10.getOrderId());
        assertEquals("order10 is deleted and cannot be found", expectedOrder, actualOrder);
    }

    /**
     * Test get Order Number
     */
    @Test
    public void testGetOrderNum() {
        long expectedSize = 3;
        long actualSize = priceLevel.getOrderNum();
        assertEquals("Size is 3", expectedSize, actualSize);
    }

    /**
     * Test Get Total Qty
     */
    @Test
    public void testGetTotalQuantity() {
        long expectedQty = 95;
        long actualQty = priceLevel.getTotalQuantity();
        assertEquals("Total quantity is 95", expectedQty, actualQty);
    }

    /**
     * Test Get total Volume
     */
    @Test
    public void testGetTotalVolume() {
        long expectedVolume = 31635;
        long actualVolume = priceLevel.getTotalVolume();
        assertEquals("Total volume is 31635", expectedVolume, actualVolume);
    }

    /**
     * Test Get Orders
     */
    @Test
    public void testGetOrders() {
        Object[] orderList = {order8, order9, order10};
        assertArrayEquals("Price Level is equal to orders of 1, 2 & 3", orderList, priceLevel.getOrders().toArray());
    }

    /**
     * Test get Order by ID
     */
    @Test
    public void testGetOrderById() {
        Order expectedOrder = order9;
        Order actualOrder = priceLevel.getOrderById("orderId9");
        assertEquals("Order is: ", expectedOrder, actualOrder);
    }
}
