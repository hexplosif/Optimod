package com.hexplosif.optimod.model;

public class DeliveryRequest {
    Node pickupLocation;
    Node deliveryLocation;

    public DeliveryRequest(Node pickupLocation, Node deliveryLocation) {
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;
    }

    public Node getPickupLocation() {
        return pickupLocation;
    }

    public Node getDeliveryLocation() {
        return deliveryLocation;
    }
}
