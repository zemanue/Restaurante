import java.util.Random;

public class Customer {
    Random random = new Random();
    private int satisfactionLevel;
    private int patienceLevel;
    private boolean prefersWindow;

    public Customer() {
        this.patienceLevel = random.nextInt(1, 11);
        this.prefersWindow = random.nextBoolean();
    }

    public Customer(int patienceLevel , boolean prefersWindow) {
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

}
