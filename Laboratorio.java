package PARCIAL_I;

/*
Chimaltenango10 de agosto  de 2023
Programador: Luis Roberto Surec Morales
Descripcion del programa: Laborato
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Laboratorio {

    //TODO: Modificar ruta segun la mmaquina
    private static final String RUTA_ARCHIVO = "C:\\Users\\dsdev\\OneDrive\\Escritorio\\inventario.txt"; // Ruta del archivo
    //SCANNER gglobal
    private static final Scanner SCANNER = new Scanner(System.in);

    //Metodo principal
    public static void main(String[] args) {
        int opcion;

        do {
            //Mostrar menu
            System.out.println("Menú:");
            System.out.println("1. Registrar producto en el inventario");
            System.out.println("2. Mostrar productos del inventario");
            System.out.println("3. Ingresar producto al inventario");
            System.out.println("4. Extraer producto del inventario");
            System.out.println("5. Salir");
            System.out.println();

            opcion = ingresarEntero("Seleccione una opción: ");

            //segun oopcion
            switch (opcion) {
                case 1:
                    //Resgistrar producto
                    System.out.println();
                    System.out.println("Opción 1: Registrar producto en el inventario");
                    registrarProductos();
                    System.out.println();
                    break;
                case 2:
                    //mostrar productos
                    System.out.println();
                    System.out.println("Opción 2: Mostrar productos del inventario");
                    mostrarProductos();
                    break;
                case 3:
                    //Ingresar nueva cantidad
                    System.out.println();
                    System.out.println("Opción 3: Ingresar producto al inventario");
                    modificarCantidadProducto();
                    break;
                case 4:
                    //extraer cantidad
                    System.out.println();
                    System.out.println("Opción 4: Extraer producto del inventario");
                    extraerProductoPorCodigo();
                    break;
                case 5:
                    System.out.println();
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println();
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 5);

        SCANNER.close();
    }

    //Valida el ingreso de un numero entero (No string)
    public static int ingresarEntero(String mensaje) {
        int numeroIngresado = 0;
        boolean entradaValida = false;

        //peidr el ingreso mientras sea un texto el que se ingrese
        do {
            System.out.print(mensaje);

            if (SCANNER.hasNextInt()) {
                numeroIngresado = SCANNER.nextInt();
                SCANNER.nextLine(); // Limpiar la entrada inválidaF

                entradaValida = true;
            } else {
                System.out.println();

                System.out.println("El valor ingresado debe ser un numero.");

                SCANNER.next(); // Limpiar la entrada inválida
                System.out.println();

            }
        } while (!entradaValida);

        return numeroIngresado;
    }

    //Valida el ingreso de un numero decimal (No string)
    public static double ingresarDecimal(String mensaje) {
        double numeroIngresado = 0;
        boolean entradaValida = false;

        //peidr el ingreso mientras sea un texto el que se ingrese
        do {
            System.out.print(mensaje);

            if (SCANNER.hasNextDouble()) {
                numeroIngresado = SCANNER.nextDouble();
                SCANNER.nextLine(); // Limpiar la entrada inválida

                entradaValida = true;
            } else {
                System.out.println();
                System.out.println("El valor ingresado debe ser un numero.");
                SCANNER.next(); // Limpiar la entrada inválida
                System.out.println();

            }
        } while (!entradaValida);

        return numeroIngresado;
    }

     //Extraer productos
    public static void extraerProductoPorCodigo() {
        
        //Validar si hay productos en el inventario
        int cantidadProductos = contarProductos();

        if (cantidadProductos == 0) {
            System.out.println();
            System.out.println("No hay productos en el inventario para extraer.");
            return;
        }
        System.out.println();

          //solciitar codigo del producto a modificar
        int codigoBuscado = ingresarEntero("Ingrese el código del producto a extraer: ");

        boolean codigoExistente = verificarCodigoExistente(codigoBuscado);

         //validar que el producto exista
        if (!codigoExistente) {
            System.out.println();
            System.out.println("El código ingresado no existe en el inventario.");
            return;
        }

        List<String> lineas = new ArrayList<>();

         //leer el archivo y hacer una copia en lineas
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    int codigo = Integer.parseInt(partes[0]);
                    if (codigo == codigoBuscado) {
                        String nombre = partes[1];
                        int cantidadExistente = Integer.parseInt(partes[2]);
                        double precio = Double.parseDouble(partes[3]);
                        System.out.println();
                         //Mostrar producto antes de modificar
                        System.out.println("Producto:");
                        System.out.println("Código: " + codigo);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("Cantidad existente: " + cantidadExistente);
                        System.out.println("Precio unitario: " + precio);
                        double totalAntes = cantidadExistente * precio;
                        System.out.println("Total: " + totalAntes);

                        int cantidadExtraer;
                        
                        //solicitar cantidad  a extraer y aplicar validaciones
                        do {
                            System.out.println();
                            cantidadExtraer = ingresarEntero("Ingrese la cantidad a extraer (no más de " + cantidadExistente + "): ");
                            if (cantidadExtraer <= 0) {
                                System.out.println();

                                System.out.println("La cantidad debe ser mayor que 0. Ingrese nuevamente: ");
                            } else if (cantidadExtraer > cantidadExistente) {
                                System.out.println();

                                System.out.println("La cantidad a extraer no puede ser mayor que la cantidad existente.");
                            }
                        } while (cantidadExtraer <= 0 || cantidadExtraer > cantidadExistente);

                        int nuevaCantidadExistente = cantidadExistente - cantidadExtraer;

                        lineas.add(codigo + "," + nombre + "," + nuevaCantidadExistente + "," + precio);
                        double totalDespues = nuevaCantidadExistente * precio;
                   
                        System.out.println();
                        System.out.println("Cantidad extraída con éxito.");
                        System.out.println();

                        //mostrar producto modificado
                        System.out.println("Producto:");
                        System.out.println("Código: " + codigo);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("Cantidad existente: " + nuevaCantidadExistente);
                        System.out.println("Precio unitario: " + precio);
                        System.out.println("Total: " + totalDespues);

                    } else {

                        lineas.add(linea);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println();

            System.out.println("Error al leer el archivo.");
        }

         //reescrinbir archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println();
            System.out.println("Error al escribir en el archivo.");
        }
        System.out.println();

    }

    //Agregar cantidad a producto del inventario
    public static void modificarCantidadProducto() {
        //Validar que existan productos
        int cantidadProductos = contarProductos();

        if (cantidadProductos == 0) {
            System.out.println();
            System.out.println("No hay productos en el inventario para modificar.");
            return;
        }

        //solicitar coidiigo del prosucto
                System.out.println();

        int codigoBuscado = ingresarEntero("Ingrese el código del producto a modificar: ");

        boolean codigoExistente = verificarCodigoExistente(codigoBuscado);

        //Valifdar si el producto existe
        if (!codigoExistente) {
            System.out.println();
            System.out.println("El código ingresado no existe en el inventario.");
            System.out.println();

            return;
        }

        List<String> lineas = new ArrayList<>();
        String lineaModificada;

           //leer el archivo y hacer una copia
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    int codigo = Integer.parseInt(partes[0]);
                    if (codigo == codigoBuscado) {
                        String nombre = partes[1];
                        int cantidadExistente = Integer.parseInt(partes[2]);
                        double precio = Double.parseDouble(partes[3]);
                        double total = cantidadExistente * precio;

                        //mostrar producto antes de modificarlo
                        System.out.println();
                        System.out.println("Producto:");
                        System.out.println("Código: " + codigo);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("Cantidad existente: " + cantidadExistente);
                        System.out.println("Precio unitario: " + precio);
                        System.out.println("Total: " + total);

                        //solicitar nueva cantidad y validarla
                        int nuevaCantidad;
                        do {
                            System.out.println();

                            nuevaCantidad = ingresarEntero("Nueva cantidad: ");
                            if (nuevaCantidad <= 0) {
                                System.out.println();

                                System.out.println("La cantidad debe ser mayor que 0. Ingrese nuevamente: ");
                            }
                        } while (nuevaCantidad <= 0);

                        int nuevaCantidadTotal = cantidadExistente + nuevaCantidad;
                        double nuevoTotal = nuevaCantidadTotal * precio;

                        lineaModificada = codigo + "," + nombre + "," + nuevaCantidadTotal + "," + precio;
                        lineas.add(lineaModificada);

                        System.out.println();
                        System.out.println("Cantidad modificada con éxito.");
                        System.out.println();

                        //mostrar pdocuto modificado
                        System.out.println("Producto:");
                        System.out.println("Código: " + codigo);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("Cantidad existente: " + nuevaCantidadTotal);
                        System.out.println("Precio unitario: " + precio);
                        System.out.println("Total: " + nuevoTotal);
                    } else {
                        lineas.add(linea);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println();
            System.out.println("Error al leer el archivo.");
        }

        //reescreibir archivo con los cambios
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println();

            System.out.println("Error al escribir en el archivo.");
        }

        System.out.println();

    }

     //metodo para mostrar productos
    public static void mostrarProductos() {
        System.out.println();

         //leer archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;

            System.out.println("Productos en el inventario: " + contarProductos());
            System.out.println();

            double granTotal = 0.0;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    int codigo = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    int cantidad = Integer.parseInt(partes[2]);
                    double precio = Double.parseDouble(partes[3]);

                    double totalProducto = cantidad * precio; // Calcular el total del producto

                    //mostrar producto encontrado
                    System.out.println("Código: " + codigo);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Cantidad: " + cantidad);
                    System.out.println("Precio: " + precio);
                    System.out.println("Total del producto: " + totalProducto);
                    System.out.println("-------------------------");

                    granTotal += totalProducto; // Sumar al gran total
                }
            }
            
            //mostrar gran total
            System.out.println();
            System.out.println("Gran Total: " + granTotal);
            System.out.println();

        } catch (IOException e) {
            System.out.println();

            System.out.println("Error al leer el archivo.");
            System.out.println();

        }
    }

    //metodo para vontar cuantos productos hay en inventario
    public static int contarProductos() {
        int cantidadProductos = 0;

        //leer archvio
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    cantidadProductos++; // Incrementar el contador de productos
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }

         //retornar cantidad encontrada
        return cantidadProductos;
    }

    //verificar existencia del producto
    public static boolean verificarCodigoExistente(int codigo) {
        //Leer archvio
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length > 0) {
                    int codigoExistente = Integer.parseInt(partes[0]);
                    if (codigoExistente == codigo) {
                        return true; // El código ya existe
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
        return false; // El código no existe
    }
    
    //ingresar nuevo producto al inventario
    public static void registrarProductos() {
        String respuesta;

        do {
            int codigo;
            boolean codigoExistente;

            do {
                //solicitar codigo
                System.out.println();
                codigo = ingresarEntero("Ingrese el código del producto: ");
                codigoExistente = verificarCodigoExistente(codigo);

                
                if (codigoExistente) {
                    System.out.println();

                    System.out.println("El código ya existe. Ingrese un código único.");
                }
            } while (codigoExistente);

            //solicitar nombre
            System.out.print("Ingrese el nombre del producto: ");
            String nombre = SCANNER.nextLine();

            //solicitar precio
            double precio = ingresarDecimal("Ingrese el precio unitario: ");

            //escribir en archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {
                writer.write(codigo + "," + nombre + "," + 0 + "," + precio);
                writer.newLine();
                System.out.println();

                System.out.println("Producto registrado en el inventario.");
            } catch (IOException e) {
                System.out.println();

                System.out.println("Error al escribir en el archivo.");
            }

            //pregntar si seguir ingresando o no productos
            System.out.println();
            System.out.print("¿Desea seguir ingresando productos? (S/N): ");
            respuesta = SCANNER.nextLine();

        } while (respuesta.length() > 0 && Character.toUpperCase(respuesta.charAt(0)) != 'N');

    }

}

//clase producto
class Producto {

    private int codigoProducto;
    private String nombreProducto;
    private int cantidadExistente;
    private double precioUnitario;

    public Producto(int codigoProducto, String nombreProducto, int cantidadExistente, double precioUnitario) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadExistente = cantidadExistente;
        this.precioUnitario = precioUnitario;
    }

    // Métodos getter y setter para los atributos
    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidadExistente() {
        return cantidadExistente;
    }

    public void setCantidadExistente(int cantidadExistente) {
        this.cantidadExistente = cantidadExistente;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
