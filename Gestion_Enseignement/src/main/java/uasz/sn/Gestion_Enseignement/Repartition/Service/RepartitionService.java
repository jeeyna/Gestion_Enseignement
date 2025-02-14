package uasz.sn.Gestion_Enseignement.Repartition.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Repartition.Modele.Repartition;
import uasz.sn.Gestion_Enseignement.Repartition.Repository.RepartitionRepository;

import java.util.List;

@Service
public class RepartitionService {
    @Autowired
    private RepartitionRepository repartitionRepository;

    public List<Repartition> listerRepartitionParClasseEtSemestre(Long idClasse, int semestre){
        return repartitionRepository.findByClasseAndSemestre(idClasse, semestre);
    }
}
