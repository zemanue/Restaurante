
public class Table {
    private int tableNumber;
    protected boolean occupied;
    private int maxCapacity;
    protected int peopleSeated;
    
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
    public int getPeopleSeated() {
        return peopleSeated;
    }
    public void setPeopleSeated(int peopleSeated) {
        this.peopleSeated = peopleSeated;
    }
    

    //Constructor con maxCapacity por defecto = 2;
    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.occupied = false;
        this.maxCapacity = 2;
        this.peopleSeated = 0;
    }
    
    public Table(int tableNumber, int maxCapacity) {
        this.tableNumber = tableNumber;
        this.maxCapacity = maxCapacity;
        this.occupied = false;
        this.peopleSeated = 0;
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
            this.peopleSeated = peopleSeated;
            System.out.println("Mesa " + tableNumber + " ocupada por " + peopleSeated + " personas.");
            return true;
        }
    }
}
