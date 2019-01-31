package com.cerner.rest.persistence;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import com.cerner.rest.persistence.search.ISearchCriteria;



public interface IPersistenceManager {

	int getRecordCount(String entityName);

	void update(PersistenceObject persistenceObject);

	void saveOrUpdate(PersistenceObject persistenceObject);

	List<PersistenceObject> getPage(int start, int size, String sortColumn, String sortDirection, String className);

	PersistenceObject findById(Class<?> clazz, String idFieldName, BigDecimal id);

	PersistenceObject findByUniqueField(Class<?> clazz, String fieldName, String value);

	List<?> findBySearchCriteria(ISearchCriteria criteria, int start, int limit);

	List<PersistenceObject> findAll(Class<?> clazz);

	List<?> getPageByQuery(String strQuery, String[] paramNames, Object[] values, int pageNumber, int pageSize,
			String orderByFieldName, String orderByDirection);
	
	int getRecordCount(String strQuery, final String[] paramNames, final Object[] values);
	
	int getRecordCount(ISearchCriteria criteria);
	
	List<?> findBySearchCriteria(ISearchCriteria criteria);
	
	List<Object[]> executeQuery(String queryStr);
	
	Session getCurrentSession();
	
	List<PersistenceObject> getFilteredPage(int start, int size, String sortColumn, String sortDirection, ISearchCriteria criteria);
	
	void save(PersistenceObject persistenceObject);
	
	void deleteAll(Collection<PersistenceObject> persistenceObjectCollection);
	
	void delete(PersistenceObject persistenceObject);

}