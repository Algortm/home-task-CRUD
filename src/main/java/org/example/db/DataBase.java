package org.example.db;
import org.flywaydb.core.Flyway;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
    private static final DataBase INSTANCE = new DataBase();
    private Connection connection;

    private DataBase(){
        try{
            connection = DriverManager.getConnection(Storage.DATA_BASE_URL);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static DataBase getInstance(){return INSTANCE;}
    public Connection getConnection(){return connection;}

    public void closeConnection(){
        try{
            this.connection.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void makeMigration(){
        Flyway flyway = Flyway.configure().dataSource(Storage.DATA_BASE_URL,  null, null).load();
        flyway.migrate();
    }
}
