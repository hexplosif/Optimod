package com.hexplosif.model;

import java.util.List;

public class OptimodModel {
    Nodes warehouse;
    List<DeliveryRequest> deliveryRequests;

    public OptimodModel(Nodes warehouse, List<DeliveryRequest> deliveryRequests) {
        this.warehouse = warehouse;
        this.deliveryRequests = deliveryRequests;
    }

    public Nodes getWarehouse() {
        return warehouse;
    }

    public List<DeliveryRequest> getDeliveryRequests() {
        return deliveryRequests;
    }

    public void loadDeliveries(String XMLfilename) {
        // Load the deliveries from the XML file
    }

    public void loadMap(String XMLfilename) {
        // Load the map from the XML file
    }
}
