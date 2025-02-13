package uasz.sn.Gestion_Enseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Notification.Service.NotificationService;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
//import uasz.sn.Gestion_Enseignement.Repartition.Service.ChoixService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Vacataire;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.PermanentService;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.VacataireService;

import java.security.Principal;
import java.util.List;

@Controller
public class EnseignantController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PermanentService permanentService;

    @Autowired
    private VacataireService vacataireService;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private NotificationService notificationService;

//    @Autowired
//    private ChoixService choixService;

    @RequestMapping(value = "/ChefDepartement/Enseignant", method = RequestMethod.GET)
    public String ChefDepartement_Enseignant(Model model, Principal principal) {
        List<Permanent> permanents=permanentService.lister();
        model.addAttribute("permanents", permanents);
        List<Vacataire> vacataires=vacataireService.lister();
        model.addAttribute("vacataires", vacataires);
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        Enseignant enseignant = enseignantService.rechercher(utilisateur.getId());
        Long notificationsNonLus = notificationService.nombreNotificationNonLu(enseignant);
        model.addAttribute("notificationsNonLus", notificationsNonLus);
        model.addAttribute("utilisateur", utilisateur);
        return "chefDepartement_Enseignant";
    }

}
