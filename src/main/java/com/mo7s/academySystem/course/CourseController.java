package com.mo7s.academySystem.course;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    final private CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDto> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable Integer id){
        return courseService.getCourseById(id);
    }

    @PostMapping
    public CourseDto createCourse(@Valid @RequestBody CourseDto courseDto){
        return courseService.saveCourse(courseDto);
    }

    @PutMapping("/{id}")
    public CourseDto updateCourseById(@PathVariable Integer id, @Valid @RequestBody CourseDto courseDto) {
        return courseService.updateCourseById(id, courseDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCourseById(@PathVariable Integer id) {
        courseService.deleteCourseById(id);
    }



}
