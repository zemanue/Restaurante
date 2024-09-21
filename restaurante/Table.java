import java.util.Random;

public class Table {
    Random random = new Random();
    private int tableNumber;
    private boolean occupied;
    private int maxCapacity;
    private Customer[] peopleSeated;
    private int occupiedSeats;
    private int tableSatisfaction = 0;
    private int timeOccupied = 0;
    
    //GETTERS AND SETTERS
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

    public int getTableSatisfaction() {
        return tableSatisfaction;
    }
    public void setTableSatisfaction(int totalSatisfaction) {
        this.tableSatisfaction = totalSatisfaction;
    }
    
    public int getTimeOccupied() {
        return timeOccupied;
    }
    public void setTimeOccupied(int timeOccupied) {
        this.timeOccupied = timeOccupied;
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
            this.timeOccupied = 0;
            System.out.println("Mesa " + tableNumber + " ocupada por " + peopleSeated + " personas.");
            return true;
        }
    }

    public void incrementTimeOccupied() {
        if (occupied) {
            timeOccupied++;
            if (timeOccupied >= random.nextInt(1,5)) {
                freeTable(true);
            }            
        }
    }

    public void freeTable(boolean message) {
        if (message) {
            System.out.println("Mesa " + tableNumber + " liberada.");
        }
        for (int i = 0; i < occupiedSeats; i++) {
            int satisfaction = random.nextInt(1, 6);
            peopleSeated[i].setSatisfactionLevel(satisfaction);
            tableSatisfaction += satisfaction;
            if (message) {
                System.out.println("- Cliente " + (i + 1) + " dejó la mesa con una satisfacción de " + satisfaction + "/5");
            }
            peopleSeated[i] = null;

        }
        this.occupied = false;
        this.occupiedSeats = 0;
        this.timeOccupied = 0;
    }
}
