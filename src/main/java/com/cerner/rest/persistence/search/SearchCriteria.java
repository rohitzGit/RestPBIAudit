package com.cerner.rest.persistence.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCriteria implements ISearchCriteria {
    private SearchCriteria        childCriteria;
    private String                className;
    private List<SearchCriterion> criterions;
    private Map<String, String>   aliasMap;

    public SearchCriteria() {
        criterions = new ArrayList<SearchCriterion>();
        aliasMap   = new HashMap<String, String>();
    }

    public SearchCriteria(SearchCriteria childCriteria, String className) {
        this();
        this.childCriteria = childCriteria;
        this.className     = className;
    }

    public SearchCriteria getChildCriteria() {
        return childCriteria;
    }

    public void setChildCriteria(SearchCriteria childCriteria) {
        this.childCriteria = childCriteria;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void addCriterion(SearchCriterion criterion) {
        this.criterions.add(criterion);
    }

    public void addAlias(String associationPath, String alias) {
        aliasMap.put(associationPath, alias);
    }

    public Map<String, String> getAlias() {
        return aliasMap;
    }

    public List<SearchCriterion> getCriterions() {
        return criterions;
    }
}
