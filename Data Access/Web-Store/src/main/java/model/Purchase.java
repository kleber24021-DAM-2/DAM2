/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Laura
 */
public class Purchase implements Comparable<Purchase> {

    private int id;
    private Customer customer;
    private Item item;
    private LocalDate date;

    public Purchase() {
    }

    public Purchase(int idPurchase, LocalDate date, Customer customer, Item item) {
        this.id = idPurchase;
        this.customer = customer;
        this.item = item;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ID: " + id + "  Customer: " + customer + "  Item: " + item + "  Date: " + date;
    }
    
    public String toStringForClientInfo() {
        return "ID: " + id + "  Item: " + item + "  Date: " + date + "\n";
    }

    public String toStringTexto() {
        return id + ";" + customer + ";" + item + ";" + date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Purchase other = (Purchase) obj;
        return this.id == other.id;
    }

    @Override
    public int compareTo(Purchase o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
