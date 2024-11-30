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
    private DeliveryRequestProxy deliveryrequestProxy;

    public DeliveryRequest getDeliveryRequestById(Long id) {
        return deliveryrequestProxy.getDeliveryRequestById(id);
    }

    public Iterable<DeliveryRequest> getAllDeliveryRequests() {
        return deliveryrequestProxy.getAllDeliveryRequests();
    }

    public void deleteDeliveryRequestById(Long id) {
        deliveryrequestProxy.deleteDeliveryRequestById(id);
    }

    public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryrequest) {
        DeliveryRequest savedDeliveryRequest;
        if (deliveryrequest.getId() == null) {
            savedDeliveryRequest = deliveryrequestProxy.createDeliveryRequest(deliveryrequest);
        } else {
            savedDeliveryRequest = deliveryrequestProxy.saveDeliveryRequest(deliveryrequest);
        }
        return savedDeliveryRequest;
    }

    public void createDeliveryRequest(DeliveryRequest deliveryrequest) {
        deliveryrequestProxy.createDeliveryRequest(deliveryrequest);
    }
}
