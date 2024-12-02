package com.hexplosif.optimod.service;

import com.hexplosif.optimod.model.DeliveryRequest;
import com.hexplosif.optimod.model.Node;
import com.hexplosif.optimod.model.Segment;
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
    private NodeService nodeService;

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private DeliveryRequestService deliveryRequestService;

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

    // Le code doit être dans service et on fait loadMap avec dedans loadNode et loadSegment
    /**
     * Load the nodes from the XML file
     * @param XMLFileName The XML file
     */
    public void loadNode(String XMLFileName) throws Exception{

        try {
            File XMLFile = new File(XMLFileName);
            Document document = parseXMLFile(XMLFile);
            NodeList nodeList = document.getElementsByTagName("noeud");

            List<Node> tmpListNodes = (List<Node>) nodeService.getAllNodes();

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

            nodeService.createNodes(tmpListNodes);

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

            List<Segment> tmpListSegments = (List<Segment>) segmentService.getAllSegments();

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

            segmentService.createSegments(tmpListSegments);

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
                    deliveryRequestService.createDeliveryRequest(deliveryRequest);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while loading delivery request");
        }
    }

    /**
     * Get all nodes
     * @return The list of nodes
     */
    public Iterable<Node> getAllNodes() {
        return nodeService.getAllNodes();
    }

    /**
     * Get all segments
     * @return The list of segments
     */
    public Iterable<Segment> getAllSegments() {
        return segmentService.getAllSegments();
    }

    /**
     * Get all delivery requests
     * @return The list of delivery requests
     */
    public Iterable<DeliveryRequest> getAllDeliveryRequests() {
        return deliveryRequestService.getAllDeliveryRequests();
    }

    /**
     * Delete all nodes
     * @return void
     */
    public void deleteAllNodes() {
        nodeService.deleteAllNodes();
    }

    /**
     * Delete all segments
     * @return void
     */
    public void deleteAllSegments() {
        segmentService.deleteAllSegments();
    }

    /**
     * Delete all delivery requests
     * @return void
     */
    public void deleteAllDeliveryRequests() {
        deliveryRequestService.deleteAllDeliveryRequests();
    }
}
