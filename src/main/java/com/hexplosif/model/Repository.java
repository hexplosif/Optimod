package com.hexplosif.model;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private List<String[]> adresses = new ArrayList<>();
    private List<String[]> informationNoeuds = new ArrayList<>();
    private List<String[]> informationTroncons = new ArrayList<>();


    public void addAdresses(String adresseEnlevement, String adresseLivraison) {
        adresses.add(new String[]{adresseEnlevement, adresseLivraison});
    }

    public void addInformationNoeuds(String id, String latitude, String longitude) {
        informationNoeuds.add(new String[]{id, latitude, longitude});
    }

    public void addInformationTroncons(String origine, String destination, String longueur, String nomRue) {
        informationTroncons.add(new String[]{origine, destination, longueur, nomRue});
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
}


