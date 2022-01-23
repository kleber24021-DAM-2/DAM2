package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ITEMS", schema = "andrePadilla_WebStore", catalog = "")

@NamedQuery(name = "get_item_by_id", query = "select i from Item i where i.idItem = :id")
@NamedQuery(name = "get_all_items", query = "select i from Item i")
public class Item {
    private int idItem;
    private String name;
    private String company;
    private double price;
    private List<Purchase> purchasesByIdItem;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "itemsByIdItem", fetch = FetchType.EAGER)
    public List<Purchase> getPurchasesByIdItem(){
        return purchasesByIdItem;
    }
    public void setPurchasesByIdItem(List<Purchase> purchasesByIdItem){
        this.purchasesByIdItem = purchasesByIdItem;
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
        return purchasesByIdItem != null ? purchasesByIdItem.equals(item.purchasesByIdItem) : item.purchasesByIdItem == null;
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
        result = 31 * result + (purchasesByIdItem != null ? purchasesByIdItem.hashCode() : 0);
        return result;
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
}
