package uasz.sn.Gestion_Enseignement.Repartition.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.service.ClasseService;
import uasz.sn.Gestion_Enseignement.Notification.Service.NotificationService;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Repartition;
import uasz.sn.Gestion_Enseignement.Repartition.Service.RepartitionService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.security.Principal;

@Controller
@RequestMapping("/repartition")
public class RepartitionController {

    @Autowired
    private ClasseService classeService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RepartitionService repartitionService;

    @GetMapping("/{id}")
    public String afficherRepartitionClasse(@PathVariable Long id, Model model, Principal principal) {
        Classe classe = classeService.touverParId(id);
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        Enseignant enseignant = enseignantService.rechercher(utilisateur.getId());
        Long notificationsNonLus = notificationService.nombreNotificationNonLu(enseignant);
        model.addAttribute("notificationsNonLus", notificationsNonLus);
        model.addAttribute("classe", classe);
        model.addAttribute("utilisateur", utilisateur);

        return "classe-repartition";
    }
}
