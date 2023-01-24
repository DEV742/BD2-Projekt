package Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;


public class LoginWindow {
    private JPanel loginScreen;
    private JButton zalogujButton;
    private JTextField emailText;
    private JTextField phoneText;
    private JLabel registerText;
    private JLabel logo;

    private JLabel iconPlace;

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
                String phoneNum = phoneText.getText();
                String email = emailText.getText();
                User_Type type = User_Type.Client;

                boolean result = app.login(email, phoneNum, type);
                if (result){
                    showMessageDialog(null, "Logowanie przebiegło pomyślnie", "Pomyślnie zalogowano", JOptionPane.INFORMATION_MESSAGE);
                    app.showMainFrame();
                }else{
                    showMessageDialog(null, "Wystąpił błąd podczas logowania", "Nieudane logowanie", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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
