
import java.sql.*;
import java.util.Scanner;

public class OperacionesBD {

    static final String URL = "jdbc:mysql://localhost:3306/autos";
    static final String USUARIO = "root";
    static final String CONTRASEÑA = "12345678";

    public static void main(String[] args) {
        Connection conexion = null;
        Statement statement = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Conectarse a la base de datos
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            statement = conexion.createStatement();

            boolean continuar = true;
            while (continuar) {
                // Mostrar menú principal
                System.out.println("\n*** Menú Principal ***");
                System.out.println("1. Insertar un nuevo auto");
                System.out.println("2. Modificar un auto existente");
                System.out.println("3. Eliminar un auto");
                System.out.println("4. Consultar autos");
                System.out.println("5. Salir");
                System.out.print("Selecciona una opción: ");

                int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        insertarAuto(statement, scanner);
                        break;
                    case 2:
                        modificarAuto(statement, scanner);
                        break;
                    case 3:
                        eliminarAuto(statement, scanner);
                        break;
                    case 4:
                        consultarAutos(statement);
                        break;
                    case 5:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, selecciona una opción del menú.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión, el statement y el scanner
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
                scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para insertar un nuevo auto
    static void insertarAuto(Statement statement, Scanner scanner) throws SQLException {
        System.out.println("\n*** Insertar un Nuevo Auto ***");
        System.out.print("Marca del auto: ");
        String marca = scanner.next();
        System.out.print("Modelo del auto: ");
        String modelo = scanner.next();
        System.out.print("Color del auto: ");
        String color = scanner.next();
        System.out.print("Cilindros del auto: ");
        int cilindros = scanner.nextInt();
        String sql = "INSERT INTO autos (marca, modelo, color, cilindros) VALUES ('" + marca + "', '" + modelo + "', '" + color + "', " + cilindros + ")";
        statement.executeUpdate(sql);
        System.out.println("Nuevo auto insertado correctamente.");
    }

    // Método para modificar un auto existente
    // Método para modificar un auto existente
    static void modificarAuto(Statement statement, Scanner scanner) throws SQLException {
        System.out.println("\n*** Modificar un Auto Existente ***");
        System.out.print("ID del auto a modificar: ");
        int id = scanner.nextInt();

        // Buscar el auto por ID y mostrar sus detalles
        String sql = "SELECT * FROM autos WHERE id = " + id;
        ResultSet resultado = statement.executeQuery(sql);
        if (resultado.next()) {
            String marcaActual = resultado.getString("marca");
            String modeloActual = resultado.getString("modelo");
            String colorActual = resultado.getString("color");
            int cilindrosActual = resultado.getInt("cilindros");
            System.out.println("Detalles del Auto:");
            System.out.println("Marca: " + marcaActual);
            System.out.println("Modelo: " + modeloActual);
            System.out.println("Color: " + colorActual);
            System.out.println("Cilindros: " + cilindrosActual);

            // Solicitar nuevos datos para la modificación
            System.out.print("\nNueva marca del auto (presiona Enter para mantener la actual): ");
            String nuevaMarca = scanner.nextLine(); // Limpiar el buffer
            nuevaMarca = scanner.nextLine(); // Leer la nueva marca
            if (nuevaMarca.isEmpty()) {
                nuevaMarca = marcaActual; // Mantener la marca actual si no se ingresa un nuevo valor
            }
            System.out.print("Nuevo modelo del auto (presiona Enter para mantener el actual): ");
            String nuevoModelo = scanner.nextLine();
            if (nuevoModelo.isEmpty()) {
                nuevoModelo = modeloActual;
            }
            System.out.print("Nuevo color del auto (presiona Enter para mantener el actual): ");
            String nuevoColor = scanner.nextLine();
            if (nuevoColor.isEmpty()) {
                nuevoColor = colorActual;
            }
            System.out.print("Nuevos cilindros del auto (presiona 0 para mantener los actuales): ");
            int nuevosCilindros = scanner.nextInt();
            if (nuevosCilindros == 0) {
                nuevosCilindros = cilindrosActual;
            }

            // Realizar la modificación en la base de datos
            sql = "UPDATE autos SET marca = '" + nuevaMarca + "', modelo = '" + nuevoModelo + "', color = '" + nuevoColor + "', cilindros = " + nuevosCilindros + " WHERE id = " + id;
            statement.executeUpdate(sql);
            System.out.println("Auto modificado correctamente.");
        } else {
            System.out.println("No se encontró ningún auto con el ID proporcionado.");
        }
    }

    // Método para eliminar un auto
    static void eliminarAuto(Statement statement, Scanner scanner) throws SQLException {
        System.out.println("\n*** Eliminar un Auto ***");
        System.out.print("ID del auto a eliminar: ");
        int id = scanner.nextInt();
        String sql = "DELETE FROM autos WHERE id = " + id;
        statement.executeUpdate(sql);
        System.out.println("Auto eliminado correctamente.");
    }

    // Método para consultar todos los autos
    static void consultarAutos(Statement statement) throws SQLException {
        System.out.println("\n*** Consultar Autos ***");
        String sql = "SELECT * FROM autos";
        ResultSet resultados = statement.executeQuery(sql);

        while (resultados.next()) {
            int id = resultados.getInt("id");
            String marca = resultados.getString("marca");
            String modelo = resultados.getString("modelo");
            String color = resultados.getString("color");
            int cilindros = resultados.getInt("cilindros");
            System.out.println("ID: " + id + ", Marca: " + marca + ", Modelo: " + modelo + ", Color: " + color + ", Cilindros: " + cilindros);
        }
        System.out.println();
    }
}
