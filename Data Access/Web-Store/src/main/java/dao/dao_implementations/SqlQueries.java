package dao.dao_implementations;

public class SqlQueries {
    private SqlQueries(){
    }
//  Queries from items table
    public static final String SELECT_ITEM_BY_ID = "SELECT * FROM ITEMS WHERE ID_ITEM = ?";
    public static final String SELECT_ALL_ITEM = "SELECT * FROM ITEMS";
    public static final String INSERT_ITEMS = "INSERT INTO ITEMS (NAME, COMPANY, PRICE) VALUES (?,?,?)";
    public static final String UPDATE_ITEM = "UPDATE ITEMS SET NAME  = ?, COMPANY = ?, PRICE = ? WHERE ID_ITEM = ?";
    public static final String DELETE_ITEM = "DELETE FROM ITEMS WHERE ID_ITEM = ?";

//  Queries from customers table
    public static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM CUSTOMERS WHERE ID_CUSTOMER = ?";
    public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM CUSTOMERS";
    public static final String INSERT_CUSTOMERS = "INSERT INTO CUSTOMERS (ID_CUSTOMER, NAME, TELEPHONE, ADDRESS) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_CUSTOMER = "UPDATE CUSTOMERS SET NAME  = ?, TELEPHONE = ?, ADDRESS = ? WHERE ID_CUSTOMER = ?";
    public static final String DELETE_CUSTOMER = "DELETE FROM CUSTOMERS WHERE ID_CUSTOMER = ?";
//  Queries from purchases table
    public static final String SELECT_PURCHASE_BY_ID = "SELECT PURCHASES.ID_PURCHASE, PURCHASES.DATE, CUSTOMERS.ID_CUSTOMER, CUSTOMERS.NAME, CUSTOMERS.TELEPHONE, CUSTOMERS.ADDRESS,ITEMS.ID_ITEM, ITEMS.NAME, ITEMS.COMPANY, ITEMS.PRICE FROM ((PURCHASES JOIN CUSTOMERS ON PURCHASES.ID_CUSTOMER = CUSTOMERS.ID_CUSTOMER) JOIN ITEMS ON PURCHASES.ID_ITEM = ITEMS.ID_ITEM) WHERE ID_PURCHASE = ? ORDER BY PURCHASES.ID_PURCHASE";
    public static final String SELECT_ALL_PURCHASES = "SELECT PURCHASES.ID_PURCHASE, PURCHASES.DATE, CUSTOMERS.ID_CUSTOMER, CUSTOMERS.NAME, CUSTOMERS.TELEPHONE, CUSTOMERS.ADDRESS,ITEMS.ID_ITEM, ITEMS.NAME, ITEMS.COMPANY, ITEMS.PRICE FROM ((PURCHASES JOIN CUSTOMERS ON PURCHASES.ID_CUSTOMER = CUSTOMERS.ID_CUSTOMER) JOIN ITEMS ON PURCHASES.ID_ITEM = ITEMS.ID_ITEM) ORDER BY PURCHASES.ID_PURCHASE";
    public static final String SELECT_PURCHASE_BY_DATE = "SELECT PURCHASES.ID_PURCHASE, PURCHASES.DATE, CUSTOMERS.ID_CUSTOMER, CUSTOMERS.NAME, CUSTOMERS.TELEPHONE, CUSTOMERS.ADDRESS,ITEMS.ID_ITEM, ITEMS.NAME, ITEMS.COMPANY, ITEMS.PRICE FROM ((PURCHASES JOIN CUSTOMERS ON PURCHASES.ID_CUSTOMER = CUSTOMERS.ID_CUSTOMER) JOIN ITEMS ON PURCHASES.ID_ITEM = ITEMS.ID_ITEM) WHERE PURCHASES.DATE = ?";
    public static final String INSERT_PURCHASE = "INSERT INTO PURCHASES (DATE, ID_CUSTOMER, ID_ITEM) VALUES (?, ?, ?)";
    public static final String UPDATE_PURCHASE = "UPDATE PURCHASES SET DATE  = ?, ID_CUSTOMER = ?, ID_ITEM = ? WHERE ID_PURCHASE = ?";
    public static final String DELETE_PURCHASE = "DELETE FROM PURCHASES WHERE ID_PURCHASE = ?";
    public static final String SELECT_PURCHASES_BY_CUSTOMER = "SELECT PURCHASES.ID_PURCHASE, PURCHASES.DATE, CUSTOMERS.ID_CUSTOMER, CUSTOMERS.NAME, CUSTOMERS.TELEPHONE, CUSTOMERS.ADDRESS,ITEMS.ID_ITEM, ITEMS.NAME, ITEMS.COMPANY, ITEMS.PRICE FROM ((PURCHASES JOIN CUSTOMERS ON PURCHASES.ID_CUSTOMER = CUSTOMERS.ID_CUSTOMER AND CUSTOMERS.ID_CUSTOMER = ?) JOIN ITEMS ON PURCHASES.ID_ITEM = ITEMS.ID_ITEM)";
    public static final String SELECT_PURCHASES_BY_ITEM = "SELECT PURCHASES.ID_PURCHASE, PURCHASES.DATE, CUSTOMERS.ID_CUSTOMER, CUSTOMERS.NAME, CUSTOMERS.TELEPHONE, CUSTOMERS.ADDRESS,ITEMS.ID_ITEM, ITEMS.NAME, ITEMS.COMPANY, ITEMS.PRICE FROM ((PURCHASES JOIN CUSTOMERS ON PURCHASES.ID_CUSTOMER = CUSTOMERS.ID_CUSTOMER) JOIN ITEMS ON PURCHASES.ID_ITEM = ITEMS.ID_ITEM) WHERE ITEMS.ID_ITEM = ?";
    public static final String DELETE_PURCHASE_BY_CUSTOMER = "DELETE FROM PURCHASES WHERE ID_CUSTOMER = ?";
    public static final String DELETE_PURCHASE_BY_ITEM = "DELETE FROM PURCHASES WHERE ID_ITEM = ?";

//  Queries from reviews table
    public static final String SELECT_REVIEW_BY_ID = "SELECT ID_REVIEW, RATING, TITLE, DESCRIPTION, REVIEWS.DATE, REVIEWS.ID_PURCHASE, P.DATE, P.ID_CUSTOMER, C.NAME, TELEPHONE, ADDRESS, P.ID_ITEM, I.NAME, COMPANY, PRICE FROM REVIEWS JOIN PURCHASES P on REVIEWS.ID_PURCHASE = P.ID_PURCHASE JOIN CUSTOMERS C on C.ID_CUSTOMER = P.ID_CUSTOMER JOIN ITEMS I on P.ID_ITEM = I.ID_ITEM; WHERE ID_REVIEW = ?";
    public static final String SELECT_ALL_REVIEWS = "SELECT ID_REVIEW, RATING, TITLE, DESCRIPTION, REVIEWS.DATE, REVIEWS.ID_PURCHASE, P.DATE, P.ID_CUSTOMER, C.NAME, TELEPHONE, ADDRESS, P.ID_ITEM, I.NAME, COMPANY, PRICE FROM REVIEWS JOIN PURCHASES P on REVIEWS.ID_PURCHASE = P.ID_PURCHASE JOIN CUSTOMERS C on C.ID_CUSTOMER = P.ID_CUSTOMER JOIN ITEMS I on P.ID_ITEM = I.ID_ITEM";
    public static final String INSERT_REVIEW = "INSERT INTO REVIEWS (RATING, TITLE, DESCRIPTION, DATE, ID_PURCHASE) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_REVIEW = "UPDATE REVIEWS SET RATING = ?, TITLE = ?, DESCRIPTION = ?, DATE = ?, ID_PURCHASE = ? WHERE ID_REVIEW = ?";
    public static final String DELETE_REVIEW = "DELETE FROM REVIEWS WHERE ID_REVIEW = ?";
    public static final String SELECT_REVIEW_BY_CUSTOMER = "SELECT ID_REVIEW, RATING, TITLE, DESCRIPTION, REVIEWS.DATE, REVIEWS.ID_PURCHASE, P.DATE, P.ID_CUSTOMER, C.NAME, TELEPHONE, ADDRESS, P.ID_ITEM, I.NAME, COMPANY, PRICE FROM REVIEWS JOIN PURCHASES P on REVIEWS.ID_PURCHASE = P.ID_PURCHASE JOIN CUSTOMERS C on C.ID_CUSTOMER = P.ID_CUSTOMER JOIN ITEMS I on P.ID_ITEM = I.ID_ITEM WHERE P.ID_CUSTOMER = ?";
    public static final String SELECT_REVIEW_BY_ITEM = "SELECT ID_REVIEW, RATING, TITLE, DESCRIPTION, REVIEWS.DATE, REVIEWS.ID_PURCHASE, P.DATE, P.ID_CUSTOMER, C.NAME, TELEPHONE, ADDRESS, P.ID_ITEM, I.NAME, COMPANY, PRICE FROM REVIEWS JOIN PURCHASES P on REVIEWS.ID_PURCHASE = P.ID_PURCHASE JOIN CUSTOMERS C on C.ID_CUSTOMER = P.ID_CUSTOMER JOIN ITEMS I on P.ID_ITEM = I.ID_ITEM WHERE P.ID_ITEM = ?";
    public static final String SELECT_REVIEW_BY_PURCHASE = "SELECT ID_REVIEW, RATING, TITLE, DESCRIPTION, REVIEWS.DATE, REVIEWS.ID_PURCHASE, P.DATE, P.ID_CUSTOMER, C.NAME, TELEPHONE, ADDRESS, P.ID_ITEM, I.NAME, COMPANY, PRICE FROM REVIEWS JOIN PURCHASES P on REVIEWS.ID_PURCHASE = P.ID_PURCHASE JOIN CUSTOMERS C on C.ID_CUSTOMER = P.ID_CUSTOMER JOIN ITEMS I on P.ID_ITEM = I.ID_ITEM WHERE REVIEWS.ID_PURCHASE = ?";

//  Queries from Users
    public static final String CHECK_USER_PASSWORD = "SELECT USER_ID, USERNAME FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
    public static final String SELECT_USER = "SELECT USER_ID, USERNAME FROM USERS WHERE USER_ID = ?";
    public static final String SELECT_ALL_USERS = "SELECT USER_ID, USERNAME FROM USERS";
    public static final String INSERT_USER = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)";
    public static final String UPDATE_USER = "UPDATE USERS SET USERNAME = ?, PASSWORD = ? WHERE USER_ID = ?";
    public static final String DELETE_USER = "DELETE FROM USERS WHERE USER_ID = ?";

}
