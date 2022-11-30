package it.prova.pokeronline.web.api;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.TavoloInsertDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.web.api.exception.IdNotNullForInsertException;
import it.prova.pokeronline.web.api.exception.UtenteNonAbilitatoException;

@RestController
@RequestMapping("/api/tavolo")
public class TavoloController {

	@Autowired
	private TavoloService tavoloService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TavoloDTO insert(@Valid @RequestBody TavoloInsertDTO tavoloInsertDTO, Principal principal) {
		if (tavoloInsertDTO.getId() != null) {
			throw new IdNotNullForInsertException("Trovato Id nella richiesta");
		}
		return TavoloDTO
				.buildDTOFromModel(tavoloService.insert(tavoloInsertDTO.buildModelFromDTO(), principal.getName()));
	}

	@PutMapping
	public TavoloDTO update(@Valid @RequestBody TavoloInsertDTO tavoloInsertDTO, Principal principal) {
		Tavolo tavolo = null;
		try {
			tavolo = tavoloService.updateSpecialPlayer(tavoloInsertDTO.buildModelFromDTO(), principal.getName());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new UtenteNonAbilitatoException("Non puoi modificare questo tavolo");
		}
		return TavoloDTO.buildDTOFromModel(tavolo);
	}

	@GetMapping
	public List<TavoloDTO> findAllMiei(Principal principal) {
		return TavoloDTO.buildDTOListFromModelList(tavoloService.findAllMiei(principal.getName()));
	}

	@GetMapping("/{id}")
	public TavoloDTO findMio(@PathVariable(required = true) Long id, Principal principal) {
		return TavoloDTO.buildDTOFromModel(tavoloService.findMioTavolo(id, principal.getName()));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true)Long id,Principal principal) {
		Tavolo tavolo=tavoloService.findById(id);
		if (!tavolo.getUtenteCreazione().getUsername().equals(principal.getName())) {
			throw new UtenteNonAbilitatoException("Non sei abilitato ad eliminare queso tavolo");
		}
		tavoloService.delete(id);
	}
}
