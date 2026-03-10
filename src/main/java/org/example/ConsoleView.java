package org.example;

import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner;
    private final ExchangeRateService exchangeRateService;

    public ConsoleView(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuMonedas();
                case 2 -> menuTemperatura();
                case 0 -> {
                    System.out.println("Programa terminado. ¡Hasta luego!");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida, intenta de nuevo.");
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== CONVERSOR ALURA (API) ===");
        System.out.println("1. Conversor de moneda (tasas en tiempo real)");
        System.out.println("2. Conversor de temperatura");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private void menuMonedas() {
        boolean seguirEnMenuMonedas = true;

        while (seguirEnMenuMonedas) {
            System.out.println("\n--- Conversor de moneda ---");
            System.out.println("1. MXN -> USD");
            System.out.println("2. MXN -> EUR");
            System.out.println("3. MXN -> GBP");
            System.out.println("4. MXN -> JPY");
            System.out.println("5. MXN -> KRW");
            System.out.println("6. USD -> MXN");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");

            int opcion = leerEntero();
            if (opcion == 0) {
                return; // vuelve al menú principal
            }

            System.out.print("Ingresa el monto: ");
            Double monto = leerDouble();
            if (monto == null) {
                System.out.println("Valor no válido. Operación cancelada.");
                continue;
            }

            String from;
            String to;

            switch (opcion) {
                case 1 -> { from = "MXN"; to = "USD"; }
                case 2 -> { from = "MXN"; to = "EUR"; }
                case 3 -> { from = "MXN"; to = "GBP"; }
                case 4 -> { from = "MXN"; to = "JPY"; }
                case 5 -> { from = "MXN"; to = "KRW"; }
                case 6 -> { from = "USD"; to = "MXN"; }
                default -> {
                    System.out.println("Opción inválida.");
                    continue;
                }
            }

            double resultado = exchangeRateService.convertir(from, to, monto);
            System.out.printf("Resultado: %.2f %s = %.2f %s%n", monto, from, resultado, to);

            // preguntar si quiere otra conversión de moneda o volver
            System.out.print("\n¿Deseas hacer otra conversión de moneda? (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (!respuesta.equals("S")) {
                seguirEnMenuMonedas = false; // sale del while y vuelve al menú principal
            }
        }
    }


    private void menuTemperatura() {
        System.out.println("\n--- Conversor de temperatura ---");
        System.out.println("1. Celsius -> Fahrenheit");
        System.out.println("2. Fahrenheit -> Celsius");
        System.out.print("Elige una opción: ");

        int opcion = leerEntero();

        System.out.print("Ingresa el valor: ");
        Double valor = leerDouble();
        if (valor == null) {
            System.out.println("Valor no válido. Operación cancelada.");
            return;
        }

        double resultado;
        String mensaje;

        switch (opcion) {
            case 1 -> {
                resultado = (valor * 9 / 5) + 32;
                mensaje = valor + " °C = " + resultado + " °F";
            }
            case 2 -> {
                resultado = (valor - 32) * 5 / 9;
                mensaje = valor + " °F = " + resultado + " °C";
            }
            default -> {
                System.out.println("Opción inválida.");
                return;
            }
        }

        System.out.println("Resultado: " + mensaje);
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private Double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
