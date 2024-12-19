import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Login extends JFrame{

    private JPanel loginPanel;
    private JButton loginButton;
    private JTextField nameTextField;
    private JButton createProfileButton;

    Login(){
        this.setContentPane(this.loginPanel);
        this.setTitle("Log in!");
        this.setBounds(600, 200, 625, 500);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = nameTextField.getText().trim();
                loadProfile(username);
            }
        });
        createProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToRegister();
            }
        });
    }
    public void loadProfile (String username) {
        if (!username.isEmpty()) {
            String csvFile = System.getProperty("user.dir") + "\\" + username + ".csv";
            System.out.println(csvFile);
            Profile profile = importProfile(csvFile);

            if (profile != null) {
                goToOverview(profile);
            } else {
                JOptionPane.showMessageDialog(null, "Error loading profile: Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter your name.");
        }
    }
    public Profile importProfile(String csvFile) {
        ArrayList<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: could not find or open the file at " + csvFile);
            return null;
        }

        if (data.size() > 1) {
            try {
                String[] row = data.get(1);
                String name = row[0];
                String fitnessGoal = row[1];
                int days = Integer.parseInt(row[2]);
                String fitnessType = row[3];
                String gender = row[4];
                String experience = row[5];
                int weight = Integer.parseInt(row[6]);
                int height = Integer.parseInt(row[7]);
                int calLimit = Integer.parseInt(row[8]);
                int dailyCals = Integer.parseInt(row[9]);
                double dailyProt = Double.parseDouble(row[10]);
                int age = Integer.parseInt(row[11]);

                Profile loadedProfile = new Profile(name, fitnessGoal, fitnessType, gender, experience, weight, height,
                        null, null, dailyCals, calLimit, dailyProt, age, days);
                return loadedProfile;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error loading profile.");
                System.out.println(ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error, No profile data found.");
        }
        return null;
    }
    public void goToOverview(Profile profile) {
        this.setVisible(false);
        this.dispose();
        new Overview(profile);
    }
    public void goToRegister() {
        this.setVisible(false);
        this.dispose();
        new CreatePlan();
    }
}
