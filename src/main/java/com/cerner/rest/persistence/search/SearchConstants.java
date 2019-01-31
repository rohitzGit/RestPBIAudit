package com.cerner.rest.persistence.search;

public class SearchConstants {
	public static final String AND                   = "AND";
    public static final String equals                = "=";
    public static final String greaterThan           = ">";
    public static final String greaterThanEquals     = ">=";
    public static final String lessThan              = "<";
    public static final String lessThanEquals        = "<=";
    public static final String NOT                   = "!";
    public static final String notEquals             = "!=";
    public static final String OR                    = "OR";
    public static final String LIKE                  = "like";
    public static final String ILIKE                 = "ilike";
    public static final String IN                 	 = "in";

    public static final String ASC                   = "ASC";
    public static final String DESC                  = "DESC";

    public static final String MIN                   = "MIN";
    public static final String MAX                   = "MAX";
    public static final String AVG                   = "AVG";

    //public static final String DEFAULT_DATE_FORMAT_1 = "%m/%d/%Y";  //sqldb
    public static final String DEFAULT_DATE_FORMAT_1 = "YYYYMMDD";    //oracle

    public static final String NULL                  = null;

    public static final char   STRING_DATA_TYPE      = 'S';
    public static final char   DATE_DATA_TYPE        = 'D';
    public static final char   BIGDECIMAL_DATA_TYPE  = 'Z';
    public static final char   LONG_DATA_TYPE        = 'L';
    public static final char   INTEGER_DATA_TYPE     = 'I';
    public static final char   FLOAT_DATA_TYPE       = 'F';
    public static final char   BOOLEAN_DATA_TYPE     = 'B';
    public static final char   ORDER                 = 'O';
    public static final char   CONDN                 = 'C';
    public static final char   DIST                  = 'U';
}
