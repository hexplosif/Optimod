package com.hexplosif.service;

import com.hexplosif.optimod.service.DeliveryRequestService;
import com.hexplosif.optimod.service.NodeService;
import com.hexplosif.optimod.service.OptimodService;
import com.hexplosif.optimod.service.SegmentService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
public class OptimodServiceTest {

    @TestConfiguration
    static class OptimodServiceTestContextConfiguration {
        @Bean
        public OptimodService optimodService() {
            return new OptimodService();
        }
    }

    @Autowired
    private OptimodService optimodService;

    @MockBean
    private NodeService nodeService;

    @MockBean
    private SegmentService segmentService;

    @MockBean
    private DeliveryRequestService deliveryRequestService;

    @ParameterizedTest
    @ValueSource(strings = {"petitPlan.xml", "moyenPlan.xml", "grandPlan.xml"})
    public void loadNodeTest(String fileName) {
        String XMLFileName = "src/main/java/com/hexplosif/optimod/ressources/" + fileName;
        try {
            optimodService.loadNode(XMLFileName);
        } catch (Exception e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"petitPlan.xml", "moyenPlan.xml", "grandPlan.xml"})
    public void loadSegmentTest(String fileName) {
        String XMLFileName = "src/main/java/com/hexplosif/optimod/ressources/" + fileName;
        try {
            optimodService.loadSegment(XMLFileName);
        } catch (Exception e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"demandePetit1.xml", "demandeMoyen3.xml", "demandeGrand7.xml"})
    public void loadDeliveryRequestTest(String fileName) {
        String XMLFileName = "src/main/java/com/hexplosif/optimod/ressources/" + fileName;
        try {
            optimodService.loadDeliveryRequest(XMLFileName);
        } catch (Exception e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }

}