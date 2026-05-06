package com.mo7s.academySystem.user.student;

import com.mo7s.academySystem.user.auth.RegisterRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {

    final private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentDto::new).toList();
    }

    public StudentDto getStudentById(Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return new StudentDto(student);
    }

    public StudentDto getStudentByUserName(String userName) {
        Student student = studentRepository.findByUserName(userName).orElseThrow(NoSuchElementException::new);
        return new StudentDto(student);
    }


    public StudentDto saveStudent(StudentDto studentDto) {
        Student saved = studentRepository.save(StudentDto.toEntity(studentDto));
        return new StudentDto(saved);
    }

    public StudentDto updateStudentById(Integer id, StudentDto newStudentDto) {
        Student student = studentRepository.findById(id).orElseThrow(NoSuchElementException::new);
        StudentDto oldStudentDto = new StudentDto(student);
        oldStudentDto = updateStudent(oldStudentDto, newStudentDto);
        Student updated = studentRepository.save(StudentDto.toEntity(oldStudentDto));
        return new StudentDto(updated);
    }

    public void deleteStudentById(Integer id) {
        studentRepository.deleteById(id);
    }

    private StudentDto updateStudent(StudentDto oldStudent, StudentDto newStudent) {
        Class<?> clazz = oldStudent.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.get(newStudent) != null) {
                        field.set(oldStudent, field.get(newStudent));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
        return oldStudent;
    }

    public StudentDto register(RegisterRequest request) {
        if(studentRepository.findByUserName("student_" + request.getUserName()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }
        if (studentRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }
        Student student = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName("student_" + request.getUserName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
        return new StudentDto(studentRepository.save(student));
    }
}
