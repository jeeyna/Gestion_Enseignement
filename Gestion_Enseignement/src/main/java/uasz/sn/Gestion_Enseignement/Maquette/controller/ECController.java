package uasz.sn.Gestion_Enseignement.Maquette.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;
import uasz.sn.Gestion_Enseignement.Maquette.service.ECservice;

import java.util.List;

@Controller
@AllArgsConstructor
public class ECController {
    @Autowired
    private ECservice ecService;


    @RequestMapping(value = "/ajouter_ec_ue", method = RequestMethod.POST)
    public String ajouter_ec_ue(Model model, EC ec) {
        ecService.ajouterEC(ec);
        return "redirect:/details_ue?id=" + ec.getUe().getId();
    }

    @RequestMapping(value = "/modifier_ec_ue", method = RequestMethod.POST)
    public String modifier_ec_ue(Model model,EC ec) {
        ecService.modifierEC(ec);
        return "redirect:/details_ue?id=" + ec.getUe().getId();
    }

    @RequestMapping(value = "/supprimer_ec_ue", method = RequestMethod.POST)
    public String supprimer_ec_ue(Model model,EC ec) {
        Long id=ec.getUe().getId();
        ecService.supprimerEC(ec);
        return "redirect:/details_ue?id=" + id;
    }
    @RequestMapping(value = "/archiverECUE", method = RequestMethod.POST)
    public String archiver_UE(Long id) {
        ecService.archiverEC(id);
        return "redirect:/ue";
    }
    @RequestMapping(value = "/activerECUE", method = RequestMethod.POST)
    public String activer_UE(Long id) {
        ecService.activerEC(id);
        return "redirect:/ue";
    }
}
