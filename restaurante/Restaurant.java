import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Restaurant {

    private static ArrayList<Table> tableList = new ArrayList<>();
    private static ArrayList<Table> tableListCapacityOrder = new ArrayList<>();

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
            initializeTables(new int[]{2, 2, 2, 2, 2, 4, 4, 4, 2, 6, 2, 2, 6, 4, 8, 12});
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
            resetTableLists();
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
        sc.close();
    }

    public static void initializeTables(int[] capacities) {
        for (int i = 0; i < capacities.length; i++) {
            tableList.add(new Table(i + 1, capacities[i]));
        }

        tableListCapacityOrder = tableList;
        Collections.sort(tableListCapacityOrder, Comparator.comparingInt(Table::getMaxCapacity));
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
        
        for (Table table : tableListCapacityOrder) {
            //People can't seat in tables with a max capacity > people + 2
            if (!table.isOccupied() && table.getMaxCapacity() >= people && table.getMaxCapacity() <= people + 2) {
                table.occupyTable(people);
                occupiedTables++;
                totalCustomers += people;
                return;
            }
        }
        System.out.println("'Lo siento, no quedan mesas disponibles para " + people + " personas. Vuelvan más tarde.'");
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

    public static void resetTableLists() {
        tableList.clear();
        tableListCapacityOrder.clear();
    }
}
