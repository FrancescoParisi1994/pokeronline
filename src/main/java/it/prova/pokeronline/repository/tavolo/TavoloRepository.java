package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloRepository extends CrudRepository<Tavolo, Long> {

//	@Query()
//	List<Tavolo> findByEsperienzaMinLessThanEqualAndCifraMinimaMinLessThanEqual(Integer esperienzaMin,Integer cifraMinima);
	
	@Query("from Tavolo t left join fetch t.utenteCreazione u where u.id=:id")
	List<Tavolo> findMieiTavoli(Long id);
	
	@Query("from Tavolo t left join fetch t.utenteCreazione u where u.id=:idUtente and t.id=:idTavolo")
	Tavolo findMioTavolo(Long idUtente,Long idTavolo);
}
