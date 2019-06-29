package com.cryptofacilities.interview;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderBookManagerTest {

    // create order book
    OrderBookManager orderBookManager;

    // create order
    Order order101;
    Order order102;

    /**
     * Add new Order
     * Add order in orderBookManager
     */
    @Before
    public void initialize() {
        orderBookManager = new OrderBookManagerImpl();

        order101 = new Order("order101", "Instrument1", Side.buy, 200, 10);

        // send order
        orderBookManager.addOrder(order101);
    }

    /**
     * Test add Order different Instruments
     * Use Order number for test the order
     */
    @Test
    public void testAddOrder() {
        Order orderId2 = new Order("orderId2", "Instrument1", Side.buy, 200, 10);

        orderBookManager.addOrder(orderId2);

        long expectedOrderNum = 2;
        long actualOrderNum = orderBookManager.getOrderNumAtLevel("Instrument1", Side.buy, 200);
        assertEquals("Order number for Price: 200, Instrument: Instrument1, Side.order101 = 2", expectedOrderNum, actualOrderNum);

        order102 = new Order("order9", "Instrument3", Side.buy, 300, 5);
        orderBookManager.addOrder(order102);

        long expectedOrderNum2 = 5;
        long actualOrderNum2 = orderBookManager.getTotalQuantityAtLevel("Instrument3", Side.buy, 300);
        assertEquals("Order number for Price: 300, Instrument: Instrument3, Side.order101 = 2", expectedOrderNum2, actualOrderNum2);
    }

    /**
     * Test modifyOrder
     * Test for increase the qty
     * Tet for decrease the qty
     */
    @Test
    public void testModifyOrder() {
        Order orderId2 = new Order("orderId2", "Instrument1", Side.buy, 200, 50);

        orderBookManager.addOrder(orderId2);

        orderBookManager.modifyOrder("order101", 33);

        long expectedIncreasedQty = 83;
        long actualIncreasedQty = orderBookManager.getTotalQuantityAtLevel("Instrument1", Side.buy, 200);
        assertEquals("Order number for Price: 200, Instrument: Instrument1, Side.order101 = 83", expectedIncreasedQty, actualIncreasedQty);

        orderBookManager.modifyOrder("order101", 2);

        long expectedDecreasedQty = 52;
        long actualDecreasedQty = orderBookManager.getTotalQuantityAtLevel("Instrument1", Side.buy, 200);
        assertEquals("Order number for Price: 200, Instrument: Instrument1, Side.order101 = 52", expectedDecreasedQty, actualDecreasedQty);
    }

    /**
     * Test Delete Order
     */
    @Test
    public void testDeleteOrder() {
        orderBookManager.deleteOrder("order101");

        long expectedOrderNum = 0;
        long actualOrderNum = orderBookManager.getOrderNumAtLevel("Instrument1", Side.buy, 200);
        assertEquals("Order number for Price: 200, Instrument: Instrument1, Side.order101 = 0", expectedOrderNum, actualOrderNum);
    }

    /**
     * Test Best price Bid
     */
    @Test
    public void testBestBidPrice() {
        long expectedPrice = 200;
        long actualPrice = orderBookManager.getBestPrice("Instrument1", Side.buy);
        assertEquals("Best Bid Price for Price: 200, Instrument: Instrument1, Side.order101 = 200", expectedPrice, actualPrice);
    }

    /**
     * Test get Order number at Level
     */
    @Test
    public void testGetOrderNumAtLevel() {
        long expectedOrderNum = 1;
        long actualOrderNum = orderBookManager.getOrderNumAtLevel("Instrument1", Side.buy, 200);
        assertEquals("Order number for Price: 200, Instrument: Instrument1, Side.order101 = 1", expectedOrderNum, actualOrderNum);
    }

    /**
     * Test get Total Qty at Level
     */
    @Test
    public void testGetTotalQuantityAtLevel() {
        long expectedTotalQty = 10;
        long actualTotalQty = orderBookManager.getTotalQuantityAtLevel("Instrument1", Side.buy, 200);
        assertEquals("Total Quantity for Price: 200, Instrument: Instrument1, Side.order101 = 10", expectedTotalQty, actualTotalQty);
    }

    /**
     * Test get Volume at Level
     */
    @Test
    public void testGetTotalVolumeAtLevel() {
        Order orderId4 = new Order("orderId4", "Instrument5", Side.buy, 200, 10);
        Order orderId5 = new Order("orderId5", "Instrument5", Side.buy, 200, 10);
        Order orderId6 = new Order("orderId6", "Instrument5", Side.buy, 200, 10);
        // this order is from different instrument, to test that it is not counted
        Order orderId7 = new Order("orderId7", "Instrument6", Side.buy, 200, 10);

        orderBookManager.addOrder(orderId4);
        orderBookManager.addOrder(orderId5);
        orderBookManager.addOrder(orderId6);
        orderBookManager.addOrder(orderId7);

        long expectedTotalVol = 6000;
        long actualTotalVol = orderBookManager.getTotalVolumeAtLevel("Instrument5", Side.buy, 200);
        assertEquals("Total Volume for Price: 200, Instrument: Instrument5, Side.order101 = 6000", expectedTotalVol, actualTotalVol);
    }

    /**
     * Test order at level
     */
    @Test
    public void testGetOrdersAtLevel() {
        Order orderId4 = new Order("orderId4", "Instrument1", Side.buy, 200, 10);
        orderBookManager.addOrder(orderId4);

        Order[] expectedOrders = {order101, orderId4 };
        assertArrayEquals(expectedOrders, orderBookManager.getOrdersAtLevel("Instrument1", Side.buy, 200).toArray());

        assertTrue("Orders on instrument Instrument6 is empty",
                orderBookManager.getOrdersAtLevel("Instrument6", Side.sell, 200).isEmpty());
    }
}
