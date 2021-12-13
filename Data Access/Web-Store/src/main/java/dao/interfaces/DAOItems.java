/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;


import model.hibernatemodels.EntityItems;

import java.util.List;

/**
 *
 */
public interface DAOItems {

    EntityItems get(int id);
     
    List<EntityItems> getAll();

    EntityItems save(EntityItems t);
     
    boolean update(EntityItems t);
     
    boolean delete(EntityItems t);

    void closePool();
}
