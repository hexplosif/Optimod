package com.hexplosif.optimod.service;

import com.hexplosif.optimod.model.DeliveryRequest;
import com.hexplosif.optimod.model.Node;
import com.hexplosif.optimod.model.Segment;
import com.hexplosif.optimod.proxy.DeliveryRequestProxy;
import com.hexplosif.optimod.proxy.NodeProxy;
import com.hexplosif.optimod.proxy.SegmentProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

@Data
@Service
public class OptimodService {

    @Autowired
    private NodeProxy nodeProxy;

    @Autowired
    private SegmentProxy segmentProxy;

    @Autowired
    private DeliveryRequestProxy deliveryRequestProxy;

    /**
     * Parse the XML file
     * @param file The XML file
     * @return The document
     */
    private Document parseXMLFile(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    /**
     * Load the nodes from the XML file
     * @param XMLFileName The XML file
     */
    public void loadNode(String XMLFileName) throws Exception{

        try {
            File XMLFile = new File(XMLFileName);
            Document document = parseXMLFile(XMLFile);
            NodeList nodeList = document.getElementsByTagName("noeud");

            List<Node> tmpListNodes = (List<Node>) nodeProxy.getAllNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Node noeud = nodeList.item(i);

                if (noeud.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element elementNoeud = (Element) noeud;

                    String idNoeud = elementNoeud.getAttribute("id");
                    String latitudeNoeud = elementNoeud.getAttribute("latitude");
                    String longitudeNoeud = elementNoeud.getAttribute("longitude");

                    Node node = new Node();
                    node.setId(Long.parseLong(idNoeud));
                    node.setLatitude(Double.parseDouble(latitudeNoeud));
                    node.setLongitude(Double.parseDouble(longitudeNoeud));

                    //System.out.println("Node: " + node.getId() + ", Latitude: " + node.getLatitude() + ", Longitude: " + node.getLongitude());
                    tmpListNodes.add(node);


                }
            }

            nodeProxy.createNodes(tmpListNodes);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while loading node");
        }
    }

    /**
     * Load the segments from the XML file
     * @param XMLFileName The XML file
     */
    public void loadSegment(String XMLFileName) throws Exception {

        try {
            File XMLFile = new File(XMLFileName);
            Document document = parseXMLFile(XMLFile);
            NodeList listeTroncons = document.getElementsByTagName("troncon");

            List<Segment> tmpListSegments = (List<Segment>) segmentProxy.getAllSegments();

            for (int i = 0; i < listeTroncons.getLength(); i++) {
                org.w3c.dom.Node troncon = listeTroncons.item(i);

                if (troncon.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element elementTroncon = (Element) troncon;

                    String origineTroncon = elementTroncon.getAttribute("origine");
                    String destinationTroncon = elementTroncon.getAttribute("destination");
                    String longueurTroncon = elementTroncon.getAttribute("longueur");
                    String nomRueTroncon = elementTroncon.getAttribute("nomRue");

                    Segment segment = new Segment();
                    segment.setIdOrigin(Long.parseLong(origineTroncon));
                    segment.setIdDestination(Long.parseLong(destinationTroncon));
                    segment.setLength(Double.parseDouble(longueurTroncon));
                    segment.setName(nomRueTroncon);

                    //System.out.println("Segment: " + segment.getIdOrigin() + ", Destination: " + segment.getIdDestination() + ", Length: " + segment.getLength() + ", Name: " + segment.getName());
                    tmpListSegments.add(segment);
                }
            }

            segmentProxy.createSegments(tmpListSegments);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while loading segment");
        }
    }

    /**
     * Load the delivery request from the XML file
     * @param XMLDeliveryRequest The XML delivery request file
     */

    public void loadDeliveryRequest(String XMLDeliveryRequest) throws Exception {

        try {
            File XMLFile = new File(XMLDeliveryRequest);
            Document document = parseXMLFile(XMLFile);
            NodeList listeLivraisons = document.getElementsByTagName("livraison");
            org.w3c.dom.Node warehouse = document.getElementsByTagName("entrepot").item(0); // Warehouse is the first element
            String warehouseAddress = ((Element) warehouse).getAttribute("adresse");

            for (int i = 0; i < listeLivraisons.getLength(); i++) {
                org.w3c.dom.Node livraison = listeLivraisons.item(i);

                if (livraison.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element elementLivraison = (Element) livraison;

                    String adresseEnlevement = elementLivraison.getAttribute("adresseEnlevement");
                    String adresseLivraison = elementLivraison.getAttribute("adresseLivraison");

                    DeliveryRequest deliveryRequest = new DeliveryRequest();
                    deliveryRequest.setIdDelivery(Long.parseLong(adresseEnlevement));
                    deliveryRequest.setIdPickup(Long.parseLong(adresseLivraison));
                    deliveryRequest.setIdWarehouse(Long.parseLong(warehouseAddress));

                    //System.out.println("DeliveryRequest: " + deliveryRequest.getIdDelivery() + ", Pickup: " + deliveryRequest.getIdPickup() + ", WarehouseLocation: " + deliveryRequest.getIdWarehouse());
                    deliveryRequestProxy.createDeliveryRequest(deliveryRequest);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while loading delivery request");
        }
    }

    /**
     * Get a node by its id
     * @param id The id of the node
     * @return The node
     */
    public Node getNodeById(Long id) {
        return nodeProxy.getNodeById(id);
    }

    /**
     * Get all nodes
     * @return The list of nodes
     */
    public Iterable<Node> getAllNodes() {
        return nodeProxy.getAllNodes();
    }

    /**
     * Delete a node by its id
     * @param id The id of the node
     */
    public void deleteNodeById(Long id) {
        nodeProxy.deleteNodeById(id);
    }

    /**
     * Save a node
     * @param node The node to save
     * @return The saved node
     */
    public Node saveNode(Node node) {
        Node savedNode;
        if (node.getId() == null) {
            savedNode = nodeProxy.createNode(node);
        } else {
            savedNode = nodeProxy.saveNode(node);
        }
        return savedNode;
    }

    /**
     * Create a node
     * @param node The node to create
     */
    public void createNode(Node node) {
        nodeProxy.createNode(node);
    }

    /**
     * Delete all nodes
     */
    public void deleteAllNodes() {
        nodeProxy.deleteAllNodes();
    }

    /**
     * Create nodes
     * @param nodes The list of nodes to create
     */
    public void createNodes(List<Node> nodes) {
        nodeProxy.createNodes(nodes);
    }

    /**
     * Get a segment by its id
     * @param id The id of the segment
     * @return The segment
     */
    public Segment getSegmentById(Long id) {
        return segmentProxy.getSegmentById(id);
    }

    /**
     * Get all segments
     * @return The list of segments
     */
    public Iterable<Segment> getAllSegments() {
        return segmentProxy.getAllSegments();
    }

    /**
     * Delete a segment by its id
     * @param id The id of the segment
     */
    public void deleteSegmentById(Long id) {
        segmentProxy.deleteSegmentById(id);
    }

    /**
     * Save a segment
     * @param segment The segment to save
     * @return The saved segment
     */
    public Segment saveSegment(Segment segment) {
        Segment savedSegment;
        if (segment.getId() == null) {
            savedSegment = segmentProxy.createSegment(segment);
        } else {
            savedSegment = segmentProxy.saveSegment(segment);
        }
        return savedSegment;
    }

    /**
     * Create a segment
     * @param segment The segment to create
     */
    public void createSegment(Segment segment) {
        segmentProxy.createSegment(segment);
    }

    /**
     * Delete all segments
     */
    public void deleteAllSegments() {
        segmentProxy.deleteAllSegments();
    }

    /**
     * Create segments
     * @param segments The list of segments to create
     */
    public void createSegments(List<Segment> segments) {
        segmentProxy.createSegments(segments);
    }

    /**
     * Get a delivery request by its id
     * @param id The id of the delivery request
     * @return The delivery request     */
    public DeliveryRequest getDeliveryRequestById(Long id) {
        return deliveryRequestProxy.getDeliveryRequestById(id);
    }

    /**
     * Get all delivery requests
     * @return The list of delivery requests
     */
    public Iterable<DeliveryRequest> getAllDeliveryRequests() {
        return deliveryRequestProxy.getAllDeliveryRequests();
    }

    /**
     * Delete a delivery request by its id
     * @param id The id of the delivery request
     */
    public void deleteDeliveryRequestById(Long id) {
        deliveryRequestProxy.deleteDeliveryRequestById(id);
    }

    /**
     * Save a delivery request
     * @param deliveryrequest The delivery request to save
     * @return The saved delivery request
     */
    public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryrequest) {
        DeliveryRequest savedDeliveryRequest;
        if (deliveryrequest.getId() == null) {
            savedDeliveryRequest = deliveryRequestProxy.createDeliveryRequest(deliveryrequest);
        } else {
            savedDeliveryRequest = deliveryRequestProxy.saveDeliveryRequest(deliveryrequest);
        }
        return savedDeliveryRequest;
    }

    /**
     * Create a delivery request
     * @param deliveryrequest The delivery request to create
     */
    public void createDeliveryRequest(DeliveryRequest deliveryrequest) {
        deliveryRequestProxy.createDeliveryRequest(deliveryrequest);
    }

    /**
     * Delete all delivery requests
     */
    public void deleteAllDeliveryRequests() {
        deliveryRequestProxy.deleteAllDeliveryRequests();
    }
}
