package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (idPurchase != purchase.idPurchase) return false;
        if (idCustomer != purchase.idCustomer) return false;
        if (idItem != purchase.idItem) return false;
        if (date != null ? !date.equals(purchase.date) : purchase.date != null) return false;

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

    @Override
    public String toString() {
        return "Purchase{" +
                "idPurchase=" + idPurchase +
                ", date=" + date +
                ", idCustomer=" + idCustomer +
                ", idItem=" + idItem +
                ", customerByIdCustomer=" + customerByIdCustomer +
                ", itemByIdItem=" + itemByIdItem +
                '}';
    }
}
