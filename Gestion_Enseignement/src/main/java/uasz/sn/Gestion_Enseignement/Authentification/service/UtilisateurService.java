package uasz.sn.Gestion_Enseignement.Authentification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.repository.RoleRepository;
import uasz.sn.Gestion_Enseignement.Authentification.repository.UtilisateurRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.Optional;

@Service
@Transactional
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Utilisateur ajouter_Utilisateur(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    public Role ajouter_Role(Role role) {
        roleRepository.save(role);
        return role;
    }

    public void ajouter_UtilisateurRoles(Utilisateur utilisateur, Role role) {
        Utilisateur user = utilisateurRepository.findUtilisateurByUsername(utilisateur.getUsername());
        Role profil = roleRepository.findRoleByRole(role.getRole());
        user.getRoles().add(profil);
    }

    public Utilisateur rechercher_Utilisateur(String username) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        return utilisateur;
    }

    public Optional<Utilisateur> findByUsername(String username) {
        return Optional.ofNullable(utilisateurRepository.findUtilisateurByUsername(username));
    }

    public Optional<Enseignant> findEnseignantByUsername(String username) {
        Optional<Utilisateur> utilisateur = Optional.ofNullable(utilisateurRepository.findUtilisateurByUsername(username));
        if (utilisateur.isPresent() && utilisateur.get() instanceof Enseignant) {
            return Optional.of((Enseignant) utilisateur.get());
        }
        return Optional.empty();
    }
    public boolean estChefDepartement(String username) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);

        // Vérifier si l'utilisateur existe
        if (utilisateur == null) {
            System.out.println("❌ Utilisateur introuvable pour le username : " + username);
            return false;  // Si l'utilisateur n'existe pas, il n'est pas chef de département
        }

        return utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRole().equalsIgnoreCase("ChefDepartement"));
    }
    public boolean estPermanent(String username) {
        return utilisateurRepository.findUtilisateurByUsername(username)
                .getRoles().stream()
                .anyMatch(role -> role.getRole().equalsIgnoreCase("Permanent") || role.getRole().equalsIgnoreCase("ChefDepartement"));
    }




}
