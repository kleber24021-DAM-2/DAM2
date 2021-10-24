/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Laura
 */
public class Item implements Comparable<Item> {

    private int idItem;
    private String name;
    private String company;
    private double price;

    public Item() {
    }

    public Item(int idItem, String name, String company, double price) {
        this.idItem = idItem;
        this.name = name;
        this.company = company;
        this.price = price;
    }

    public Item(String stringFromFile){
        String[] elementsOfItem = stringFromFile.split(";");
            this.idItem = Integer.parseInt(elementsOfItem[0]);
            this.name = elementsOfItem[1];
            this.company = elementsOfItem[2];
            this.price = Double.parseDouble(elementsOfItem[3]);
    }

    

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return idItem + " " + name + " " + company + " " + price + "€";
    }
    
    public String toStringTextFile() {
        return idItem + ";" + name + ";" + company + ";" + price;
    }

    public String toStringVisual() {
        return "ID: " + idItem + "  Name: " + name + "  Company: " + company + " Price: " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return idItem == item.idItem;
    }



    @Override
    public int compareTo(Item o) {
        return Integer.compare(this.idItem, o.getIdItem());
    }
}
