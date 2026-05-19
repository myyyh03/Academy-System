package com.mo7s.academySystem.user.admin;


import com.mo7s.academySystem.user.Role;
import com.mo7s.academySystem.user.User;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class Admin extends User {


    @Override
    protected void onCreate() {
        setRole(Role.ADMIN);
    }

    public String toString() {
        return "Admin{" +
                "id=" + getId() +
                ", userName='" + getUserName() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                '}';
    }
}
