package com.hexplosif.OptimodFrontEnd.controller;

import java.util.List;
import java.util.Map;

import com.hexplosif.OptimodFrontEnd.model.Courier;
import com.hexplosif.OptimodFrontEnd.model.DeliveryRequest;
import com.hexplosif.OptimodFrontEnd.model.Node;
import com.hexplosif.OptimodFrontEnd.model.Segment;
import com.hexplosif.OptimodFrontEnd.proxy.OptimodProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OptimodController {

    @Autowired
    private OptimodProxy optimodProxy;

    @GetMapping("/")
    public ModelAndView home(Model model) {
        populateModel(model);
        return new ModelAndView("index");
    }

    @PostMapping("/loadMap")
    public ModelAndView loadMap(@RequestParam("file") MultipartFile file, Model model) {
        try {
            optimodProxy.loadMap(file);
            model.addAttribute("success", "The map has been successfully loaded !");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while loading the map");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    @PostMapping("/loadDeliveryRequest")
    public ModelAndView loadDeliveryRequest(@RequestParam("file") MultipartFile file, Model model) {
        try {
            optimodProxy.loadDeliveryRequest(file);
            model.addAttribute("success", "The delivery request(s) has been successfully loaded !");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while loading the delivery request(s)");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    @GetMapping("/deleteAllNodes")
    public ModelAndView deleteAllNodes(Model model) {
        try {
            optimodProxy.deleteAllNodes();
            model.addAttribute("success", "The nodes have been successfully deleted !");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while deleting the nodes");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    @GetMapping("/deleteDeliveryRequest/{id}")
    public ModelAndView deleteDeliveryRequest(@PathVariable("id") final Long id, Model model) {
        try {
            optimodProxy.deleteDeliveryRequestById(id);
            model.addAttribute("success", "The delivery request has been successfully deleted !");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while deleting the delivery request");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    @GetMapping("/deleteAllDeliveryRequests")
    public ModelAndView deleteAllDeliveryRequests(Model model) {
        try {
            optimodProxy.deleteAllDeliveryRequests();
            model.addAttribute("success", "The delivery requests have been successfully deleted !");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while deleting the delivery requests");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    @GetMapping("/addCourier")
    public ModelAndView addCourrier(Model model) {
        try {
            optimodProxy.addCourier();
            model.addAttribute("success", "The courier has been successfully added !");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while adding the courier");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    @GetMapping("/deleteCourier")
    public ModelAndView deleteCourrier(Model model) {
        try {
            optimodProxy.deleteCourier();
            model.addAttribute("success", "The courier has been successfully deleted !");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while deleting the courier");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    @GetMapping("/deleteCourier/{id}")
    public ModelAndView deleteCourrier(@PathVariable("id") final Long id, Model model) {
        try {
            optimodProxy.deleteCourierById(id);
            model.addAttribute("success", "The courier has been successfully deleted !");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while deleting the courier");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    @PostMapping("/assignCourier")
    public ModelAndView assignCourier(@RequestParam("courierId") Long courierId, @RequestParam("deliveryRequestId") Long deliveryRequestId, Model model) {
        try {
            optimodProxy.assignCourier(courierId, deliveryRequestId);
            model.addAttribute("success", "The courier has been successfully assigned to the delivery request !");
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while assigning courier to delivery request");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    @GetMapping("/calculateOptimalRoute")
    public ModelAndView calculateOptimalRoute(Model model) {
        try {
            List<List<Long>> optimalRoutes = optimodProxy.calculateOptimalRoute();
            model.addAttribute("success", "The optimal route has been calculated successfully !");
            model.addAttribute("optimalRoutes", optimalRoutes);
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error while calculating the optimal route");
            model.addAttribute("details", e.getMessage());
        }
        populateModel(model);
        return new ModelAndView("index");
    }

    private void populateModel(Model model) {
        model.addAttribute("nodes", optimodProxy.getAllNodes());
        model.addAttribute("segments", optimodProxy.getAllSegments());
        model.addAttribute("deliveryRequests", optimodProxy.getAllDeliveryRequests());
        model.addAttribute("couriers", optimodProxy.getAllCouriers());
    }
}