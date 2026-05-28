package expensetracker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.io.*;
public class PersonalExpenseTracker {
// Expense Class
static class Expense {
    private double amount;
    private String category;
    private LocalDate date;
    public Expense(double amount, String category, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }
    public String getCategory() {
        return category;
    }
    public LocalDate getDate() {
        return date;
    }
    @Override
    public String toString() {
        return amount + " | " + category + " | " + date;
    }
}
// Expense Manager Class
static class ExpenseManager {
    private ArrayList<Expense> expenses = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    // (a) Add Expense
    public void addExpense() {
        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter category: ");
        String category = sc.nextLine();
        System.out.print("Enter date (yyyy-mm-dd): ");
        String dateInput = sc.nextLine();
        LocalDate date = LocalDate.parse(dateInput);
        Expense e = new Expense(amount, category, date);
        expenses.add(e);
        System.out.println("Expense added successfully!");
    }
    // (b) Display Expenses
    public void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        System.out.println("\n===== ALL EXPENSES =====");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }
    // (c) Monthly Report
    public void monthlyReport() {
        System.out.print("Enter month (1-12): ");
        int month = sc.nextInt();
        System.out.print("Enter year: ");
        int year = sc.nextInt();
        double total = 0;
        for (Expense e : expenses) {
            if (e.getDate().getMonthValue() == month &&
                e.getDate().getYear() == year) {

                total += e.getAmount();
            }
        }
        System.out.println("Total Expense for Month " +
                month + " in " + year + " = " + total);
    }
    // (d) Highest Expense Category
    public void highestCategory() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }
        HashMap<String, Double> categoryTotals = new HashMap<>();
        for (Expense e : expenses) {
            String category = e.getCategory();
            double amount = e.getAmount();
            if (categoryTotals.containsKey(category)) {
                categoryTotals.put(category,categoryTotals.get(category) + amount);
            } else {
                categoryTotals.put(category, amount);
            }
        }
        String highestCategory = "";
        double max = 0;
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                highestCategory = entry.getKey();
            }
        }
        System.out.println("Highest Expense Category: " + highestCategory);
        System.out.println("Total Amount: " + max);
    }
    // (e) Save to File
    public void saveToFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("expenses.txt"));
            for (Expense e : expenses) {
                pw.println(e.getAmount() + "," e.getCategory() + "," +  e.getDate());
            }
            pw.close();
            System.out.println("Expenses saved successfully!");
        } catch (IOException e) {
          System.out.println("Error saving file.");
        }
    }// (e) Load from File
    public void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("expenses.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                double amount=Double.parseDouble(data[0]);
                String category = data[1];
                LocalDate date = LocalDate.parse(data[2]);
                Expense e = new Expense(amount, category, date);
                expenses.add(e);
            }
            br.close();
           System.out.println("Expenses loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}
public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== PERSONAL EXPENSE TRACKER =====");
            System.out.println("1. Add Expense");
            System.out.println("2. Display Expenses");
            System.out.println("3. Monthly Report");
            System.out.println("4. Highest Expense Category");
            System.out.println("5. Save Expenses");
            System.out.println("6. Load Expenses");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    manager.addExpense();
                    break;
                case 2:
                    manager.displayExpenses();
                    break;
                case 3:
                    manager.monthlyReport();
                    break;
                case 4:
                    manager.highestCategory();
                    break;
                case 5:
                    manager.saveToFile();
                    break;
                case 6:
                    manager.loadFromFile();
                    break;
                case 7:
                    System.out.println("Exiting Program...");
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 7);
        sc.close();
    }
}
