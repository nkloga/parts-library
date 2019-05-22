package com.javarush.Parts.service;

import com.javarush.Parts.model.Part;
import com.javarush.Parts.repository.PartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartsServiceImpl implements PartsService {

    private final PartsRepository partsRepository;

    @Autowired
    public PartsServiceImpl(PartsRepository partsRepository) {
        this.partsRepository = partsRepository;
    }

    @Override
    public Integer amountOfPcsCanBeAssembled() {
        List<Part> parts = partsRepository.findAll();
        if (parts.size() > 1) {
            parts = partsRepository.findAll().stream().filter(Part::getNecessity).collect(Collectors.toList());
            if (parts.size() > 0) {
                return parts.stream().min(Comparator.comparing(Part::getQuantity)).get().getQuantity();
            } else {
                return 0;
            }
        } else if (parts.size() == 1 && parts.get(0).getNecessity()) {
            return parts.get(0).getQuantity();
        } else return 0;
    }
    @Override
    public Part save(Part part) {
        return partsRepository.save(part);
    }

    @Override
    public List<Part> findAll() {
        return partsRepository.findAll();
    }

    @Override
    public Part findById(Long id) {
        return partsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid part Id:" + id));
    }

    @Override
    public void delete(Part part) {
        partsRepository.delete(part);
    }

    @Override
    public Page<Part> findPaginated(Pageable pageable) {
        List<Part> parts = partsRepository.findAll();
        return getPages(parts, pageable);
    }

    @Override
    public Page<Part> findPaginatedOptional(Pageable pageable) {
        List<Part> parts = partsRepository.findByNecessity(false);
        return getPages(parts, pageable);
    }

    @Override
    public Page<Part> findPaginatedNeccesary(Pageable pageable) {
        List<Part> parts = partsRepository.findByNecessity(true);
        return getPages(parts, pageable);
    }

    @Override
    public Page<Part> findByNamePaginated(Pageable pageable, String name) {
        List<Part> parts = partsRepository.findByName(name.toLowerCase());
        return getPages(parts, pageable);
    }

    private Page<Part> getPages(List<Part> parts, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Part> list;

        if (parts.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, parts.size());
            list = parts.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), parts.size());
    }
}

