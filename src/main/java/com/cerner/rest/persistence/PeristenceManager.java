package com.cerner.rest.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.cerner.rest.persistence.search.ISearchCriteria;
import com.cerner.rest.persistence.search.SearchConstants;
import com.cerner.rest.persistence.search.SearchCriteria;
import com.cerner.rest.persistence.search.SearchCriterion;
import com.cerner.rest.persistence.util.Expression;

public class PeristenceManager extends HibernateDaoSupport implements IPersistenceManager{

	@Override
	public int getRecordCount(String entityName) {
		System.out.println("Inside getRecordCount()");
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT count(*) FROM " + entityName));
	}
	
	public int getRecordCount(ISearchCriteria criteria) {
		return findBySearchCriteria(criteria).size();
	}
	
	public List<?> findBySearchCriteria(ISearchCriteria criteria) {
    	return findBySearchCriteria(criteria, 0, 0);
    }

	@Override
	public void update(PersistenceObject persistenceObject) {
		getHibernateTemplate().update(persistenceObject);
	}
	
	@Override
	public void saveOrUpdate(PersistenceObject persistenceObject) {
    	getHibernateTemplate().saveOrUpdate(persistenceObject);
    }

	@Override
	public List<PersistenceObject> getPage(int start, int size, String sortColumn, String sortDirection, String className) {
		System.out.println("Inside getPage()");
    	
    	ISearchCriteria criteria = new SearchCriteria();
		criteria.setClassName(className);

        if((sortColumn != null) && (!sortColumn.trim().equals(""))) {
        	criteria.addCriterion(new SearchCriterion(SearchConstants.ORDER, "", sortColumn, sortDirection.trim().toUpperCase(), ""));
        }

        @SuppressWarnings("unchecked")
		List<PersistenceObject> listPersistenceObject = (List<PersistenceObject>) findBySearchCriteria(criteria, start, size);

		return listPersistenceObject;
	}

	@Override
	public PersistenceObject findById(Class<?> clazz, String idFieldName, BigDecimal id) {
    	String queryString="from "+ clazz.getName() +" where " + idFieldName + " = ?";

    	List<PersistenceObject> persistenceObjectList =  (List<PersistenceObject>) getHibernateTemplate().find(queryString, id);

    	if (!persistenceObjectList.isEmpty()) {
    		return persistenceObjectList.get(0);
        }

    	return null;
    }

	@Override
	public PersistenceObject findByUniqueField(Class<?> clazz, String fieldName, String value) {
    	String queryString="from "+ clazz.getName() +" where " + fieldName + " = ?";

    	List<PersistenceObject> persistenceObjectList = (List<PersistenceObject>) getHibernateTemplate().find(queryString, value);

    	if (!persistenceObjectList.isEmpty()) {
    		return persistenceObjectList.get(0);
        }

    	return null;
    }

	@Override
	public List<?> findBySearchCriteria(ISearchCriteria criteria, int start, int limit) {
    	DetachedCriteria detached = null;
        try{
            detached = DetachedCriteria.forClass(Class.forName(criteria.getClassName()));
        } catch (ClassNotFoundException classNotFoundException){
        	classNotFoundException.printStackTrace();
        }

        Set<String> keyAlias = criteria.getAlias().keySet();
        for (String key : keyAlias){
            detached.createAlias(key, criteria.getAlias().get(key));
        }

        for (SearchCriterion criterion : criteria.getCriterions()) {
        	StringTokenizer   tokenizer  = new StringTokenizer(criterion.getLhs(), ".");
        	ArrayList<String> tokens     = new ArrayList<String>();

    		while(tokenizer.hasMoreTokens()) {
    			tokens.add(tokenizer.nextToken());
    		}

            Criterion exp = Expression.getExpression(criterion);
            if (exp != null){
                if (SearchConstants.AND.equals(criterion.getConditionType()) || (criterion.getConditionType() == null)) {
                	for(int i=0; i<(tokens.size() - 1); i++) {
                		detached = detached.createCriteria(tokens.get(i));
            		}
            		detached.add(exp);
                } else if (criterion.getConditionType().equals(SearchConstants.OR)) {
                    Disjunction disjunction = Restrictions.disjunction();
                    disjunction.add(exp);
                    detached.add(disjunction);
                }
            }

            if (criterion.getType() == SearchConstants.ORDER){
                if (SearchConstants.ASC.equals(criterion.getOperator())) {
                    detached.addOrder(Order.asc(criterion.getLhs()));
                } else if (SearchConstants.DESC.equals(criterion.getOperator())) {
                    detached.addOrder(Order.desc(criterion.getLhs()));
                }
            } else if (criterion.getType() == SearchConstants.CONDN){
                ProjectionList pList = Projections.projectionList();
                if (SearchConstants.MIN.equals(criterion.getOperator())) {
                    pList.add(Projections.min(criterion.getLhs()));
                } else if (SearchConstants.MAX.equals(criterion.getOperator())) {
                    pList.add(Projections.max(criterion.getLhs()));
                } else if (SearchConstants.AVG.equals(criterion.getOperator())) {
                    pList.add(Projections.avg(criterion.getLhs()));
                }
                detached.setProjection(pList);
            } else if (SearchConstants.DIST == criterion.getType()){
                detached.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            }
        }

        List list = null;
        if (limit == 0){
            list = getHibernateTemplate().findByCriteria(detached);
        } else if (limit > 0){
            list = getHibernateTemplate()
                    .findByCriteria(detached, start, limit);
        }
        return list;
    }

	@Override
	public List<PersistenceObject> findAll(Class<?> clazz) {
    	return (List<PersistenceObject>) getHibernateTemplate().loadAll(clazz);
    }
	
	@Override
	public List<?> getPageByQuery(String strQuery, final String[] paramNames, final Object[] values, int pageNumber, int pageSize, String orderByFieldName, String orderByDirection) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<PersistenceObject> searchList = new ArrayList<PersistenceObject>();

		if((orderByFieldName != null) && (!orderByFieldName.trim().equals(""))) {
			strQuery = strQuery + " order by " + orderByFieldName;
			
			if((orderByDirection != null) && (orderByDirection.trim().toLowerCase().equals("desc"))) {
				strQuery = strQuery + " desc";
			} else {
				strQuery = strQuery + " asc";
			}
		}
		
		Query query = session.createQuery(strQuery);
		for(int i=0; i<paramNames.length; i++) {
			query.setParameter(paramNames[i], values[i]);
		}
		
		query.setFirstResult(pageSize * (pageNumber - 1));
		query.setMaxResults(pageSize);

		return query.list();
	}
	
	public int getRecordCount(String strQuery, final String[] paramNames, final Object[] values) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		//strQuery = "select count(*) from " + strQuery.substring(strQuery.toLowerCase().lastIndexOf(" from ") + 5);
		
		Query query = session.createQuery(strQuery);
		for(int i=0; i<paramNames.length; i++) {
			query.setParameter(paramNames[i], values[i]);
		}
		
		//return ((Long) query.list().get(0)).intValue();
		return query.list().size();
	}
	
	public List<PersistenceObject> getFilteredPage(int start, int size, String sortColumn, String sortDirection, ISearchCriteria criteria) {
        if((sortColumn != null) && (!sortColumn.trim().equals(""))) {
        	System.out.println("SORT COLUMN = " + sortColumn);
        	criteria.addCriterion(new SearchCriterion(SearchConstants.ORDER, "", sortColumn, sortDirection.trim().toUpperCase(), ""));
        }

        @SuppressWarnings("unchecked")
		List<PersistenceObject> listPersistenceObject = (List<PersistenceObject>) findBySearchCriteria(criteria, start, size);

		return listPersistenceObject;
	}
	
	public void save(PersistenceObject persistenceObject) {
    	getHibernateTemplate().save(persistenceObject);
    }
	
	public void delete(PersistenceObject persistenceObject) {
		 getHibernateTemplate().delete(persistenceObject);
	}
	
	public List<Object[]> executeQuery(String queryStr) {
    	return getCurrentSession().createQuery(queryStr).list();
    }
	
    public void deleteAll(Collection<PersistenceObject> persistenceObjectCollection) {
    	getHibernateTemplate().  deleteAll(persistenceObjectCollection);
    }
	
	public Session getCurrentSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
}
