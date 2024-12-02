import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class CalorieTracker extends JFrame{
    private JTable foodsTable;
    private JPanel mainPanel;
    private JButton addNewFoodButton;
    private JButton backToMainMenuButton;
    private JButton clearTableButton;
    private JPanel buttonsPanel;
    private JScrollPane scrollPane;
    private JLabel highestCalLabel;
    private JLabel lowestCalLabel;
    private JLabel totalCalLabel;
    private JLabel totalProtLabel;
    private JButton undoButton;
    private LinkedList<Food> foodList;
    private Stack<Action> actions;
    public CalorieTracker(Profile profile) { // USES LINKED LIST, STACK, BUBBLE SORT
        Profile userProfile = profile;

        this.setContentPane(this.mainPanel);
        this.setTitle("Workout");
        this.setBounds(600, 200, 625, 500);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        actions = new Stack<>();        // STACK!!
        foodList = profile.foodList;    // LINKED LIST!!

        if (foodList == null) {
            System.out.println("No diet found, creating new diet...");
            foodList = new LinkedList<>();
            foodList.add(new Food("Apple", 95, 0.5));
            foodList.add(new Food("Chicken Breast", 165, 31));
            foodList.add(new Food("Rice", 200, 4));
            foodList.add(new Food("Broccoli", 55, 4));
            for (Food food : foodList) {
                System.out.println(food.name+" "+food.calories+" "+food.protein);
            }
            profile.foodList = foodList;
        } else {
            System.out.println("Loading diet...");
        }
        populateTable(userProfile);

        setUpClearTableButton(userProfile);
        setUpAddFoodButton(userProfile);
        setUpMainMenuButton(userProfile);
        setUpUndoButton(userProfile);
    }
    private void populateTable(Profile profile) {
        String[] columnNames = {"Name", "Calories", "Protein (g)"};
        DefaultTableModel foods = new DefaultTableModel(columnNames,0);

        for (Food food : foodList) {
            String[] newRow = {food.name, String.valueOf(food.calories), String.valueOf(food.protein)};
            foods.addRow(newRow);
            System.out.println("Added: "+food.name+" "+food.calories+" "+food.protein);
        }

        LinkedList<Food> sortedFoods = bubbleSortFood(foodList);
        if (!sortedFoods.isEmpty()) {
            Food lowestCalFood = sortedFoods.get(0);
            Food highestCalFood = sortedFoods.get(sortedFoods.size()-1);

            highestCalLabel.setText("Highest Cal. Item: " + highestCalFood.name + ", " + highestCalFood.calories + " cals");
            lowestCalLabel.setText("Lowest Cal. Item: " + lowestCalFood.name + ", " + lowestCalFood.calories +  " cals");
        }
        foodsTable.setModel(foods);

        int totalCals = 0;
        double totalProt = 0;

        for (int i = 0 ; i < foodList.size() ; i++) {
            totalCals = totalCals + foodList.get(i).calories;
            totalProt = totalProt + foodList.get(i).protein;
        }

        totalCalLabel.setText("Total Calories: "+totalCals+" g");
        totalProtLabel.setText("Total Protein: "+totalProt+" cals");
        profile.dailyCals = totalCals;
        profile.dailyProt = totalProt;
    }
    private void setUpUndoButton(Profile profile) {
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!actions.isEmpty()) {
                    Action lastAction = actions.pop();

                    switch (lastAction.type) {
                        case "ADD":
                            foodList.removeLast();
                            break;
                        case "CLEAR":
                            foodList = lastAction.previousList;
                            break;
                    }
                    populateTable(profile);
                } else {
                    JOptionPane.showMessageDialog(null, "No actions to undo!");
                }
            }
        });
    }
    private void setUpClearTableButton(Profile profile) {
        clearTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to clear the table?",
                        "Clear Table?",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    LinkedList<Food> prevList = new LinkedList<>(foodList); // saves cleared list to be undo-able.
                    foodList.clear();

                    Action clear = new Action("CLEAR", null, prevList);
                    actions.push(clear);   // pushes to stack
                    System.out.println("CLEAR action pushed to stack");

                    populateTable(profile);

                    highestCalLabel.setText("Highest Cal. Item: N/A");
                    lowestCalLabel.setText("Lowest Cal. Item: N/A");
                    totalCalLabel.setText("Total Calories: 0");
                    totalProtLabel.setText("Total Protein: 0 g");
                }
            }
        });
    }
    private void setUpAddFoodButton(Profile profile) {
        addNewFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter food name:");
                int calories = Integer.parseInt(JOptionPane.showInputDialog("Enter calories:"));
                double protein = Double.parseDouble(JOptionPane.showInputDialog("Enter protein (g):"));

                Food newFood = new Food(name, calories, protein);
                foodList.add(newFood);

                actions.push(new Action("ADD", newFood, null));
                System.out.println("ADD action pushed to stack");
                populateTable(profile);
            }
        });
    }
    private void setUpMainMenuButton(Profile profile) {
        backToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToOverview(profile);
            }
        });
    }
    public void goToOverview(Profile profile) {
        this.setVisible(false);
        this.dispose();
        new Overview(profile);
    }
    private LinkedList<Food> bubbleSortFood(LinkedList<Food> foodList) { // BUBBLE SORT!!!
        int n = foodList.size();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (foodList.get(j - 1).calories > foodList.get(j).calories) {
                    // Swap the Food objects
                    Food temp = foodList.get(j - 1);
                    foodList.set(j - 1, foodList.get(j));
                    foodList.set(j, temp);
                }
            }
        }
        return foodList;
    }
}
class Food {
    String name;
    int calories;
    double protein;
    public Food(String name, int calories, double protein) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
    }
}
class Action {
    String type;
    Food food;
    LinkedList<Food> previousList;
    public Action(String type, Food food, LinkedList<Food> previousFoodList) {
        this.type = type;
        this.food = food;
        this.previousList = previousFoodList;
    }
}