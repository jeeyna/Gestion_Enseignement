package uasz.sn.Gestion_Enseignement.Repartition.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Notification.Modele.Notification;
import uasz.sn.Gestion_Enseignement.Notification.Service.NotificationService;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Repartition;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Statut;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.ChoixRepository;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.EnseignementRepository;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.RepartitionRepository;
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
    @Autowired
    private RepartitionRepository repartitionRepository;

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
    public void validerChoix(Long choixId) {
        Choix choix = choixRepository.findById(choixId)
                .orElseThrow(() -> new RuntimeException("Choix non trouvé"));

        choix.getEnseignement().setDisponible(false);
        choix.setStatut(Statut.VALIDE);
        String typeEns = choix.getEnseignement().getTypeSeance().name();

        // Récupérer l'EC lié à cet enseignement
        EC ec = choix.getEnseignement().getEc();

        // Vérification si une répartition existe déjà pour cet EC
        Repartition existingRepartition = repartitionRepository.findByEC(ec);
        if (existingRepartition == null) {

            if (typeEns.equalsIgnoreCase("CM")) {
                repartitionRepository.save(new Repartition(null, choix.getEnseignant(), null, null, choix.getEnseignement()));
            } else if (typeEns.equalsIgnoreCase("TD")) {
                repartitionRepository.save(new Repartition(null, null, choix.getEnseignant(), null, choix.getEnseignement()));
            } else if (typeEns.equalsIgnoreCase("TP")) {
                repartitionRepository.save(new Repartition(null, null, null, choix.getEnseignant(), choix.getEnseignement()));
            }

        } else {
            // Si une répartition existe déjà, mettre à jour le responsable correspondant
            if (typeEns.equalsIgnoreCase("CM")) {
                existingRepartition.setEnseignant(choix.getEnseignant());
            } else if (typeEns.equalsIgnoreCase("TD")) {
                existingRepartition.setResponsableTd(choix.getEnseignant());
            } else if (typeEns.equalsIgnoreCase("TP")) {
                existingRepartition.setResponsableTp(choix.getEnseignant());
            }

            // Sauvegarde de la répartition mise à jour
            repartitionRepository.save(existingRepartition);
        }
        // Notifier l'enseignant
        Notification notification = new Notification();
        notification.setDestinataire(choix.getEnseignant());
        notification.setMessage(
                "Votre choix pour l'enseignement : " + choix.getEnseignement().getEc().getLibelle() +" de type " + choix.getEnseignement().getTypeSeance()+" a été validé.");
        notificationService.envoyerNotification(notification);


    }

    public void refuserChoix(Long choixId) {
        Choix choix = choixRepository.findById(choixId)
                .orElseThrow(() -> new RuntimeException("Choix non trouvé"));

        choix.setStatut(Statut.REFUSE);
        // Notifier l'enseignant du refus
        Notification notification = new Notification();
        notification.setDestinataire(choix.getEnseignant());
        notification.setMessage(
                "Votre choix pour l'enseignement ID : " + choix.getEnseignement().getEc().getLibelle() +" de type " + choix.getEnseignement().getTypeSeance()+ " a été refusé.");
        notificationService.envoyerNotification(notification);
    }
}
