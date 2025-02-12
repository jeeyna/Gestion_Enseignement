package uasz.sn.Gestion_Enseignement.Repartition.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Statut;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.ChoixRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.util.Date;

@Service
@Transactional
public class ChoixService {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private EnseignementService enseignementService;
    @Autowired
    private ChoixRepository choixRepository;

    public Choix ajouterChoix(Long idEnseignement) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Utilisateur non authentifié !");
        }
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(auth.getName());
        Enseignant enseignant= enseignantService.rechercher(utilisateur.getId());

        Enseignement enseignement= enseignementService.trouverEnseignementParId(idEnseignement);
        boolean dejaChoisi= choixRepository.existsByEnseignantAndEnseignement(enseignant, enseignement);
        if (dejaChoisi) {
            throw new RuntimeException("l'enseignant a deja choisi cette enseignement!");
        }
        Choix choix = new Choix();
        choix.setEnseignant(enseignant);
        choix.setEnseignement(enseignement);
        choix.setStatut(Statut.EN_ATTENTE);
        choix.setDateChoix(new Date());

        return choixRepository.save(choix);
    }
}
