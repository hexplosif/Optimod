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
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
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
    public void loadMap(MultipartFile file) {
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
            // Envoyer la requête POST
            restTemplate.exchange(
                    loadMapUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class
            );

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Extraire le corps de la réponse d'erreur
            log.error("Server responded with error: " + e.getResponseBodyAsString());

            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error during load map: ", e.getMessage());
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Load a delivery request in the API
     * @param file The file to load
     * @return The response from the API
     */
    public void loadDeliveryRequest(MultipartFile file) {

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
            restTemplate.exchange(
                    loadDeliveryRequestUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class
            );

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Extraire le corps de la réponse d'erreur
            log.error("Server responded with error: " + e.getResponseBodyAsString());

            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error during load delivery request: ", e.getMessage());
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

        try {
            restTemplate.delete(deleteAllNodesUrl);
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la suppression des noeuds.");
        }
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

        try {
            ResponseEntity<Iterable<DeliveryRequest>> response = restTemplate.exchange(
                    getAllDeliveryRequestsUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Iterable<DeliveryRequest>>() {
                    }
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            // Gestion des erreurs 404 avec le message retourné
            throw new RuntimeException("Aucune demande de livraison n'a été trouvée.");
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la récupération des demandes de livraison.");
        }
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

        try {
            ResponseEntity<DeliveryRequest> response = restTemplate.exchange(
                    getDeliveryRequestByIdUrl,
                    HttpMethod.GET,
                    null,
                    DeliveryRequest.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            // Gestion des erreurs 404 avec le message retourné
            throw new RuntimeException("La demande de livraison n'existe pas.");
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la récupération de la demande de livraison.");
        }
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

        try {
            ResponseEntity<DeliveryRequest> response = restTemplate.exchange(
                    createDeliveryRequestUrl,
                    HttpMethod.POST,
                    request,
                    DeliveryRequest.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la création de la demande de livraison.");
        }
    }

    /**
     * Delete a delivery_request by its id
     * @param id The id of the delivery_request
     */
    public void deleteDeliveryRequestById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String deleteDeliveryRequestByIdUrl = apiUrl + "/delivery_request/" + id;

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.delete(deleteDeliveryRequestByIdUrl);
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la suppression de la demande de livraison.");
        }
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

        try {
            ResponseEntity<DeliveryRequest> response = restTemplate.exchange(
                    saveDeliveryRequestUrl,
                    HttpMethod.PUT,
                    request,
                    DeliveryRequest.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la sauvegarde de la demande de livraison.");
        }
    }

    public void deleteAllDeliveryRequests() {
        String apiUrl = customProperties.getApiUrl();
        String deleteAllDeliveryRequestsUrl = apiUrl + "/delivery_requests";

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.delete(deleteAllDeliveryRequestsUrl);
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la suppression des demandes de livraison.");
        }
    }

    /**
     * Get all courier from the API
     * @return An iterable of courier
     */
    public Iterable<Courier> getAllCouriers() {
        String apiUrl = customProperties.getApiUrl();
        String getAllCouriersUrl = apiUrl + "/couriers";

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Iterable<Courier>> response = restTemplate.exchange(
                    getAllCouriersUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Iterable<Courier>>() {
                    }
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            // Gestion des erreurs 404 avec le message retourné
            throw new RuntimeException("Aucun coursier n'a été trouvé.");
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la récupération des coursiers.");
        }
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

        try {
            ResponseEntity<Courier> response = restTemplate.exchange(
                    getCourierByIdUrl,
                    HttpMethod.GET,
                    null,
                    Courier.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            // Gestion des erreurs 404 avec le message retourné
            throw new RuntimeException("Le coursier n'existe pas.");
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la récupération du coursier.");
        }
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

        try {
            ResponseEntity<Courier> response = restTemplate.exchange(
                    createCourierUrl,
                    HttpMethod.POST,
                    request,
                    Courier.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la création du courier.");
        }
    }

    /**
     * Delete a courier by its id
     * @param id The id of the courier
     */
    public void deleteCourierById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String deleteCourierByIdUrl = apiUrl + "/courier/" + id;

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(deleteCourierByIdUrl);
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la suppression du courier.");
        }
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

        try {
            ResponseEntity<Courier> response = restTemplate.exchange(
                    saveCourierUrl,
                    HttpMethod.PUT,
                    request,
                    Courier.class
            );
            return response.getBody();

        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la sauvegarde du courier.");
        }
    }

    public void deleteAllCouriers() {
        String apiUrl = customProperties.getApiUrl();
        String deleteAllCouriersUrl = apiUrl + "/couriers";

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.delete(deleteAllCouriersUrl);
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la suppression du courier.");
        }
    }

    public void addCourier() {
        String apiUrl = customProperties.getApiUrl();
        String addCourierUrl = apiUrl + "/addCourier";

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.postForEntity(addCourierUrl, null, Void.class);
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de l'ajout du courier.");
        }

    }

    public void deleteCourier() {
        String apiUrl = customProperties.getApiUrl();
        String deleteCourierUrl = apiUrl + "/deleteCourier";

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.delete(deleteCourierUrl);
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de la suppression du courier.");
        }
    }

    public void assignCourier(Long courierId, Long deliveryRequestId) {
        String apiUrl = customProperties.getApiUrl();
        String assignCourierUrl = apiUrl + "/assignCourier";

        RestTemplate restTemplate = new RestTemplate();

        // Création du corps de la requête sous forme de Map (pour convertir en JSON)
        Map<String, Object> body = new HashMap<>();
        body.put("courierId", courierId);
        body.put("deliveryRequestId", deliveryRequestId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Spécifier le type de contenu JSON

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(
                    assignCourierUrl,
                    HttpMethod.PUT,
                    requestEntity,
                    Void.class
            );
        } catch (HttpClientErrorException.BadRequest e) {
            // Gestion des erreurs 400 avec le message retourné
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gestion des erreurs génériques
            throw new RuntimeException("Erreur lors de l'assignation du courier.");
        }
    }

    public Map<Long, List<Long>> calculateOptimalRoute() {
        String apiUrl = customProperties.getApiUrl();
        String calculateOptimalRouteUrl = apiUrl + "/calculateOptimalRoute";

        RestTemplate restTemplate = new RestTemplate();

        try {
            // Envoi de la requête GET
            ResponseEntity<Map<Long, List<Long>>> response = restTemplate.exchange(
                    calculateOptimalRouteUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<Long, List<Long>>>() {
                    }
            );

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Extraire le corps de la réponse d'erreur
            log.error("Server responded with error: " + e.getResponseBodyAsString());

            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error during calculate optimal route: ", e.getMessage());
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<Resource> saveSession() {
        String apiUrl = customProperties.getApiUrl();
        String saveSessionUrl = apiUrl + "/saveSession";

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Resource> response = restTemplate.exchange(
                    saveSessionUrl,
                    HttpMethod.GET,
                    null,
                    Resource.class
            );

            // Retourner la réponse brute au contrôleur pour qu'elle soit transmise au client
            return ResponseEntity.ok()
                    .contentType(response.getHeaders().getContentType())
                    .header(HttpHeaders.CONTENT_DISPOSITION, response.getHeaders().getContentDisposition().toString())
                    .body(response.getBody());

        } catch (HttpClientErrorException.BadRequest e) {
            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la session.");
        }
    }

    public void restoreSession(MultipartFile file) {
        String apiUrl = customProperties.getApiUrl();
        String restoreSessionUrl = apiUrl + "/restoreSession";

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
            restTemplate.exchange(
                    restoreSessionUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class
            );

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Extraire le corps de la réponse d'erreur
            log.error("Server responded with error: " + e.getResponseBodyAsString());

            throw new RuntimeException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error during restore session: ", e.getMessage());
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
    }
}
