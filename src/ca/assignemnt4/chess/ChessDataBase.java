
package ca.assignemnt4.chess;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.sql.Statement;

public class ChessDataBase {
    //Private connection - Make one connection, reuse it when possible, always close it when it's done. This is the data base connection class
    private Connection con;
    
    //Constructor that takes in a dataSource
    public ChessDataBase(DataSource dataSource) throws SQLException{
        con = dataSource.getConnection();
    }

    //Create Table Function
    public void CreateTable() throws SQLException {
        try(Statement stmt = con.createStatement()) {
            //Create Player info table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS player_information (player_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "player_name VARCHAR(32) NOT NULL, player_email VARCHAR(32) NOT NULL, player_password VARCHAR(32) NOT NULL)");
            
            //Create player score table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS player_ranking (player_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "player_wins INT, player_loses INT)");
            
            //Create game info table
             stmt.executeUpdate("CREATE TABLE IF NOT EXISTS game_information (game_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "player_1_name VARCHAR(32) NOT NULL, player_2_name VARCHAR(32) NOT NULL, game_winner INT, game_start_time DATETIME, "
            + "game_end_time DATETIME)");
            
        }
        catch(SQLException ex) {
            System.out.println("NewTableCreation() Error " + ex.getErrorCode() + " " + ex.getMessage());
        }
    }

    //Method to insert data
    public void insertNewData(String user_name, String user_email, String user_password) throws SQLException {
        try(Statement stmt = con.createStatement()) {
            stmt.executeUpdate("INSERT INTO player_information (player_name, player_email, player_password) VALUES " 
                    + "('" + user_name + "', '" + user_email + "', '" + user_password + "')");
            
            stmt.executeUpdate("INSERT INTO player_ranking (player_wins, player_loses) VALUES (0, 0)");
        }
        catch(SQLException ex) {          
            System.out.println("InserNewData() Error " + ex.getErrorCode() + " " + ex.getMessage());
        }
    }
    
    public int AddGameInfo(String p1name, String p2name) throws SQLException
    {
        int gameID = -1;
        
        try(Statement stmt = con.createStatement()) {
            //Create game info table
            stmt.executeUpdate("INSERT INTO game_information (player_1_name, player_2_name, game_winner, game_start_time) VALUES " 
                    + "('" + p1name + "', '" + p2name + "', 0, CURRENT_TIMESTAMP)");
            
            ResultSet rs = stmt.executeQuery("SELECT game_id FROM game_information");
            while(rs.next())
            {
                gameID = rs.getInt("game_id");
            }
        }
        catch(SQLException ex) {
            System.out.println("AddGameInfo() Error " + ex.getErrorCode() + " " + ex.getMessage());
        }
        return gameID;
    }
    
    public int GetPlayerID(String user_email)
    {
        int playerID = -1;
        try(Statement stmt = con.createStatement()) 
        {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM player_information "
                    + "WHERE player_email='" + user_email + "'")) 
            {
                if (rs.next()) {
                    playerID = rs.getInt("player_id");
                } 
                
                else {
                    throw new RuntimeException("Invalid character");
                }
            }
        }
        catch(SQLException ex) {          
            System.out.println("GetPlayerID() Error " + ex.getErrorCode() + " " + ex.getMessage());
        }
        return playerID;
    }
    
    public int getPlayerWins(int user_id)
    {
        int playerWins = -1;
        try(Statement stmt = con.createStatement()) 
        {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM player_ranking "
                    + "WHERE player_id=" + user_id)) 
            {
                if (rs.next()) {
                    playerWins = rs.getInt("player_wins");
                } 
                
                else {
                    throw new RuntimeException("Error reading score");
                }
            }
        }
        catch(SQLException ex) {          
            System.out.println("GetWins() Error " + ex.getErrorCode() + " " + ex.getMessage());
        }
        return playerWins;
    }
    
    public int getPlayerLoses(int user_id)
    {
        int playerLoses = -1;
        try(Statement stmt = con.createStatement()) 
        {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM player_ranking "
                    + "WHERE player_id=" + user_id)) 
            {
                if (rs.next()) {
                    playerLoses = rs.getInt("player_loses");
                } 
                
                else {
                    throw new RuntimeException("Error reading score");
                }
            }
        }
        catch(SQLException ex) {          
            System.out.println("GetLoses() Error " + ex.getErrorCode() + " " + ex.getMessage());
        }
        return playerLoses;
    }
 
    
    public void updatePlayerScore(int user_id, int gamesWon, int gamesLost)
    {
        try(Statement stmt = con.createStatement()) 
        {
            stmt.executeUpdate("UPDATE player_ranking SET player_wins=" + gamesWon + ", player_loses=" + gamesLost + " WHERE player_id=" + user_id);                  
            System.out.println("Score Updated!");  
        }
        catch(SQLException ex) {          
            System.out.println("Error " + ex.getErrorCode() + " " + ex.getMessage());
        }
    }
    
    public void updateGameScore(int gameID, int playerWinner)
    {
        try(Statement stmt = con.createStatement()) 
        {
            stmt.executeUpdate("UPDATE game_information SET game_winner=" + playerWinner + ", game_end_time=CURRENT_TIMESTAMP WHERE game_id=" + gameID);                  
            System.out.println("Score Updated!");  
        }
        catch(SQLException ex) {          
            System.out.println("Error " + ex.getErrorCode() + " " + ex.getMessage());
        }
    }
    
    public boolean PasswordCheck(String user_name, String user_email, String user_password) throws SQLException
    {
        //Create the statement
        try (Statement stmt = con.createStatement()) 
        {
            //Try and find the input email 
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM player_information")) 
            {
                //Move the cursor to that line of 
                while(rs.next()) 
                {
                    String savedEmail = rs.getString("player_email");
                    
                    if(savedEmail.equals(user_email))
                    {
                        String savedPassword = rs.getString("player_password");
                        if(savedPassword.equals(user_password))
                        {
                            System.out.println("Password matches saved password");
                            return true;
                        }
                        else
                        {
                            System.out.println("Error, password mismatch");
                            return false;
                        }
                    }
                }

                //If the user email was never found, then add the user as a new member
                insertNewData(user_name, user_email, user_password);
                System.out.println("New User found! Information saved");
                return true;
            }
        }
        catch(SQLException ex) {          
            System.out.println("PasswordCheck() Error " + ex.getErrorCode() + " " + ex.getMessage());
            return false;
        }
    }
}
