import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.*;


public class Startup extends JFrame{
    private JButton createButton;
    private JPanel startPanel;
    private JButton loginButton;
    private JLabel titleLabel;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf()); // For dark mode
//             UIManager.setLookAndFeel(new FlatLightLaf()); // For light mode
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Startup startup = new Startup();
        startup.setContentPane(startup.startPanel);
        startup.setTitle("Welcome!");
        startup.setBounds(600, 200, 625, 500);
        startup.setResizable(true);
        startup.setVisible(true);
        startup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startup.setCreateButton();
        startup.setLoginButton();
    }
    public void setCreateButton() {
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToCreatePage();
            }
        });
    }
    public void setLoginButton() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToLoginPage();
            }
        });
    }
    public void goToCreatePage(){
        this.setVisible(false);
        this.dispose();
        new CreatePlan();
    }
    public void goToLoginPage(){
        this.setVisible(false);
        this.dispose();
        new Login();
    }
}
