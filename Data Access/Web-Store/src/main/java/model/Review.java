package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REVIEWS", schema = "andrePadilla_WebStore", catalog = "")
@NamedQuery(name = "get_review_by_id", query = "select r from Review r where r.idReview = :id")
@NamedQuery(name = "get_all_reviews", query = "select r from Review r")
@NamedQuery(name = "get_reviews_by_customer", query = "select  r from Review r where r.purchasesByIdPurchases.customersByIdCustomer.idCustomer = :customerId")
@NamedQuery(name = "get_reviews_by_item", query = "select r from Review r where r.purchasesByIdPurchases.itemsByIdItem.idItem = :itemId")
@NamedQuery(name = "get_reviews_by_purchase", query = "select r from Review r where r.purchasesByIdPurchases.idPurchase = :purchaseId")
@NamedQuery(name = "get_reviews_by_item_sorted_by_date", query = "select r from Review r where r.purchasesByIdPurchases.itemsByIdItem = :item order by r.date")
@NamedQuery(name = "get_reviews_by_item_sorted_by_rating", query = "select r from Review r where r.purchasesByIdPurchases.itemsByIdItem = :item order by r.rating")
@NamedQuery(name = "get_reviews_by_rating", query = "select r from Review r where r.rating = :rating")
public class Review {
    private int idReview;
    private int rating;
    private String title;
    private String description;
    private LocalDate date;
    private Purchase purchasesByIdPurchases;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REVIEW")
    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    @Basic
    @Column(name = "RATING")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "DATE")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "ID_PURCHASE", referencedColumnName = "ID_PURCHASE", nullable = false)
    public Purchase getPurchasesByIdPurchases(){
        return purchasesByIdPurchases;
    }
    public void setPurchasesByIdPurchases(Purchase purchasesByIdPurchases){
        this.purchasesByIdPurchases = purchasesByIdPurchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (idReview != review.idReview) return false;
        if (rating != review.rating) return false;
        if (title != null ? !title.equals(review.title) : review.title != null) return false;
        if (description != null ? !description.equals(review.description) : review.description != null) return false;
        if (date != null ? !date.equals(review.date) : review.date != null) return false;
        return purchasesByIdPurchases != null ? purchasesByIdPurchases.equals(review.purchasesByIdPurchases) : review.purchasesByIdPurchases == null;
    }

    @Override
    public int hashCode() {
        int result = idReview;
        result = 31 * result + rating;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (purchasesByIdPurchases != null ? purchasesByIdPurchases.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "idReview=" + idReview +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", purchasesByIdPurchases=" + purchasesByIdPurchases +
                '}';
    }
}
