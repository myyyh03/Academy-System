package com.mo7s.academySystem.user;


import com.mo7s.academySystem.user.admin.AdminDto;
import com.mo7s.academySystem.user.admin.AdminService;
import com.mo7s.academySystem.user.auth.RegisterRequest;
import com.mo7s.academySystem.user.author.AuthorDto;
import com.mo7s.academySystem.user.author.AuthorService;
import com.mo7s.academySystem.user.student.StudentDto;
import com.mo7s.academySystem.user.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final StudentService studentService;
    private final AuthorService authorService;
    private final AdminService adminService;

    public User getUserByUserName(String username){

        String pre = username.split("_")[0];

        User user = new User();

        switch (pre) {
            case "student" -> {
                user = StudentDto.toEntity(studentService.getStudentByUserName(username));
            }
            case "author" -> {
                user = AuthorDto.toEntity(authorService.getAuthorByUserName(username));
            }
            case "admin" -> {
                user = AdminDto.toEntity(adminService.getAdminByUsername(username));
            }
        }
        return user;
    }

    public User register(RegisterRequest request){
        switch (request.getRole()) {
            case STUDENT -> {
                return StudentDto.toEntity(studentService.register(request));
            }
            case AUTHOR -> {
                return AuthorDto.toEntity(authorService.register(request));
            }
            case ADMIN -> {
                return AdminDto.toEntity(adminService.register(request));
            }
        }
        return null;
    }

}
