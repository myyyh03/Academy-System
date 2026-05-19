package com.mo7s.academySystem.lecture;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LectureService {
    final private LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository){
        this.lectureRepository = lectureRepository;
    }

    @Cacheable(value = "lectures", key = "'all'")
    public List<LectureDto> getAllLectures(){
        List<Lecture> lectures = lectureRepository.findAll();
        return lectures.stream().map(LectureDto::new).toList();
    }

    @Cacheable(value = "lectures", key = "#id")
    public LectureDto getLectureById(Integer id){
        Lecture lecture = lectureRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return new LectureDto(lecture);
    }

    @Caching(
        put   = { @CachePut(value = "lectures", key = "#result.id") },
        evict = { @CacheEvict(value = "lectures", key = "'all'") }
    )
    public LectureDto createLecture(LectureDto lectureDto){
        Lecture saved = lectureRepository.save(LectureDto.toEntity(lectureDto));
        return new LectureDto(saved);
    }

    @Caching(
        put   = { @CachePut(value = "lectures", key = "#id") },
        evict = { @CacheEvict(value = "lectures", key = "'all'") }
    )
    public LectureDto updateLectureById(Integer id, LectureDto newLectureDto){
        Lecture lecture = lectureRepository.findById(id).orElseThrow(NoSuchElementException::new);
        LectureDto oldLectureDto = new LectureDto(lecture);
        oldLectureDto = updateLecture(oldLectureDto, newLectureDto);
        Lecture updated = lectureRepository.save(LectureDto.toEntity(oldLectureDto));
        return new LectureDto(updated);
    }

    @Caching(evict = {
        @CacheEvict(value = "lectures", key = "#id"),
        @CacheEvict(value = "lectures", key = "'all'")
    })
    public void deleteLectureById(Integer id){
        lectureRepository.deleteById(id);
    }

    private LectureDto updateLecture(LectureDto oldLecture, LectureDto newLecture){
        for(Field field : newLecture.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                Object newValue = field.get(newLecture);
                if(newValue != null){
                    field.set(oldLecture, newValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return oldLecture;
    }
}
