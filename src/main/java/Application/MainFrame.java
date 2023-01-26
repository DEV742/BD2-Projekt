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
    private JLabel iconPlace;
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
                if (blikRadioButton.isSelected()) payMethod = "'blik'";
                if (cardRadioButton.isSelected()) payMethod = "'karta'";
                if (cashRadioButton.isSelected()) payMethod = "'gotowka'";
                DatabaseManager db = new DatabaseManager();
                db.makeOrder(source, destination, "1", "1", "1", "'2022-12-02'", "20", payMethod);
            }
        });
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
        String filePath = "beer.png";
        ImageIcon icon = new ImageIcon(filePath);
        iconPlace.setIcon(icon);
        iconPlace.setBounds(0, 0, 70, 70);
        getStreetsList();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}