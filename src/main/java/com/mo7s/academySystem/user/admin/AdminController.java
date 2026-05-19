package com.mo7s.academySystem.user.admin;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    final private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<AdminDto> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public AdminDto getAdminById(@PathVariable Integer id) {
        return adminService.getAdminById(id);
    }

    @PostMapping
    public AdminDto createAdmin(@Valid @RequestBody AdminDto admin) {
        return adminService.saveAdmin(admin);
    }

    @PutMapping("/{id}")
    public AdminDto updateAdmin(@PathVariable Integer id, @Valid @RequestBody AdminDto newAdmin) {
        return adminService.updateAdminById(id, newAdmin);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdminById(id);
    }
}
