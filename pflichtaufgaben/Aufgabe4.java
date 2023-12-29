package de.hska.iwii.db1.jdbc;

import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;

public class Aufgabe4 {
	private static final String URL = "jdbc:postgresql://datenbanken1.ddns.net:3690/g28";
	private static final String USER = "g28";
	private static final String PASSWORD = "ak5GsCvhPF";

	public static void main(String[] args) throws ClassNotFoundException {
		// 1. Laden des PostgreSQL-Treibers durch Angabe des Klassennamens
		Class.forName("org.postgresql.Driver");
		Properties props = new Properties();
		props.put("user", USER);
		props.put("password", PASSWORD);
		Connection connection = null;
		try {
			connection = openConnection(URL, props);
			if (connection != null)
			{
				System.out.println("Verbindung hergestellt!");
				printDatabaseInfo(connection);
			}
			else
			{
				System.out.println("Verbindung fehlgeschlagen!");
			}
		}
		catch (SQLException e)
		{
			System.out.println("Fehler beim Herstellen der Verbindung");
		}
		
    	String query = "SELECT persnr, name, ort, aufgabe FROM personal";
    	String query2 = "SELECT * FROM kunde";
    	
		readFromDatabase(connection, query);
		closeConnection(connection);
		
		
		

	}
		
	
	private static Connection openConnection(String url, Properties props) throws SQLException {
		return DriverManager.getConnection(url, props);
	}
	
	private static void closeConnection(Connection connection)
	{
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println();
                System.out.println("Verbindung geschlossen.");
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Schließen der Verbindung: " + e.getMessage());
        }
	}
	
    private static void printDatabaseInfo(Connection connection) throws SQLException {
        System.out.println("Datenbank-URL: " + URL);
        System.out.println("Benutzer: " + USER);
        System.out.println("JDBC-Treiber: " + connection.getMetaData().getDriverName());
    }
    
    private static void readFromDatabase(Connection connection, String query)
    {
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;

    	
    	try 
    	{
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			int columnCount = resultSet.getMetaData().getColumnCount();
			ArrayList<Integer> columnWidths = new ArrayList<Integer>();
			columnWidths.add(null);
			
			System.out.println();
			for(int i = 1; i <= columnCount; i++)
			{

				String columnHeaderName = resultSet.getMetaData().getColumnName(i);
				String columnHeaderType = resultSet.getMetaData().getColumnTypeName(i);
				int displaySize = resultSet.getMetaData().getColumnDisplaySize(i);
				int columnMaxWidth = Math.max(displaySize, Math.max(columnHeaderName.length(), columnHeaderType.length()));
				columnWidths.add(columnMaxWidth);
				System.out.printf("%-" + columnMaxWidth + "s | ", columnHeaderName);
			}
			System.out.println();
			for(int i = 1; i <= columnCount; i++)
			{
				String columnHeaderType = resultSet.getMetaData().getColumnTypeName(i);
				int columnMaxWidth = columnWidths.get(i);
				System.out.printf("%-" + columnMaxWidth + "s | ", columnHeaderType);

			}
			System.out.println();
			//Abtrennung von Header
			for(int i = 1; i <= columnCount; i++)
			{
				int columnMaxWidth = columnWidths.get(i);
				String result = String.format ( "%-" + columnMaxWidth + "s + ", "-").replace(" ", "-");
				System.out.printf(result);
			}
			
			
			while(resultSet.next())
			{
				System.out.println();
				for(int i = 1; i <= columnCount; i++)
				{
					int columnMaxWidth = columnWidths.get(i);
					String result;
					if (resultSet.getMetaData().getColumnType(i) == java.sql.Types.INTEGER)
					{
						 result = String.format( "%" + columnMaxWidth + "d | " , resultSet.getInt(i)) ;
					}
					else
					{
						 result = String.format( "%-" + columnMaxWidth + "s | " , resultSet.getString(i)) ;
					}

					System.out.printf(result);
				}
			}
			System.out.println();



			
			
 
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}

    }
	
}
		