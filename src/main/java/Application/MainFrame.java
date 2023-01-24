package Application;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

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

    public MainFrame()
    {
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                frame.dispose();
                System.exit(0); //calling the method is a must
            }
        });
    }
    public void getStreetsList(){
        DatabaseManager db = new DatabaseManager();
        streets = db.getStreets();

        System.out.println(streets.size());
        if (streets != null){
            for (String street : streets) {
                routeSourceSelect.addItem(street);
                routeDestinationSelect.addItem(street);
            }
        }
    }
    public void init(){
        frame = new JFrame("żUber");
        frame.setContentPane(mainPanel);
        String filePath ="beer.png";
        ImageIcon icon = new ImageIcon(filePath);
        iconPlace.setIcon(icon);
        iconPlace.setBounds(0, 0, 70, 70);
        getStreetsList();
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
