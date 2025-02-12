package uasz.sn.Gestion_Enseignement.Maquette.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.service.ClasseService;
import uasz.sn.Gestion_Enseignement.Maquette.service.MaquetteService;

@Controller
@AllArgsConstructor
@RequestMapping("/classe") // Préfixe commun pour toutes les routes
public class ClasseController {

    private final ClasseService classeService;
    private final MaquetteService maquetteService;



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
    public String afficherDetailsClasse(@PathVariable("id") Long id, Model model) {
        Classe classe = classeService.touverParId(id);
        if (classe != null) {
            model.addAttribute("classe", classe);
            model.addAttribute("maquettesNonArchivees", maquetteService.listerMaquetteByClasse(classe));
        }
        return "classe-details";
    }

}
