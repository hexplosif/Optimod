package com.hexplosif.model;

import com.hexplosif.controller.OptimodController;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

public class OptimodModelTest {

    @ParameterizedTest
    @ValueSource(strings = {"petitPlan.xml", "moyenPlan.xml", "grandPlan.xml"})
    public void loadMapTest(String mapFile) throws Exception {
        Nodes warehouse = new Nodes();
        warehouse.setNodesAttributes(0L,0,0);
        List<DeliveryRequest> deliveryRequestList = new ArrayList<>();
        OptimodModel optimodModel = new OptimodModel(warehouse, deliveryRequestList);
        optimodModel.loadMap(mapFile);
    }

    @ParameterizedTest
    @ValueSource(strings = {"demandePetit11.xml", "demandeMoyen3.xml", "demandeGrand7.xml"})
    public void loadDeliveryRequest(String xmlFilename) throws Exception {
        Nodes warehouse = new Nodes();
        warehouse.setNodesAttributes(0L,0,0);
        List<DeliveryRequest> deliveryRequestList = new ArrayList<>();
        OptimodModel optimodModel = new OptimodModel(warehouse, deliveryRequestList);
        optimodModel.loadDeliveries(xmlFilename);
    }
}
