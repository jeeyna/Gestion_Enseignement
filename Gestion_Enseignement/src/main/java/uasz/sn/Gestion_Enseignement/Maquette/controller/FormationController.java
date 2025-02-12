package uasz.sn.Gestion_Enseignement.Maquette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.Maquette.service.FormationService;

import java.util.List;

@Controller
public class FormationController {

    @Autowired
    private FormationService formationService;

    @RequestMapping(value="/formation", method= RequestMethod.GET)
    public String lister(Model model) {
        List<Formation> formationList= formationService.lister();
        model.addAttribute("listeDesformation", formationList);
        return "formation";
    }

    @RequestMapping(value = "/ajouter_formation", method = RequestMethod.POST)
    public String ajouter_formation(Model model, Formation formation) {
        formationService.ajouter(formation);
        return "redirect:/formation";
    }

    @RequestMapping(value = "/modifier_formation", method = RequestMethod.POST)
    public String modifier_formation( Formation formation){
        formationService.modifier(formation);
        return "redirect:/formation";
    }

    @RequestMapping(value = "/archiver_formation", method = RequestMethod.POST)
    public String archiver_formation(Long id){
        formationService.archiver(id);
        return "redirect:/formation";
    }

    @RequestMapping(value = "/formation/{id}", method = RequestMethod.GET)
    public String classe(Model model, @PathVariable("id") Long id) {
        Formation formation = formationService.afficherFormation(id);
        List<Classe> classeList= formationService.afficherLesClasses(formation.getId());
        model.addAttribute("formation", formation);
        model.addAttribute("listeDesClasse", classeList);
        return "classe";
    }

}
