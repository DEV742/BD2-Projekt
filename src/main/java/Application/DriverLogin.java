package Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static javax.swing.JOptionPane.showMessageDialog;

public class DriverLogin {
    private JLabel logoText;
    private JTextField phoneText;
    private JTextField emailText;
    private JTextField peselText;
    private JTextField driversLicenceText;
    private JButton loginBtn;
    private JPanel driverLoginForm;
    private JFrame frame;

    public void setApp(Application app) {
        this.app = app;
    }

    public void init(){
        frame = new JFrame("żUber");
        frame.setContentPane(driverLoginForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private Application app;

    public DriverLogin() {
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone = phoneText.getText();
                String email = emailText.getText();
                String driverId = driversLicenceText.getText();
                String pesel = peselText.getText();
                boolean result = app.login(email, phone, driverId, pesel);
                if(result){
                    Driver driver = new Driver();
                    Random rand = new Random();
                    driver.setUser(app.getUser());
                    driver.setX(rand.nextInt(app.getMap().mapSize));
                    driver.setY(rand.nextInt(app.getMap().mapSize));
                    showMessageDialog(null, "Pomyślnie zalogowano!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    DriverUI ui = new DriverUI();
                    ui.setApplication(app);
                    ui.init();
                    frame.dispose();
                }else{
                    showMessageDialog(null, "Błąd przy logowaniu!", "Błąd", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }
}
