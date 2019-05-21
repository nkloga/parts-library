package com.javarush.Parts.service;

import com.javarush.Parts.model.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartsService {

    Integer amountOfPcsCanBeAssembled();
    Part save(Part part);
    List<Part> findAll();
    Part findById(Long id);
    void delete(Part part);
    Page<Part> findPaginated(Pageable pageable);
    Page<Part> findPaginatedOptional(Pageable pageable);
    Page<Part> findPaginatedNeccesary(Pageable pageable);
    Page<Part> findByNamePaginated(Pageable pageable, String name);
}

