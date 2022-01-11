package model;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Entity
@Table(name = "PURCHASES", schema = "andrePadilla_WebStore", catalog = "")
public class Purchase {
    private int idPurchase;
    private Date date;
    private int idCustomer;
    private int idItem;
    private Customer customerByIdCustomer;
    private Item itemByIdItem;

    @Id
    @Column(name = "ID_PURCHASE")
    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    @Basic
    @Column(name = "DATE")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "ID_CUSTOMER")
    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    @Basic
    @Column(name = "ID_ITEM")
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase that = (Purchase) o;

        if (idPurchase != that.idPurchase) return false;
        if (idCustomer != that.idCustomer) return false;
        if (idItem != that.idItem) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPurchase;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + idCustomer;
        result = 31 * result + idItem;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID_CUSTOMER", nullable = false)
    public Customer getCustomersByIdCustomer() {
        return customerByIdCustomer;
    }

    public void setCustomersByIdCustomer(Customer customerByIdCustomer) {
        this.customerByIdCustomer = customerByIdCustomer;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ITEM", referencedColumnName = "ID_ITEM", nullable = false)
    public Item getItemsByIdItem() {
        return itemByIdItem;
    }

    public void setItemsByIdItem(Item itemByIdItem) {
        this.itemByIdItem = itemByIdItem;
    }
}
