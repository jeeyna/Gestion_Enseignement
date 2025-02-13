package uasz.sn.Gestion_Enseignement.Repartition.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Notification.Modele.Notification;
import uasz.sn.Gestion_Enseignement.Notification.Service.NotificationService;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.Service.EnseignementService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("enseignement")
public class EnseignementController {

    @Autowired
    private EnseignementService enseignementService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/chefDepartement")
    public String enseignement_chefDepartement(Model model, Principal principal) {
        Utilisateur utilisateur= utilisateurService.rechercher_Utilisateur(principal.getName());
        Enseignant chefDepartement= enseignantService.rechercher(utilisateur.getId());
        Long notificationsNonLus = notificationService.nombreNotificationNonLu(chefDepartement);
        model.addAttribute("notificationsNonLus", notificationsNonLus);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("chefDepartement", chefDepartement);
        model.addAttribute("enseignements", enseignementService.trouverChefDepartement(chefDepartement));
        return "chefDepartement-enseignement";

    }

    @GetMapping("/permanent")
    public String listerEnseignement(Model model, Principal principal) {
        Utilisateur utilisateur= utilisateurService.rechercher_Utilisateur(principal.getName());
        List<Enseignement> enseignements = enseignementService.listerEnseignementsDisponibles();
        Enseignant enseignant= enseignantService.rechercher(utilisateur.getId());
        Long notificationNonLus= notificationService.nombreNotificationNonLu(enseignant);
        model.addAttribute("notificationsNonLus", notificationNonLus);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("enseignements", enseignements);
        return "permanent-enseignement";

    }
}
