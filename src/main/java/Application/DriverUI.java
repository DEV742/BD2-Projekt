package Application;

import javax.swing.*;

public class DriverUI {
    private JList list1;
    private JButton wylogujButton;
    private JPanel driverPanel;
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
}
