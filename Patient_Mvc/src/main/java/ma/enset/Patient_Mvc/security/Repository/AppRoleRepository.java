package ma.enset.Patient_Mvc.security.Repository;

import ma.enset.Patient_Mvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
}
