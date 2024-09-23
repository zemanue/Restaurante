import java.util.ArrayList;
import java.util.Scanner;

public class Restaurant {

    private static ArrayList<Table> tableList = new ArrayList<>();
    private static ArrayList<Table> tablesFor2 = new ArrayList<>();
    private static ArrayList<Table> tablesFor4 = new ArrayList<>();
    private static ArrayList<Table> tablesFor6 = new ArrayList<>();
    private static ArrayList<Table> tablesFor8Plus = new ArrayList<>();

    private static int occupiedTables = 0;
    private static int totalCustomers = 0;
    private static int totalSatisfaction = 0;
        
    private static int openingHour = 13;
    private static int currentHour = openingHour;
    private static int closingHour = 21;
    private static int day = 1;
    
    public static void main(String[] args) {

        boolean keepSimulating = true;
        Scanner sc = new Scanner(System.in);
        while (keepSimulating) {
            System.out.println("Día " + day);
            initializeTables();
            System.out.println("Restaurante abierto");
    
            for (int i = openingHour; i < closingHour; i++) {
                System.out.println("Son las " + currentHour + ":00. ");
                customersArrive(3);
                advanceTime(1);
            }
    
            System.out.println("Son las " + closingHour + ":00. Hora de cerrar el restaurante.");
            freeAllTables();
            System.out.println("Restaurante cerrado. ¡Hasta mañana!");
            showStatistics();
            resetStatistics();
            System.out.println("¿Quieres avanzar al próximo día?");  
            String response = sc.nextLine();
            if (response.equalsIgnoreCase("s") || response.equalsIgnoreCase("si")) {
                System.out.println("Avanzando al próximo día");
                day++;
            } else {
                System.out.println("Saliendo de la simulación. ¡Vuelve cuando quieras!");
                keepSimulating = false;
            }
        }
    }

    public static void initializeTables() {
        Table table1 = new Table(1);
        Table table2 = new Table(2);
        Table table3 = new Table(3);
        Table table4 = new Table(4);
        Table table5 = new Table(5);
        Table table6 = new Table(6, 4);
        Table table7 = new Table(7, 4);
        Table table8 = new Table(8, 4);
        Table table9 = new Table(9);
        Table table10 = new Table(10, 6);
        Table table11 = new Table(11);
        Table table12 = new Table(12);
        Table table13 = new Table(13, 6);
        Table table14 = new Table(14, 4);
        Table table15 = new Table(15, 8);
        Table table16 = new Table(16, 12);

        tableList.add(table1);
        tableList.add(table2);
        tableList.add(table3);
        tableList.add(table4);
        tableList.add(table5);
        tableList.add(table6);
        tableList.add(table7);
        tableList.add(table8);
        tableList.add(table9);
        tableList.add(table10);
        tableList.add(table11);
        tableList.add(table12);
        tableList.add(table13);
        tableList.add(table14);
        tableList.add(table15);
        tableList.add(table16);

        //Tables with maxCapacity <= 2
        for (Table table : tableList) {
            if (table.getMaxCapacity() <= 2) {
                tablesFor2.add(table); //Add to tablesFor2 list
            }
        }

        //Tables with maxCapacity <= 4 
        for (Table table : tableList) {
            if (table.getMaxCapacity() > 2 && table.getMaxCapacity() <= 4) {
                tablesFor4.add(table); //Add to tablesFor4 list
                tablesFor2.add(table); //And add also to tablesFor2 list, after the smaller tables.
            }
        }

        //Tables with maxCapacity <= 6 
        for (Table table : tableList) {
            if (table.getMaxCapacity() > 4 && table.getMaxCapacity() <= 6) {
                tablesFor6.add(table); //Add to tablesFor6 list
                tablesFor4.add(table); //And add also to tablesFor4 list, after the smaller tables.
            }
        }
        
        //Tables with maxCapacity > 6 
        for (Table table : tableList) {
            if (table.getMaxCapacity() > 6) {
                tablesFor8Plus.add(table); //Add to tablesFor8Plus list
                tablesFor6.add(table); //And add also to tablesFor6 list, after the smaller tables.
            }
        }

        for (Table table : tableList) {
            if (table.getMaxCapacity() <= 2) {
                tablesFor2.add(table);
            } else if (table.getMaxCapacity() <= 4) {
                tablesFor4.add(table);
            } else if (table.getMaxCapacity() <= 6) {
                tablesFor6.add(table);
            } else {
                tablesFor8Plus.add(table);
            }
        }
    }

    public static void newCustomerGroup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Llegan nuevos clientes.");
        System.out.println("'¡Bienvenidos/as! ¿Cuántas personas son?'");
        int people = sc.nextInt();
        findSuitableTable(people);
    }

    public static void customersArrive(int numArrivalsPerHour) {
        for (int i = 0; i < numArrivalsPerHour; i++) {
            newCustomerGroup();
        }
    }

    public static void findSuitableTable(int people) {
        ArrayList<Table> suitableTables = null;
        if (people <= 2) {
            suitableTables = tablesFor2;
        } else if (people <= 4) {
            suitableTables = tablesFor4;
        } else if (people <= 6) {
            suitableTables = tablesFor6;
        } else {
            suitableTables = tablesFor8Plus;
        }

        boolean tableAssigned = false;
        for (Table table : suitableTables) {
            if (!table.isOccupied() && table.getMaxCapacity() >= people) {
                table.occupyTable(people);
                tableAssigned = true;
                occupiedTables++;
                totalCustomers += people;
                break;
            }
        }

        if (!tableAssigned) {
            System.out.println("'Lo siento, no quedan mesas disponibles para " + people + " personas. Vuelvan más tarde.'");
        }
    }

    public static void advanceTime(int hours) {
        for (Table table : tableList) {
            if (table.isOccupied()) {
                table.incrementTimeOccupied();
            }
        }
        currentHour += hours;
    }

    public static void freeAllTables() {
        for (Table table : tableList) {
            table.freeTable(true);
        }
        totalSatisfaction = Table.getSumOfTablesSatisfaction();
        System.out.println("Todas las mesas han sido liberadas.");
    }

    public static void showStatistics() {
        System.out.println("Estadísticas:");
        System.out.println("- Mesas ocupadas durante el día: " + occupiedTables);
        System.out.println("- Clientes atendidos: " + totalCustomers);
        double averageSatisfaction = (double) totalSatisfaction / totalCustomers;
        averageSatisfaction = Math.round(averageSatisfaction * 10.0) / 10.0;
        System.out.println("- Satisfacción media: " + averageSatisfaction + "/5");
    }

    public static void resetStatistics() {
        occupiedTables = 0;
        totalCustomers = 0;
        totalSatisfaction = 0;
        currentHour = openingHour;
        Table.setSumOfTablesSatisfaction(0);
    }
}
