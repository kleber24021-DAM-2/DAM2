package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PURCHASES", schema = "andrePadilla_WebStore", catalog = "")

@NamedQuery(name = "get_purchase_by_id", query = "select p from Purchase p where p.idPurchase = :id")
@NamedQuery(name = "get_all_purchase", query = "select p from Purchase p")
@NamedQuery(name = "get_purchase_by_customer", query = "select p from Purchase p where p.customersByIdCustomer.idCustomer = :idCustomer")
@NamedQuery(name = "get_purchase_by_item", query = "select p from Purchase p where p.itemsByIdItem.idItem = :idItem")
@NamedQuery(name = "get_purchase_by_date", query = "select p from Purchase p where p.date = :date")
@NamedQuery(name = "get_purchases_order_by_item", query = "select p from Purchase p order by p.itemsByIdItem.idItem")
@NamedQuery(name = "get_purchases_order_by_customer", query = "select p from Purchase p order by p.customersByIdCustomer.idCustomer")
@NamedQuery(name = "get_purchases_in_date_range", query = "select p from Purchase p where p.date between :initialDate and :finalDate")
public class Purchase {
    private int idPurchase;
    private LocalDate date;
    private Customer customersByIdCustomer;
    private Item itemsByIdItem;
    private List<Review> reviewsByIdPurchase;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PURCHASE")
    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    @Basic
    @Column(name = "DATE", nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID_CUSTOMER", nullable = false)
    public Customer getCustomersByIdCustomer(){
        return customersByIdCustomer;
    }
    public void setCustomersByIdCustomer(Customer customersByIdCustomer){
        this.customersByIdCustomer = customersByIdCustomer;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ITEM", referencedColumnName = "ID_ITEM", nullable = false)
    public Item getItemsByIdItem(){
        return this.itemsByIdItem;
    }
    public void setItemsByIdItem(Item itemsByIdItem){
        this.itemsByIdItem = itemsByIdItem;
    }

    @OneToMany(mappedBy = "purchasesByIdPurchases", fetch = FetchType.EAGER)
    public List<Review> getReviewsByIdPurchase(){
        return reviewsByIdPurchase;
    }
    public void setReviewsByIdPurchase(List<Review> reviewsByIdPurchase){
        this.reviewsByIdPurchase = reviewsByIdPurchase;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "idPurchase=" + idPurchase +
                ", date=" + date +
                ", customersByIdCustomer=" + customersByIdCustomer +
                ", itemsByIdItem=" + itemsByIdItem +
                '}';
    }
}
