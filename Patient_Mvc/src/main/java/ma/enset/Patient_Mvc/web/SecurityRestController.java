package ma.enset.Patient_Mvc.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.AuthProvider;
@RestController
public class SecurityRestController {
    @GetMapping("/profile")
    public Authentication authentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    } // POUR recuperer les information de user conecter a l'app
}
