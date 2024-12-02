import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Workouts extends JFrame {
    private JPanel mainPanel;
    private JPanel planPanel;
    private JTable workoutTable;
    private JButton backToMainMenuButton;
    private JButton searchExerciseButton;
    private JTextField searchTextField;

    public Workouts(Profile profile) { // USES QUEUE, BINARY SEARCH TREE
        Profile userProfile = profile;

        this.setContentPane(this.mainPanel);
        this.setTitle("Workout");
        this.setBounds(600, 200, 625, 500);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Queue<String> workout = profile.workout;
        if (workout == null) {
            System.out.println("New user detected, generating workout...");
            workout = generateWorkout(userProfile);
            System.out.println("Generated Workout:" + workout);
            profile.workout = workout;
            System.out.println("Workout saved to profile!");
        } else {
            System.out.println("Loading workout...");
        }

        populateTable(workout);
        BinarySearchTree exerciseBST = initializeExerciseTree();

        setUpBackToMainMenuButton(userProfile);
        setUpSearchExerciseButton(exerciseBST, userProfile, workout);
    }
    private BinarySearchTree initializeExerciseTree() { // BINARY SEARCH TREE WOOOOOO
        BinarySearchTree exerciseTree = new BinarySearchTree();
        String[] exercises = {"Barbell Bench Press", "Squats", "Deadlifts", "Overhead Press", "Pull Ups", "Incline Bench Press",
                "Leg Press", "Lat Pull Down", "Rows", "Bicep Curls", "Tricep Pushdown", "Dumbbell Shoulder Press", "Treadmill",
                "Row Machine", "Stair Climber", "Jump Rope", "Push Ups", "Lunges", "Chest Fly", "Plank", "Russian Twists",
                "Leg Raises", "Burpees", "Kettlebell Swings", "Cable Rows", "Dips", "Hammer Curls",
                "Front Squats", "Romanian Deadlifts", "Seated Calf Raises", "Box Jumps", "Glute Bridges", "Leg Extensions",
                "Hamstring Curls", "Barbell Rows", "Lat Pullover", "Bulgarian Split Squats", "Pistol Squats", "Goblet Squats",
                "Sled Push", "Farmer Walk", "Medicine Ball Slams", "Skater Jumps", "Sprints", "Battle Ropes", "Cycling",
                "Jumping Jacks", "Handstand Push Ups", "Tire Flips"}; // a whole INDEX of exercises to choose from!
        for (String ex : exercises) {
            exerciseTree.insert(ex);
            System.out.println("Inserted " + ex);
        }
        return exerciseTree;
    }
    private Queue<String> generateWorkout(Profile profile) {
        String fitnessType = profile.fitnessType;
        String experience = profile.experience;
        int weight = profile.weight;

        // define routines based on fitness type
        HashMap<String, List<String>> routineMap = new HashMap<>();
        routineMap.put("Strength", Arrays.asList("Barbell Bench Press", "Squats", "Deadlifts", "Overhead Press", "Pull Ups"));
        routineMap.put("Body Building", Arrays.asList("Incline Bench Press", "Leg Press", "Lat Pull Down", "Rows", "Bicep Curls", "Tricep Pushdown", "Dumbell Shoulder Press"));
        routineMap.put("Endurance", Arrays.asList("Treadmill", "Row Machine", "Stair Climber", "Jump Rope"));

        // get exercises based on fitness type
        List<String> baseRoutine = routineMap.getOrDefault(fitnessType, new ArrayList<>());

        // customize sets on experience (change this to be able to be customized by the user.)
        Queue<String> workoutQueue = new LinkedList<>();
        if (fitnessType.equals("Endurance")) {
            for (String exercise : baseRoutine) {
                if (experience.equals("Beginner")) {
                    workoutQueue.add(exercise + " - Slow pace, 15 minutes");
                } else if (experience.equals("Intermediate")) {
                    workoutQueue.add(exercise + " - Moderate pace, 30 minutes");
                } else if (experience.equals("Expert")) {
                    workoutQueue.add(exercise + " - Intense pace, 30 minutes");
                }
            }
        } else {
            for (String exercise : baseRoutine) {
                if (experience.equals("Beginner")) {
                    workoutQueue.add(exercise + " - 3 sets of 8 reps (light weight)");
                } else if (experience.equals("Intermediate")) {
                    workoutQueue.add(exercise + " - 3 sets of 10 reps (moderate weight)");
                } else if (experience.equals("Expert") || fitnessType.equals("Strength")) {
                    workoutQueue.add(exercise + " - 3 sets of 8 reps (heavy weight)");
                }
            }
        }
        return workoutQueue;
    }
    private void populateTable(Queue<String> workout) {
        Queue<String> workoutCopy = new LinkedList<>(workout); // QUEUE!!

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Exercise", "Details"}, 0);

        while (!workoutCopy.isEmpty()) {
            String fullDetails = workoutCopy.poll();
            String[] splitDetails = fullDetails.split(" - ", 2);

            String exercise = splitDetails.length > 0 ? splitDetails[0] : "???";
            String details = splitDetails.length > 1 ? splitDetails[1] : "...";

            model.addRow(new Object[]{exercise, details});
        }

        workoutTable.setModel(model);
    }
    public void setUpBackToMainMenuButton(Profile profile) {
        backToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToMainMenu(profile);
            }
        });
    }
    public void goToMainMenu(Profile profile) {
        this.setVisible(false);
        this.dispose();
        new Overview(profile);
    }
    public void setUpSearchExerciseButton(BinarySearchTree exerciseTree, Profile profile, Queue<String> workout){
        searchExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // SEARCHING THE BINARY TREE WOOO IN ALPHABETICAL ORDER
                String target = searchTextField.getText().trim();
                if (target.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter an exercise to search for.");
                    return;
                }
                if (exerciseTree.search(target)) {
                    System.out.println(target + " found");
                    int response = JOptionPane.showConfirmDialog(null, target + " found, add to workout?", "Add exercise?", JOptionPane.YES_NO_OPTION);

                    if (response == JOptionPane.YES_OPTION) {
                        workout.add(target);
                        populateTable(workout);
                    }
                } else {
                    System.out.println(target + " not found");
                    JOptionPane.showMessageDialog(null, target + "not found, try another");
                }
            }
        });
    }
}
