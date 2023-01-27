package Application;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import static javax.swing.JOptionPane.showMessageDialog;

public class MainFrame {

    private JPanel mainPanel;
    private JComboBox routeSourceSelect;
    private JComboBox routeDestinationSelect;
    private JRadioButton cashRadioButton;
    private JRadioButton cardRadioButton;
    private JRadioButton blikRadioButton;
    private JButton orderBtn;
    private Application app;
    private JLabel logo;
    public double price;
    private JLabel iconPlace;
    private JLabel priceLabel;
    private JFrame frame;

    ArrayList<String> streets = new ArrayList<>();

    public void setApp(Application app) {
        this.app = app;
    }

    public MainFrame() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                System.exit(0); //calling the method is a must
            }
        });
        orderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = routeSourceSelect.getSelectedItem().toString();
                String destination = routeDestinationSelect.getSelectedItem().toString();
                String payMethod = "";
                if (blikRadioButton.isSelected()) payMethod = "blik";
                if (cardRadioButton.isSelected()) payMethod = "karta";
                if (cashRadioButton.isSelected()) payMethod = "gotowka";
                DatabaseManager db = new DatabaseManager();
                int cena = (int)price;
                int orderID = db.makeOrder(source, destination, "1", "1", "1", "'2022-12-02'", String.valueOf(cena), payMethod);
                Order order = new Order();
                order.setId(orderID);
                order.setClient(app.getUser());
                order.setDate("2022-12-02");
                ArrayList<Integer> coords = app.getMap().getCoordinates(source);
                order.setX(coords.get(0));
                order.setY(coords.get(1));
                Driver driver = app.getMap().findClosestDriver(order);
                order.setDriver(driver);
            }
        });
        routeDestinationSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePrice();
            }
        });
        routeSourceSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePrice();
            }
        });
    }

    public void recalculatePrice() {

        if(routeDestinationSelect.getItemCount() > 0 && routeSourceSelect.getItemCount() > 0) {
            String street1;
            String street2;

            street1 = routeSourceSelect.getSelectedItem().toString();
            street2 = routeDestinationSelect.getSelectedItem().toString();
            double cost = app.getMap().calculateCost(street1, street2);
            price = cost;
            int pText = (int)price;
            priceLabel.setText("Cena: " + pText);
        }

    }
//public void makeOrder(String start, String koniec, String klient_id, String auto_id, String kierowca_id, String data){
    public void getStreetsList() {
        DatabaseManager db = new DatabaseManager();
        streets = db.getStreets();

        System.out.println(streets.size());
        if (streets != null) {
            for (String street : streets) {
                routeSourceSelect.addItem(street);
                routeDestinationSelect.addItem(street);
            }
        }
    }

    public void init() {
        frame = new JFrame("żUber");
        frame.setContentPane(mainPanel);
        getStreetsList();
        String filePath = "beer.png";
        ImageIcon icon = new ImageIcon(filePath);
        iconPlace.setIcon(icon);
        iconPlace.setBounds(0, 0, 70, 70);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}