package org.example;

public class Main {
    public static void main(String[] args) {
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        ConsoleView view = new ConsoleView(exchangeRateService);
        view.iniciar();
    }
}
