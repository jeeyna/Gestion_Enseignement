package uasz.sn.Gestion_Enseignement.Maquette.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;
import uasz.sn.Gestion_Enseignement.Maquette.service.UEService;
import uasz.sn.Gestion_Enseignement.Notification.Service.NotificationService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class UEController {

    @Autowired
    private UEService ueService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/ue/{id}", method = RequestMethod.GET)
    public String lister_ue(Model model) {
        List<UE> ueList = ueService.afficherToutUE();
        model.addAttribute("listeDesUE", ueList);
        return "ue";
    }
    @RequestMapping(value = "/ue", method = RequestMethod.GET)
    public String lister_ues(Model model, Principal principal) {
        List<UE> ueList = ueService.afficherToutUE();
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        Enseignant enseignant = enseignantService.rechercher(utilisateur.getId());
        Long notificationsNonLus = notificationService.nombreNotificationNonLu(enseignant);
        model.addAttribute("notificationsNonLus", notificationsNonLus);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("listeDesUE", ueList);
        return "ue";
    }

    @RequestMapping(value = "/ajouter_ue", method = RequestMethod.POST)
    public String ajouter_ue(Model model, UE ue) {
        ueService.ajouterUE(ue);
        return "redirect:/ue";
    }

    @RequestMapping(value = "/modifier_ue", method = RequestMethod.POST)
    public String modifier_ue(Model model, UE ue) {
        ueService.modifierUE(ue);
        return "redirect:/ue";
    }

    @RequestMapping(value = "/supprimer_ue", method = RequestMethod.POST)
    public String supprimer_ue(Model model, UE ue) {
        ueService.supprimerUE(ue);
        return "redirect:/ue";
    }

    @RequestMapping(value = "/details_ue", method = RequestMethod.GET)
    public String details_ue(Model model,Principal principal ,@RequestParam("id") Long id) {
        UE ue = ueService.afficherUE(id);
        List<EC> ecList = ueService.afficherLesEC(ue);
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        Enseignant enseignant = enseignantService.rechercher(utilisateur.getId());
        Long notificationsNonLus = notificationService.nombreNotificationNonLu(enseignant);
        model.addAttribute("notificationsNonLus", notificationsNonLus);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("ue", ue);
        model.addAttribute("listeDesEC", ecList);
        return "ue_details";
    }



    @RequestMapping(value = "/archiverUE", method = RequestMethod.POST)
    public String archiver_UE(Long id) {
        ueService.archiver(id);
        return "redirect:/ue";
    }
    @RequestMapping(value = "/activerUE", method = RequestMethod.POST)
    public String activer_UE(Long id) {
        ueService.activer(id);
        return "redirect:/ue";
    }
    @PostMapping("/ue/{ueId}/add-ec")
    public ResponseEntity<?> ajouterEcUe(@PathVariable Long ueId, @RequestBody EC ec) {
        if (ueId == null) {
            return ResponseEntity.badRequest().body("L'ID de l'UE est obligatoire.");
        }

        try {
            UE ue = ueService.ajouterEcUe(ueId, ec);
            return ResponseEntity.ok(ue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
