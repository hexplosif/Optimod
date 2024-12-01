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
     * Get all deliveryrequests from the API
     * @return An iterable of deliveryrequests
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

        log.debug("Get all deliveryrequests called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Get a deliveryrequest by its id
     * @param id The id of the deliveryrequest
     * @return The deliveryrequest
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

        log.debug("Get deliveryrequest by id called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Create a deliveryrequest in the API
     * @param deliveryrequest The deliveryrequest to create
     * @return The created deliveryrequest
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

        log.debug("Create deliveryrequest called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Delete a deliveryrequest by its id
     * @param id The id of the deliveryrequest
     */
    public void deleteDeliveryRequestById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String deleteDeliveryRequestByIdUrl = apiUrl + "/deliveryrequest/" + id;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteDeliveryRequestByIdUrl);

        log.debug("Delete deliveryrequest by id called");
    }

    /**
     * Save a deliveryrequest in the API
     * @param deliveryrequest The deliveryrequest to save
     * @return The saved deliveryrequest
     */
    public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryrequest) {
        String apiUrl = customProperties.getApiUrl();
        String saveDeliveryRequestUrl = apiUrl + "/deliveryrequest/" + deliveryrequest.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<DeliveryRequest> request = new HttpEntity<DeliveryRequest>(deliveryrequest);
        ResponseEntity<DeliveryRequest> response = restTemplate.exchange(
                saveDeliveryRequestUrl,
                HttpMethod.PUT,
                request,
                DeliveryRequest.class
        );

        log.debug("Save deliveryrequest called with response: " + response.toString());

        return response.getBody();
    }

    public void deleteAllDeliveryRequests() {
        String apiUrl = customProperties.getApiUrl();
        String deleteAllDeliveryRequestsUrl = apiUrl + "/deliveryrequests";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteAllDeliveryRequestsUrl);

        log.debug("Delete all deliveryrequests called");
    }
}
