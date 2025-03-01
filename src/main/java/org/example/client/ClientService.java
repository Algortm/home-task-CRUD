package org.example.client;
import org.example.db.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private Connection connection;
    private List<Client> clientsAll = new ArrayList<>();

    public ClientService() {
        this.connection = DataBase.getInstance().getConnection();
    }

    public static boolean checkName(String name){
        return name.length()<1000 || name.length()>2;
    }

    public long create(String name) throws IllegalArgumentException {
        if(!checkName(name)){
            throw new IllegalArgumentException("Incorrect input name: " + name);
        }
        long id = -1;
        String sqlCreate = "INSERT INTO client (NAME) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCreate, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            id = rs.getLong("id");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public String getById(long id){
        String sqlSelect = "SELECT name FROM client WHERE id LIKE ?";
        String name = "";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect)){
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                name = result.getString("name");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return name;
    }

    public void setName(long id, String name) throws IllegalArgumentException{
        if(!checkName(name)){
            throw new IllegalArgumentException("Incorrect input name: " + name);
        }
        String sqlUpdate = "UPDATE client SET name=? WHERE id LIKE ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)){
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }catch (Exception ex){
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectAll)){
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                this.clientsAll.add(new Client(result.getLong("id"), result.getString("name")));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return clientsAll;
    }
}
