package test.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import java.util.ArrayList;

public class DbMain {

    private static final String URL = "jdbc:postgresql://localhost:5432/irondb";
	private static final String USER = "postgres";
	private static final String PASSWORD = "";

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");     // seit java 1.6 nicht mehr noetig
        Properties props = new Properties();
        props.put("user", USER);
        props.put("password", PASSWORD);
        Connection connection = openConnection(URL, props);   
        
        String ausgabe = sqlExecute("SELECT * FROM personal",connection);
        //4.3 fehlt



        System.out.println(ausgabe);
        close(connection);
    }



    private static Connection openConnection (String url, Properties props){
        try{
            return DriverManager.getConnection(url, props);
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        } return null;
    }

    private static String sqlExecute(String command, Connection connection) {
        String giveback = null;

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(command);
            ResultSetMetaData rsmd = rs.getMetaData();
            int maxSpacing = 0;
            int columnCount = rsmd.getColumnCount();
            ArrayList<Integer> columnWidths = new ArrayList<Integer>();
            
            // Print Header
            for (int i=1;i<columnCount;i++) {

                int columnMax = Math.max(rsmd.getColumnDisplaySize(i),rsmd.getColumnTypeName(i).length());
                columnMax = Math.max(columnMax, rsmd.getColumnName(i).length());
                columnWidths.add(columnMax);
                giveback += String.format("%-" + columnMax + "s |", rsmd.getColumnLabel(i));
            } giveback += "\n";

            // Print Type
            for (int i=1;i<columnCount;i++) {
                String columnHeaderType = rsmd.getColumnTypeName(i);
				int columnMaxWidth = columnWidths.get(i-1);
				System.out.printf("%-" + columnMaxWidth + "s | ", columnHeaderType);
            } giveback += "\n";

            // Print header seperation border
            for (int i=1;i<columnCount;i++) {
                giveback += String.format("%-" + columnWidths.get(i-1) + "s", "-" ).replace(' ', '-');
            } giveback += "\n";

            // Print Data
            while (rs.next()) {
                for (int i=1;i<columnCount;i++) {
                    int colWidth = columnWidths.get(i-1);
                    giveback += String.format("%-" + colWidth + "s | " , rs.getString(i));
                } giveback += " |\n";
            }

        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return giveback;
    }

    private static void close(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println();
                System.out.println("Closed Connection.");
            }
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
	}
    
    
}
