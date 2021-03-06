package co.mz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import co.mz.domain.Pais;
import co.mz.domain.Regiao;
import co.mz.domain.SubRegiao;
import co.mz.domain.Repository.PaisRepository;
import co.mz.domain.Repository.RegiaoRepository;
import co.mz.domain.Repository.SubRegiaoRepository;
import co.mz.domain.exception.EntidadeNaoEncontradaException;

/**
 * @author Blandino Junior Sibone Mucumbe
 * */
@Service
public class PaisService {

	@Autowired
	private PaisRepository paisRepository;
	
	@Autowired
	private RegiaoRepository regiaoRepository;
	
	@Autowired
	private SubRegiaoRepository subRegiaoRepository;
	
	/*
	 * Metodo Responsavel por Guardar e actualizar Propiedades de pais
	 * Valida a Existencia de regiao ou Subregiao
	 * */
	public Pais guardar_Actualizar(Pais pais) {
		long idR= pais.getRegiao().getId();
		long idSR= pais.getSubRegiao().getId();
		Regiao regiao= regiaoRepository.findById(idR)
				.orElseThrow(()-> new EntidadeNaoEncontradaException(String.format("Não existe Regiao com o codigo: %d%n",idR)));
		SubRegiao subRegiao= subRegiaoRepository.findById(idSR)
				.orElseThrow(()-> new EntidadeNaoEncontradaException(String.format("Não existe Sub-Regiao com o codigo %n%d",idSR)));
		pais.setRegiao(regiao);
		pais.setSubRegiao(subRegiao);
		System.err.println("servico");
		return paisRepository.save(pais);	
	}
	
	/*
	 * Metodo Responsavel por apagar eum pais corespondente a um ID
	 * Valida se a regiao que se pretende apagar Existe ou não
	 * */
	public void apagar(long id) {
		
		try {
			paisRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe Pais com o codigo %n%d",id));
		}
		
	}
}
