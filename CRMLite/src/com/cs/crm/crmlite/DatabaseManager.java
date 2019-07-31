package com.cs.crm.crmlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {

	/*
	 * Nothing to be kept static, to be accessed only through objects
	 * Statement object to be used for DDLs
	 * PreparedStatement object to be used for DMLs
	 * Two separate methods for running queries:
	 * 	- for select queries, that returns an ArrayList<String[]>, where each String[] is a row
	 *  - for non-select queries, that does not return anything
	 * Every object to be closed at connection closing
	 */
	
	private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    private String dbUserName = null;
    private String dbPassword=null;
    private String databaseName=null;
    
    /*
     * before every operation it is to be checked if connectionEstablished is true
     * if not, establish connection first and continue
     */
    
    private boolean connectionEstablished = false;
    
    /*
     * Setting database credentials through constructor 
     */
    
    protected DatabaseManager (String databaseName1, String dbUserName1, String dbPassword1) {
    	databaseName = databaseName1;
    	dbUserName = dbUserName1;
    	dbPassword = dbPassword1;
    	connectionEstablished = establishConnection();
    }
    
    /*
     * establishConnection() establishes the connection
     */
    
    private boolean establishConnection () {
    	try {
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://167.88.3.33:3306/" + databaseName + "?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow", dbUserName, dbPassword);
//	    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, dbUserName, dbPassword);
	        statement = connection.createStatement();
	        return true;
    	} catch (SQLException | ClassNotFoundException e) {
    		e.printStackTrace();
    		return false;
		}
    }
    
    /*
     * closeConnection() closes the connection
     */
    
    protected void closeConnection() {
    	if (resultSet != null) {
	        try {
	            resultSet.close();
	        } catch (SQLException sqlEx) {}
	        resultSet = null;
	    }
	    if (statement != null) {
	        try {
	            statement.close();
	        } catch (SQLException sqlEx) {}
	        statement = null;
	    }
	    if (preparedStatement != null) {
	        try {
	            preparedStatement.close();
	        } catch (SQLException sqlEx) {}
	        preparedStatement = null;
	    }
	    if (connection != null) {
	        try {
	            connection.close();
	        } catch (SQLException sqlEx) {}
	        connection = null;
	    }
	    connectionEstablished = false;
    }
    
    /*
     * executeSelectQuery() executes a select query and returns an ArrayList of String[] for the result
     */
    
	protected ArrayList<String[]> executeSelectQuery(String query) throws Exception {
		ArrayList<String[]> resultToReturn = new ArrayList<>();
		
		// establish connection if not already done
		if(!connectionEstablished)
			connectionEstablished = establishConnection();
		
		//running the query and getting the output
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
        	int cols = resultSet.getMetaData().getColumnCount();
	        while(resultSet.next()) {
	        	String[] tempRowStringArray = new String[cols];
	        	for (int i = 1; i <= cols; ++i)
	        		tempRowStringArray[i-1] = resultSet.getString(i);
	        	resultToReturn.add(tempRowStringArray);
	        }
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		    throw new Exception();
		}
		
		return resultToReturn;
	}
    
    /*
     * executeSelectQueryWithMetaData() executes a select query and returns an ArrayList of String[] for the result
     * the first entry in the ArrayList is a String[] containing table meta data
     */
    
	protected ArrayList<String[]> executeSelectQueryWithMetaData(String query) throws Exception {
		ArrayList<String[]> resultToReturn = new ArrayList<>();
		
		// establish connection if not already done
		if(!connectionEstablished)
			connectionEstablished = establishConnection();
		
		//running the query and getting the output
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
        	int cols = resultSet.getMetaData().getColumnCount();
        	String[] metaHeads = new String[cols];
        	for(int i = 0; i < cols; i++)
        		metaHeads[i] = resultSet.getMetaData().getColumnName(i+1);
        	resultToReturn.add(metaHeads);
	        while(resultSet.next()) {
	        	String[] tempRowStringArray = new String[cols];
	        	for (int i = 1; i <= cols; ++i)
	        		tempRowStringArray[i-1] = resultSet.getString(i);
	        	resultToReturn.add(tempRowStringArray);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		    throw new Exception();
		}

		return resultToReturn;
	}
    
    /*
     * executeNonSelectDMLQuery() executes an insert or an update query
     */
    
	protected int executeNonSelectDMLQuery(String query) throws Exception {

		// establish connection if not already done
		if(!connectionEstablished)
			connectionEstablished = establishConnection();
		
		// running the query and getting number of rows updated
		try {
	        preparedStatement = connection.prepareStatement(query);
	        return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		    throw new Exception();
		}
	}


}
