package com.hexplosif.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
public class MapTest {

    @ParameterizedTest
    @ValueSource(strings = {"petitPlan.xml", "moyenPlan.xml", "grandPlan.xml"})
    public void loadMapTest(String mapFile) throws Exception {
        List< Nodes > nodes = new ArrayList<>();
        List< Segment > segments = new ArrayList<>();
        Map map = new Map(nodes, segments);
        map.loadMap(mapFile);
    }
}
