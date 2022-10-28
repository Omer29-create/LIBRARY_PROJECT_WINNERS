package com.winners.libraryproject.service;

import com.winners.libraryproject.entity.Publisher;
import com.winners.libraryproject.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    private final static String PUBLISHER_NOT_FOUND = "Publisher with id %d not found";

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    public Optional<Publisher> findPublisherById(Long id) {
        return publisherRepository.findById(id);
    }

    public void createPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void updatedPublisher(Long id, Publisher publisher) {
        Optional<Publisher> publisherDetails = publisherRepository.findById(id);
        if (publisherDetails.isPresent()) {
            Publisher newPublisher = new Publisher(publisher.getId(), publisher.getName(), publisher.getBuiltIn(), publisher.getBooks());
        }
    }

    public void deletePublisherById(Long id) {
        publisherRepository.deleteById(id.getId());
    }
}
