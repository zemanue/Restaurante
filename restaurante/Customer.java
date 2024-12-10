import java.util.Random;

public class Customer {
    Random random = new Random();
    private int satisfactionLevel;
    private int patienceLevel;
    private boolean prefersWindow;
    private double spending;
    private double tip;

    public Customer() {
        this.patienceLevel = random.nextInt(1, 11);
        this.prefersWindow = random.nextBoolean();
    }

    public Customer(int patienceLevel, boolean prefersWindow) {
        this.patienceLevel = patienceLevel;
        this.prefersWindow = prefersWindow;
    }

    public int getSatisfactionLevel() {
        return satisfactionLevel;
    }

    public void setSatisfactionLevel(int satisfactionLevel) {
        this.satisfactionLevel = satisfactionLevel;
    }

    public int getPatienceLevel() {
        return patienceLevel;
    }

    public void setPatienceLevel(int patienceLevel) {
        this.patienceLevel = patienceLevel;
    }

    public boolean getPrefersWindow() {
        return prefersWindow;
    }

    public void setPrefersWindow(boolean prefersWindow) {
        this.prefersWindow = prefersWindow;
    }

    public double calculateSpending() {
        // Random number between 10 and 20, rounded to 1 decimal place
        spending = Math.round((random.nextDouble() * 10 + 10) * 10.0) / 10.0;
        return spending;
    }

    public double calculateTip() {
        // Random number between 0 and 20% of the total spending, rounded to 2 decimal places
        tip = Math.round(random.nextDouble() * spending * 0.2 * 100.0) / 100.0;
        return tip;
    }
}
