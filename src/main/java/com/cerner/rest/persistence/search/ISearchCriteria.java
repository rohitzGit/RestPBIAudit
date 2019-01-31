package com.cerner.rest.persistence.search;

import java.util.List;
import java.util.Map;

public interface ISearchCriteria {
	public String getClassName();

	public void setClassName(String className);

	public SearchCriteria getChildCriteria();

	public void setChildCriteria(SearchCriteria childCriteria);

	public void addCriterion(SearchCriterion criterion);

	public List<SearchCriterion> getCriterions();

	public void addAlias(String associationPath, String alias);

	public Map<String, String> getAlias();
}
