package Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import static javax.swing.JOptionPane.showMessageDialog;

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
    private JPanel carChoice;
    private JComboBox comboBox1;
    private JButton chooseCarBtn;
    private JPanel regPanel;
    private ButtonGroup buttonGroup1;

    private int userID;

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
                if(app.register(name, surname, email, phone, role, pesel, driverID)){
                    //enable car choice

                    ArrayList<ArrayList<String>> cars = app.db.getFreeCars();
                    if(cars != null){
                        String carPosition = "";
                        for(ArrayList<String> car : cars){
                            carPosition = "";
                            carPosition+= "["+car.get(0)+"] - ";
                            carPosition+=car.get(1) + " - ";
                            carPosition+=" liczba mjsc: " + car.get(2) + " - ";
                            if(car.get(3).equals("1")){
                                carPosition+=" klim. ";
                            }else{
                                carPosition+=" brak klim. ";
                            }
                            carPosition+=car.get(3) + " - ";
                            carPosition+=car.get(4);

                            comboBox1.addItem(carPosition);
                        }
                    }
                    regPanel.setVisible(false);
                    carChoice.setVisible(true);
                    frame.pack();
                    frame.revalidate();
                }
            }
        });
        chooseCarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int carID;
                String str = comboBox1.getSelectedItem().toString();
                int indexOfBracket = str.indexOf(']');
                carID = Integer.parseInt(str.substring(1, indexOfBracket));
                System.out.println("Car id: " + carID);
                boolean result = app.db.chooseCar(app.registeredDriverID, carID);

                if(result){
                    showMessageDialog(null, "Samochód został pomyślnie wybrany", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }else{
                    showMessageDialog(null, "Samochód nie został wybrany z powodu błędu", "Błąd", JOptionPane.ERROR_MESSAGE);

                }
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
