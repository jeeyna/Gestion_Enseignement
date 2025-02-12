package uasz.sn.Gestion_Enseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
//import uasz.sn.Gestion_Enseignement.Repartition.Service.ChoixService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Vacataire;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.PermanentService;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.VacataireService;

import java.security.Principal;
import java.util.List;

@Controller
public class EnseignantController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PermanentService permanentService;

    @Autowired
    private VacataireService vacataireService;

    @Autowired
    private EnseignantService enseignantService;

//    @Autowired
//    private ChoixService choixService;

    @RequestMapping(value = "/ChefDepartement/Enseignant", method = RequestMethod.GET)
    public String ChefDepartement_Enseignant(Model model, Principal principal) {
        List<Permanent> permanents=permanentService.lister();
        model.addAttribute("permanents", permanents);
        List<Vacataire> vacataires=vacataireService.lister();
        model.addAttribute("vacataires", vacataires);
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        return "chefDepartement_Enseignant";
    }
    @GetMapping("/choix")
    public String repartir(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login"; // Rediriger vers la page de connexion si l'utilisateur n'est pas authentifié
        }

        String username = principal.getName(); // Récupérer le nom d'utilisateur
        Enseignant enseignant = enseignantService.findByUsername(username);

        // Vérifier si l'utilisateur est bien un enseignant
        boolean isChefDepartement = false;
        if (enseignant != null && enseignant.getRoles() != null) {
            isChefDepartement = enseignant.getRoles().stream()
                    .anyMatch(role -> role.getRole().equalsIgnoreCase("ChefDepartement"));
        }

        model.addAttribute("isChefDepartement", isChefDepartement);

        // Récupérer les choix en attente pour le chef de département uniquement
//        if (isChefDepartement) {
//            model.addAttribute("choixEnAttente", choixService.getChoixEnAttente());
//        }

        return "choix";
    }

}
