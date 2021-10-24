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

    private int idPurchase;
    private int idCustomer;
    private int idItem;
    private LocalDate date;

    public Purchase() {
    }

    public Purchase(String fromFile){
        String[] splited = fromFile.split(";");
        this.idPurchase = Integer.parseInt(splited[0]);
        this.idCustomer = Integer.parseInt(splited[1]);
        this.idItem = Integer.parseInt(splited[2]);
        this.date = LocalDate.parse(splited[3]);
    }

    public Purchase(int idPurchase, int idCustomer, int item, LocalDate date) {
        this.idPurchase = idPurchase;
        this.idCustomer = idCustomer;
        this.idItem = item;
        this.date = date;
    }

    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ID: " + idPurchase + "  Customer: " + idCustomer + "  Item: " + idItem + "  Date: " + date;
    }
    
    public String toStringForClientInfo() {
        return "ID: " + idPurchase + "  Item: " + idItem + "  Date: " + date + "\n";
    }

    public String toStringTexto() {
        return idPurchase + ";" + idCustomer + ";" + idItem + ";" + date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idPurchase;
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
        return this.idPurchase == other.idPurchase;
    }

    @Override
    public int compareTo(Purchase o) {
        return Integer.compare(this.getIdPurchase(), o.getIdPurchase());
    }
}
