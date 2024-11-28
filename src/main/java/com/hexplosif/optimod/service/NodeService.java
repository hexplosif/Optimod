package com.hexplosif.optimod.service;

import com.hexplosif.optimod.model.Node;
import com.hexplosif.optimod.repository.NodeProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class NodeService {

    @Autowired
    private NodeProxy nodeProxy;

    public Node getNodeById(Long id) {
        return nodeProxy.getNodeById(id);
    }

    public Iterable<Node> getAllNodes() {
        return nodeProxy.getAllNodes();
    }

    public void deleteNodeById(Long id) {
        nodeProxy.deleteNodeById(id);
    }

    public Node saveNode(Node node) {
        return nodeProxy.saveNode(node);
    }
}
