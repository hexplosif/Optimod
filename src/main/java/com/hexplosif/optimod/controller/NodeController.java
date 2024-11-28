package com.hexplosif.optimod.controller;

import com.hexplosif.optimod.model.Node;
import com.hexplosif.optimod.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/nodes")
    public String nodes(Model model) {
        model.addAttribute("nodes", nodeService.getAllNodes());
        return "nodes";
    }

    @GetMapping("/node/{id}")
    public Node getNodeById(@PathVariable("id") final Long id) {
        return nodeService.getNodeById(id);
    }

    @GetMapping("/deleteNode/{id}")
    public ModelAndView deleteNode(@PathVariable("id") final Long id) {
        nodeService.deleteNodeById(id);
        return new ModelAndView("redirect:/nodes");
    }

    @GetMapping("/editNode/{id}")
    public String editNode(@PathVariable("id") final Long id, Model model) {
        model.addAttribute("node", nodeService.getNodeById(id));
        return "editNode";
    }

    @GetMapping("/addNode")
    public String addNode(Node node) {
        nodeService.saveNode(node);
        return "redirect:/nodes";
    }
}
