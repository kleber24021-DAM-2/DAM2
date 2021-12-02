package model.hibernatemodels;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ITEMS", schema = "andrePadilla_WebStore", catalog = "")
public class EntityItems {
    private int idItem;
    private String name;
    private String company;
    private double price;
    private Collection<EntityPurchases> purchasesByIdItem;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityItems that = (EntityItems) o;

        if (idItem != that.idItem) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;

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

    @OneToMany(mappedBy = "itemsByIdItem")
    public Collection<EntityPurchases> getPurchasesByIdItem() {
        return purchasesByIdItem;
    }

    public void setPurchasesByIdItem(Collection<EntityPurchases> purchasesByIdItem) {
        this.purchasesByIdItem = purchasesByIdItem;
    }
}
