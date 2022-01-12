package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ITEMS", schema = "andrePadilla_WebStore", catalog = "")
public class Item {
    private int idItem;
    private String name;
    private String company;
    private double price;

    @Id
    @Column(name = "ID_ITEM")
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "COMPANY")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Basic
    @Column(name = "PRICE")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "idItem=" + idItem +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (idItem != item.idItem) return false;
        if (Double.compare(item.price, price) != 0) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;
        if (company != null ? !company.equals(item.company) : item.company != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idItem;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
