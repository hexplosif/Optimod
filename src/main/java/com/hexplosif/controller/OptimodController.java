package com.hexplosif.controller;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
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
            File fichierXML = new File("src/main/java/com/hexplosif/ressources/" + XMLfilename);
            File mapXML = new File("src/main/java/com/hexplosif/ressources/" + XMLmap);

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

            // Liste pour stocker les tuples (enlèvement, livraison)
            List<String[]> adresses = new ArrayList<>();

            // Liste pour stocker les tuples (id, latitude, longitude)
            List<String[]> informationNoeuds = new ArrayList<>();

            // Liste pour stocker les tuples (origine, destination, longueur, nomRue)
            List<String[]> informationTroncons = new ArrayList<>();


            for (int i = 0; i < listeLivraisons.getLength(); i++) {
                Node livraison = listeLivraisons.item(i);

                if (livraison.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementLivraison = (Element) livraison;

                    // Extraire les attributs adresseEnlevement et adresseLivraison
                    String adresseEnlevement = elementLivraison.getAttribute("adresseEnlevement");
                    String adresseLivraison = elementLivraison.getAttribute("adresseLivraison");

                    // Ajouter dans la liste sous forme de tableau
                    adresses.add(new String[]{adresseEnlevement, adresseLivraison});
                }
            }

            for (int i = 0; i < listeNoeuds.getLength(); i++) {
                Node noeud = listeNoeuds.item(i);

                if (noeud.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementNoeud = (Element) noeud;

                    // Extraire les attributs id, latitude et longitude
                    String idNoeud = elementNoeud.getAttribute("id");
                    String latitudeNoeud = elementNoeud.getAttribute("latitude");
                    String longitudeNoeud = elementNoeud.getAttribute("longitude");

                    // Ajouter dans la liste sous forme de tableau
                    informationNoeuds.add(new String[]{idNoeud, latitudeNoeud, longitudeNoeud});
                }
            }

            for (int i = 0; i < listeTroncons.getLength(); i++) {
                Node troncon = listeTroncons.item(i);

                if (troncon.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementTroncon = (Element) troncon;

                    // Extraire les attributs origine, destination, longueur et nomRue
                    String origineTroncon = elementTroncon.getAttribute("origine");
                    String destinationTroncon = elementTroncon.getAttribute("destination");
                    String longueurTroncon = elementTroncon.getAttribute("longueur");
                    String nomRueTroncon = elementTroncon.getAttribute("nomRue");

                    // Ajouter dans la liste sous forme de tableau
                    informationTroncons.add(new String[]{origineTroncon, destinationTroncon, longueurTroncon, nomRueTroncon});
                }
            }


            System.out.println("Tuples (origine, destination, longueur, nomRue) :");
            for (String[] tuple : informationTroncons) {
                System.out.println("origine : " + tuple[0] + ", destination : " + tuple[1] + ", longueur : " + tuple[2] + ", nomRue : " + tuple[3]);
            }

            System.out.println("Tuples (id, latitude, longitude) :");
            for (String[] tuple : informationNoeuds) {
                System.out.println("id : " + tuple[0] + ", latitude : " + tuple[1] + ", longitude : " + tuple[2]);
            }

            System.out.println("Tuples (enlèvement, livraison) :");
            for (String[] tuple : adresses) {
                System.out.println("Enlèvement : " + tuple[0] + ", Livraison : " + tuple[1]);
            }



        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("bbb");
        }

    }
}

