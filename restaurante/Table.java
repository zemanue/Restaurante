
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
    

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.occupied = false;
        this.peopleSeated = 0;
    }
    
    public Table(int tableNumber, int peopleSeated) {
        this.tableNumber = tableNumber;
        this.occupied = peopleSeated > 0;
        this.peopleSeated = peopleSeated;
    }

    public void occupyTable(int peopleSeated) {
        if (peopleSeated <= 0) {
            System.out.println("Mesa no adjudicada. El número de personas debe ser mayor a 0");
        } else {
            this.occupied = peopleSeated > 0;
            this.peopleSeated = peopleSeated;
            System.out.println("Mesa adjudicada");
        }
    }
}
