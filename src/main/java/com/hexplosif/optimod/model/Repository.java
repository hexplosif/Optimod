package com.hexplosif.optimod.model;

import java.util.ArrayList;
import java.util.List;


public class Repository {
    private final List<String[]> adresses = new ArrayList<>();
    private final List<String[]> informationNoeuds = new ArrayList<>();
    private final List<String[]> informationTroncons = new ArrayList<>();
    private final List<DeliveryRequest> deliveryRequests = new ArrayList<>();
    private final List<Segment> segments = new ArrayList<>();


    public void addAdresses(String adresseEnlevement, String adresseLivraison) {
        adresses.add(new String[]{adresseEnlevement, adresseLivraison});
    }

    public void addInformationNoeuds(String id, String latitude, String longitude) {
        informationNoeuds.add(new String[]{id, latitude, longitude});
    }

    public void addInformationTroncons(String origine, String destination, String longueur, String nomRue) {
        informationTroncons.add(new String[]{origine, destination, longueur, nomRue});
    }

    public void addDeliveryRequests(DeliveryRequest deliveryRequest) {
        deliveryRequests.add(deliveryRequest);
    }

    public void addSegments(Segment segment) {
        segments.add(segment);
    }


    public Iterator<String[]> getIteratorAdresses() {
        return new IteratorImpl<>(adresses);
    }

    public Iterator<String[]> getIteratorInformationNoeuds() {
        return new IteratorImpl<>(informationNoeuds);
    }

    public Iterator<String[]> getIteratorInformationTroncons() {
        return new IteratorImpl<>(informationTroncons);
    }

    public IteratorImpl<DeliveryRequest> getIteratorDeliveryRequests() {
        return new IteratorImpl<>(deliveryRequests);
    }

    public IteratorImpl<Segment> getIteratorSegments() {
        return new IteratorImpl<>(segments);
    }
}


