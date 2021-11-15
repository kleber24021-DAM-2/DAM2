package dao.dao_implementations.file;

import configuration.ConfigProperties;
import dao.interfaces.DAOPurchases;
import model.Purchase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.*;

public class NioDAOPurchases implements DAOPurchases {
    Path file = Paths.get(ConfigProperties.getInstance().getProperty("purchasesFile"));

    @Override
    public Purchase get(int id) {
        List<Purchase> purchaseList = getAll();
        List<Purchase> result = purchaseList.stream().filter(purchase -> purchase.getItem().getIdItem() == id).collect(Collectors.toList());
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public List<Purchase> getAll() {
        List<Purchase> purchaseList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            reader.lines().forEach(s -> purchaseList.add(new Purchase()));
        } catch (IOException io) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error de IO DAOPurchase getAll()", io);
        }
        return purchaseList;
    }

    @Override
    public Purchase save(Purchase t) {
        List<Purchase> purchaseList = getAll();
        t.setId(getLastId()+1);
        if (get(t.getItem().getIdItem()) == null) {
            purchaseList.add(t);
        }
        OpenOption[] options = new OpenOption[2];
        options[0] = APPEND;
        options[1] = WRITE;
        try (BufferedWriter bfw = Files.newBufferedWriter(file, options)) {
            bfw.write(t.toStringTexto());
            bfw.newLine();
        } catch (IOException io) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, io);
        }
    }

    @Override
    public void update(Purchase t) {
        //Implement update purchase method
        //TODO check the funtion of this method. May need to have previos and new purchase
    }

    @Override
    public void delete(Purchase p) {
        List<Purchase> purchaseList = getAll();
        purchaseList.remove(p);
        OpenOption[] options = new OpenOption[2];
        options[0] = TRUNCATE_EXISTING;
        options[1] = WRITE;
        try (BufferedWriter bfw = Files.newBufferedWriter(file, options)) {
            for (Purchase purchase : purchaseList) {
                bfw.write(purchase.toStringTexto());
                bfw.newLine();
            }
        } catch (IOException io) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, io);
        }
    }

    public void deleteByItemId(int itemId){
        getAll().forEach(purchase -> {
            if (purchase.getItem().getIdItem() == itemId){
                delete(purchase);
            }
        });
    }

    private int getLastId() {
        List<Purchase> purchaseList = getAll();
        if (purchaseList.isEmpty()) {
            return 0;
        }
        purchaseList = purchaseList.stream().sorted().collect(Collectors.toList());
        return purchaseList.get(purchaseList.size() - 1).getId();
    }

    public List<Purchase> getByDate(LocalDate selectedDate) {
        return getAll().stream().filter(purchase -> purchase.getDate().equals(selectedDate)).collect(Collectors.toList());
    }

    public List<Purchase> getByCustomerId(int idCustomer) {
        List<Purchase> purchaseList = getAll();
        return purchaseList.stream().filter(p -> p.getCustomer().getIdCustomer() == idCustomer).collect(Collectors.toList());
    }

    public void deleteByCustomerId(int idCustomer) {
        getAll().forEach(purchase -> {
            if (purchase.getCustomer().getIdCustomer() == idCustomer)
                delete(purchase);
        });
    }
}
