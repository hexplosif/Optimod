package com.hexplosif.optimod.service;

import com.hexplosif.optimod.model.Segment;
import com.hexplosif.optimod.repository.SegmentProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class SegmentService {

    @Autowired
    private SegmentProxy segmentProxy;

    public Segment getSegmentById(Long id) {
        return segmentProxy.getSegmentById(id);
    }

    public Iterable<Segment> getAllSegments() {
        return segmentProxy.getAllSegments();
    }

    public void deleteSegmentById(Long id) {
        segmentProxy.deleteSegmentById(id);
    }

    public Segment saveSegment(Segment segment) {
        return segmentProxy.saveSegment(segment);
    }
}
