import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

public class Overview extends JFrame { // USES ARRAYLIST
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel caloriesPanel;
    private JPanel wellnessPanel;
    private JProgressBar caloriesProgBar;
    private JPanel navigationPanel;
    private JButton seeWorkoutButton;
    private JButton calorieTrackerButton;
    private JLabel tipLabel;
    private JLabel welcomeLabel;
    private JLabel totalCalLabel;
    private JLabel totalProtLabel;
    private JButton saveAndExitButton;
    private ArrayList<String> tipsList = new ArrayList<>(); // ARRAYLIST!!!!
    public void loadTips() { // ayoo pauseee
        tipsList.add("Remember to drink plenty of water");
        tipsList.add("Sleep is essential, try to get 7-8 hours daily");
        tipsList.add("Always warm up and stretch before your workout");
        tipsList.add("Stretch regularly to improve flexibility");
        tipsList.add("Form >> Intensity. Lower the weight if needed");
        tipsList.add("Working out with a friend can keep you accountable");
        tipsList.add("Aim for consistency, not perfection");
        tipsList.add("Getting tired? Fuel up with carbs before working out");
        tipsList.add("Take a sip of water inbetween sets");
        tipsList.add("Do not skip leg day.");
        tipsList.add("DO NOT EGOLIFT!!!");
    }
    public Overview(Profile profile) {
        Profile userProfile = profile;

        this.setContentPane(this.mainPanel);
        this.setTitle("Welcome!");
        this.setBounds(600, 200, 625, 500);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUpSeeWorkoutPlansButton(userProfile);
        setUpCalorieTrackerButton(userProfile);
        setUpSaveAndExitButton(userProfile);

        updateNumbers(userProfile);
        loadTips();

        tipLabel.setText(getRandomTip());
        welcomeLabel.setText("Welcome, " + profile.name);
    }
    public String getRandomTip() {
        int index = (int) (Math.random() * tipsList.size());
        return tipsList.get(index);
    }
    public void updateNumbers(Profile profile) {
        totalCalLabel.setText(String.valueOf(profile.dailyCals)+" / "+String.valueOf(profile.calLimit)+" calories");
        totalProtLabel.setText(String.valueOf(profile.dailyProt)+" g protein");

        caloriesProgBar.setMaximum(profile.calLimit);
        if (profile.dailyCals >= profile.calLimit) {
            caloriesProgBar.setValue(profile.calLimit);
        } else {
            caloriesProgBar.setValue(profile.dailyCals);
        }
    }
    public void setUpSeeWorkoutPlansButton(Profile profile) {
        seeWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToWorkouts(profile);
            }
        });
    }
    public void goToWorkouts(Profile profile) {
        this.setVisible(false);
        this.dispose();
        new Workouts(profile);
    }
    public void setUpCalorieTrackerButton(Profile profile) {
        calorieTrackerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToTracker(profile);
            }
        });
    }
    public void goToTracker(Profile profile) {
        this.setVisible(false);
        this.dispose();
        new CalorieTracker(profile);
    }
    public void setUpSaveAndExitButton(Profile profile) {
        saveAndExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfileToCSV(profile, profile.name+".csv"); // save to a csv file
                JOptionPane.showMessageDialog(null, "Profile saved successfully!");
                System.exit(0);
            }
        });
    }
    public void saveProfileToCSV(Profile profile, String fileName) {
        try {
            String workingDirectory = System.getProperty("user.dir");
            String filePath = workingDirectory + "/" + fileName;

            FileWriter writer = new FileWriter(filePath);

            writer.append("Name,Fitness Goal,Days,Type,Gender,Experience,Weight,Height,Calorie Limit,Daily Calories,Daily Protein,Age\n");
            writer.append(profile.name).append(",");
            writer.append(profile.fitnessGoal).append(",");
            writer.append(String.valueOf(profile.days)).append(",");
            writer.append(profile.fitnessType).append(",");
            writer.append(profile.gender).append(",");
            writer.append(profile.experience).append(",");
            writer.append(String.valueOf(profile.weight)).append(",");
            writer.append(String.valueOf(profile.height)).append(",");
            writer.append(String.valueOf(profile.calLimit)).append(",");
            writer.append(String.valueOf(profile.dailyCals)).append(",");
            writer.append(String.valueOf(profile.dailyProt)).append(",");
            writer.append(String.valueOf(profile.age)).append(",");

            writer.flush();
            writer.close();

            JOptionPane.showMessageDialog(null, "Profile saved successfully!\nFile saved at:\n" + filePath);

        } catch (Exception ex) {
            // Handle any exceptions during the file save
            JOptionPane.showMessageDialog(null, "Error saving profile: " + ex.getMessage());
        }
    }
}