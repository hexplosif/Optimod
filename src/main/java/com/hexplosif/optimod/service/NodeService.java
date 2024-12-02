package com.hexplosif.optimod.service;

import com.hexplosif.optimod.model.Node;
import com.hexplosif.optimod.proxy.NodeProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Node savedNode;
        if (node.getId() == null) {
            savedNode = nodeProxy.createNode(node);
        } else {
            savedNode = nodeProxy.saveNode(node);
        }
        return savedNode;
    }

    public void createNode(Node node) {
        nodeProxy.createNode(node);
    }

    public void deleteAllNodes() {
        nodeProxy.deleteAllNodes();
    }

    public void createNodes(List<Node> nodes) {
        nodeProxy.createNodes(nodes);
    }
}
