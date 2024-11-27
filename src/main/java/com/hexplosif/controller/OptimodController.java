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
    public void loadDeliveryRequest(String XMLfilename) {

        try {
            // Load the deliveries from the XML file
            File fichierXML = new File("../ressources/" + XMLfilename);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(fichierXML);

            document.getDocumentElement().normalize();

            String nomRacine = document.getDocumentElement().getNodeName();
            if (!"demandeDeLivraisons".equals(nomRacine)) {
                System.err.println("Erreur : le nom de l'élément racine n'est pas 'demandeDeLivraisons'.");
                System.exit(1);
            }

            NodeList listeLivraisons = document.getElementsByTagName("livraison");

            // Liste pour stocker les tuples (enlèvement, livraison)
            List<String[]> adresses = new ArrayList<>();

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

            // Afficher les résultats
            System.out.println("Tuples (enlèvement, livraison) :");
            for (String[] tuple : adresses) {
                System.out.println("Enlèvement : " + tuple[0] + ", Livraison : " + tuple[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

