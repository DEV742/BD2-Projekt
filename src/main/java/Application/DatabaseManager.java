package Application;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;


public class DatabaseManager {
    String findPersonByCredentials = "SELECT * FROM osoba WHERE nr_telefonu=";
    String findPersonByPesel = "SELECT * FROM osoba WHERE pesel=";
    String addDriver = "INSERT INTO kierowca (osoba_id, prawo_jazdy) VALUES(";
    String registerClient = "INSERT INTO osoba (imie, nazwisko, nr_telefonu, email) VALUES(";
    String registerDriver = "INSERT INTO osoba (imie, nazwisko, nr_telefonu, email, pesel) VALUES(";
    String checkIfClientExists = "SELECT * FROM osoba WHERE nr_telefonu=";
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

    public boolean registerNewClient(String name, String surname, String email, String phone) {
        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient")) {
            try (PreparedStatement ps = conn.prepareStatement(checkIfClientExists + phone + ";");
                 ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                    String resname = rs.getString("imie");
                    if (resname != null) {
                        //user already exists
                        showMessageDialog(null, "Użytkownik z podanym numerem telefonu już istnieje", "Błąd!", JOptionPane.ERROR_MESSAGE);

                        return false;
                    }
                }
            }


            String clientString;
            clientString = registerClient + "\"" + name + "\",\"" + surname + "\"," + phone + ",\"" + email + "\");";
            System.out.println(clientString);
            try (PreparedStatement ps = conn.prepareStatement(clientString);) {
                try (Statement stat = conn.createStatement();) {
                    stat.executeUpdate(clientString);
                } catch (SQLException e) {

                }

            } catch (SQLException e) {
                // handle the exception
                //dialog msg?
                return false;
            }
        }catch (SQLException e) {
            // handle the exception
            //dialog msg?
            System.out.println("Exception podczas sprawdzania, czy taki uzytkownik istnieje");
            return false;
        }

        if (findPersonByCredentials(phone, email).size() > 0){
            return true;
        }
        return false;
    }


    //To select cars that are not occupied by drivers use
    //SELECT * FROM auto LEFT JOIN kierowca ON (auto.id=kierowca.auto_id) WHERE kierowca.auto_id IS NULL;
    public int registerNewDriver(String name, String surname, String email, String phone, String pesel, String driversID){
        //PreparedStatement ps = conn.prepareStatement(findPersonByCredentials);
        int dId = -1;
        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient")) {
            try (PreparedStatement ps = conn.prepareStatement(checkIfClientExists + phone + ";");
                 ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    String resname = rs.getString("imie");
                    if (resname != null) {
                        //user already exists
                        showMessageDialog(null, "Użytkownik z podanym numerem telefonu już istnieje", "Błąd!", JOptionPane.ERROR_MESSAGE);

                        return -1;
                    }
                }
            }


            try (PreparedStatement ps = conn.prepareStatement(findPersonByPesel+pesel+";");
             //PreparedStatement ps = conn.prepareStatement(findPersonByCredentials);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String resname = rs.getString("imie");
                if(resname != null){
                    //user already exists
                    showMessageDialog(null, "Użytkownik z podanym numerem telefonu już istnieje", "Błąd!", JOptionPane.ERROR_MESSAGE);

                    return -1;
                }
            }

            } catch (SQLException e) {
                // handle the exception
                //dialog msg?
                System.out.println("Exception podczas sprawdzania, czy taki uzytkownik istnieje");
                return -1;
            }


            String clientString;
            clientString = registerDriver +"\""+name+"\",\""+surname+"\","+phone+",\""+email+"\"," + pesel +");";
            System.out.println(clientString);
            try( Statement stat = conn.createStatement();){
                stat.executeUpdate(clientString);
            }catch (SQLException e){

            }

            try(PreparedStatement ps = conn.prepareStatement("SELECT id FROM osoba WHERE pesel="+pesel+";");
                ResultSet rs = ps.executeQuery();){
                String id = "";
                while (rs.next()) {
                    id = rs.getString("id");
                    System.out.println(id);
                    dId = Integer.parseInt(id);
                }

                try( Statement stat = conn.createStatement();){
                    stat.executeUpdate("INSERT INTO kierowca(osoba_id, prawo_jazdy) VALUES("+id+",\""+driversID+"\");");
                }catch (SQLException e){
                    System.out.println("Error while updating the drivers");
                }
            }catch(SQLException e){

            }

        } catch (SQLException e) {
            // handle the exception
            //dialog msg?
            return -1;
        }



        if (findPersonByCredentials(phone, email).size() > 0){
            return dId;
        }
        return -1;
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

    public ArrayList<ArrayList<String>> getFreeCars() {
        ArrayList<ArrayList<String>> cars = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient");
             PreparedStatement ps = conn.prepareStatement("SELECT auto.id, marka, liczba_miejsc, klimatyzacja, rejestracja FROM auto LEFT JOIN kierowca ON (auto.id=kierowca.auto_id) WHERE kierowca.auto_id IS NULL;");
             //PreparedStatement ps = conn.prepareStatement(findPersonByCredentials);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ArrayList<String> carData = new ArrayList<>();
                carData.add(rs.getString("id"));
                carData.add(rs.getString("marka"));
                carData.add(rs.getString("liczba_miejsc"));
                carData.add(rs.getString("klimatyzacja"));
                carData.add(rs.getString("rejestracja"));
                cars.add(carData);

            }
            System.out.println("Cars: " +cars.size());
            return cars;
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<String> findDriver(String email, String phone, String drivID, String pesel){
        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient");
             PreparedStatement ps = conn.prepareStatement(findPersonByCredentials + phone + " AND email=\"" + email + "\" and pesel=" + pesel+";");
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
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean chooseCar(int registeredDriverID, int carID) {
        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient")) {

            String clientString;
            clientString = "UPDATE kierowca SET auto_id="+carID+" WHERE osoba_id = " + registeredDriverID +";";
            System.out.println(clientString);
            try (Statement stat = conn.createStatement();) {
                stat.executeUpdate(clientString);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }catch(SQLException e){
            return false;
        }
        return true;
    }
}
