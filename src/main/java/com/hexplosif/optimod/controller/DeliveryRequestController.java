package com.hexplosif.optimod.controller;

import com.hexplosif.optimod.model.DeliveryRequest;
import com.hexplosif.optimod.service.DeliveryRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeliveryRequestController {

    @Autowired
    private DeliveryRequestService deliveryrequestService;

    @GetMapping("/deliveryrequests")
    public String home(Model model) {
        Iterable<DeliveryRequest> listDeliveryRequest = deliveryrequestService.getAllDeliveryRequests();
        model.addAttribute("deliveryrequests", listDeliveryRequest);
        return "index";
    }

    @GetMapping("/createDeliveryRequest")
    public String createDeliveryRequest(Model model) {
        DeliveryRequest e = new DeliveryRequest();
        model.addAttribute("deliveryrequest", e);
        return "formNewDeliveryRequest";
    }

    @GetMapping("/updateDeliveryRequest/{id}")
    public String updateDeliveryRequest(@PathVariable("id") final Long id, Model model) {
        DeliveryRequest e = deliveryrequestService.getDeliveryRequestById(id);
        model.addAttribute("deliveryrequest", e);
        return "formUpdateDeliveryRequest";
    }

    @GetMapping("/deleteDeliveryRequest/{id}")
    public ModelAndView deleteDeliveryRequest(@PathVariable("id") final Long id) {
        deliveryrequestService.deleteDeliveryRequestById(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveDeliveryRequest")
    public ModelAndView saveDeliveryRequest(@ModelAttribute DeliveryRequest deliveryrequest) {
        if (deliveryrequest.getId() != null) {
            DeliveryRequest e = deliveryrequestService.getDeliveryRequestById(deliveryrequest.getId());
            e.setIdDelivery(deliveryrequest.getIdDelivery());
            e.setIdPickup(deliveryrequest.getIdPickup());
            e.setIdWarehouse(deliveryrequest.getIdWarehouse());
            deliveryrequestService.saveDeliveryRequest(e);
        } else {
            deliveryrequestService.saveDeliveryRequest(deliveryrequest);
        }
        return new ModelAndView("redirect:/");
    }
}
