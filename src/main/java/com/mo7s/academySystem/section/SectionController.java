package com.mo7s.academySystem.section;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {

    final private SectionService sectionService;

    public SectionController(SectionService sectionService){
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionDto> getAllSections(){
        return sectionService.getAllSections();
    }

    @GetMapping("/{id}")
    public SectionDto getSectionById(@PathVariable Integer id){
        return sectionService.getSectionById(id);
    }

    @PostMapping
    public SectionDto createSection(@Valid @RequestBody SectionDto section){
        return sectionService.createSection(section);
    }

    @PutMapping("/{id}")
    public SectionDto updateSectionById(@PathVariable Integer id , @Valid @RequestBody SectionDto section){
        return sectionService.updateSectionById(id , section);
    }

    @DeleteMapping("/{id}")
    public void deleteSectionById(@PathVariable Integer id){
        sectionService.deleteSectionById(id);
    }

}
