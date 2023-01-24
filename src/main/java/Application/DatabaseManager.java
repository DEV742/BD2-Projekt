package Application;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseManager {
    String findPersonByCredentials = "SELECT * FROM osoba WHERE nr_telefonu=";
    String streetsList = "SELECT nazwa FROM ulice;";
    String connectionUrl = "jdbc:mysql://localhost:3306/apkabd2";

    public ArrayList<String> getStreets() {
        ArrayList<String> streets = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient");
             PreparedStatement ps = conn.prepareStatement(streetsList);
             //PreparedStatement ps = conn.prepareStatement(findPersonByCredentials);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("nazwa");
                streets.add(name);
            }

            return streets;
        } catch (SQLException e) {
            // handle the exception
            return null;
        }
    }
    public ArrayList<String> findPersonByCredentials(String phone, String email) {
        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient");
             PreparedStatement ps = conn.prepareStatement(findPersonByCredentials + phone + " AND email=\"" + email + "\";");
             //PreparedStatement ps = conn.prepareStatement(findPersonByCredentials);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("imie");
                String lastName = rs.getString("nazwisko");
                String emailReturn = rs.getString("email");
                String phoneReturn = rs.getString("nr_telefonu");
                ArrayList<String> list = new ArrayList<>();
                list.add(String.valueOf(id));
                list.add(name);
                list.add(lastName);
                list.add(emailReturn);
                list.add(phoneReturn);

                return list;
            }
            return null;
        } catch (SQLException e) {
            // handle the exception
            return null;
        }
    }
}
