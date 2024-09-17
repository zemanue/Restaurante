import java.util.ArrayList;
import java.util.Scanner;

public class Restaurant {

    private static ArrayList<Table> tableList = new ArrayList<>();

    public static void initializeTables() {
        Table table1 = new Table(1, 8);
        Table table2 = new Table(2);
        Table table3 = new Table(3);
        Table table4 = new Table(4);
        Table table5 = new Table(5);
        Table table6 = new Table(6, 4);
        Table table7 = new Table(7, 4);
        Table table8 = new Table(8);
        Table table9 = new Table(9);
        Table table10 = new Table(10, 6);
        Table table11 = new Table(11);
        Table table12 = new Table(12);
        Table table13 = new Table(13);
        Table table14 = new Table(14);
        Table table15 = new Table(15, 4);
        Table table16 = new Table(16, 6);

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
    }

    public static void customersArrive() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Llegan nuevos clientes");
        System.out.println("'¡Bienvenidos/as! ¿Cuántas personas son?'");
        int people = sc.nextInt();
        
        //se comprueba desde la mesa 1 si está libre y si no supera el número máximo de ocupantes
        //true: la mesa queda asignada
        //false: se comprueba la siguiente mesa hasta que no quede ninguna, en cuyo caso se van para casita.
    }

    public static void main(String[] args) {
        //Buscar la manera de crear una lista con todas las mesas


        System.out.println("Restaurante abierto");
        
        customersArrive();

    }
}
