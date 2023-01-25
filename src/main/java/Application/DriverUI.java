package Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class DriverUI {
    private JButton wylogujButton;
    private JPanel driverPanel;
    private JButton updateListButton;
    private JTextArea textArea1;
    private JFrame frame;

    public void setApplication(Application application) {
        this.application = application;
    }

    private Application application;

    public void init(){
        frame = new JFrame("żUber");
        frame.setContentPane(driverPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public DriverUI() {
        wylogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        updateListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseManager db = new DatabaseManager();


                ArrayList<String[]> info = db.getOrders("3121213");
                if (info != null) {
                    for (String[] order : info) {
                        textArea1.append(order[0] + " " + order[1] + " " + order[2] + " " + order[3] + "\n"); // TODO make better UI
                    }
                }
            }
        });
    }
}
