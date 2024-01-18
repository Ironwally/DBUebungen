package de.hska.iwii.db1.jdbc;

import java.util.ArrayList;
import java.util.Properties;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class Aufgabe4 {
	private static final String URL = "jdbc:postgresql://datenbanken1.ddns.net:3690/g28";
	private static final String USER = "g28";
	private static final String PASSWORD = "ak5GsCvhPF";

	public static void main(String[] args) throws ClassNotFoundException {


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
		
    	String query1 = "SELECT persnr, name, ort, aufgabe FROM personal";
    	String query2 = "SELECT * FROM kunde";
    	String query3 = "SELECT k.name AS kunde, k.nr AS knr, l.name AS lieferant, l.nr AS lnr FROM kunde k "
    				  + "JOIN lieferant l ON k.nr = l.nr WHERE k.name LIKE ?";
    	String query4 = "SELECT * from auftrag";
    	String query5 = "SELECT * from auftragsposten";
    	
		reInitializeDB(connection);
    	addEntry(connection, "Dreirad Rad Jas", "Renckstrasse 3", 76189, "Karlsruhe");
		readFromDatabase(connection, query2, "%");
		readFromDatabase(connection, query4, "%");
		readFromDatabase(connection, query5, "%");
		updateSperre(connection);
		readFromDatabase(connection, query2, "%");
		deleteEntry(connection);
		readFromDatabase(connection, query2, "%");
		closeConnection(connection);
		

	}
		
	
	private static Connection openConnection(String url, Properties props) throws SQLException {
		return DriverManager.getConnection(url, props);
	}
	
	private static void closeConnection(Connection connection)
	{
        try 
        {
            if (connection != null && !connection.isClosed()) 
            {
                connection.close();
                System.out.println();
                System.out.println("Verbindung geschlossen.");
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Fehler beim Schließen der Verbindung: " + e.getMessage());
        }
	}
	
    private static void printDatabaseInfo(Connection connection) throws SQLException {
        System.out.println("Datenbank-URL: " + URL);
        System.out.println("Benutzer: " + USER);
        System.out.println("JDBC-Treiber: " + connection.getMetaData().getDriverName());
    }
    
    private static void readFromDatabase(Connection connection, String query, String filter)
    {
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;

    	
    	try 
    	{
			preparedStatement = connection.prepareStatement(query);
			if (preparedStatement.getParameterMetaData().getParameterCount() != 0)
			{
				preparedStatement.setString(1, filter);
			}
			resultSet = preparedStatement.executeQuery();
			int columnCount = resultSet.getMetaData().getColumnCount();
			ArrayList<Integer> columnWidths = new ArrayList<Integer>();
			columnWidths.add(null);
			
			System.out.println();
			//Header ausgeben und maximale spalten breite ermitteln
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
			//Spalten Typen ausgeben
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
			
			//Alle Zeilen durchgehen und ausgeben
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
    
    //Aufgabe 4.4.1
    private static void addEntry(Connection connection, String name, String strasse, int plz, String ort)
    {

    	String insertKundeQuery = "INSERT INTO kunde (nr, name, strasse, plz, ort, sperre) " +
    						 "VALUES (7, ?, ?, ?, ?, 0)";
    	String insertAuftragQuery = "INSERT INTO auftrag (auftrnr, datum, kundnr, persnr) " +
    						 		"VALUES (6, ?, 7, 3)";
    	String insertPostenQuery = "INSERT INTO auftragsposten (posnr, auftrnr, teilnr, anzahl, gesamtpreis) " +
    							   "VALUES (61, 6, 200002, 3, 1200)";

    	
    	
    	PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(insertKundeQuery);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, strasse);
			preparedStatement.setInt(3, plz);
			preparedStatement.setString(4, ort);
			preparedStatement.executeUpdate();
			
			//Auftrag setzen
			Date currentDate = new Date(System.currentTimeMillis());
			preparedStatement = connection.prepareStatement(insertAuftragQuery);
			preparedStatement.setDate(1, currentDate);
			preparedStatement.executeUpdate();
			
			//Posten setzen
			preparedStatement = connection.prepareStatement(insertPostenQuery);
			preparedStatement.executeUpdate();
			

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    //Aufgabe 4.4.2
    private static void updateSperre(Connection connection)
    {
    	PreparedStatement preparedStatement;
		//Sperre updaten
    	System.out.println("Updated Sperre");
    	String updateKundeQuery = "UPDATE kunde SET sperre = 1 WHERE nr = 7";
		try {
			preparedStatement = connection.prepareStatement(updateKundeQuery);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    //Aufgabe 4.4.3
    private static void deleteEntry(Connection connection)
    {
    	PreparedStatement preparedStatement;
		//Sperre updaten
    	System.out.println("Deleted Entry");
    	String deleteKundeQuery = "DELETE FROM kunde WHERE nr = 7";
    	String deleteAuftragQuery = "DELETE FROM auftrag where kundnr = 7";
    	String deletePostenQuery = "DELETE FROM auftragsposten WHERE auftrnr = 6";
		try {
			//Posten mit Auftragnr löschen
			preparedStatement = connection.prepareStatement(deletePostenQuery);
			preparedStatement.executeUpdate();
			//Auftrag mit Kundennr löschen
			preparedStatement = connection.prepareStatement(deleteAuftragQuery);
			preparedStatement.executeUpdate();
			//Kunde löschen
			preparedStatement = connection.prepareStatement(deleteKundeQuery);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    

    //Vorgegeben aus JDBCBikeShop.java -> Resetet Datenbank
    public static void reInitializeDB(Connection connection) {
        try {
            System.out.println("\nInitializing DB.");
            connection.setAutoCommit(true);
            String productName = connection.getMetaData().getDatabaseProductName();
            boolean isMsSql = productName.equals("Microsoft SQL Server");
            Statement statement = connection.createStatement();
            int numStmts = 0;
            
            // Liest den Inhalt der Datei ein.
            String[] fileContents = new String(Files.readAllBytes(Paths.get("sql/Bike.sql")),
					StandardCharsets.UTF_8).split(";");
            
            for (String sqlString : fileContents) {
                try {
                	// Microsoft kenn den DATE-Operator nicht.
                    if (isMsSql) {
                        sqlString = sqlString.replace(", DATE '", ", '");
                    }
                    statement.execute(sqlString);
                    System.out.print((++numStmts % 80 == 0 ? "/\n" : "."));
                } catch (SQLException e) {
                    System.out.print("\n" + sqlString.replace('\n', ' ').trim() + ": ");
                    System.out.println(e.getMessage());
                }
            }
            statement.close();
            System.out.println("\nBike database is reinitialized on " + productName +
                    "\nat URL " + connection.getMetaData().getURL()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
		