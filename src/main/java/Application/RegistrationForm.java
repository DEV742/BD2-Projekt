package Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class RegistrationForm {
    private JTextField nameText;
    private JTextField surnameText;
    private JRadioButton klientRadioButton;
    private JRadioButton kierowcaRadioButton;
    private JTextField phoneNumberText;
    private JTextField emailText;
    private JTextField peselText;
    private JTextField driverIDText;
    private JButton registerButton;
    private JButton exitButton;
    private JPanel registerPanel;
    private JLabel peselLabel;
    private JLabel driverIDLabel;
    private ButtonGroup buttonGroup1;

    public void setApp(Application app) {
        this.app = app;
    }

    private Application app;

    JFrame frame;

    public RegistrationForm() {
        kierowcaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                peselText.setEnabled(true);
                driverIDText.setEnabled(true);
                peselLabel.setEnabled(true);
                driverIDLabel.setEnabled(true);
            }
        });
        klientRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                peselText.setEnabled(false);
                driverIDText.setEnabled(false);
                peselLabel.setEnabled(false);
                driverIDLabel.setEnabled(false);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String surname = surnameText.getText();
                String email = emailText.getText();
                String phone = phoneNumberText.getText();
                String choice = getSelectedButton();
                User_Type role;
                if (choice.equals("Klient")){
                    role = User_Type.Client;
                }else{
                    role = User_Type.Driver;
                }
                String pesel = peselText.getText();
                String driverID = driverIDText.getText();
                app.register(name, surname, email, phone, role, pesel, driverID);
            }
        });
    }

    String getSelectedButton()
    {
        for (Enumeration<AbstractButton> buttons = buttonGroup1.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
    public void init() {
        frame = new JFrame("żUber");
        frame.setContentPane(registerPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
