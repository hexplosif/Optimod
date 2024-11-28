package com.hexplosif.optimod.repository;

import com.hexplosif.optimod.CustomProperties;
import com.hexplosif.optimod.model.Node;
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
public class NodeProxy {

    @Autowired
    private CustomProperties customProperties;

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
}
