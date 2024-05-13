package dev.faceless.swiftlib.lib.storage.database;

public final class SQLKeyword {
    private SQLKeyword() {}

    //Data Types
    public static final String INTEGER = "INTEGER";
    public static final String TEXT = "TEXT";
    public static final String REAL = "REAL";
    public static final String BLOB = "BLOB";

    //Constraints
    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String AUTOINCREMENT = "AUTOINCREMENT";
    public static final String NOT_NULL = "NOT NULL";
    public static final String UNIQUE = "UNIQUE";
    public static final String DEFAULT = "DEFAULT";
    public static final String CHECK = "CHECK";

    //Statements
    public static final String CREATE_TABLE = "CREATE TABLE";
    public static final String DROP_TABLE = "DROP TABLE";
    public static final String INSERT_INTO = "INSERT INTO";
    public static final String SELECT = "SELECT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String FROM = "FROM";
    public static final String WHERE = "WHERE";
    public static final String SET = "SET";
    public static final String ORDER_BY = "ORDER BY";
    public static final String GROUP_BY = "GROUP BY";
    public static final String HAVING = "HAVING";
    public static final String LIMIT = "LIMIT";

    //Operators
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String NOT = "NOT";
    public static final String LIKE = "LIKE";
    public static final String BETWEEN = "BETWEEN";
    public static final String IN = "IN";
    public static final String IS_NULL = "IS NULL";
    public static final String IS_NOT_NULL = "IS NOT NULL";
    public static final String EQUALS = "=";
    public static final String NOT_EQUALS = "<>";
    public static final String GREATER_THAN = ">";
    public static final String LESS_THAN = "<";
    public static final String GREATER_THAN_OR_EQUALS = ">=";
    public static final String LESS_THAN_OR_EQUALS = "<=";

    //Functions
    public static final String COUNT = "COUNT";
    public static final String SUM = "SUM";
    public static final String AVG = "AVG";
    public static final String MIN = "MIN";
    public static final String MAX = "MAX";
}
