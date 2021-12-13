/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;


import model.hibernatemodels.EntityPurchases;

import java.time.LocalDate;
import java.util.List;

/**
 *
 */
public interface DAOPurchases {

    EntityPurchases get(int id);

    List<EntityPurchases> getAll();

    EntityPurchases save(EntityPurchases t);

    void update(EntityPurchases t);

    void delete(EntityPurchases t);

    List<EntityPurchases> getByCustomerId(int idCustomer);

    List<EntityPurchases> getByItemId(int idItem);

    List<EntityPurchases> getByDate(LocalDate selectedDate);

    void deleteByCustomerId(int idCustomer);

    void deleteByItemId(int idItem);
}
