package uasz.sn.Gestion_Enseignement.Utilisateur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.PermanentRepository;
import uasz.sn.Gestion_Enseignement.exception.ResourceAlreadyExistException;
import uasz.sn.Gestion_Enseignement.exception.ResourceNotFoundException;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PermanentService {
    @Autowired
    private PermanentRepository permanentRepository;
    public Permanent ajouter(Permanent permanent) {
        Optional<Permanent> optionalPermanent = permanentRepository.findByMatricule(permanent.getMatricule());
        if (optionalPermanent.isPresent())
            throw new ResourceAlreadyExistException("le matricule" + permanent.getMatricule() + "existe deja");
        try {
                return permanentRepository.save(permanent);
            } catch (Exception exception) {
                throw new ResourceNotFoundException("Erreur lors de l'ajout");
            }
    }
    public List<Permanent> lister() {return permanentRepository.findAll();}
    public Permanent rechercher(Long id){
        try {return permanentRepository.findById(id).get();
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Permament avec pour ID " + id + " n'existe pas !");
        }
    }
    public Permanent modifier (Permanent permanent) {
        try {
            return permanentRepository.save(permanent);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Erreur lors de la modification permanent !" + permanent.getId());
        }
    }
    public void supprimer (Long id){permanentRepository.deleteById(id);}
}
