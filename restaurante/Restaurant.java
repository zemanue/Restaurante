import java.util.*;

public class Restaurant {

    private static ArrayList<Table> tableList = new ArrayList<>();
    private static ArrayList<Table> tableListCapacityOrder = new ArrayList<>();

    private static int occupiedTables = 0;
    private static int totalCustomers = 0;
    private static int totalSatisfaction = 0;
    private static int timesWithoutTable = 0;
    private static int windowPreference = 0;
    private static int timesWindowPreferenceGiven = 0;

    private static int openingHour = 13;
    private static int currentHour = openingHour;
    private static int closingHour = 21;
    private static int day = 1;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean keepSimulating = true;
        while (keepSimulating) {
            System.out.println("Día " + day);
            initializeTables(new int[] { 2, 2, 2, 2, 2, 4, 4, 4, 10, 6, 2, 2, 6, 4, 8, 12 },
                    new boolean[] { false, false, false, true, true, true, false, false, true, true, false, false,
                            false, true, true, true });
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

    public static void initializeTables(int[] capacities, boolean[] nextToWindow) {
        for (int i = 0; i < capacities.length; i++) {
            tableList.add(new Table(i + 1, capacities[i], nextToWindow[i]));
        }

        tableListCapacityOrder = new ArrayList<>(tableList);
        Collections.sort(tableListCapacityOrder);
    }

    public static void customersArrive(int numArrivalsPerHour) {
        for (int i = 0; i < numArrivalsPerHour; i++) {
            newCustomerGroup();
        }
    }

    public static void newCustomerGroup() {
        System.out.println("Llegan nuevos clientes.");
        System.out.println("'¡Bienvenidos/as! ¿Cuántas personas son?'");
        int people = 0;
        while (true) {
            try {
                people = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número entero.");
                System.out.println("¿Cuántas personas son?");
                sc.next();
            }
        }
        findSuitableTable(people);
    }

    public static void findSuitableTable(int people) {
        ArrayList<Customer> customerGroup = new ArrayList<>();
        // Create the customers and check if they prefer the window
        int customerPrefersWindow = 0;
        for (int i = 0; i < people; i++) {
            Customer customer = new Customer();
            customerGroup.add(customer);
            if (customer.getPrefersWindow()) {
                customerPrefersWindow ++;
            }
        }
        // Try to assign a table next to the window (if half of the customers or more prefer window)
        if (customerPrefersWindow >= Math.ceil(people / 2)) {
            windowPreference++;
            System.out.println("Los clientes prefieren una mesa junto a la ventana. Buscando...");
            for (Table table : tableListCapacityOrder) {
                if (!table.isOccupied()
                        && table.getMaxCapacity() >= people
                        && table.getMaxCapacity() <= people + 2
                        && table.isNextToWindow()) {
                    
                    System.out.println("Mesa junto a la ventana encontrada.");
                    timesWindowPreferenceGiven++;
                    table.occupyTable(customerGroup);
                    occupiedTables++;
                    totalCustomers += people;
                    return;
                }
            }
            System.out.println("No hay mesas disponibles junto a la ventana. Se buscará una libre.");
            timesWithoutTable++;
        }
        // If it doesn't work, a regular table will try to be assigned
        for (Table table : tableListCapacityOrder) {
            if (!table.isOccupied()
                    && table.getMaxCapacity() >= people
                    && table.getMaxCapacity() <= people + 2) {
                table.occupyTable(customerGroup);
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
        double averageSatisfaction = totalCustomers > 0 ? (double) totalSatisfaction / totalCustomers : 0;
        averageSatisfaction = Math.round(averageSatisfaction * 10.0) / 10.0;
        System.out.println("- Satisfacción media: " + averageSatisfaction + "/5");
        System.out.println("- Veces que un grupo de clientes se quedó sin mesa: " + timesWithoutTable);
        System.out.println("- Preferencias de ventana dadas: " + timesWindowPreferenceGiven + "/" + windowPreference);
    }

    public static void resetStatistics() {
        occupiedTables = 0;
        totalCustomers = 0;
        totalSatisfaction = 0;
        timesWithoutTable = 0;
        windowPreference = 0;
        timesWindowPreferenceGiven = 0;
        currentHour = openingHour;
        Table.setSumOfTablesSatisfaction(0);
    }

    public static void resetTableLists() {
        tableList.clear();
        tableListCapacityOrder.clear();
    }
}
