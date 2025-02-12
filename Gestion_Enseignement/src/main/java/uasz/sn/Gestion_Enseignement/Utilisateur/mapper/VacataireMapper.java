package uasz.sn.Gestion_Enseignement.Utilisateur.mapper;

import org.mapstruct.Mapper;
import uasz.sn.Gestion_Enseignement.Utilisateur.dto.VacataireDTO;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Vacataire;

import java.util.List;

@Mapper

public interface VacataireMapper {
    VacataireDTO vacataireToDTO(Vacataire vacataire) ;
    Vacataire dtoToVacataire(VacataireDTO vacataireDTO) ;
    List<VacataireDTO> vacataireToDTOs(List<Vacataire> vacataires) ;

}
