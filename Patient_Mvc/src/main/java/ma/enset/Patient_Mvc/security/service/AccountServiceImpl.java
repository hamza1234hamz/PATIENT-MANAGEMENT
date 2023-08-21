package ma.enset.Patient_Mvc.security.service;

import lombok.AllArgsConstructor;
import ma.enset.Patient_Mvc.security.Repository.AppRoleRepository;
import ma.enset.Patient_Mvc.security.Repository.AppUserRepository;
import ma.enset.Patient_Mvc.security.entities.AppRole;
import ma.enset.Patient_Mvc.security.entities.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if(appUser!=null) throw new RuntimeException("this user alredy exist");
        if(!password.equals(confirmPassword)) throw new RuntimeException("password not match");
        AppUser useradded=AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        AppUser userSaved=appUserRepository.save(useradded);
        return userSaved;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole=appRoleRepository.findById(role).orElse(null);
        if(appRole!=null) throw new RuntimeException("this role elready exist");
        appRole=AppRole.builder()
                .role(role)
                .build();
        AppRole roleSeved=appRoleRepository.save(appRole);
        return roleSeved;
    }

    @Override
    public void AddRoleToUser(String username, String role) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findById(role).orElse(null);
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
