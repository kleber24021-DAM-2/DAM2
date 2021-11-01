/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dam2
 */


public class Customer implements Comparable<Customer> {

    private int idCustomer;
    private String name;
    private String phone;
    private String address;
    private List<Review> reviews;

    public Customer(int idCustomer, String name, String phone, String address) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.address = address;
        this.phone = phone;
        reviews = new ArrayList<>();
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public String toStringShort() {
        return idCustomer + " - " + name;
    }

    public String toStringTexto() {
        return idCustomer + "/" + name + "/" + phone + "/" + address + "/" + reviews;
    }

    public String toStringReviews() {
        List<String> rev = new ArrayList<>();

        if (reviews != null) {
            for (int i = 0; i < reviews.size(); i++) {
                rev.add(reviews.get(i).toStringVisual());
            }
        }


        return "ID: " + idCustomer + "  Name: " + name
                + "\nPhone: " + phone + "  Address: " + address
                + "\n\n======       Reviews done by this client:      ======\n\n" + rev;
    }

    @Override
    public String toString() {
        return "ID: " + idCustomer + "  Name: " + name + "  Phone: " + phone + "  Address: " + address;
    }

    @Override
    public int hashCode() {
        return -1;
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
        final Customer other = (Customer) obj;
        if (this.idCustomer != other.idCustomer) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }


    @Override
    public int compareTo(Customer o) {
        return Integer.compare((this.idCustomer), o.getIdCustomer());
    }
}
