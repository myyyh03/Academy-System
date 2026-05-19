package com.mo7s.academySystem.user.admin;

import com.mo7s.academySystem.user.auth.RegisterRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AdminService {

    final private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminDto> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(AdminDto::new).toList();
    }

    public AdminDto getAdminById(Integer id) {
        Admin admin = adminRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return new AdminDto(admin);
    }

    public AdminDto getAdminByUsername(String username) {
        Admin admin = adminRepository.findByUserName(username).orElseThrow(NoSuchElementException::new);
        return new AdminDto(admin);
    }

    public AdminDto saveAdmin(AdminDto adminDto) {
        Admin admin = adminRepository.save(AdminDto.toEntity(adminDto));
        return new AdminDto(admin);
    }

    public AdminDto updateAdminById(Integer id, AdminDto adminDto) {

        Admin admin = adminRepository.findById(id).orElseThrow(NoSuchElementException::new);
        AdminDto oldAdminDto = new AdminDto(admin);
        oldAdminDto = updateAdmin(oldAdminDto, adminDto);
        Admin updatedAdmin =  adminRepository.save(AdminDto.toEntity(oldAdminDto));
        return new AdminDto(updatedAdmin);
    }

    public void deleteAdminById(Integer id) {
        adminRepository.deleteById(id);
    }

    private AdminDto updateAdmin(AdminDto oldAdmin, AdminDto newAdmin) {
        Class<?> clazz = oldAdmin.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.get(newAdmin) != null) {
                        field.set(oldAdmin, field.get(newAdmin));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
        return oldAdmin;
    }

    public AdminDto register(RegisterRequest request) {

        if(adminRepository.findByUserName("admin_" + request.getUserName()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }
        if(adminRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }

        Admin admin = Admin.builder()
                .userName("admin_" + request.getUserName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        Admin saved = adminRepository.save(admin);
        return new AdminDto(saved);
    }
}
