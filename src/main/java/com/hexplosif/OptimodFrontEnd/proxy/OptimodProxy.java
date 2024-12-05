package com.hexplosif.OptimodFrontEnd.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexplosif.OptimodFrontEnd.CustomProperties;
import com.hexplosif.OptimodFrontEnd.model.Courier;
import com.hexplosif.OptimodFrontEnd.model.DeliveryRequest;
import com.hexplosif.OptimodFrontEnd.model.Node;
import com.hexplosif.OptimodFrontEnd.model.Segment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class OptimodProxy {

    @Autowired
    private CustomProperties customProperties;

    /**
     * Load a map in the API
     * @param file The file to load
     * @return The response from the API
     */
    public Map<String, Object> loadMap(MultipartFile file) {
        String apiUrl = customProperties.getApiUrl();
        String loadMapUrl = apiUrl + "/loadMap";

        RestTemplate restTemplate = new RestTemplate();

        // Convertir le fichier en ByteArrayResource
        ByteArrayResource fileAsResource;
        try {
            fileAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + e.getMessage());
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileAsResource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    loadMapUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Extraire le corps de la réponse d'erreur
            String responseBody = e.getResponseBodyAsString();
            log.error("Server responded with error: " + responseBody);

            ObjectMapper mapper = new ObjectMapper();
            try {
                // Convertir la réponse JSON d'erreur en Map
                return mapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            } catch (JsonProcessingException jsonException) {
                throw new RuntimeException("Failed to parse error response: " + responseBody);
            }
        } catch (Exception e) {
            log.error("Unexpected error during load map: ", e);
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Load a delivery request in the API
     * @param file The file to load
     * @return The response from the API
     */
    public Map<String, Object> loadDeliveryRequest(MultipartFile file) {

        String apiUrl = customProperties.getApiUrl();
        String loadDeliveryRequestUrl = apiUrl + "/loadDeliveryRequest";

        RestTemplate restTemplate = new RestTemplate();


        // Convertir le fichier en ByteArrayResource
        ByteArrayResource fileAsResource;
        try {
            fileAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + e.getMessage());
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileAsResource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Envoyer la requête POST
            ResponseEntity<Map> response = restTemplate.exchange(
                    loadDeliveryRequestUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Extraire le corps de la réponse d'erreur
            String responseBody = e.getResponseBodyAsString();
            log.error("Server responded with error: " + responseBody);

            ObjectMapper mapper = new ObjectMapper();
            try {
                // Convertir la réponse JSON d'erreur en Map
                return mapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            } catch (JsonProcessingException jsonException) {
                throw new RuntimeException("Failed to parse error response: " + responseBody);
            }
        } catch (Exception e) {
            log.error("Unexpected error during load delivery request: ", e);
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Get all nodes from the API
     * @return An iterable of nodes
     */
    public Iterable<Node> getAllNodes() {
        String apiUrl = customProperties.getApiUrl();
        String getAllNodesUrl = apiUrl + "/nodes";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Node>> response = restTemplate.exchange(
                getAllNodesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Node>>() {
                }
        );

        log.debug("Get all nodes called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Get a node by its id
     * @param id The id of the node
     * @return The node
     */
    public Node getNodeById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String getNodeByIdUrl = apiUrl + "/node/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Node> response = restTemplate.exchange(
                getNodeByIdUrl,
                HttpMethod.GET,
                null,
                Node.class
        );

        log.debug("Get node by id called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Create a node in the API
     * @param node The node to create
     * @return The created node
     */
    public Node createNode(Node node) {
        String apiUrl = customProperties.getApiUrl();
        String createNodeUrl = apiUrl + "/node";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Node> request = new HttpEntity<Node>(node);
        ResponseEntity<Node> response = restTemplate.postForEntity(
                createNodeUrl,
                request,
                Node.class
        );

        log.debug("Create node called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Create multiple nodes in the API
     * @param nodes The nodes to create
     * @return The created nodes
     */
    public Iterable<Node> createNodes(Iterable<Node> nodes) {
        String apiUrl = customProperties.getApiUrl();
        String createNodesUrl = apiUrl + "/nodes";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Iterable<Node>> request = new HttpEntity<Iterable<Node>>(nodes);
        ResponseEntity<Iterable<Node>> response = restTemplate.exchange(
                createNodesUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Iterable<Node>>() {
                }
        );

        log.debug("Create nodes called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Delete a node by its id
     * @param id The id of the node
     */
    public void deleteNodeById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String deleteNodeByIdUrl = apiUrl + "/node/" + id;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteNodeByIdUrl);

        log.debug("Delete node by id called");
    }

    /**
     * Save a node in the API
     * @param node The node to save
     * @return The saved node
     */
    public Node saveNode(Node node) {
        String apiUrl = customProperties.getApiUrl();
        String saveNodeUrl = apiUrl + "/node/" + node.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Node> request = new HttpEntity<Node>(node);
        ResponseEntity<Node> response = restTemplate.exchange(
                saveNodeUrl,
                HttpMethod.PUT,
                request,
                Node.class
        );

        log.debug("Save node called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Delete all nodes
     */
    public void deleteAllNodes() {
        String apiUrl = customProperties.getApiUrl();
        String deleteAllNodesUrl = apiUrl + "/nodes";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteAllNodesUrl);

        log.debug("Delete all nodes called");
    }

    /**
     * Get all segments from the API
     * @return An iterable of segments
     */
    public Iterable<Segment> getAllSegments() {
        String apiUrl = customProperties.getApiUrl();
        String getAllSegmentsUrl = apiUrl + "/segments";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Segment>> response = restTemplate.exchange(
                getAllSegmentsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Segment>>() {
                }
        );

        log.debug("Get all segments called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Get a segment by its id
     * @param id The id of the segment
     * @return The segment
     */
    public Segment getSegmentById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String getSegmentByIdUrl = apiUrl + "/segment/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Segment> response = restTemplate.exchange(
                getSegmentByIdUrl,
                HttpMethod.GET,
                null,
                Segment.class
        );

        log.debug("Get segment by id called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Create a segment in the API
     * @param segment The segment to create
     * @return The created segment
     */
    public Segment createSegment(Segment segment) {
        String apiUrl = customProperties.getApiUrl();
        String createSegmentUrl = apiUrl + "/segment";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Segment> request = new HttpEntity<Segment>(segment);
        ResponseEntity<Segment> response = restTemplate.postForEntity(
                createSegmentUrl,
                request,
                Segment.class
        );

        log.debug("Create segment called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Delete a segment by its id
     * @param id The id of the segment
     */
    public void deleteSegmentById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String deleteSegmentByIdUrl = apiUrl + "/segment/" + id;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteSegmentByIdUrl);

        log.debug("Delete segment by id called");
    }

    /**
     * Save a segment in the API
     * @param segment The segment to save
     * @return The saved segment
     */
    public Segment saveSegment(Segment segment) {
        String apiUrl = customProperties.getApiUrl();
        String saveSegmentUrl = apiUrl + "/segment/" + segment.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Segment> request = new HttpEntity<Segment>(segment);
        ResponseEntity<Segment> response = restTemplate.exchange(
                saveSegmentUrl,
                HttpMethod.PUT,
                request,
                Segment.class
        );

        log.debug("Save segment called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Delete all segments
     */
    public void deleteAllSegments() {
        String apiUrl = customProperties.getApiUrl();
        String deleteAllSegmentsUrl = apiUrl + "/segments";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteAllSegmentsUrl);

        log.debug("Delete all segments called");
    }

    /**
     * Create segments in the API
     * @param segments The list of segments to create
     */
    public void createSegments(List<Segment> segments) {
        String apiUrl = customProperties.getApiUrl();
        String createSegmentsUrl = apiUrl + "/segments";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<List<Segment>> request = new HttpEntity<List<Segment>>(segments);
        ResponseEntity<List<Segment>> response = restTemplate.exchange(
                createSegmentsUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<Segment>>() {
                }
        );
    }

    /**
     * Get all delivery_requests from the API
     * @return An iterable of delivery_requests
     */
    public Iterable<DeliveryRequest> getAllDeliveryRequests() {
        String apiUrl = customProperties.getApiUrl();
        String getAllDeliveryRequestsUrl = apiUrl + "/delivery_requests";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<DeliveryRequest>> response = restTemplate.exchange(
                getAllDeliveryRequestsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<DeliveryRequest>>() {
                }
        );

        log.debug("Get all delivery_requests called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Get a delivery_request by its id
     * @param id The id of the delivery_request
     * @return The delivery_request
     */
    public DeliveryRequest getDeliveryRequestById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String getDeliveryRequestByIdUrl = apiUrl + "/delivery_request/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DeliveryRequest> response = restTemplate.exchange(
                getDeliveryRequestByIdUrl,
                HttpMethod.GET,
                null,
                DeliveryRequest.class
        );

        log.debug("Get delivery_request by id called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Create a delivery_request in the API
     * @param delivery_request The delivery_request to create
     * @return The created delivery_request
     */
    public DeliveryRequest createDeliveryRequest(DeliveryRequest delivery_request) {
        String apiUrl = customProperties.getApiUrl();
        String createDeliveryRequestUrl = apiUrl + "/delivery_request";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<DeliveryRequest> request = new HttpEntity<DeliveryRequest>(delivery_request);
        ResponseEntity<DeliveryRequest> response = restTemplate.postForEntity(
                createDeliveryRequestUrl,
                request,
                DeliveryRequest.class
        );

        log.debug("Create delivery_request called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Delete a delivery_request by its id
     * @param id The id of the delivery_request
     */
    public void deleteDeliveryRequestById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String deleteDeliveryRequestByIdUrl = apiUrl + "/delivery_request/" + id;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteDeliveryRequestByIdUrl);

        System.out.println("Delete delivery_request by id called");
        log.debug("Delete delivery_request by id called");
    }

    /**
     * Save a delivery_request in the API
     * @param delivery_request The delivery_request to save
     * @return The saved delivery_request
     */
    public DeliveryRequest saveDeliveryRequest(DeliveryRequest delivery_request) {
        String apiUrl = customProperties.getApiUrl();
        String saveDeliveryRequestUrl = apiUrl + "/delivery_request/" + delivery_request.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<DeliveryRequest> request = new HttpEntity<DeliveryRequest>(delivery_request);
        ResponseEntity<DeliveryRequest> response = restTemplate.exchange(
                saveDeliveryRequestUrl,
                HttpMethod.PUT,
                request,
                DeliveryRequest.class
        );

        log.debug("Save delivery_request called with response: " + response.toString());

        return response.getBody();
    }

    public void deleteAllDeliveryRequests() {
        String apiUrl = customProperties.getApiUrl();
        String deleteAllDeliveryRequestsUrl = apiUrl + "/delivery_requests";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteAllDeliveryRequestsUrl);

        log.debug("Delete all delivery_requests called");
    }

    /**
     * Get all courier from the API
     * @return An iterable of courier
     */
    public Iterable<Courier> getAllCouriers() {
        String apiUrl = customProperties.getApiUrl();
        String getAllCouriersUrl = apiUrl + "/couriers";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Courier>> response = restTemplate.exchange(
                getAllCouriersUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Courier>>() {
                }
        );

        log.debug("Get all delivery_requests called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Get a courier by its id
     * @param id The id of the courier
     * @return The courier
     */
    public Courier getCourierById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String getCourierByIdUrl = apiUrl + "/courier/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Courier> response = restTemplate.exchange(
                getCourierByIdUrl,
                HttpMethod.GET,
                null,
                Courier.class
        );

        log.debug("Get courier by id called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Create a courier in the API
     * @param courier The courier to create
     * @return The created courier
     */
    public Courier createCourier(Courier courier) {
        String apiUrl = customProperties.getApiUrl();
        String createCourierUrl = apiUrl + "/courier";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Courier> request = new HttpEntity<Courier>(courier);
        ResponseEntity<Courier> response = restTemplate.postForEntity(
                createCourierUrl,
                request,
                Courier.class
        );

        log.debug("Create courier called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Delete a courier by its id
     * @param id The id of the courier
     */
    public void deleteCourierById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String deleteCourierByIdUrl = apiUrl + "/courier/" + id;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteCourierByIdUrl);

        System.out.println("Delete courier by id called");
        log.debug("Delete courier by id called");
    }

    /**
     * Save a courier in the API
     * @param courier The courier to save
     * @return The saved courier
     */
    public Courier saveCourier(Courier courier) {
        String apiUrl = customProperties.getApiUrl();
        String saveCourierUrl = apiUrl + "/courier/" + courier.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Courier> request = new HttpEntity<Courier>(courier);
        ResponseEntity<Courier> response = restTemplate.exchange(
                saveCourierUrl,
                HttpMethod.PUT,
                request,
                Courier.class
        );

        log.debug("Save courier called with response: " + response.toString());

        return response.getBody();
    }

    public void deleteAllCouriers() {
        String apiUrl = customProperties.getApiUrl();
        String deleteAllCouriersUrl = apiUrl + "/couriers";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteAllCouriersUrl);

        log.debug("Delete all couriers called");
    }
}
