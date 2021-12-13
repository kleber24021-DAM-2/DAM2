package model.hibernatemodels;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "PURCHASES", schema = "andrePadilla_WebStore", catalog = "")
public class EntityPurchases {
    private int idPurchase;
    private Date date;
    private EntityCustomers customersByIdCustomer;
    private EntityItems itemsByIdItem;
    private Collection<EntityReviews> reviewsByIdPurchase;

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

        EntityPurchases that = (EntityPurchases) o;

        if (idPurchase != that.idPurchase) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPurchase;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID_CUSTOMER", nullable = false)
    public EntityCustomers getCustomersByIdCustomer() {
        return customersByIdCustomer;
    }

    public void setCustomersByIdCustomer(EntityCustomers customersByIdCustomer) {
        this.customersByIdCustomer = customersByIdCustomer;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ITEM", referencedColumnName = "ID_ITEM", nullable = false)
    public EntityItems getItemsByIdItem() {
        return itemsByIdItem;
    }

    public void setItemsByIdItem(EntityItems itemsByIdItem) {
        this.itemsByIdItem = itemsByIdItem;
    }

    @OneToMany(mappedBy = "purchasesByIdPurchase")
    public Collection<EntityReviews> getReviewsByIdPurchase() {
        return reviewsByIdPurchase;
    }

    public void setReviewsByIdPurchase(Collection<EntityReviews> reviewsByIdPurchase) {
        this.reviewsByIdPurchase = reviewsByIdPurchase;
    }
}
