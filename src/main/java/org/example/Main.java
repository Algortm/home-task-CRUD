package org.example;

import org.example.client.Client;
import org.example.client.ClientService;
import org.example.db.DataBase;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataBase.makeMigration();
        ClientService service = new ClientService();

        // TEST
        long resultIdOfClient = service.create("Tony");
        System.out.println(resultIdOfClient);

        String resultClientNameById = service.getById(6);
        System.out.println(resultClientNameById);

        service.setName(6, "Thomas");
        System.out.println(service.getById(6));

        service.deleteById(6);

        List<Client> allClients =  service.listAll();
        for(Client client: allClients){
            System.out.println("ID: " + client.getId() + " Name: " + client.getName()+ '\n');
        }
    }
}