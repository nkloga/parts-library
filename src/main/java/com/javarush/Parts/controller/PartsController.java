package com.javarush.Parts.controller;

import com.javarush.Parts.model.Part;
import com.javarush.Parts.service.PartsService;
import com.javarush.Parts.service.Display;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.javarush.Parts.service.Display.BYNAME;
import static com.javarush.Parts.service.Display.NECESSARY;
import static com.javarush.Parts.service.Display.OPTIONAL;

@Controller
public class PartsController {

    private final PartsService partsService;
    private final Optional<Integer> STARTPAGE = Optional.of(1);
    private final Optional<Integer> DISPLAYPAGE = Optional.of(10);

    @Autowired
    public PartsController(PartsService partsService) {
        this.partsService = partsService;
    }

    @GetMapping("/")
    public String showParts(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(STARTPAGE.get());
        int pageSize = size.orElse(DISPLAYPAGE.get());
        Display display = (Display) model.asMap().get("display");
        Page<Part> bookPage = null;
        if (display == null) {
            bookPage = partsService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        } else if (display == OPTIONAL) {
            bookPage = partsService.findPaginatedOptional(PageRequest.of(currentPage - 1, pageSize));
        } else if (display == NECESSARY) {
            bookPage = partsService.findPaginatedNeccesary(PageRequest.of(currentPage - 1, pageSize));
        } else if (display == BYNAME) {
            bookPage = partsService.findByNamePaginated(PageRequest.of(currentPage - 1, pageSize), (String) model.asMap().get("name"));
        }

        model.addAttribute("partPage", bookPage);
        int totalPages = bookPage.getTotalPages();
        model.addAttribute("pc", partsService.amountOfPcsCanBeAssembled());
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

    @GetMapping("/addpart")
    public String addNewPart(Model model) {
        Part part = partsService.save(new Part());
        model.addAttribute("part", part);
        return "update-part";
    }

    @PostMapping("/update/{id}")
    public String updatePart(@PathVariable("id") long id, @Valid Part part, Model model) {
        if (part.getQuantity() == null) {
            part.setQuantity(0);
        }
        partsService.save(part);
        showParts(model, STARTPAGE, DISPLAYPAGE);
        return "index";
    }

    @GetMapping("/necessary")
    public String showNecessaryParts(Model model) {
        model.addAttribute("display", Display.NECESSARY);
        showParts(model, STARTPAGE, DISPLAYPAGE);
        return "index";
    }

    @GetMapping("/optional")
    public String showOptionalParts(Model model) {
        model.addAttribute("display", OPTIONAL);
        showParts(model, STARTPAGE, DISPLAYPAGE);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Part part = partsService.findById(id);
        model.addAttribute("part", part);
        return "update-part";
    }

    @PostMapping("/get")
    public String findPartByName(@RequestParam String sourceText, Model model) {
        model.addAttribute("display", BYNAME);
        model.addAttribute("name", sourceText);
        showParts(model, STARTPAGE, DISPLAYPAGE);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deletePart(@PathVariable("id") long id, Model model) {
        Part part = partsService.findById(id);
        partsService.delete(part);
        showParts(model, STARTPAGE, DISPLAYPAGE);
        return "index";
    }
}