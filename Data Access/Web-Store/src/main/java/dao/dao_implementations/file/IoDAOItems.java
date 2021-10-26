package dao.dao_implementations.file;

import configuration.ConfigProperties;
import dao.interfaces.DAOItems;
import model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class IoDAOItems implements DAOItems {

    private final File sourceFile = new File(ConfigProperties.getInstance().getProperty("itemFile"));

    @Override
    public Item get(int id) {

        List<Item> temp = getAll().stream().filter(
                item -> item.getIdItem() == id
        ).collect(Collectors.toList());
        if (!temp.isEmpty()) {
            return temp.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAll() {
        List<Item> listOfItems = new ArrayList<>();
        try (BufferedReader brf = new BufferedReader(new FileReader(sourceFile))) {
            String st;
            while ((st = brf.readLine()) != null) {
                listOfItems.add(new Item(st));
            }
            return listOfItems;
        } catch (IOException io) {
            Logger.getLogger(io.getClass().getName()).log(Level.SEVERE, "Error de IO ", io);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean save(Item t) {
        if (get(t.getIdItem()) == null) {
            try (FileWriter writer = new FileWriter(sourceFile, true);
                 BufferedWriter bw = new BufferedWriter(writer)) {
                bw.write(t.toStringTextFile());
                bw.newLine();
            } catch (Exception io) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, io.toString());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Item t) {
        return true;
    }

    @Override
    public boolean delete(Item t) {
        List<Item> actualItems;
        actualItems = getAll();
        if (actualItems.removeIf(item -> item.equals(t))) {
            try (BufferedWriter bfw = new BufferedWriter(new FileWriter(sourceFile, false))) {
                //Vacíamos por completo el archivo
                bfw.write("");
                //Escribimos los elementos restantes
                for (Item item : actualItems) {
                    bfw.append(item.toStringTextFile()).append("\n");
                }
                return true;
            } catch (IOException io) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, io.toString());
            }
        }
        return false;
    }
}
