package uasz.sn.Gestion_Enseignement.Maquette.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;
import uasz.sn.Gestion_Enseignement.Maquette.service.UEService;

import java.util.List;

@Controller
@AllArgsConstructor
public class UEController {

    @Autowired
    private UEService ueService;

    @RequestMapping(value = "/ue/{id}", method = RequestMethod.GET)
    public String lister_ue(Model model) {
        List<UE> ueList = ueService.afficherToutUE();
        model.addAttribute("listeDesUE", ueList);
        return "ue";
    }
    @RequestMapping(value = "/ue", method = RequestMethod.GET)
    public String lister_ues(Model model) {
        List<UE> ueList = ueService.afficherToutUE();
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
    public String details_ue(Model model, @RequestParam("id") Long id) {
        UE ue = ueService.afficherUE(id);
        List<EC> ecList = ueService.afficherLesEC(ue);
        model.addAttribute("ue", ue);
        model.addAttribute("listeDesEC", ecList);
        return "ue_details";
    }


    @GetMapping("/ue/{id}/ecs")
    public String listerEcs(@PathVariable Long id, Model model) {
        List<EC> ecs = ueService.getEcsByID(id);

        if (ecs.isEmpty()) {
            model.addAttribute("error", "Aucun EC associé à cette UE !");
            return "error"; // Page d'erreur
        }

        model.addAttribute("ecs", ecs);
        model.addAttribute("ueId", id);
        return "ecs-list"; // Page Thymeleaf
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
