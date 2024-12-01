package com.hexplosif.optimod.controller;

import com.hexplosif.optimod.model.Segment;
import com.hexplosif.optimod.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SegmentController {

    @Autowired
    private SegmentService segmentService;

    @GetMapping("/segments")
    public String home(Model model) {
        Iterable<Segment> listSegment = segmentService.getAllSegments();
        model.addAttribute("segments", listSegment);
        return "index";
    }

    @GetMapping("/createSegment")
    public String createSegment(Model model) {
        Segment e = new Segment();
        model.addAttribute("segment", e);
        return "formNewSegment";
    }

    @GetMapping("/updateSegment/{id}")
    public String updateSegment(@PathVariable("id") final Long id, Model model) {
        Segment e = segmentService.getSegmentById(id);
        model.addAttribute("segment", e);
        return "formUpdateSegment";
    }

    @GetMapping("/deleteSegment/{id}")
    public ModelAndView deleteSegment(@PathVariable("id") final Long id) {
        segmentService.deleteSegmentById(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveSegment")
    public ModelAndView saveSegment(@ModelAttribute Segment segment) {
        if (segment.getId() != null) {
            Segment e = segmentService.getSegmentById(segment.getId());
            e.setIdOrigin(segment.getIdOrigin());
            e.setIdDestination(segment.getIdDestination());
            e.setLength(segment.getLength());
            e.setName(segment.getName());
            segmentService.saveSegment(e);
        } else {
            segmentService.saveSegment(segment);
        }
        return new ModelAndView("redirect:/");
    }
}
