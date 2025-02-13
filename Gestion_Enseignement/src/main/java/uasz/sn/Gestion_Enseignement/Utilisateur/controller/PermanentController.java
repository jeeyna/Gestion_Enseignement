package uasz.sn.Gestion_Enseignement.Utilisateur.controller;

import ch.qos.logback.core.encoder.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Notification.Service.NotificationService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.PermanentService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PermanentController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private PermanentService permanentService;
    @Autowired
    private NotificationService notificationService;

    public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }


    @RequestMapping(value = "/Permanent/Accueil", method = RequestMethod.GET)
    public String accueil_Permanent(Model model, Principal principal) {
        Utilisateur utilisateur= utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        return "template_Permanent";
    }
    @RequestMapping(value = "/ChefDepartement/Accueil", method = RequestMethod.GET)
    public String accueil_ChefDepartement(Model model, Principal principal) {
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("utilisateur", utilisateur);
        Enseignant enseignant = enseignantService.rechercher(utilisateur.getId());
        Long notificationsNonLus = notificationService.nombreNotificationNonLu(enseignant);
        model.addAttribute("notificationsNonLus", notificationsNonLus);
        return "template_ChefDepartement";
    }

    @RequestMapping(value = "/ChefDepartement/ajouterPermanent", method = RequestMethod.POST)
    public String ajouter_Permanent(Permanent permanent) {
        String password = passwordEncoder().encode("Passer123");
        permanent.setPassword(password);
        permanent.setDateCreation(new Date());
        permanent.setActive(true);

        Role role = utilisateurService.ajouter_Role(new Role("Permanent"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        permanent.setRoles(roles);
        enseignantService.ajouter(permanent);

        return "redirect:/ChefDepartement/Enseignant";
    }



    @RequestMapping(value = "/ChefDepartement/modifierPermanent", method = RequestMethod.POST)
    public String modifier_Permanent(Permanent permanent) {
        Permanent per_modif = permanentService.rechercher(permanent.getId());

        per_modif.setMatricule(permanent.getMatricule());
        per_modif.setNom(permanent.getNom());
        per_modif.setPrenom(permanent.getPrenom());
        per_modif.setSpecialite(permanent.getSpecialite());
        per_modif.setGrade(permanent.getGrade());

       enseignantService.modifier(per_modif);

        return "redirect:/ChefDepartement/Enseignant";
    }

    @RequestMapping(value = "/ChefDepartement/activerPermanent", method = RequestMethod.POST)
    public String activer_Permanent(Long id) {
        enseignantService.activer(id);

        return "redirect:/ChefDepartement/Enseignant";
    }

    @RequestMapping(value = "/ChefDepartement/archiverPermanent", method = RequestMethod.POST)
    public String archiver_Permanent(Long id) {
        enseignantService.archiver(id);

        return "redirect:/ChefDepartement/Enseignant";
    }
}
