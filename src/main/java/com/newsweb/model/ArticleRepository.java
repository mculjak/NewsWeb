package com.newsweb.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ArticleRepository {

	@PersistenceContext
	protected EntityManager entityManager;


	public Article getById(Long id) {
		return entityManager.find(Article.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Article> list(int currentPageNum, int elementsPerPage) {
		Query q = entityManager.createQuery("from " + Article.class.getName());
		int from = ((currentPageNum - 1) * elementsPerPage);
        q.setFirstResult(from);
        q.setMaxResults(elementsPerPage);
        return q.getResultList();
	}

	@Transactional
	public void save(Article dao) throws InvalidDataException {
		if (dao == null) throw new InvalidDataException("Trying to save null object!");
		try {
			if (dao.getId() == null) {
				entityManager.persist(dao);
			} else {
				entityManager.merge(dao);
			}
		} catch (Exception e) {
			throw new InvalidDataException(e);
		}
	}
	
	@Transactional
	public void delete(Article daoObject) throws InvalidDataException {
		if (daoObject == null) throw new InvalidDataException("Trying to delete null object!");
		entityManager.remove(daoObject);
	}
	
	@Transactional
	public void delete(Long id) throws InvalidDataException {
		if (id == null) throw new InvalidDataException("Trying to delete object with null ID!");
		Article dao = entityManager.find(Article.class, id);
		delete(dao);
	}

	public Collection<Article> getArticlesForCategories(List<String> categories,int currentPageNum, int elementsPerPage) {
		Collection<Article> result = null;
		int from = ((currentPageNum - 1) * elementsPerPage);
		try {
			result = (Collection<Article>) entityManager.createNamedQuery("Article.getArticleForCategory")
				.setParameter("categories", categories).setParameter("numCategories", categories.size())
				.setFirstResult(from)
				.setMaxResults(elementsPerPage)
				.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return result;
	}	
}
