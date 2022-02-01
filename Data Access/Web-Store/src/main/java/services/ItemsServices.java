/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.DAOItems;
import dao.mongo.DaoItemsMongo;
import io.vavr.control.Either;
import model.DeleteItemResults;
import model.customer.Purchase;
import model.item.Item;
import model.item.Review;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dam2
 */
public class ItemsServices {

    public Either<String, Item> getItemById(int itemId){
        DAOItems daoItems = new DaoItemsMongo();
        return daoItems.get(itemId);
    }

    public Either<String, List<Item>> getAllItems() {
        DAOItems daoItems = new DaoItemsMongo();
        return daoItems.getAll();
    }

    public Either<String, Item> addItem(String itemName, String itemCompany, double price) {
        DAOItems daoItems = new DaoItemsMongo();
        Item it = new Item();
        it.setName(itemName);
        it.setCompany(itemCompany);
        it.setPrice(price);
        return daoItems.saveItem(it);
    }

    public Either<DeleteItemResults,Void> deleteItem(Item toDelete, boolean userHasConfirmed) {
        DAOItems daoItems = new DaoItemsMongo();
        Either<DeleteItemResults, Void> deleteResult;
        if (toDelete.getPurchases().isEmpty()){
            if (daoItems.deleteWithoutPurchases(toDelete).isRight()){
                deleteResult = Either.right(null);
            }else {
                deleteResult = Either.left(DeleteItemResults.DB_ERROR);
            }
        }else {
            List<Review> associatedReviews = toDelete.getPurchases().stream().map(Purchase::getReview).collect(Collectors.toList());
            if (associatedReviews.isEmpty()){
                if (userHasConfirmed){
                    if (daoItems.deleteWithPurchases(toDelete).isRight()){
                        deleteResult = Either.right(null);
                    }else {
                        deleteResult = Either.left(DeleteItemResults.DB_ERROR);
                    }
                }else {
                    deleteResult = Either.left(DeleteItemResults.ASSOCIATED_PURCHASES);
                }
            }else {
                deleteResult = Either.left(DeleteItemResults.ASSOCIATED_REVIEWS);
            }
        }
        return deleteResult;
    }

    public Either<String, Item> updateItem(Item updatedCustomer) {
        DAOItems daoItems = new DaoItemsMongo();
        return daoItems.updateItem(updatedCustomer);
    }
}
