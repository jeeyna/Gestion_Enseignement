package uasz.sn.Gestion_Enseignement.Utilisateur.mapper;

import org.mapstruct.Mapper;
import uasz.sn.Gestion_Enseignement.Utilisateur.dto.PermanentDTO;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;

import java.util.List;

@Mapper
public interface PermanentMapper {
    PermanentDTO permanentToDTO(Permanent permanent) ;
    Permanent dtoToPermanent(PermanentDTO permanentDTO) ;
    List<PermanentDTO> permanentToDTOs(List<Permanent> permanents) ;


}
