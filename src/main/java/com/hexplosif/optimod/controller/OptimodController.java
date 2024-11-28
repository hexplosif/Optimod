package com.hexplosif.optimod.controller;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.hexplosif.optimod.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class OptimodController {

    public void loadMap(String XMLfilename) {
        // Load the map from the XML file
    }

    private Document parseXMLFile(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    public void loadDeliveryRequest(String XMLmap, String XMLfilename) throws Exception {

        try {
            // Load the deliveries from the XML file
            File fichierXML = new File("src/main/java/com/hexplosif/optimod/ressources/" + XMLfilename);
            File mapXML = new File("src/main/java/com/hexplosif/optimod/ressources/" + XMLmap);

            Document documentDeliveryRequests = parseXMLFile(fichierXML);
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
            NodeList listeNoeuds = documentMap.getElementsByTagName("noeud");
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

            for (int i = 0; i < listeNoeuds.getLength(); i++) {
                org.w3c.dom.Node noeud = listeNoeuds.item(i);

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
/*
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


*/

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("bbb");
        }

    }
}

