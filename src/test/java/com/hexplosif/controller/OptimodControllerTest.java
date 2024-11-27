package com.hexplosif.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OptimodControllerTest {

    @ParameterizedTest
    @ValueSource(strings = {"petitPlan.xml", "moyenPlan.xml", "grandPlan.xml"})
    public void loadMapTest(String mapFile) {

        OptimodController optimodController = new OptimodController();
        optimodController.loadMap(mapFile);
    }

    @ParameterizedTest
    @ValueSource(strings = {"demandePetit1.xml", "demandeMoyen3.xml", "demandeGrand7.xml"})
    public void loadDeliveryRequest(String xmlFilename) {

        OptimodController optimodController = new OptimodController();
        optimodController.loadDeliveryRequest(xmlFilename);
    }
}
