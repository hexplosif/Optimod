package com.hexplosif.model;

import java.util.ArrayList;
import java.util.List;

import com.hexplosif.optimod.model.Map;
import com.hexplosif.optimod.model.Node;
import com.hexplosif.optimod.model.Segment;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
public class MapTest {

    @ParameterizedTest
    @ValueSource(strings = {"petitPlan.xml", "moyenPlan.xml", "grandPlan.xml"})
    public void loadMapTest(String mapFile) throws Exception {
        List<Node> nodes = new ArrayList<>();
        List<Segment> segments = new ArrayList<>();
        Map map = new Map(nodes, segments);
        map.loadMap(mapFile);
    }
}
