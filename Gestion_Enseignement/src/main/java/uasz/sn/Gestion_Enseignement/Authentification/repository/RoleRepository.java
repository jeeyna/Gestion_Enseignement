package uasz.sn.Gestion_Enseignement.Authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("SELECT r FROM Role r where r.role= :role")
    Role findRoleByRole(@Param("role") String role);
}
