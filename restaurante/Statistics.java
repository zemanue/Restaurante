public class Statistics {
    private int occupiedTables = 0;
    private int totalCustomers = 0;
    private int totalSatisfaction = 0;
    private int timesWithoutTable = 0;
    private int windowPreference = 0;
    private int timesWindowPreferenceGiven = 0;

    public void incrementOccupiedTables() {
        occupiedTables++;
    }

    public void addCustomer(int numCustomers) {
        totalCustomers += numCustomers;
    }

    public void addSatisfaction(int satisfaction) {
        totalSatisfaction += satisfaction;
    }

    public void incrementTimesWithoutTable() {
        timesWithoutTable++;
    }

    public void incrementWindowPreference() {
        windowPreference++;
    }

    public void incrementTimesWindowPreferenceGiven() {
        timesWindowPreferenceGiven++;
    }

    public void show() {
        System.out.println();
        System.out.println("Estadísticas:");
        System.out.println("- Mesas ocupadas durante el día: " + occupiedTables);
        System.out.println("- Clientes atendidos: " + totalCustomers);
        double averageSatisfaction = totalCustomers > 0 ? (double) totalSatisfaction / totalCustomers : 0;
        System.out.println("- Satisfacción media: " + Math.round(averageSatisfaction * 10.0) / 10.0 + "/5");
        System.out.println("- Veces que un grupo de clientes se quedó sin mesa: " + timesWithoutTable);
        System.out.println("- Preferencias de ventana dadas: " + timesWindowPreferenceGiven + "/" + windowPreference);
    }

    public void reset() {
        occupiedTables = 0;
        totalCustomers = 0;
        totalSatisfaction = 0;
        timesWithoutTable = 0;
        windowPreference = 0;
        timesWindowPreferenceGiven = 0;
        Table.setSumOfTablesSatisfaction(0);
    }
}
