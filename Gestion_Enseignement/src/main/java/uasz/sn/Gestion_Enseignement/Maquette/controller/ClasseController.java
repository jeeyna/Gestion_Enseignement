package uasz.sn.Gestion_Enseignement.Maquette.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.service.ClasseService;
import uasz.sn.Gestion_Enseignement.Maquette.service.MaquetteService;
import uasz.sn.Gestion_Enseignement.Notification.Service.NotificationService;
import uasz.sn.Gestion_Enseignement.Repartition.Service.RepartitionService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/classe") // Préfixe commun pour toutes les routes
public class ClasseController {

    private final ClasseService classeService;
    private final MaquetteService maquetteService;
    private final UtilisateurService utilisateurService;
    private final EnseignantService enseignantService;
    private final NotificationService notificationService;
    private final RepartitionService repartitionService;


    // Ajouter une nouvelle classe
    @PostMapping("/ajouter")
    public String ajouterClasse(@ModelAttribute Classe classe) {
        classeService.ajouterClasse(classe);
        return "redirect:/formation/" + classe.getFormation().getId();

    }

    // Modifier une classe
    @PostMapping("/modifier/{id}")
    public String modifierClasse(@PathVariable Long id, @ModelAttribute Classe classe) {
        classeService.modifierClasse(id, classe);
        return "redirect:/formation/" + classe.getFormation().getId();
    }

    // Archiver une classe
    @PostMapping("/archiver/{id}")
    public String archiverClasse(@PathVariable Long id) {
        Classe c = classeService.touverParId(id);
        classeService.archiverClasse(id);
        return "redirect:/formation/" + c.getFormation().getId();
    }

    @GetMapping("/{id}")
    public String afficherDetailsClasse(@PathVariable("id") Long id, Model model, Principal principal) {
        Classe classe = classeService.touverParId(id);
        if (classe != null) {
            model.addAttribute("classe", classe);
            model.addAttribute("maquettesNonArchivees", maquetteService.listerMaquetteByClasse(classe));
            Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
            Enseignant enseignant = enseignantService.rechercher(utilisateur.getId());
            Long notificationsNonLus = notificationService.nombreNotificationNonLu(enseignant);
            model.addAttribute("notificationsNonLus", notificationsNonLus);
            model.addAttribute("utilisateur", utilisateur);
        }
        return "classe-details";
    }


    @GetMapping("/{classeId}/semestre/{semestre}/repartition")
    public String getRepartitionByClasseAndSemestre(@PathVariable Long classeId, @PathVariable int semestre, Model model, Principal principal) {
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        Classe classe = classeService.touverParId(classeId);
        if (classe != null) {
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("classe", classe);
            model.addAttribute("semestre", semestre); // Ajout du semestre
            model.addAttribute("repartitions",repartitionService.listerRepartitionParClasseEtSemestre(classeId, semestre));
        }
        return "classe-repartition";
    }

}
