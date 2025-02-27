package org.example.client;

import org.example.db.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private Connection connection;

    public ClientService() {
        this.connection = DataBase.getInstance().getConnection();
    }

    public long create(String name) {
        try{
            CheckValideNameClient.checkName(name);
            String sqlCreate = "INSERT INTO client (NAME) VALUES (?)";
            String sqlSelectNewId = "SELECT * FROM client ORDER BY id DESC LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCreate)) {
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectNewId)) {
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    return result.getLong("id");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }

    public String getById(long id){
        String sqlSelect = "SELECT name FROM client WHERE id LIKE ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect)){
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getString("name");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "1";
    }

    public void setName(long id, String name){
        try{
            CheckValideNameClient.checkName(name);
            String sqlUpdate = "UPDATE client SET name=? WHERE id LIKE ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)){
                preparedStatement.setString(1, name);
                preparedStatement.setLong(2, id);
                preparedStatement.executeUpdate();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void deleteById(long id){
        String sqlDelete = "DELETE FROM client WHERE id LIKE ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public List<Client> listAll(){
        String sqlSelectAll = "SELECT id, name FROM client";
        List<Client> arrayListOfClients = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectAll)){
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                arrayListOfClients.add(new Client(result.getLong("id"), result.getString("name")));
            }
            return  arrayListOfClients;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
