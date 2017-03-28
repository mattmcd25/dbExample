/**
 * Created by WilsonWong on 3/19/2017.
 */

import java.sql.*;
import java.util.Scanner;
public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("-------Embedded Java DB Connection Testing --------");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.");
            e.printStackTrace();
            return;
        }

        System.out.println("Java DB driver registered!");
        Connection connection = null;

        try {
            // substitute your database name for myDB
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
            System.out.println("Java DB connection established!");

            Statement stmt = connection.createStatement();
            ResultSet rset = stmt.getResultSet();

            while(true) {
                System.out.print("Menu:\n(1) Add new entry\n(2) Print current entries\n(3) Re-create table\n(4) Quit\nChoose an option: ");
                String in = sc.nextLine();
                if(in.equals("1")) {
                    String ID = "0";
                    String name = "Allergy";
                    String room = "4G";
                    String locType = "Service";
                    stmt.execute("INSERT INTO Location VALUES (0, 'Allergy', '4G', 'Service')");// + ID + ", " + name + ", " + room + ", " + locType + ")");
                }
                else if(in.equals("2")) {
                    rset = stmt.executeQuery("SELECT * FROM Location");
                    System.out.println(" ID|                                             Name |     Room |   locType");
                    System.out.println("---|--------------------------------------------------|----------|----------");
                    while (rset.next()) {
                        String ID = String.format("%1$"+3+ "s", rset.getString("locID"));
                        String name = String.format("%1$"+50+ "s", rset.getString("name"));
                        String room = String.format("%1$"+10+ "s", rset.getString("room"));
                        String locType = String.format("%1$"+10+ "s", rset.getString("locType"));
                        System.out.println(ID + "|" + name + "|" + room + "|" + locType);
                    }
                    System.out.println("---|--------------------------------------------------|----------|----------");
                }
                else if(in.equals("3")) {
                    stmt.execute("DROP TABLE Location");
                    stmt.execute("CREATE TABLE Location ( locID integer primary key, name varchar(50), room varchar(10), locType varchar(10))");
                }
                else if(in.equals("4")) {
                    break;
                }
                System.out.println();
            }

            if(rset != null) rset.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }
}
