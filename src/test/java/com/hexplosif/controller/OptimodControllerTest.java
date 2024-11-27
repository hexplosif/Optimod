package com.hexplosif.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OptimodControllerTest {

    @ParameterizedTest
    @ValueSource(strings = {"petitPlan.xml", "moyenPlan.xml", "grandPlan.xml"})
    public void loadMapTest(String mapFile) throws Exception {

        OptimodController optimodController = new OptimodController();
        optimodController.loadMap(mapFile);
    }

    @ParameterizedTest
    @ValueSource(strings = {"demandePetit11.xml", "demandeMoyen3.xml", "demandeGrand7.xml"})
    public void loadDeliveryRequest(String xmlFilename) throws Exception {

        OptimodController optimodController = new OptimodController();
        optimodController.loadDeliveryRequest(xmlFilename);
    }
}
