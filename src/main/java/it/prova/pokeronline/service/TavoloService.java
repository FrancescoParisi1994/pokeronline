package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloService {

	public Tavolo insert(Tavolo tavolo, String username);

	public Tavolo updateSpecialPlayer(Tavolo tavoloModificato, String username);

	public List<Tavolo> findAllMiei(String username);

	public Tavolo findMioTavolo(Long id, String username);
	
	public Tavolo findById(Long id);
	
	public void delete(Long id);
	
	public List<Tavolo> findAll();
	
	public void deleteSplecialPlayer(Long id,String username);
}
