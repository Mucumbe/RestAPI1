package co.mz.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import co.mz.domain.Pais;
import co.mz.domain.Repository.PaisRepositoryQueres;
import co.mz.domain.Repository.RegiaoRepository;

/**
 * @author Blandino Junior Sibone Mucumbe
 * */
@Repository
public class PaisRepositoryImpl implements PaisRepositoryQueres {

	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	RegiaoRepository regiaoRepository;
	
	/*
	 * Metodo que implementa listagem de paises Por combinação de varios criterios
	 * */
	@Override
	public List<Pais> find(String nome,String capital,String regiao,String subRegiao){
		
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<Pais> criteria=builder.createQuery(Pais.class);
		Root<Pais> root=criteria.from(Pais.class);
		ArrayList<Predicate> predicates= new ArrayList<Predicate>();
		
		if (StringUtils.hasLength(nome)) {
			predicates.add(builder.like(root.get("nome"), "%"+nome+"%"));
		}
		
		if (StringUtils.hasLength(capital)) {
			predicates.add(builder.like(root.get("capital"), "%"+capital+"%"));
		}
		if (regiao!=null) {
			predicates.add(builder.equal(root.get("regiao"),regiao));
		}
		
		criteria.where(predicates.toArray( new Predicate[0]));
		TypedQuery<Pais> query=manager.createQuery(criteria);
		
		return query.getResultList();
		
	}
}
