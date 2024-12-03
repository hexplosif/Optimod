package com.hexplosif.optimod.proxy;

import com.hexplosif.optimod.CustomProperties;
import com.hexplosif.optimod.model.Segment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Repository
public class SegmentProxy {

    @Autowired
    private CustomProperties customProperties;

    /**
     * Get all segments from the API
     * @return An iterable of segments
     */
    public Iterable<Segment> getAllSegments() {
        String apiUrl = customProperties.getApiUrl();
        String getAllSegmentsUrl = apiUrl + "/segments";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Segment>> response = restTemplate.exchange(
                getAllSegmentsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Segment>>() {
                }
        );

        log.debug("Get all segments called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Get a segment by its id
     * @param id The id of the segment
     * @return The segment
     */
    public Segment getSegmentById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String getSegmentByIdUrl = apiUrl + "/segment/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Segment> response = restTemplate.exchange(
                getSegmentByIdUrl,
                HttpMethod.GET,
                null,
                Segment.class
        );

        log.debug("Get segment by id called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Create a segment in the API
     * @param segment The segment to create
     * @return The created segment
     */
    public Segment createSegment(Segment segment) {
        String apiUrl = customProperties.getApiUrl();
        String createSegmentUrl = apiUrl + "/segment";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Segment> request = new HttpEntity<Segment>(segment);
        ResponseEntity<Segment> response = restTemplate.postForEntity(
                createSegmentUrl,
                request,
                Segment.class
        );

        log.debug("Create segment called with response: " + response.toString());

        return response.getBody();
    }

    /**
     * Delete a segment by its id
     * @param id The id of the segment
     */
    public void deleteSegmentById(Long id) {
        String apiUrl = customProperties.getApiUrl();
        String deleteSegmentByIdUrl = apiUrl + "/segment/" + id;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteSegmentByIdUrl);

        log.debug("Delete segment by id called");
    }

    /**
     * Save a segment in the API
     * @param segment The segment to save
     * @return The saved segment
     */
    public Segment saveSegment(Segment segment) {
        String apiUrl = customProperties.getApiUrl();
        String saveSegmentUrl = apiUrl + "/segment/" + segment.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Segment> request = new HttpEntity<Segment>(segment);
        ResponseEntity<Segment> response = restTemplate.exchange(
                saveSegmentUrl,
                HttpMethod.PUT,
                request,
                Segment.class
        );

        log.debug("Save segment called with response: " + response.toString());

        return response.getBody();
    }

    public void deleteAllSegments() {
        String apiUrl = customProperties.getApiUrl();
        String deleteAllSegmentsUrl = apiUrl + "/segments";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(deleteAllSegmentsUrl);

        log.debug("Delete all segments called");
    }

    public void createSegments(List<Segment> tmpListSegments) {
        String apiUrl = customProperties.getApiUrl();
        String createSegmentsUrl = apiUrl + "/segments";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<List<Segment>> request = new HttpEntity<List<Segment>>(tmpListSegments);
        ResponseEntity<List<Segment>> response = restTemplate.exchange(
                createSegmentsUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<Segment>>() {
                }
        );
    }
}
