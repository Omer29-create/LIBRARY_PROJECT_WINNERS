package com.winners.libraryproject.controller;

import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.entity.Publisher;
import com.winners.libraryproject.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Publisher>> findAllPublishers() {
        List<Publisher> publisher = publisherService.findAllPublishers();
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @GetMapping("/publisher/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Publisher>> findPublisherById(@PathVariable Long id) {
        Optional<Publisher> publisher = publisherService.findPublisherById(id);
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> createPublisher(@RequestBody Publisher publisher){
        publisherService.createPublisher(publisher);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Loan created successfully", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> updatePublisher(@Valid @PathVariable Long id, @RequestBody Publisher publisher){
        publisherService.updatedPublisher(id, publisher);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePublisher(@PathVariable("id") Long id){
        publisherService.deletePublisherById(id);

        return new ResponseEntity<>("success",HttpStatus.OK);
    }
}
