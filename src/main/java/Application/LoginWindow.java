package Application;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;


public class LoginWindow {
    private JPanel loginScreen;
    private JButton zalogujButton;
    private JTextField emailText;
    private JTextField phoneText;
    private JLabel logo;

    private JLabel iconPlace;
    private JButton regButton;
    private JButton driverLoginButton;

    private JFrame frame;

    public void setApp(Application app) {
        this.app = app;
    }

    private Application app;

    public LoginWindow() {
        zalogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //login
                attemptLogin();
            }
        });

        emailText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    attemptLogin();
                }
            }
        });
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm regForm = new RegistrationForm();
                regForm.setApp(app);
                regForm.init();
            }
        });
        driverLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DriverLogin lg = new DriverLogin();
                lg.setApp(app);
                lg.init();
                frame.dispose();
            }
        });
    }

    public void attemptLogin() {
        String phoneNum = phoneText.getText();
        String email = emailText.getText();
        User_Type type = User_Type.Client;

        boolean result = app.login(email, phoneNum, type);
        if (result){
            showMessageDialog(null, "Logowanie przebiegło pomyślnie", "Pomyślnie zalogowano", JOptionPane.INFORMATION_MESSAGE);
            MainFrame mainFrame = new MainFrame();
            mainFrame.setApp(app);
            mainFrame.init();
            frame.dispose();
        }else{
            showMessageDialog(null, "Wystąpił błąd podczas logowania", "Nieudane logowanie", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void init() {
        frame = new JFrame("żUber");
        frame.setContentPane(loginScreen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String filePath ="beer.png";
        ImageIcon icon = new ImageIcon(filePath);
        iconPlace.setIcon(icon);
        iconPlace.setBounds(0, 0, 70, 70);
        frame.pack();
        frame.setVisible(true);
    }
}
