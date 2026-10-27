package tn.esprit.rh.achat.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.rh.achat.entities.Reglement;

import java.util.Date;
import java.util.List;

@Repository
public interface ReglementRepository extends CrudRepository<Reglement, Long>{
	@Query("SELECT r FROM Reglement r where r.idReglement=:idFacture")
	List<Reglement> retrieveReglementByFacture(@Param("idFacture") Long idFacture);

	
	@Query("SELECT sum(r.montantPaye) FROM Reglement r where r.dateReglement between :startDate and :endDate")
	float getChiffreAffaireEntreDeuxDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
