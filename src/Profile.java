import java.util.LinkedList;
import java.util.Queue;

class Profile {
    String name;
    String fitnessGoal;
    String fitnessType;
    String gender;
    String experience;
    int weight;
    int height;
    Queue<String> workout;
    LinkedList<Food> foodList;
    int dailyCals;
    int calLimit;
    double dailyProt;
    int age;
    int days;
    Profile (String name, String fitnessGoal, String fitnessType,
             String gender, String experience, int weight, int height,
             Queue<String> workout, LinkedList<Food> foodList, int dailyCals,
             int calLimit, double dailyProt, int age, int days) {
        this.name = name;
        this.fitnessGoal = fitnessGoal;
        this.fitnessType = fitnessType;
        this.gender = gender;
        this.experience = experience;
        this.weight = weight;
        this.height = height;
        this.workout = workout;
        this.foodList = foodList;
        this.dailyCals = dailyCals;
        this.calLimit = calLimit;
        this.dailyProt = dailyProt;
        this.age = age;
        this.days = days;
    }
    public void displayStats() {
        System.out.println(name);
        System.out.println(age);
        System.out.println(fitnessGoal);
        System.out.println(fitnessType);
        System.out.println(gender);
        System.out.println(experience);
        System.out.println(weight);
        System.out.println(height);
        System.out.println(workout);
        System.out.println(days);
    }
}