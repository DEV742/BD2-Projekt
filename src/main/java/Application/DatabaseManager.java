package Application;


import com.mysql.cj.x.protobuf.MysqlxPrepare;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javax.swing.JOptionPane.showMessageDialog;


public class DatabaseManager {
    String findPersonByCredentials = "SELECT * FROM osoba WHERE nr_telefonu=";
    String findPersonByPesel = "SELECT * FROM osoba WHERE pesel=";
    String addDriver = "INSERT INTO kierowca (osoba_id, prawo_jazdy) VALUES("; // TODO ewentualnie wyjebać
    String registerClient = "INSERT INTO osoba (imie, nazwisko, nr_telefonu, email) VALUES(";
    String registerDriver = "INSERT INTO osoba (imie, nazwisko, nr_telefonu, email, pesel) VALUES(";
    String checkIfClientExists = "SELECT * FROM osoba WHERE nr_telefonu=";
    String streetsList = "SELECT nazwa FROM ulice;";
    String orderList = "Select droga_przejazdu.adres_startowy, droga_przejazdu.adres_koncowy, oplata.kwota, oplata.sposob_oplaty FROM zamowienie INNER JOIN droga_przejazdu ON droga_przejazdu.id = zamowienie.droga_przejazdu_id INNER JOIN oplata ON oplata.id = oplata_id WHERE kierowca_id = (SELECT id FROM kierowca WHERE prawo_jazdy = ";
    String makeOrder = "INSERT INTO `zamowienie` (`oplata_id`, `droga_przejazdu_id`, `klient_id`, `auto_id`, `kierowca_id`, `data`) VALUES(";
    String connectionUrl = "jdbc:mysql://localhost:3306/apkabd2";
    String makeRoute = "INSERT INTO droga_przejazdu (adres_startowy, adres_koncowy) values('";
    String makePayment = "INSERT INTO oplata (kwota, sposob_oplaty) VALUES(\"";

    public ArrayList<String> getStreets() {
        ArrayList<String> streets = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient");
             PreparedStatement ps = conn.prepareStatement(streetsList);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("nazwa");
                streets.add(name);
            }

            return streets;
        } catch (SQLException e) {
            showMessageDialog(null, "błąd bazy danych", "Błąd!", JOptionPane.ERROR_MESSAGE);
            // handle the exception
            return null;
        }
    }

    public String getLatestRouteID(){
        try (Connection conn = DriverManager.getConnection(connectionUrl, "Admin", "Admin");
            PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM droga_przejazdu");
            ResultSet rs = ps.executeQuery()) {
            String temp ="";
            while (rs.next()) {
                temp = rs.getString("MAX(id)");
            }
            return temp;
        } catch(SQLException e){
            return null;
        }
    }

    public String makeRoute(String start, String koniec) {
        System.out.println(makeRoute + start + "', '"  + koniec + " ');" + "\n");

        try (Connection conn = DriverManager.getConnection(connectionUrl, "Admin", "Admin");
             PreparedStatement ps = conn.prepareStatement(makeRoute + start + "', '"  + koniec + "');");) {
            try (Statement stat = conn.createStatement();) {
                stat.executeUpdate(makeRoute + start + "', '"  + koniec + "');");
            } catch (SQLException e) {

            }
            return getLatestRouteID();

        } catch (SQLException e) {
            // handle the exception
            //dialog msg?
            return getLatestRouteID();
        }
    }


    public String getLatestPaymentID(){
        try (Connection conn = DriverManager.getConnection(connectionUrl, "Admin", "Admin");
             PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM oplata");
             ResultSet rs = ps.executeQuery()) {
            String temp ="";
            while (rs.next()) {
                temp = rs.getString("MAX(id)");
            }
            return temp;
        } catch(SQLException e){
            return null;
        }
    }

    public String makePayment(String kwota, String sposob_oplaty) {
        System.out.println(makePayment + kwota + "\", \"" + sposob_oplaty +  "\" );" + "\n");

        try (Connection conn = DriverManager.getConnection(connectionUrl, "Admin", "Admin");
             PreparedStatement ps = conn.prepareStatement(makePayment + kwota + "', '" + sposob_oplaty +  "' );")) {
            try (Statement stat = conn.createStatement();) {
                stat.executeUpdate(makePayment + kwota + "\", \"" + sposob_oplaty +  "\" );");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return getLatestPaymentID();

        } catch (SQLException e) {
            // handle the exception
            //dialog msg?
            return getLatestPaymentID();
        }
    }

    public int makeOrder(String start, String koniec, String klient_id, String auto_id, String kierowca_id, String data, String kwota, String sposob_oplaty){
        int tempPaymentId = Integer.parseInt(makePayment(kwota,sposob_oplaty));

        int tempRouteId = Integer.parseInt(makeRoute(start,koniec));
        String tempValues = tempPaymentId + ", " + tempRouteId + ", " + klient_id + ", " + auto_id + ", " + kierowca_id + ", " + data + ");";
        System.out.println(makeOrder + tempValues + "\n");
        try(Connection conn = DriverManager.getConnection(connectionUrl, "Admin", "Admin");
            PreparedStatement ps = conn.prepareStatement(makeOrder + tempValues)){

            try (Statement stat = conn.createStatement()) {
                stat.executeUpdate(makeOrder + tempValues);

            } catch (SQLException e) {
                showMessageDialog(null, "błąd bazy danych6", "Błąd!", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
                return -1;
            }


            PreparedStatement ps1 = conn.prepareStatement("SELECT MAX(id) FROM zamowienie");
            ResultSet rs = ps1.executeQuery();
            int temp = -1;
            while (rs.next()) {
                temp = rs.getInt("MAX(id)");
            }
            return temp;

        } catch (SQLException e) {
            showMessageDialog(null, "błąd bazy danych", "Błąd!", JOptionPane.ERROR_MESSAGE);
            // handle the exception
            return -1;
        }
    }



    public ArrayList<String[]> getOrders(String prawo_jazdy){
        try(Connection conn = DriverManager.getConnection(connectionUrl, "Admin", "Admin");
            PreparedStatement ps = conn.prepareStatement(orderList + prawo_jazdy + ");");
            ResultSet rs = ps.executeQuery()){

            ArrayList<String[]> info = new ArrayList<>();

            while(rs.next()) {
                String[] tempInfo = new String[4];
                tempInfo[0] = rs.getString("adres_startowy");
                tempInfo[1] = rs.getString("adres_koncowy");
                tempInfo[2] = rs.getString("kwota");
                tempInfo[3] = rs.getString("sposob_oplaty");
                info.add(tempInfo);
            }

            return info;
        } catch (SQLException e){
            showMessageDialog(null, "błąd bazy danych", "Błąd!", JOptionPane.ERROR_MESSAGE);
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

    public ArrayList<String> loginDriver(String email, String phone, String drivID, String pesel){
        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient");
             PreparedStatement ps = conn.prepareStatement(findPersonByCredentials + phone + " AND email=\"" + email + "\" and pesel=" + pesel+";");
             //PreparedStatement ps = conn.prepareStatement(findPersonByCredentials);
             ResultSet rs = ps.executeQuery()) {
            int id = 0;
            ArrayList<String> list = new ArrayList<>();
            while (rs.next()) {
                id = rs.getInt("id");
                String name = rs.getString("imie");
                String lastName = rs.getString("nazwisko");
                String emailReturn = rs.getString("email");
                String phoneReturn = rs.getString("nr_telefonu");

                list.add(String.valueOf(id));
                list.add(name);
                list.add(lastName);
                list.add(emailReturn);
                list.add(phoneReturn);
            }

            String clientString;
            clientString = "UPDATE kierowca SET aktywny=1 WHERE osoba_id = " + id +";";
            System.out.println(clientString);
            try (Statement stat = conn.createStatement();) {
                stat.executeUpdate(clientString);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }

            return list;
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

    public ArrayList<Driver> getAvailableDrivers() {
        ArrayList<Driver> drivers = new ArrayList<Driver>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient");
             PreparedStatement ps = conn.prepareStatement("SELECT kierowca.id, osoba.imie FROM kierowca LEFT JOIN osoba ON (kierowca.osoba_id=osoba.id) WHERE kierowca.aktywny=1;");
             //PreparedStatement ps = conn.prepareStatement(findPersonByCredentials);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Driver driv = new Driver();
                User us = new User(rs.getString("osoba.imie"), "", "", "");
                driv.setID(rs.getInt("kierowca.id"));

                //Random rand = new Random();
                driv.setUser(us);
                drivers.add(driv);
            }
            System.out.println("Drivers: " +drivers.size());
            return drivers;
        } catch (SQLException e) {
            // handle the exception
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean addDriverToOrder(int orderID, int driverID) {
        try (Connection conn = DriverManager.getConnection(connectionUrl, "Klient", "Klient")) {

            String clientString;
            clientString = "UPDATE zamowienie SET kierowca_id="+driverID+" WHERE id = " + orderID +";";
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
