package com.hexplosif.optimod.controller;

import com.hexplosif.optimod.model.Node;
import com.hexplosif.optimod.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Node> listNode = nodeService.getAllNodes();
        model.addAttribute("nodes", listNode);
        return "index";
    }

    @GetMapping("/createNode")
    public String createNode(Model model) {
        Node e = new Node();
        model.addAttribute("node", e);
        return "formNewNode";
    }

    @GetMapping("/updateNode/{id}")
    public String updateNode(@PathVariable("id") final Long id, Model model) {
        Node e = nodeService.getNodeById(id);
        model.addAttribute("node", e);
        return "formUpdateNode";
    }

    @GetMapping("/deleteNode/{id}")
        public ModelAndView deleteNode(@PathVariable("id") final Long id) {
        nodeService.deleteNodeById(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveNode")
    public ModelAndView saveNode(@ModelAttribute Node node) {
        if (node.getId() != null) {
            Node e = nodeService.getNodeById(node.getId());
            e.setLatitude(node.getLatitude());
            e.setLongitude(node.getLongitude());
            nodeService.saveNode(e);
        } else {
            nodeService.saveNode(node);
        }
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/map")
    public String showMap(Model model) {
        List<Node> nodes = (List<Node>) nodeService.getAllNodes();
        model.addAttribute("nodes", nodes);
        return "map";
    }
}
