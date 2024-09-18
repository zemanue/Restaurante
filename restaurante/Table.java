// import java.util.ArrayList;
import java.util.Random;

public class Table {
    Random random = new Random();
    private int tableNumber;
    private boolean occupied;
    private int maxCapacity;
    private Customer[] peopleSeated;
    private int occupiedSeats;
    private static int totalSatisfaction = 0;
    
    public int getTableNumber() {
        return tableNumber;
    }
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Customer[] getPeopleSeated() {
        return peopleSeated;
    }
    public void setPeopleSeated(Customer[] peopleSeated) {
        this.peopleSeated = peopleSeated;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }
    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public static int getTotalSatisfaction() {
        return totalSatisfaction;
    }
    public static void setTotalSatisfaction(int totalSatisfaction) {
        Table.totalSatisfaction = totalSatisfaction;
    }    

    //Constructor with maxCapacity by default = 2;
    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.occupied = false;
        this.maxCapacity = 2;
        this.peopleSeated = new Customer[this.maxCapacity];
        this.occupiedSeats = 0;
    }
    
    public Table(int tableNumber, int maxCapacity) {
        this.tableNumber = tableNumber;
        this.maxCapacity = maxCapacity;
        this.occupied = false;
        this.peopleSeated = new Customer[maxCapacity];
        this.occupiedSeats = 0;
    }

    public boolean occupyTable(int peopleSeated) {
        if (peopleSeated <= 0) {
            System.out.println("Error: El número de personas debe ser mayor a 0");
            return false;
        } else if (peopleSeated > maxCapacity) {
            System.out.println("Mesa no adjudicada: La mesa " + tableNumber + " tiene capacidad máxima para " + maxCapacity + " personas.");
            return false;
        }
        else {
            this.occupied = true;
            this.occupiedSeats = peopleSeated;
            for (int i = 0; i < peopleSeated; i++) {
                this.peopleSeated[i] = new Customer();
            }
            System.out.println("Mesa " + tableNumber + " ocupada por " + peopleSeated + " personas.");
            return true;
        }
    }

    public void freeTable() {
        for (int i = 0; i < peopleSeated.length; i++) {
            this.peopleSeated[i].setSatisfactionLevel(random.nextInt(1, 6));
            totalSatisfaction += this.peopleSeated[i].getSatisfactionLevel();
            this.peopleSeated[i] = null;
        }
        this.occupied = false;
        this.occupiedSeats = 0;
        System.out.println("Mesa " + tableNumber + " liberada.");
    }

    public void freeTable(boolean message) {
        for (int i = 0; i < occupiedSeats; i++) {
            peopleSeated[i].setSatisfactionLevel(random.nextInt(1, 6));
            totalSatisfaction += peopleSeated[i].getSatisfactionLevel();
            peopleSeated[i] = null;
        }
        this.occupied = false;
        this.occupiedSeats = 0;
        if (message) {
            System.out.println("Mesa " + tableNumber + " liberada.");
        }
    }
}
