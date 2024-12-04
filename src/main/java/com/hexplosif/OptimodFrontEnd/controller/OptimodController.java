package com.hexplosif.OptimodFrontEnd.controller;

import java.io.IOException;
import java.util.Map;

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

@Controller
public class OptimodController {

    @Autowired
    private OptimodProxy optimodProxy;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Node> listNode = optimodProxy.getAllNodes();
        Iterable<Segment> listSegment = optimodProxy.getAllSegments();
        Iterable<DeliveryRequest> listDeliveryRequest = optimodProxy.getAllDeliveryRequests();
        model.addAttribute("nodes", listNode);
        model.addAttribute("segments", listSegment);
        model.addAttribute("deliveryrequests", listDeliveryRequest);
        return "index";
    }

    @PostMapping("/loadMap")
    public ResponseEntity<Map<String, Object>> loadMap(@RequestParam("file") MultipartFile file) {
        try {

            Map<String, Object> response = optimodProxy.loadMap(file);

            if (response.containsKey("error")) {
                // Propager l'erreur du service en tant que réponse HTTP 500
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Erreur lors du chargement de la carte.",
                    "details", e.getMessage()
            ));
        }
    }

    @PostMapping("/loadDeliveryRequest")
    public ResponseEntity<Map<String, Object>> loadDeliveryRequest(@RequestParam("file") MultipartFile file) {
        try {

            Map<String, Object> response = optimodProxy.loadDeliveryRequest(file);

            if (response.containsKey("error")) {
                // Propager l'erreur du service en tant que réponse HTTP 500
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Erreur lors du chargement de la carte.",
                    "details", e.getMessage()
            ));
        }
    }

    @GetMapping("/deleteDeliveryRequest/{id}")
    public String deleteDeliveryRequestById(@PathVariable("id") Long id) {
        try {

            optimodProxy.deleteDeliveryRequestById(id);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "redirect:/";
    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        String tempFileName = System.getProperty("java.io.tmpdir") + file.getOriginalFilename();
        file.transferTo(new java.io.File(tempFileName));
        return tempFileName;
    }
}