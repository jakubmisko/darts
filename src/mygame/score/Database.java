/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.score;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jakub
 */
public class Database {
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://db4free.net:3306/darts";
    private final String USER = "jakubmisko";
    private final String PASSWORD = "cocK12345";
    private final String QUERY_SELECT_PLAYERS = "SELECT meno, pocet FROM skore ORDER BY pocet asc";
    private final String QUERY_INSERT_PLAYER = "INSERT into `skore` (meno, pocet) values (?, ?)";
    
    private List<Record> players; 

    public Database() {
        players = new LinkedList<Record>();
    }
    
    public List load() {
        players = new ArrayList<Record>();
        try {
            Class.forName(DRIVER_CLASS);

            // try to connect with DB
            Connection connection = DriverManager.getConnection(URL, USER,
                    PASSWORD);

            // Create statement and query
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(QUERY_SELECT_PLAYERS);

            // Get all times from database
            while (rs.next()) {
                String meno = rs.getString("meno");
                int pocet = rs.getInt("pocet");
                players.add(new Record(meno, pocet));
            }
            
            stm.close();
            connection.close();
            
        } catch (ClassNotFoundException e) {
            System.out.println("Mysql driver not found");
        } catch (SQLException e) {
            System.out.println("Select problem");
        }
        return players;
    }

    public void add(String name, int pocet) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER,
                    PASSWORD);
            PreparedStatement pstm = connection
                    .prepareStatement(QUERY_INSERT_PLAYER);
            pstm.setString(1, name);
            pstm.setInt(2, pocet);
            pstm.execute();

            pstm.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Insert problem");
        }
        load();
    }
}
