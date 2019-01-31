package com.cerner.rest.persistence.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.cerner.rest.persistence.search.SearchConstants;
import com.cerner.rest.persistence.search.SearchCriterion;


public class Expression {
	public static Criterion getExpression(SearchCriterion criterion) {
        Criterion expression = null;

        switch(criterion.getType()) {

	    	case SearchConstants.STRING_DATA_TYPE:
	    		expression = getStringExpression(criterion);
	    	break;

	    	case SearchConstants.INTEGER_DATA_TYPE:
	    		expression = getIntegerExpression(criterion);
	    	break;

	    	case SearchConstants.FLOAT_DATA_TYPE:
	    		expression = getFloatExpression(criterion);
	    	break;

	    	case SearchConstants.LONG_DATA_TYPE:
	    		expression = getLongExpression(criterion);
	    	break;

	    	case SearchConstants.DATE_DATA_TYPE:
	    		//oracle specific; for sqldb change it to DEFAULT_DATE_FORMAT_2
	    		expression = getDateExpression(criterion, SearchConstants.DEFAULT_DATE_FORMAT_1);
	    	break;

	    	case SearchConstants.BOOLEAN_DATA_TYPE:
	    		expression = getBooleanExpression(criterion);
	    	break;

	    	case SearchConstants.BIGDECIMAL_DATA_TYPE:
	    		expression = getBigDecimalExpression(criterion);
	    	break;
	    }

        return expression;
    }

    private static Criterion getBooleanExpression(SearchCriterion criterion) {
    	Criterion expression = null;
        Boolean rhs = null;
        if (criterion.getType() == SearchConstants.BOOLEAN_DATA_TYPE){
            rhs = Boolean.parseBoolean(criterion.getLhs());
        }
        if (criterion.getOperator().equals(SearchConstants.equals)){
            expression = Restrictions.eq(criterion.getLhs(), rhs);
        } else if (criterion.getOperator().equals(SearchConstants.notEquals)){
            expression = Restrictions.ne(criterion.getLhs(), rhs);
        }
        return expression;
    }

    private static Criterion getStringExpression(SearchCriterion criterion) {
    	Criterion expression = null;
    	String    lhs        = getLastTokenfromLHS(criterion.getLhs());

        if (criterion.getOperator().equals(SearchConstants.equals)) {
            expression = Restrictions.eq(lhs, criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.greaterThan)) {
            expression = Restrictions.gt(lhs, criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.greaterThanEquals)) {
            expression = Restrictions.ge(lhs, criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.lessThan)) {
            expression = Restrictions.lt(lhs, criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.lessThanEquals)) {
            expression = Restrictions.le(lhs, criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.notEquals)) {
            expression = Restrictions.ne(lhs, criterion.getRhs());
        } else if (criterion.getOperator() == SearchConstants.LIKE) {
            expression = Restrictions.like(lhs, criterion.getRhs());
        } else if (criterion.getOperator() == SearchConstants.ILIKE) {
            expression = Restrictions.ilike(lhs, criterion.getRhs());
        } else if(criterion.getOperator() == SearchConstants.IN) {
        	ArrayList<String> tokens = new ArrayList<String>();
    		
        	StringTokenizer st = new StringTokenizer(criterion.getRhs(), ","); 
        	while (st.hasMoreTokens()) {
        		String token = st.nextToken();
        	    tokens.add(token);
        	}
        	expression = Restrictions.in(lhs, tokens);
        }
        return expression;
    }

    private static Criterion getLongExpression(SearchCriterion criterion) {
    	Criterion expression = null;
    	String    lhs        = getLastTokenfromLHS(criterion.getLhs());
        Long      rhs        = null;

        if (criterion.getType() == SearchConstants.LONG_DATA_TYPE){
            rhs = Long.parseLong(criterion.getRhs());
        }

        if (criterion.getOperator().equals(SearchConstants.equals)) {
            expression = Restrictions.eq(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.greaterThan)) {
            expression = Restrictions.gt(lhs, rhs);
        } else if (criterion.getOperator().equals(
                SearchConstants.greaterThanEquals)) {
            expression = Restrictions.ge(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.lessThan)) {
            expression = Restrictions.lt(lhs, rhs);
        } else if (criterion.getOperator().equals(
                SearchConstants.lessThanEquals)) {
            expression = Restrictions.le(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.notEquals)) {
            expression = Restrictions.ne(lhs, rhs);
        }  else if(criterion.getOperator() == SearchConstants.IN) {
        	ArrayList<Long> tokens = new ArrayList<Long>();
    		
        	StringTokenizer st = new StringTokenizer(criterion.getRhs(), ","); 
        	while (st.hasMoreTokens()) {
        		String token = st.nextToken();
        	    tokens.add(Long.parseLong(token));
        	}

        	expression = Restrictions.in(lhs, tokens);
        }

        return expression;
    }

    private static Criterion getBigDecimalExpression(SearchCriterion criterion) {
    	Criterion  expression = null;
    	String     lhs        = getLastTokenfromLHS(criterion.getLhs());
    	BigDecimal rhs        = null;

        if (criterion.getType() == SearchConstants.BIGDECIMAL_DATA_TYPE){
            rhs = new BigDecimal(criterion.getRhs());
        }

        if (criterion.getOperator().equals(SearchConstants.equals)) {
            expression = Restrictions.eq(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.greaterThan)) {
            expression = Restrictions.gt(lhs, rhs);
        } else if (criterion.getOperator().equals(
                SearchConstants.greaterThanEquals)) {
            expression = Restrictions.ge(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.lessThan)) {
            expression = Restrictions.lt(lhs, rhs);
        } else if (criterion.getOperator().equals(
                SearchConstants.lessThanEquals)) {
            expression = Restrictions.le(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.notEquals)) {
            expression = Restrictions.ne(lhs, rhs);
        }  else if(criterion.getOperator() == SearchConstants.IN) {
        	ArrayList<BigDecimal> tokens = new ArrayList<BigDecimal>();
    		
        	StringTokenizer st = new StringTokenizer(criterion.getRhs(), ","); 
        	while (st.hasMoreTokens()) {
        		String token = st.nextToken();
        	    tokens.add(new BigDecimal(token));
        	}
        	
        	expression = Restrictions.in(lhs, tokens);
        }

        return expression;
    }

    private static Criterion getIntegerExpression(SearchCriterion criterion) {
    	Criterion expression = null;
    	String    lhs        = getLastTokenfromLHS(criterion.getLhs());
        Integer   rhs        = null;

        if (criterion.getType() == SearchConstants.INTEGER_DATA_TYPE) {
            rhs = Integer.parseInt(criterion.getRhs());
        }

        if (criterion.getOperator().equals(SearchConstants.equals)) {
            expression = Restrictions.eq(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.greaterThan)) {
            expression = Restrictions.gt(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.greaterThanEquals)) {
            expression = Restrictions.ge(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.lessThan)) {
            expression = Restrictions.lt(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.lessThanEquals)) {
            expression = Restrictions.le(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.notEquals)) {
            expression = Restrictions.ne(lhs, rhs);
        } else if(criterion.getOperator() == SearchConstants.IN) {
        	ArrayList<Integer> tokens = new ArrayList<Integer>();
    		
        	StringTokenizer st = new StringTokenizer(criterion.getRhs(), ","); 
        	while (st.hasMoreTokens()) {
        		String token = st.nextToken();
        	    tokens.add(new Integer(token));
        	}
        	
        	expression = Restrictions.in(lhs, tokens);
        }
        return expression;
    }

    private static Criterion getFloatExpression(SearchCriterion criterion) {
    	Criterion expression = null;
    	String    lhs        = getLastTokenfromLHS(criterion.getLhs());
        Float     rhs        = null;

        if (criterion.getType() == SearchConstants.FLOAT_DATA_TYPE){
            rhs = Float.parseFloat(criterion.getRhs());
        }

        if (criterion.getOperator().equals(SearchConstants.equals)) {
            expression = Restrictions.eq(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.greaterThan)) {
            expression = Restrictions.gt(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.greaterThanEquals)) {
            expression = Restrictions.ge(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.lessThan)) {
            expression = Restrictions.lt(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.lessThanEquals)) {
            expression = Restrictions.le(lhs, rhs);
        } else if (criterion.getOperator().equals(SearchConstants.notEquals)) {
            expression = Restrictions.ne(lhs, rhs);
        } else if(criterion.getOperator() == SearchConstants.IN) {
        	ArrayList<Float> tokens = new ArrayList<Float>();
    		
        	StringTokenizer st = new StringTokenizer(criterion.getRhs(), ","); 
        	while (st.hasMoreTokens()) {
        		String token = st.nextToken();
        	    tokens.add(new Float(token));
        	}
        	
        	expression = Restrictions.in(lhs, tokens);
        }
        return expression;
    }

    private static Criterion getDateExpression(SearchCriterion criterion, String format) {
    	Criterion expression = null;
    	String    lhs        = getLastTokenfromLHS(criterion.getLhs());

        if (criterion.getOperator().equals(SearchConstants.equals)) {
        	expression = Restrictions.sqlRestriction("TO_CHAR(" + lhs + ",'" + format + "') = '"  + criterion.getRhs() + "'");
        } else if (criterion.getOperator().equals(SearchConstants.greaterThan)){
            expression = Restrictions.sqlRestriction("TO_CHAR(" + lhs + ",'" + format + "') > '"  + criterion.getRhs() + "'");
        } else if (criterion.getOperator().equals(SearchConstants.greaterThanEquals)) {
        	expression = Restrictions.sqlRestriction("TO_CHAR(" + lhs + ",'" + format + "') >= '"  + criterion.getRhs() + "'");
        } else if (criterion.getOperator().equals(SearchConstants.lessThan)) {
        	expression = Restrictions.sqlRestriction("TO_CHAR(" + lhs + ",'" + format + "') < '"  + criterion.getRhs() + "'");
        } else if (criterion.getOperator().equals(SearchConstants.lessThanEquals)){
            expression = Restrictions.sqlRestriction("TO_CHAR(" + lhs + ",'" + format + "') <= '"  + criterion.getRhs() + "'");
        } else if (criterion.getOperator().equals(SearchConstants.notEquals)) {
            expression = Restrictions.sqlRestriction("TO_CHAR(" + lhs + ",'" + format + "') != '"  + criterion.getRhs() + "'");
        }
        return expression;
    }

    private static String getLastTokenfromLHS(String lhs) {
    	return lhs.substring(lhs.lastIndexOf('.') + 1);
    }

    /******************************************FOR SQL DB ***********************************************************************************
    private static Criterion getDateExpression(SearchCriterion criterion, String format) {
    	Criterion expression = null;
        if (criterion.getOperator().equals(SearchConstants.equals)) {
            expression = Restrictions.sqlRestriction("DATE_FORMAT(" + criterion.getLhs() + ",'" + format + "') = "  + criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.greaterThan)){
            expression = Restrictions.sqlRestriction("DATE_FORMAT(" + criterion.getLhs() + ",'" + format + "') > "  + criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.greaterThanEquals)) {
            expression = Restrictions.sqlRestriction("DATE_FORMAT(" + criterion.getLhs() + ",'" + format + "') >= " + criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.lessThan)) {
            expression = Restrictions.sqlRestriction("DATE_FORMAT(" + criterion.getLhs() + ",'" + format + "') < "  + criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.lessThanEquals)){
            expression = Restrictions.sqlRestriction("DATE_FORMAT(" + criterion.getLhs() + ",'" + format + "') <= " + criterion.getRhs());
        } else if (criterion.getOperator().equals(SearchConstants.notEquals)) {
            expression = Restrictions.sqlRestriction("DATE_FORMAT(" + criterion.getLhs() + ",'" + format + "') != " + criterion.getRhs());
        }
        return expression;
    }
    ******************************************************************************************************************************************/
}
