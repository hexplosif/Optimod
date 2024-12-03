package com.hexplosif.OptimodFrontEnd.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexplosif.OptimodFrontEnd.CustomProperties;
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
