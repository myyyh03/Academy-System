package com.mo7s.academySystem.section;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SectionService {
    final private SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository){
        this.sectionRepository = sectionRepository;
    }

    @Cacheable(value = "sections", key = "'all'")
    public List<SectionDto> getAllSections(){
        List<Section> sections = sectionRepository.findAll();
        return sections.stream().map(SectionDto::new).toList();
    }

    @Cacheable(value = "sections", key = "#id")
    public SectionDto getSectionById(Integer id){
        Section section = sectionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return new SectionDto(section);
    }

    @Caching(
        put   = { @CachePut(value = "sections", key = "#result.id") },
        evict = { @CacheEvict(value = "sections", key = "'all'") }
    )
    public SectionDto createSection(SectionDto sectionDto){
        Section saved = sectionRepository.save(SectionDto.toEntity(sectionDto));
        return new SectionDto(saved);
    }

    @Caching(
        put   = { @CachePut(value = "sections", key = "#id") },
        evict = { @CacheEvict(value = "sections", key = "'all'") }
    )
    public SectionDto updateSectionById(Integer id, SectionDto newSectionDto){
        Section section = sectionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        SectionDto oldSectionDto = new SectionDto(section);
        oldSectionDto = updateSection(oldSectionDto, newSectionDto);
        Section updated = sectionRepository.save(SectionDto.toEntity(oldSectionDto));
        return new SectionDto(updated);
    }

    @Caching(evict = {
        @CacheEvict(value = "sections", key = "#id"),
        @CacheEvict(value = "sections", key = "'all'")
    })
    public void deleteSectionById(Integer id){
        sectionRepository.deleteById(id);
    }

    private SectionDto updateSection(SectionDto oldSection, SectionDto newSection){
        for(Field field : newSection.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if(field.get(newSection) != null){
                    field.set(oldSection, field.get(newSection));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return oldSection;
    }
}