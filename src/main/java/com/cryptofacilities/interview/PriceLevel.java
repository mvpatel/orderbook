package com.cryptofacilities.interview;

import java.util.Iterator;
import java.util.LinkedList;

public class PriceLevel {

    private LinkedList<Order> orders;

    private final long price;
    private final String instrument;
    private final Side side;

    public PriceLevel(LinkedList<Order> orders, String instrument, Side side, long price) {
        super();
        this.orders = orders;
        this.instrument = instrument;
        this.side = side;
        this.price = price;
    }

    /**
     * Add new order.
     * @param order
     */
    public void addOrder(Order order) {
        orders.addLast(order);
        System.out.println("Added order: " + order.toString());
    }

    /**
     * Modify existing order.
     *
     * @param orderId
     * @param newQuantity
     */
    public void modifyOrder(String orderId, long newQuantity) {
        if (newQuantity <= 0) {
            System.out.printf("Modified order quantity is %d. It must be greater than 0.\n", newQuantity);
        } else {
            Order modifiedOrder = getOrderById(orderId);

            if (null != modifiedOrder) {
                long oldQuantity = modifiedOrder.getQuantity();

                if (newQuantity > oldQuantity) {
                    Order newOrder = modifiedOrder;
                    newOrder.setQuantity(newQuantity);
                    deleteOrder(modifiedOrder.getOrderId());
                    orders.addLast(newOrder);
                } else {
                    modifiedOrder.setQuantity(newQuantity);
                }
            } else {
                System.out.printf("No such order with Id %s is found to modify.\n", orderId);
            }
        }
    }

    /**
     * Delete existing order.
     *
     * @param orderId
     */
    public void deleteOrder(String orderId) {
        Order deletedOrder = getOrderById(orderId);
        if (null != deletedOrder) {
            if (orders.remove(deletedOrder)) {
            } else {
            }
        } else {
            System.out.printf("No order with Id= %s is found to delete.\n", orderId);
        }
    }

    /**
     * Get number of orders
     *
     * @return
     */
    public long getOrderNum() {
        return orders.size();
    }

    /**
     * Get total quantity
     *
     * @return
     */
    public long getTotalQuantity() {
        long totalQty = 0;
        for (Order order : orders) {
            totalQty += order.getQuantity();
        }

        return totalQty;
    }

    /**
     * Get total Volume (Qty*Price)
     *
     * @return
     */
    public long getTotalVolume() {

        return this.getTotalQuantity() * price;
    }

    /**
     * Get list of orders, in the correct order
     *
     * @return
     */
    public LinkedList<Order> getOrders() {

        return orders;
    }

    /**
     * Get price
     *
     * @return
     */
    public long getPrice() {

        return price;
    }

    /**
     * Get instrument
     *
     * @return
     */
    public String getInstrument() {

        return instrument;
    }

    /**
     * Get side
     *
     * @return
     */
    public Side getSide() {

        return side;
    }

    /**
     * Get order by orderId
     *
     * @param orderId
     * @return
     */
    public Order getOrderById(String orderId) {
        Iterator<Order> itr = orders.iterator();
        while (itr.hasNext()) {
            Order currOrder = itr.next();
            if (currOrder.getOrderId().equals(orderId)) {
                return currOrder;
            }
        }

        return null;
    }
}
