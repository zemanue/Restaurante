import java.io.IOException;
import java.util.*;

public class Restaurant {

    private static ArrayList<Table> tableList = new ArrayList<>();
    private static ArrayList<Table> tableListCapacityOrder = new ArrayList<>();
    private static Statistics statistics = new Statistics();

    private static int openingHour = 13;
    private static int currentHour = openingHour;
    private static int closingHour = 21;
    private static int day = 1;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println();
        System.out.println("Bienvenido a la simulación de un restaurante.");
        System.out.println("A continuación, el restaurante abrirá en su primer día, y desde las " + openingHour + ":00 hasta las " + closingHour + ":00 llegarán clientes.");
        System.out.println("El usuario debe ingresar el número de personas que habrá en cada grupo de clientes, y el sistema se encargará de asignarles automáticamente para optimizar el espacio y la satisfacción.");
        System.out.println("Los clientes pueden tener preferencias, como querer sentarse junto a la ventana, y esto también influirá en la satisfacción final.");
        System.out.println("Recuerda: el sistema no permite que grupos de clientes pequeños ocupen mesas demasiado grandes, porque entiende que esto sería desperdiciar el espacio.");
        System.out.println("Una vez termine el día, se mostrarán las estadísticas y se podrá avanzar a un nuevo día.");
        System.out.println("¿Todo listo? ¡Introduce cualquier texto para comenzar!");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean keepSimulating = true;
        while (keepSimulating) {
            System.out.println("");
            System.out.println("Día " + day);
            initializeTables(new int[] { 2, 2, 2, 2, 2, 4, 4, 4, 10, 6, 2, 2, 6, 4, 8, 12 },
                    new boolean[] { false, false, false, true, true, true, false, false, true, true, false, false,
                            false, true, true, true });
            System.out.println("Hay un total de " + tableList.size() + " mesas: ");
            for (Table table : tableList) {
                System.out.println(table.showDetails());
            }
            System.out.println("Restaurante abierto");

            for (int i = openingHour; i < closingHour; i++) {
                System.out.println("Son las " + currentHour + ":00. ");
                System.out.println("");
                customersArrive(3);
                advanceTime(1);
            }

            System.out.println("Son las " + closingHour + ":00. Hora de cerrar el restaurante.");
            freeAllTables();
            System.out.println("Restaurante cerrado. ¡Hasta mañana!");
            statistics.show();
            statistics.reset();
            resetTableLists();
            System.out.println("");
            System.out.println(
                    "¿Quieres avanzar al próximo día? Escribe 's' o 'si' para avanzar, o cualquier otra letra para salir.");
            sc.nextLine();
            String response = sc.nextLine();
            if (response.equalsIgnoreCase("s") || response.equalsIgnoreCase("si")) {
                System.out.println("Avanzando al próximo día");
                day++;
                currentHour = openingHour;
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
        System.out.print("'¡Bienvenidos/as! ¿Cuántas personas son?' Escribe el número a continuación: ");
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
                customerPrefersWindow++;
            }
        }
        // Try to assign a table next to the window (if half of the customers or more
        // prefer window)
        if (customerPrefersWindow >= Math.ceil(people / 2)) {
            statistics.incrementWindowPreference();
            System.out.println("Los clientes prefieren una mesa junto a la ventana. Buscando...");
            for (Table table : tableListCapacityOrder) {
                if (!table.isOccupied()
                        && table.getMaxCapacity() >= people
                        && table.getMaxCapacity() <= people + 2
                        && table.isNextToWindow()) {

                    System.out.println("Mesa junto a la ventana encontrada.");
                    statistics.incrementTimesWindowPreferenceGiven();
                    table.occupyTable(customerGroup);
                    statistics.incrementOccupiedTables();
                    statistics.addCustomer(people);
                    return;
                }
            }
            System.out.println("No hay mesas disponibles junto a la ventana. Se buscará una libre.");
            statistics.incrementTimesWithoutTable();
        }
        // If it doesn't work, a regular table will try to be assigned
        for (Table table : tableListCapacityOrder) {
            if (!table.isOccupied()
                    && table.getMaxCapacity() >= people
                    && table.getMaxCapacity() <= people + 2) {
                table.occupyTable(customerGroup);
                statistics.incrementOccupiedTables();
                statistics.addCustomer(people);
                return;
            }
        }
        System.out.println("'Lo siento, no quedan mesas disponibles para " + people + " personas. Vuelvan más tarde.'");
        System.out.println();
    }

    public static void advanceTime(int hours) {
        System.out.println("Avanzamos " + hours + " hora/s. Estado de las mesas ocupadas:");
        for (Table table : tableList) {
            if (table.isOccupied()) {
                table.incrementTimeOccupied();
            }
        }
        currentHour += hours;
        System.out.println("");
    }

    public static void freeAllTables() {
        System.out.println("");
        System.out.println("Liberando todas las mesas...");
        for (Table table : tableList) {
            table.freeTable(true);
        }
        statistics.addSatisfaction(Table.getSumOfTablesSatisfaction());
        System.out.println("Todas las mesas han sido liberadas.");
        System.out.println("");
    }

    public static void resetTableLists() {
        tableList.clear();
        tableListCapacityOrder.clear();
    }
}
