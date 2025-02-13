package uasz.sn.Gestion_Enseignement.Repartition.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Notification.Modele.Notification;
import uasz.sn.Gestion_Enseignement.Notification.Service.NotificationService;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Statut;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.ChoixRepository;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.EnseignementRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EnseignementRepository enseignementRepository;

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
        Enseignant chefDepartement= enseignement.getChefDepartement();

        Notification notification= new Notification();
        notification.setLu(false);
        notification.setDestinataire(chefDepartement);
        notification.setMessage("L'enseignant " + enseignant.getPrenom()+ " "+ enseignant.getNom()+ " a choisi l'enseignement "+ enseignement.getEc().getLibelle()+" "+ enseignement.getTypeSeance().name());
        notificationService.envoyerNotification(notification);
        return choixRepository.save(choix);
    }

    public List<Choix> listeChoixEnseignementChefDep(Enseignant chefDepartement){
        List<Enseignement> enseignements = enseignementRepository.findByChefDepartement(chefDepartement);
        List<Choix> choix = new ArrayList<>();
        for (Enseignement enseignement:enseignements){
            if (!enseignement.getChoix().isEmpty()){
                choix.addAll(choixRepository.findByEnseignement(enseignement));
            }
        }
        return choix;
    }

    public List<Choix> choixEnAttenteChefDep(Enseignant chefDepartement){
        List<Enseignement> enseignements = enseignementRepository.findByChefDepartement(chefDepartement);
        List<Choix> choix = new ArrayList<>();

        for (Enseignement enseignement:enseignements){
            if(!enseignement.getChoix().isEmpty()){
                for (Choix c:choixRepository.findByEnseignement(enseignement)){
                    if (c.getStatut().name().equalsIgnoreCase("EN_ATTENTE")){
                        choix.add(c);
                    }
                }
            }
        }
        return choix;
    }
}
