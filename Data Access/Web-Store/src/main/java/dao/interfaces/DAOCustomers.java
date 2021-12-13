/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import model.hibernatemodels.EntityCustomers;

import java.util.List;

/**
 *
 */
public interface DAOCustomers {
     
    EntityCustomers get(int id);
     
    List<EntityCustomers> getAll();

    EntityCustomers save(EntityCustomers t);
     
    boolean update(EntityCustomers t);
     
    boolean delete(EntityCustomers t);
}
