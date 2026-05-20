package com.mo7s.academySystem.course;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class CourseService {
    final private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

   // @Cacheable(value = "courses", key = "'all'")
    public List<CourseDto> getAllCourses(){
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(CourseDto::new).toList();
    }

   // @Cacheable(value = "courses", key = "#id")
    public CourseDto getCourseById(Integer id){
        Course course = courseRepository.findById(id).orElseThrow();
        return new CourseDto(course);
    }

//    @Caching(
//        put   = { @CachePut(value = "courses", key = "#result.id") },
//        evict = { @CacheEvict(value = "courses", key = "'all'") }
//    )
    public CourseDto saveCourse(CourseDto courseDto){
        Course savedCourse = courseRepository.save(CourseDto.toEntity(courseDto));
        return new CourseDto(savedCourse);
    }

//    @Caching(
//        put   = { @CachePut(value = "courses", key = "#id") },
//        evict = { @CacheEvict(value = "courses", key = "'all'") }
//    )
    public CourseDto updateCourseById(Integer id, CourseDto newCourseDto) {
        Course courseToUpdate = courseRepository.findById(id).orElseThrow();
        CourseDto courseDtoToUpdate = new CourseDto(courseToUpdate);
        courseDtoToUpdate = updateCourse(courseDtoToUpdate, newCourseDto);
        Course updatedCourse = courseRepository.save(CourseDto.toEntity(courseDtoToUpdate));
        return new CourseDto(updatedCourse);
    }

//    @Caching(evict = {
//        @CacheEvict(value = "courses", key = "#id"),
//        @CacheEvict(value = "courses", key = "'all'")
//    })
    public void deleteCourseById(Integer id) {
        courseRepository.deleteById(id);
    }

    private CourseDto updateCourse(CourseDto oldCourseDto, CourseDto newCourseDto){
        for(Field field : newCourseDto.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try{
                if(field.get(newCourseDto) != null){
                    field.set(oldCourseDto, field.get(newCourseDto));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error occurred while updating course", e);
            }
        }
        return oldCourseDto;
    }
}