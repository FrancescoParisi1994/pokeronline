package it.prova.pokeronline.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.prova.pokeronline.model.Tavolo;

public class TavoloDTO {
	private Long id;
	private Integer esperienzaMin;
	private Integer cifraMinima;
	private String denominazione;
	private LocalDate dataCreazione;

	private Set<UtenteDTO> giocatori = new HashSet<>();

	private UtenteDTO utenteCreazione;

	public TavoloDTO() {
		super();
	}

	public TavoloDTO(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione,
			Set<UtenteDTO> giocatori) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.giocatori = giocatori;
	}

	public TavoloDTO(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione, LocalDate dataCreazione,
			Set<UtenteDTO> giocatori, UtenteDTO utenteCreazione) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.giocatori = giocatori;
		this.utenteCreazione = utenteCreazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEsperienzaMin() {
		return esperienzaMin;
	}

	public void setEsperienzaMin(Integer esperienzaMin) {
		this.esperienzaMin = esperienzaMin;
	}

	public Integer getCifraMinima() {
		return cifraMinima;
	}

	public void setCifraMinima(Integer cifraMinima) {
		this.cifraMinima = cifraMinima;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Set<UtenteDTO> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<UtenteDTO> giocatori) {
		this.giocatori = giocatori;
	}

	public UtenteDTO getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(UtenteDTO utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}

	public Tavolo buildModelFromDTO() {
		Tavolo result = new Tavolo(this.id, this.esperienzaMin, this.cifraMinima, this.denominazione,
				this.dataCreazione, this.giocatori.stream().map(i -> {
					return i.buildUtenteModel(false);
				}).collect(Collectors.toSet()), this.utenteCreazione.buildUtenteModel(false));
		return result;
	}

	public static TavoloDTO buildDTOFromModel(Tavolo tavolo) {
		TavoloDTO result = new TavoloDTO(tavolo.getId(), tavolo.getEsperienzaMin(), tavolo.getCifraMinima(),
				tavolo.getDenominazione(), tavolo.getDataCreazione(), tavolo.getGiocatori().stream().map(i -> {
					return UtenteDTO.buildUtenteDTOFromModel(i);
				}).collect(Collectors.toSet()), UtenteDTO.buildUtenteDTOFromModel(tavolo.getUtenteCreazione()));
		return result;
	}

	public static List<TavoloDTO> buildDTOListFromModelList(List<Tavolo> tavolos) {
		return tavolos.stream().map(i->{return TavoloDTO.buildDTOFromModel(i);}).collect(Collectors.toList());
	}
}
