package uasz.sn.Gestion_Enseignement.Repartition.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.Service.ChoixService;
import uasz.sn.Gestion_Enseignement.Repartition.Service.EnseignementService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/choix")
public class ChoixController {

    @Autowired
    private ChoixService choixService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private EnseignementService enseignementService;
    @Autowired
    private EnseignantService enseignantService;


    @PostMapping("/ajouter")
    public String ajouterChoix(@RequestParam Long idEnseignement){
        choixService.ajouterChoix(idEnseignement);
        return "redirect:/enseignement/permanent";
    }

    @GetMapping("/chefDepartement")
    public String listeChoixChefDep (Model model, Principal principal){
        Utilisateur utilisateur= utilisateurService.rechercher_Utilisateur(principal.getName());
        Enseignant chefDepartement= enseignantService.rechercher(utilisateur.getId());
        List<Choix> choix = choixService.listeChoixEnseignementChefDep(chefDepartement);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("chefDepartement",chefDepartement);
        model.addAttribute("choix", choix);

        return "chefDepartement-listeChoix";

    }

    @GetMapping("/en_attente/chefDepartement")
    public String listeChoixEnAttente(Model model, Principal principal){
        Utilisateur utilisateur= utilisateurService.rechercher_Utilisateur(principal.getName());
        Enseignant chefDepartement= enseignantService.rechercher(utilisateur.getId());
        List<Choix> choix = choixService.choixEnAttenteChefDep(chefDepartement);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("chefDepartement",chefDepartement);
        model.addAttribute("choix", choix);

        return "chefDepartement-listeChoixEnAttente";
    }
    @PostMapping("/valider/{id}")
    public String validerChoix(Model model, Principal principal, @PathVariable Long id){
        Utilisateur utilisateur= utilisateurService.rechercher_Utilisateur(principal.getName());
        Enseignant chefDepartement= enseignantService.rechercher(utilisateur.getId());
        choixService.validerChoix(id);

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("chefDepartement",chefDepartement);

        return "redirect:/choix/en_attente/chefDepartement";
    }

    @PostMapping("/refuser/{id}")
    public String refuserChoix(Model model, Principal principal, @PathVariable Long id){
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        Enseignant chefDepartement= enseignantService.rechercher(utilisateur.getId());
        choixService.refuserChoix(id);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("chefDepartement",chefDepartement);

        return "redirect:/choix/en_attente/chefDepartement";
    }
}
