package uasz.sn.Gestion_Enseignement.Maquette.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Maquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Maquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.Maquette.modele.UE;
import uasz.sn.Gestion_Enseignement.Maquette.repository.MaquetteRepository;
import uasz.sn.Gestion_Enseignement.Maquette.repository.UERepository;

import uasz.sn.Gestion_Enseignement.Repartition.Modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Sceance;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.EnseignementRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.EnseignantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class MaquetteService {
    private final MaquetteRepository maquetteRepository;
    private final UERepository ueRepository;
    private final EnseignementRepository enseignementRepository;
    private final EnseignantRepository enseignantRepository;
    private final UtilisateurService utilisateurService;

    // Ajouter une maquette
    public Maquette ajouterMaquette(Maquette maquette) {
        return maquetteRepository.save(maquette);
    }

    // Modifier une maquette
    public Maquette modifierMaquette(Long id, Maquette maquetteDetails) {
        Optional<Maquette> maquetteExistante = maquetteRepository.findById(id);
        if (maquetteExistante.isPresent()) {
            Maquette maquette = maquetteExistante.get();
            maquette.setSemestre(maquetteDetails.getSemestre());
            maquette.setUes(maquetteDetails.getUes());
            maquette.setClasse(maquetteDetails.getClasse());
            return maquetteRepository.save(maquette);
        }
        return null; // Ou lever une exception
    }

    // Archiver une maquette
    public Maquette archiverMaquette(Long id) {
        Optional<Maquette> maquetteExistante = maquetteRepository.findById(id);
        if (maquetteExistante.isPresent()) {
            Maquette maquette = maquetteExistante.get();
            return maquetteRepository.save(maquette);
        }
        return null;
    }

    // Activer ou désactiver une maquette
    public Maquette activerDesactiverMaquette(Long id, boolean statut) {
        Optional<Maquette> maquetteExistante = maquetteRepository.findById(id);
        if (maquetteExistante.isPresent()) {
            Maquette maquette = maquetteExistante.get();
            return maquetteRepository.save(maquette);
        }
        return null;
    }

    // Afficher toutes les maquettes
    public List<Maquette> afficherToutesLesMaquettes() {
        return maquetteRepository.findAll();
    }

    public Maquette rechercherMaquetteParId(Long id) {
        return maquetteRepository.findById(id).get();
    }

    public List<Maquette> listerMaquetteByClasse(Classe classe) {
        return maquetteRepository.findByClasse(classe);

    }

    public void ajouterUEaMaquette(Long maquetteId, Long ueId) {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(authentication.getName());

        // Vérifier si l'utilisateur est bien un enseignant
        Enseignant enseignant = enseignantRepository.findById(utilisateur.getId()).get();

        // Récupérer la maquette et l'UE
        Maquette maquette = maquetteRepository.findById(maquetteId)
                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));

        UE ue = ueRepository.findById(ueId)
                .orElseThrow(() -> new RuntimeException("UE non trouvée"));

        // Ajouter l'UE à la maquette
        ue.getMaquettes().add(maquette);
        maquette.getUes().add(ue);

        for (EC ec : ue.getEcs()) {
            if (ec.getCm() > 0) {
                Enseignement e = new Enseignement(null, Sceance.CM, ec, maquette, enseignant,
                        true, null);
                enseignementRepository.save(e);
            }
            if (ec.getTd() > 0) {
                Enseignement e = new Enseignement(null, Sceance.TD, ec, maquette, enseignant,
                        true, null);
                enseignementRepository.save(e);

            }
            if (ec.getTp() > 0) {
                Enseignement e = new Enseignement(null, Sceance.TP, ec, maquette, enseignant,
                        true, null);
                enseignementRepository.save(e);
            }
        }

        // Sauvegarde
        ueRepository.save(ue);
        maquetteRepository.save(maquette);
    }

    public List<UE> getUesNonAjoutees(Long maquetteId) {
        // Récupère la maquette
        Maquette maquette = maquetteRepository.findById(maquetteId)
                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));

        // Récupère toutes les UE
        List<UE> toutesLesUEs = ueRepository.findAll();

        // Filtrer les UE qui ne sont pas dans la maquette
        List<UE> uesNonAjoutees = toutesLesUEs.stream()
                .filter(ue -> !maquette.getUes().contains(ue))
                .collect(Collectors.toList());

        return uesNonAjoutees;
    }

    public List<UE> getUesAjoutees(Long id) {
        Maquette maquette = maquetteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));

        // Récupère toutes les UE
        List<UE> toutesLesUEs = ueRepository.findAll();

        // Filtrer les UE qui ne sont pas dans la maquette
        List<UE> uesAjoutees = toutesLesUEs.stream()
                .filter(ue -> maquette.getUes().contains(ue))
                .collect(Collectors.toList());

        return uesAjoutees;
    }



    // supprimer une ue dans la maquette
//    public void retirerUEDansMaquette(Long maquetteId, Long ueId) {
//        Maquette maquette = maquetteRepository.findById(maquetteId)
//                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));
//
//        maquette.getUes().removeIf(ue -> ue.getId().equals(ueId));
//
//        maquetteRepository.save(maquette);
//    }
    @Transactional
    public void retirerUEDansMaquette(Long maquetteId, Long ueId) {
        Maquette maquette = maquetteRepository.findById(maquetteId)
                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));

        UE ueASupprimer = maquette.getUes().stream()
                .filter(ue -> ue.getId().equals(ueId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("UE non trouvée dans la maquette"));

        // Supprimer les enseignements liés aux ECs de cette UE
        for (EC ec : ueASupprimer.getEcs()) {
            enseignementRepository.deleteByEcId(ec.getId());
        }

        // Supprimer l'UE de la maquette
        maquette.getUes().remove(ueASupprimer);

        maquetteRepository.save(maquette);
    }

}
