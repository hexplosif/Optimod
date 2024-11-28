package com.hexplosif.optimod.service;

import com.hexplosif.optimod.model.DeliveryRequest;
import com.hexplosif.optimod.repository.DeliveryRequestProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class DeliveryRequestService {

    @Autowired
    private DeliveryRequestProxy deliveryRequestProxy;

    public DeliveryRequest getDeliveryRequestById(Long id) {
        return deliveryRequestProxy.getDeliveryRequestById(id);
    }

    public Iterable<DeliveryRequest> getAllDeliveryRequests() {
        return deliveryRequestProxy.getAllDeliveryRequests();
    }

    public void deleteDeliveryRequestById(Long id) {
        deliveryRequestProxy.deleteDeliveryRequestById(id);
    }

    public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryRequest) {
        return deliveryRequestProxy.saveDeliveryRequest(deliveryRequest);
    }
}
