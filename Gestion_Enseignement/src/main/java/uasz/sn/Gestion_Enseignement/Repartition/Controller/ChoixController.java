package uasz.sn.Gestion_Enseignement.Repartition.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.Service.ChoixService;

@Controller
@RequestMapping("/choix")
public class ChoixController {

    private final ChoixService choixService;

    public ChoixController(ChoixService choixService) {
        this.choixService = choixService;
    }

    @PostMapping("/ajouter")
    public String ajouterChoix(@RequestParam Long idEnseignement){
        choixService.ajouterChoix(idEnseignement);
        return "redirect:/enseignement/permanent";
    }
}
