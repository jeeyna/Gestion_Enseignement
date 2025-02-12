package uasz.sn.Gestion_Enseignement.Maquette.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;
import uasz.sn.Gestion_Enseignement.Maquette.repository.ClasseRepository;
import uasz.sn.Gestion_Enseignement.Maquette.repository.ECRepository;
import uasz.sn.Gestion_Enseignement.Maquette.repository.MaquetteRepository;
import uasz.sn.Gestion_Enseignement.Maquette.repository.UERepository;
import uasz.sn.Gestion_Enseignement.Maquette.service.MaquetteService;
import uasz.sn.Gestion_Enseignement.Maquette.service.UEService;
import uasz.sn.Gestion_Enseignement.exception.ResourceNotFoundException;

import java.util.List;

import java.util.List;

@Controller
@RequestMapping("/maquettes") // Préfixe commun pour toutes les routes
public class MaquetteController {
    @Autowired
    private  MaquetteService maquetteService;
    @Autowired
    private  UEService ueService;
    @Autowired
    private ECRepository ecRepository;

    // Afficher la liste des maquettes non archivées
    @GetMapping
    public String listerMaquettes(Model model) {
        List<Maquette> maquettes = maquetteService.afficherToutesLesMaquettes();
        model.addAttribute("listeDesMaquettesA", maquettes);
        return "maquettes";
    }

    // Ajouter une nouvelle maquette
    @PostMapping("/ajouter")
    public String ajouterMaquette(@ModelAttribute Maquette maquette) {
        maquetteService.ajouterMaquette(maquette);
        return "redirect:/classes/" + maquette.getClasse().getId();

    }

    // Modifier une maquette
    @PostMapping("/modifier/{id}")
    public String modifierMaquette(@PathVariable Long id, @ModelAttribute Maquette maquette) {
        maquetteService.modifierMaquette(id, maquette);
        return "redirect:/classes/" + maquette.getClasse().getId();
    }

    // Archiver une maquette
    @PostMapping("/archiver/{id}")
    public String archiverMaquette(@PathVariable Long id) {
        Maquette maquette = maquetteService.rechercherMaquetteParId(id);
        maquetteService.archiverMaquette(id);
        return "redirect:/classes/" + maquette.getClasse().getId();
    }

    /**
     * Ajouter une UE à une maquette.
     */

    @PostMapping("/{id}/ajouterUE")
    public String ajouterUEaMaquette(@PathVariable("id") Long id, @RequestParam("ueId") Long ueId) {
        maquetteService.ajouterUEaMaquette(id, ueId);
        return "redirect:/maquettes/" + id;
    }

    @GetMapping("/{id}")
    public String afficherDetailsMaquette(@PathVariable Long id, Model model) {
        Maquette maquette = maquetteService.rechercherMaquetteParId(id);

        if (maquette == null) {
            throw new RuntimeException("Maquette non trouvée !");
        }

        // UE déjà dans la maquette
        List<UE> uesDansMaquette = maquetteService.getUesAjoutees(id);
        List<UE> uesNonAjoutees = maquetteService.getUesNonAjoutees(id);

         //Charger les ECS pour chaque UE (si nécessaire)
        for (UE ue : uesDansMaquette) {
            ue.setEcs(ecRepository.findByUe(ue));
        }

        model.addAttribute("maquette", maquette);
        model.addAttribute("uesDansMaquette", uesDansMaquette);
        model.addAttribute("uesNonAjoutees", uesNonAjoutees); // Pour l'ajout d'une nouvelle UE

        return "maquette-classe";
    }


    @PostMapping("/{maquetteId}/remove-ue/{ueId}")
    public String retirerUE(@PathVariable Long maquetteId, @PathVariable Long ueId) {
        maquetteService.retirerUEDansMaquette(maquetteId, ueId);
        return "redirect:/maquettes/" + maquetteId;
    }



}
