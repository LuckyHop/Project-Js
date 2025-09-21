package com.project.currencyconverter.model;

import java.time.LocalDateTime;

public class Conversion {
    private int id;
    private String fromCurrency;
    private String toCurrency;
    private double amount;
    private double convertedAmount;
    private double rate;
    private LocalDateTime timestamp;

    // Конструкторы
    public Conversion() {}

    public Conversion(String fromCurrency, String toCurrency, double amount, double convertedAmount, double rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.rate = rate;
        this.timestamp = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFromCurrency() { return fromCurrency; }
    public void setFromCurrency(String fromCurrency) { this.fromCurrency = fromCurrency; }
    
    public String getToCurrency() { return toCurrency; }
    public void setToCurrency(String toCurrency) { this.toCurrency = toCurrency; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public double getConvertedAmount() { return convertedAmount; }
    public void setConvertedAmount(double convertedAmount) { this.convertedAmount = convertedAmount; }
    
    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}