package com.project.currencyconverter;

import com.project.currencyconverter.api.CurrencyApiService;
import com.project.currencyconverter.database.DatabaseManager;
import com.project.currencyconverter.model.Conversion;
import java.util.List;
import java.util.Scanner;

public class CurrencyConverterApp {
    private final CurrencyApiService apiService;
    private final DatabaseManager dbManager;
    private final Scanner scanner;

    public CurrencyConverterApp() {
        this.apiService = new CurrencyApiService();
        this.dbManager = new DatabaseManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = getIntInput("Выберите опцию: ");
            
            switch (choice) {
                case 1 -> performConversion();
                case 2 -> showHistory();
                case 3 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Currency Converter ===");
        System.out.println("1. Конвертировать валюту");
        System.out.println("2. Показать историю");
        System.out.println("3. Выход");
    }

    private void performConversion() {
        try {
            String fromCurrency = getStringInput("Из валюты (например: USD): ");
            String toCurrency = getStringInput("В валюту (например: EUR): ");
            double amount = getDoubleInput("Сумма для конвертации: ");
            
            double rate = apiService.getExchangeRate(fromCurrency, toCurrency);
            double convertedAmount = amount * rate;
            
            Conversion conversion = new Conversion(fromCurrency, toCurrency, amount, convertedAmount, rate);
            dbManager.saveConversion(conversion);
            
            System.out.printf("\nРезультат: %.2f %s = %.2f %s (Курс: %.4f)%n",
                    amount, fromCurrency.toUpperCase(),
                    convertedAmount, toCurrency.toUpperCase(),
                    rate);
            
        } catch (Exception e) {
            System.err.println("Ошибка конвертации: " + e.getMessage());
        }
    }

    private void showHistory() {
        List<Conversion> history = dbManager.getConversionHistory();
        System.out.println("\n=== История конвертаций ===");
        
        if (history.isEmpty()) {
            System.out.println("История пуста");
            return;
        }
        
        for (Conversion conv : history) {
            System.out.printf("[%s] %s %.2f -> %s %.2f (Курс: %.4f)%n",
                    conv.getTimestamp().toString(),
                    conv.getFromCurrency(),
                    conv.getAmount(),
                    conv.getToCurrency(),
                    conv.getConvertedAmount(),
                    conv.getRate());
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите целое число");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число");
            }
        }
    }

    public static void main(String[] args) {
        new CurrencyConverterApp().start();
    }
}