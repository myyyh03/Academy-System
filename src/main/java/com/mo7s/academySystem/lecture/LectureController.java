package com.mo7s.academySystem.lecture;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    final private LectureService lectureService;

    public LectureController(LectureService lectureService){
        this.lectureService = lectureService;
    }


    @GetMapping
    public List<LectureDto> getAllLectures(){
        return lectureService.getAllLectures();
    }

    @GetMapping("/{id}")
    public LectureDto getLectureById(@PathVariable Integer id){
        return lectureService.getLectureById(id);
    }

    @PostMapping
    public LectureDto createLecture(@Valid @RequestBody LectureDto lecture){
        return lectureService.createLecture(lecture);
    }

    @PutMapping("/{id}")
    public LectureDto updateLectureById(@PathVariable Integer id, @Valid @RequestBody LectureDto lecture){
        return lectureService.updateLectureById(id, lecture);
    }

    @DeleteMapping("/{id}")
    public void deleteLectureById(@PathVariable Integer id){
        lectureService.deleteLectureById(id);
    }

}
