package com.hexplosif.model;

public class DeliveryRequest {
    Nodes pickupLocation;
    Nodes deliveryLocation;

    public DeliveryRequest(Nodes pickupLocation, Nodes deliveryLocation) {
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;
    }

    public Nodes getPickupLocation() {
        return pickupLocation;
    }

    public Nodes getDeliveryLocation() {
        return deliveryLocation;
    }
}
