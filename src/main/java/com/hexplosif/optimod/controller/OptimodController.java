package com.hexplosif.optimod.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.hexplosif.optimod.model.DeliveryRequest;
import com.hexplosif.optimod.model.Node;
import com.hexplosif.optimod.model.Segment;
import com.hexplosif.optimod.service.OptimodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/*
public class OptimodController {

    @Autowired
    private NodeService nodeService;

    public void loadMap(String XMLFileName) {
        // Load the map from the XML file
    }

    private Document parseXMLFile(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    public void loadDeliveryRequest(String XMLmap, String XMLFileName) throws Exception {

        try {
            // Load the deliveries from the XML file
            File XMLFile = new File("src/main/java/com/hexplosif/optimod/ressources/" + XMLFileName);
            File mapXML = new File("src/main/java/com/hexplosif/optimod/ressources/" + XMLmap);

            Document documentDeliveryRequests = parseXMLFile(XMLFile);
            Document documentMap = parseXMLFile(mapXML);

            String nomRacineDeliveryRequests = documentDeliveryRequests.getDocumentElement().getNodeName();
            if (!"demandeDeLivraisons".equals(nomRacineDeliveryRequests)) {
                System.err.println("Erreur : le nom de l'élément racine n'est pas 'demandeDeLivraisons'.");
                System.exit(1);
            }

            String nomRacineMap = documentMap.getDocumentElement().getNodeName();
            if (!"reseau".equals(nomRacineMap)) {
                System.err.println("Erreur : le nom de l'élément racine n'est pas 'reseau'.");
                System.exit(1);
            }

            NodeList listeLivraisons = documentDeliveryRequests.getElementsByTagName("livraison");
            NodeList nodeList = documentMap.getElementsByTagName("noeud");
            NodeList listeTroncons = documentMap.getElementsByTagName("troncon");

            Repository repository = new Repository();

            for (int i = 0; i < listeLivraisons.getLength(); i++) {
                org.w3c.dom.Node livraison = listeLivraisons.item(i);

                if (livraison.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element elementLivraison = (Element) livraison;

                    // Extraire les attributs adresseEnlevement et adresseLivraison
                    String adresseEnlevement = elementLivraison.getAttribute("adresseEnlevement");
                    String adresseLivraison = elementLivraison.getAttribute("adresseLivraison");

                    repository.addAdresses(adresseEnlevement, adresseLivraison);
                }
            }

            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Node noeud = nodeList.item(i);

                if (noeud.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element elementNoeud = (Element) noeud;

                    // Extraire les attributs id, latitude et longitude
                    String idNoeud = elementNoeud.getAttribute("id");
                    String latitudeNoeud = elementNoeud.getAttribute("latitude");
                    String longitudeNoeud = elementNoeud.getAttribute("longitude");

                    repository.addInformationNoeuds(idNoeud, latitudeNoeud, longitudeNoeud);

                }
            }

            for (int i = 0; i < listeTroncons.getLength(); i++) {
                org.w3c.dom.Node troncon = listeTroncons.item(i);

                if (troncon.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element elementTroncon = (Element) troncon;

                    // Extraire les attributs origine, destination, longueur et nomRue
                    String origineTroncon = elementTroncon.getAttribute("origine");
                    String destinationTroncon = elementTroncon.getAttribute("destination");
                    String longueurTroncon = elementTroncon.getAttribute("longueur");
                    String nomRueTroncon = elementTroncon.getAttribute("nomRue");

                    repository.addInformationTroncons(origineTroncon, destinationTroncon, longueurTroncon, nomRueTroncon);
                }
            }

            Iterator<String[]> addressIterator = repository.getIteratorAdresses();
            while (addressIterator.hasNext()) {
                String[] address = addressIterator.next();

                Node pickupLocation = new Node();
                Node deliveryLocation = new Node();

                Iterator<String[]> nodeIterator = repository.getIteratorInformationNoeuds();
                while (nodeIterator.hasNext()) {
                    String[] node = nodeIterator.next();
                    if(address[0].equals(node[0])) {
                        pickupLocation.setId(Long.parseLong(node[0]));
                        pickupLocation.setLatitude(Double.parseDouble(node[1]));
                        pickupLocation.setLongitude(Double.parseDouble(node[2]));
                    }
                    if(address[1].equals(node[0])) {
                        deliveryLocation.setId(Long.parseLong(node[0]));
                        deliveryLocation.setLatitude(Double.parseDouble(node[1]));
                        deliveryLocation.setLongitude(Double.parseDouble(node[2]));
                    }
                }
                repository.addDeliveryRequests(new DeliveryRequest(pickupLocation, deliveryLocation));

            }


            Node start = new Node();
            Node end = new Node();

            Iterator<String[]> tronconIterator = repository.getIteratorInformationTroncons();
            while (tronconIterator.hasNext()) {
                String[] troncon = tronconIterator.next();

                Iterator<String[]> nodeIterator2 = repository.getIteratorInformationNoeuds();
                while (nodeIterator2.hasNext()) {
                    String[] node = nodeIterator2.next();
                    if(troncon[0].equals(node[0])) {
                        start.setId(Long.parseLong(node[0]));
                        start.setLatitude(Double.parseDouble(node[1]));
                        start.setLongitude(Double.parseDouble(node[2]));
                    }
                    if(troncon[1].equals(node[0])) {
                        end.setId(Long.parseLong(node[0]));
                        end.setLatitude(Double.parseDouble(node[1]));
                        end.setLongitude(Double.parseDouble(node[2]));
                    }

                }
                repository.addSegments(new Segment(Double.parseDouble(troncon[2]), troncon[3], start, end));
            }



            Iterator<Segment> segmentIterator = repository.getIteratorSegments();
            while (segmentIterator.hasNext()) {
                Segment segment = segmentIterator.next();
                System.out.println("Name: " + segment.getName() + ", Longueur: " + segment.getLength());
            }

            Iterator<String[]> nodeIterator = repository.getIteratorInformationNoeuds();
            System.out.println("\nNoeuds:");
            while (nodeIterator.hasNext()) {
                String[] node = nodeIterator.next();
                System.out.println("ID: " + node[0] + ", Latitude: " + node[1] + ", Longitude: " + node[2]);
            }

            Iterator<String[]> addressIterator1 = repository.getIteratorAdresses();
            System.out.println("\nAdresses:");
            while (addressIterator1.hasNext()) {
                String[] address = addressIterator1.next();
                System.out.println("Enlèvement: " + address[0] + ", Livraison: " + address[1]);
            }

            Iterator<DeliveryRequest> addressIterator1 = repository.getIteratorDeliveryRequests();
            System.out.println("\nDeliveryRequest:");
            while (addressIterator1.hasNext()) {
                DeliveryRequest deliveryRequest = addressIterator1.next();
                System.out.println("ID : " + deliveryRequest.getPickupLocation().getID()+ " Latitude : " + deliveryRequest.getPickupLocation().getLatitude() + " Longitude : " + deliveryRequest.getPickupLocation().getLongitude() + " ID : " + deliveryRequest.getDeliveryLocation().getID()+ " Latitude : " + deliveryRequest.getDeliveryLocation().getLatitude() + " Longitude : " + deliveryRequest.getDeliveryLocation().getLongitude() );
            }



        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("bbb");
        }

    }
}
*/

@Controller
public class OptimodController {

    @Autowired
    private OptimodService optimodService;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Node> listNode = optimodService.getAllNodes();
        Iterable<Segment> listSegment = optimodService.getAllSegments();
        Iterable<DeliveryRequest> listDeliveryRequest = optimodService.getAllDeliveryRequests();
        model.addAttribute("nodes", listNode);
        model.addAttribute("segments", listSegment);
        model.addAttribute("deliveryrequests", listDeliveryRequest);
        return "index";
    }

    @PostMapping("/loadMap")
    public ResponseEntity<Map<String, Object>> loadMap(@RequestParam("file") MultipartFile file) {
        try {
            String XMLFileName = saveUploadedFile(file);

            // Supprimer les données existantes
            optimodService.deleteAllNodes();
            optimodService.deleteAllSegments();

            // Charger les données
            optimodService.loadNode(XMLFileName);
            optimodService.loadSegment(XMLFileName);

            // Renvoyer les données mises à jour
            Map<String, Object> response = new HashMap<>();
            response.put("nodes", optimodService.getAllNodes()); // Renvoie les nodes
            response.put("segments", optimodService.getAllSegments()); // Renvoie les segments

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Erreur lors du chargement de la carte.",
                    "details", e.getMessage()
            ));
        }
    }

    @PostMapping("/loadDeliveryRequest")
    public ResponseEntity<Map<String, Object>> loadDeliveryRequest(@RequestParam("file") MultipartFile file) {
        try {
            String XMLFileName = saveUploadedFile(file);

            // Supprimer les données existantes
            optimodService.deleteAllDeliveryRequests();

            // Charger les données
            optimodService.loadDeliveryRequest(XMLFileName);

            // Renvoyer les données mises à jour
            Map<String, Object> response = new HashMap<>();
            response.put("deliveryrequests", optimodService.getAllDeliveryRequests()); // Renvoie les demandes de livraisons

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Erreur lors du chargement de la carte.",
                    "details", e.getMessage()
            ));
        }
    }



    private String saveUploadedFile(MultipartFile file) throws IOException {
        String tempFileName = System.getProperty("java.io.tmpdir") + file.getOriginalFilename();
        file.transferTo(new java.io.File(tempFileName));
        return tempFileName;
    }
}