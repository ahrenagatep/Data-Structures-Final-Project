import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePlan extends JFrame{
    private JLabel titleLabel;
    private JComboBox goalComboBox;
    private JComboBox fitnessComboBox;
    private JComboBox genderComboBox;
    private JComboBox experienceComboBox;
    private JTextField weightTextField;
    private JTextField feetTextField;
    private JTextField daysTextField;
    private JPanel createPlanPanel;
    private JTextField inchesTextField;
    private JButton submitInformationButton;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField ageTextField;
    private JSpinner daysSpinner;
    private JSlider daysSlider;

    CreatePlan(){
        this.setContentPane(this.createPlanPanel);
        this.setTitle("Create your fitness plan!");
        this.setBounds(600, 200, 625, 500);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submitInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = firstNameTextField.getText() + " " + lastNameTextField.getText();
                    String fitnessGoal = goalComboBox.getSelectedItem().toString();
                    String fitnessType = fitnessComboBox.getSelectedItem().toString();
                    String gender = genderComboBox.getSelectedItem().toString();
                    String experience = experienceComboBox.getSelectedItem().toString();
                    int weight = Integer.parseInt(weightTextField.getText());
                    int height = getHeight(Integer.parseInt(feetTextField.getText()), Integer.parseInt(inchesTextField.getText()));
                    String ageInput = ageTextField.getText();
                    if (!checkIfInteger(ageInput)) {
                        JOptionPane.showMessageDialog(null, "Invalid age, please enter a valid age number.");
                        return;
                    }
                    int age = Integer.parseInt(ageInput);
                    int days = (Integer)daysSpinner.getValue();

                    // checks for any "(select one)" option was picked
                    if (fitnessGoal.equals("(select one)") || fitnessType.equals("(select one)") ||
                            gender.equals("(select one)") || experience.equals("(select one)")) {
                        JOptionPane.showMessageDialog(null, "Error, please enter all values and try again.");
                    } else {
                        Profile profile = new Profile(name, fitnessGoal, fitnessType, gender,
                                experience, weight, height, null, null, 0,
                                0, 0, age, days);
                        profile.calLimit = calculateCalGoal(profile);
                        profile.displayStats();
                        goToOverview(profile);
                    }
                } catch (NumberFormatException j) {
                    System.out.println(j);
                    JOptionPane.showMessageDialog(null, "Error, please enter all values and try again.");
                    System.out.println("error");
                }
            }
        });
    }
    public int getHeight(int feet, int inches){
        int heightInInches;
        int ftToIn = feet * 12;
        heightInInches = ftToIn + inches;
        return heightInInches;
    }
    public void goToOverview(Profile profile){
        this.setVisible(false);
        this.dispose();
        new Overview(profile);
    }
    private boolean checkIfInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private int calculateCalGoal(Profile profile) {
        int calGoal;
        double bmr;

        if (profile.gender.equalsIgnoreCase("Male")) {
            bmr = 66 + (6.23 * profile.weight) + (12.7 * profile.height) - (6.8 * profile.age);
        } else {
            bmr = 655 + (4.35 * profile.weight) + (4.7 * profile.height) - (4.7 * profile.age);
        }

        double activityFactor;
        if (profile.days <= 1) {
            activityFactor = 1.2; // sedentary
        } else if (profile.days <= 3) {
            activityFactor = 1.375; // lightly active
        } else if (profile.days <= 5) {
            activityFactor = 1.55; // moderately active
        } else {
            activityFactor = 1.725; // very active
        }

        double calorieNeeds = bmr * activityFactor;

        switch (profile.fitnessGoal.toLowerCase()) {
            case "gain muscle":
                calorieNeeds = calorieNeeds * 1.1;
                break;
            case "lose weight":
                calorieNeeds = calorieNeeds * 0.9;
                break;
            case "body maintenace": // nothing.
            default:
                break;
        }

        calGoal = (int)Math.round(calorieNeeds);
        return calGoal;
    }
}
