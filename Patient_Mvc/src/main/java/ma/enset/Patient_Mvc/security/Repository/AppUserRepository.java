package ma.enset.Patient_Mvc.security.Repository;

import ma.enset.Patient_Mvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
