package it.prova.pokeronline.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.web.api.exception.UtenteNonAbilitatoException;

@Service
@Transactional
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	private TavoloRepository tavoloRepository;
	@Autowired
	private UtenteService utenteService;
//	public List<Tavolo> 

	public Tavolo insert(Tavolo tavolo, String username) {
		Utente utente = utenteService.findByUsername(username);
		tavolo.setUtenteCreazione(utente);
		tavolo.setDataCreazione(LocalDate.now());
		return tavoloRepository.save(tavolo);
	}

	public Tavolo updateSpecialPlayer(Tavolo tavoloModificato, String username) {
		Utente utente = utenteService.findByUsername(username);
		Tavolo tavolo = tavoloRepository.findById(tavoloModificato.getId()).orElse(null);
		if (tavolo.getUtenteCreazione().getId().equals(utente.getId()) || tavolo.getGiocatori().isEmpty()) {
			throw new UtenteNonAbilitatoException("Non puoi modificare questo tavolo");
		}
		tavoloModificato.setDataCreazione(tavolo.getDataCreazione());
		tavoloModificato.setUtenteCreazione(tavolo.getUtenteCreazione());
		return tavoloRepository.save(tavoloModificato);
	}

	@Transactional(readOnly = true)
	public List<Tavolo> findAllMiei(String username) {
		return tavoloRepository.findMieiTavoli(utenteService.findByUsername(username).getId());
	}

	@Transactional(readOnly = true)
	public List<Tavolo> findAll() {
		return (List<Tavolo>) tavoloRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Tavolo findMioTavolo(Long id, String username) {
		return tavoloRepository.findMioTavolo(utenteService.findByUsername(username).getId(), id);
	}

	@Transactional(readOnly = true)
	public Tavolo findById(Long id) {
		return tavoloRepository.findById(id).orElse(null);
	}

	public void deleteSplecialPlayer(Long id,String username) {
		Utente utente = utenteService.findByUsername(username);
		Tavolo tavolo = tavoloRepository.findById(id).orElse(null);
		if (tavolo.getUtenteCreazione().getId().equals(utente.getId()) || tavolo.getGiocatori().isEmpty()) {
			throw new UtenteNonAbilitatoException("Non puoi cancellare questo tavolo");
		}
		tavoloRepository.deleteById(id);
	}
	
	public void delete(Long id) {
		Tavolo tavolo = tavoloRepository.findById(id).orElse(null);
		if (tavolo.getGiocatori().isEmpty()) {
			throw new UtenteNonAbilitatoException("Non puoi cancellare questo tavolo giocatori ancora presenti");
		}
		tavoloRepository.deleteById(id);
	}

}
