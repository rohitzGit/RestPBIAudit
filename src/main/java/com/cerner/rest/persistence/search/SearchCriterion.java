package com.cerner.rest.persistence.search;

public class SearchCriterion {
    private char   type = 'S';
    private String conditionType;
    private String lhs;
    private String rhs;
    private String operator;

    public SearchCriterion(char type, String conditionType, String lhs,
            String operator, String rhs) {
        this.type = type;
        this.conditionType = conditionType;
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getLhs() {
        return lhs;
    }

    public void setLhs(String lhs) {
        this.lhs = lhs;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRhs() {
        return rhs;
    }

    public void setRhs(String rhs) {
        this.rhs = rhs;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
