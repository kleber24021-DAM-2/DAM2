package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REVIEWS", schema = "andrePadilla_WebStore", catalog = "")
public class Review {
    private int idReview;
    private int rating;
    private String title;
    private String description;
    private Date date;
    private int idPurchase;
    private Purchase purchaseByIdPurchase;

    @Id
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

        Review review = (Review) o;

        if (idReview != review.idReview) return false;
        if (rating != review.rating) return false;
        if (idPurchase != review.idPurchase) return false;
        if (title != null ? !title.equals(review.title) : review.title != null) return false;
        if (description != null ? !description.equals(review.description) : review.description != null) return false;
        if (date != null ? !date.equals(review.date) : review.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idReview;
        result = 31 * result + rating;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + idPurchase;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_PURCHASE", referencedColumnName = "ID_PURCHASE", nullable = false)
    public Purchase getPurchasesByIdPurchase() {
        return purchaseByIdPurchase;
    }

    public void setPurchasesByIdPurchase(Purchase purchaseByIdPurchase) {
        this.purchaseByIdPurchase = purchaseByIdPurchase;
    }

    @Override
    public String toString() {
        return "Review{" +
                "idReview=" + idReview +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", idPurchase=" + idPurchase +
                ", purchaseByIdPurchase=" + purchaseByIdPurchase +
                '}';
    }
}
