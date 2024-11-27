package com.hexplosif.controller;

import com.hexplosif.model.Map;
import com.hexplosif.model.Node;
import com.hexplosif.model.Segment;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

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
