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
//    public class GradientPanel extends JPanel {
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2d = (Graphics2D) g;
//
//            // Define gradient colors
//            int width = getWidth();
//            int height = getHeight();
//            Color startColor = new Color(55,58,68);     // Black
//            Color endColor = new Color(66,115,244);     // Blue
//
//            GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, height, endColor);
//            g2d.setPaint(gradient);
//            g2d.fillRect(0, 0, width, height);
//        }
//    }
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
