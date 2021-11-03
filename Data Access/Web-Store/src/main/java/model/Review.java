/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import gui.controllers.reviews.Ratings;

import java.time.LocalDate;

/**
 *
 * @author dam2
 */
public class Review  {

    private int idReview;
    private Ratings rating;
    private String title;
    private String description;
    private LocalDate date;
    private Purchase purchase;

    public Review(int idReview, Ratings rating, String title, String description, LocalDate date, Purchase purchase) {
        this.idReview = idReview;
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.date = date;
        this.purchase = purchase;
    }

    public Review(int idReview, int rating, String title, String description, LocalDate date, Purchase purchase) {
        this.idReview = idReview;
        this.rating = Ratings.valueOf(rating);
        this.title = title;
        this.description = description;
        this.date = date;
        this.purchase = purchase;
    }

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public Ratings getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = Ratings.valueOf(rating);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }



    @Override
    public String toString() {
        return "No. " + idReview + "\nRating: " + rating + "\nTitle: " + title + "\nComment: " + description + "\nDate: " + date + "  Purchase no. " + purchase;
    }

    public String toStringVisual() {
        return "No. " + idReview + "  Rating: " + rating + "\nTitle: " + title + "\nComment: " + description + "\nDate: " + date + "\n____________________________________________________________\n";
    }

    public String toStringTexto() {
        return idReview + ":" + rating + ":" + title + ":" + description + ":" + date + ":" + ":" + ":" + purchase;
    }

}
