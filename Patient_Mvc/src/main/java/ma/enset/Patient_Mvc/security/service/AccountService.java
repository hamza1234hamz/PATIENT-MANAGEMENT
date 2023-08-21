package ma.enset.Patient_Mvc.security.service;

import ma.enset.Patient_Mvc.security.entities.AppRole;
import ma.enset.Patient_Mvc.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username,String password,String email,String confirmPassword);
    AppRole addNewRole(String role);
    void AddRoleToUser(String username,String role);
    void removeRoleFromUser(String username,String role);

    AppUser loadUserByUsername(String username);
}
