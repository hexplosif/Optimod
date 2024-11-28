package com.hexplosif.optimod.repository;

import com.hexplosif.optimod.CustomProperties;
import com.hexplosif.optimod.model.DeliveryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class DeliveryRequestProxy {

    @Autowired
    private CustomProperties customProperties;

    /**
     * Get all delivery requests from the API
     * @return An iterable of delivery requests
     */
    public Iterable<DeliveryRequest> getAllDeliveryRequests() {
        String apiUrl = customProperties.getApiUrl();
        String getAllDeliveryRequestsUrl = apiUrl + "/deliveryrequests";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<DeliveryRequest>> response = restTemplate.exchange(
                getAllDeliveryRequestsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<DeliveryRequest>>() {
                }
        );

        log.debug("Get all delivery requests called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Get a delivery request by its id
     * @param id The id of the delivery request
     * @return The delivery request
     */
    public DeliveryRequest getDeliveryRequestById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String getDeliveryRequestByIdUrl = apiUrl + "/deliveryrequest/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DeliveryRequest> response = restTemplate.exchange(
                getDeliveryRequestByIdUrl,
                HttpMethod.GET,
                null,
                DeliveryRequest.class
        );

        log.debug("Get delivery request by id called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Create a delivery request in the API
     * @param deliveryrequest The delivery request to create
     * @return The created delivery request
     */
    public DeliveryRequest createDeliveryRequest(DeliveryRequest deliveryrequest) {
        String apiUrl = customProperties.getApiUrl();
        String createDeliveryRequestUrl = apiUrl + "/deliveryrequest";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<DeliveryRequest> request = new HttpEntity<DeliveryRequest>(deliveryrequest);
        ResponseEntity<DeliveryRequest> response = restTemplate.postForEntity(
                createDeliveryRequestUrl,
                request,
                DeliveryRequest.class
        );

        log.debug("Create delivery request called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Delete a delivery request in the API
     * @param id The id of the delivery request to delete
     */
    public void deleteDeliveryRequestById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String deleteDeliveryRequestUrl = apiUrl + "/deliveryrequest/" + id;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteDeliveryRequestUrl);

        log.debug("Delete delivery request called with id: " + id);
    }

    /**
     * Save a delivery request in the API
     * @param deliveryrequest The delivery request to save
     * @return The saved delivery request
     */
    public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryrequest) {
        String apiUrl = customProperties.getApiUrl();
        String saveDeliveryRequestUrl = apiUrl + "/deliveryrequest";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<DeliveryRequest> request = new HttpEntity<DeliveryRequest>(deliveryrequest);
        ResponseEntity<DeliveryRequest> response = restTemplate.exchange(
                saveDeliveryRequestUrl,
                HttpMethod.PUT,
                request,
                DeliveryRequest.class
        );

        log.debug("Save delivery request called with response: " + response.toString());

        return response.getBody();
    }
}
