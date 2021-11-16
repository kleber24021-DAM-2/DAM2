package dao.dao_implementations.file;

import configuration.ConfigProperties;
import dao.interfaces.DAOCustomers;
import model.Customer;
import model.Customers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class XMLDaoCustomers implements DAOCustomers {
    final private Path path = Paths.get(ConfigProperties.getInstance().getProperty("customerFile"));

    @Override
    public Customer get(int id) {
        List<Customer> actualList = getAll();
        List<Customer> filteredList = actualList.stream().filter(c -> c.getIdCustomer() == id).collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            return null;
        } else {
            filteredList.get(0).setName("Andre");
            return filteredList.get(0);

        }
    }

    @Override
    public List<Customer> getAll() {
        try {
            JAXBContext context = JAXBContext.newInstance(Customers.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Customers customersList = (Customers) unmarshaller.unmarshal(Files.newInputStream(path));
            return customersList.getCustomerList();

        } catch (JAXBException | IOException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, exception.toString(), exception);
            return Collections.emptyList();
        }
    }

    @Override
    public Customer save(Customer t) {
        if (get(t.getIdCustomer()) == null) {
            try {
                JAXBContext context = JAXBContext.newInstance(Customers.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                List<Customer> toLoad = getAll();
                toLoad.add(t);
                Customers customersList = new Customers();
                customersList.setCustomerList(toLoad.stream().sorted().collect(Collectors.toList()));
                marshaller.marshal(customersList, Files.newOutputStream(path));
            } catch (JAXBException | IOException exception) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, exception.toString(), exception);
            }
        }
        return null;
    }

    @Override
    public void update(Customer t) {
        //Implementar método para hacer update de un customer
    }

    @Override
    public boolean delete(Customer t) {
        if (get(t.getIdCustomer()) != null) {
            try {
                JAXBContext context = JAXBContext.newInstance(Customers.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                List<Customer> toLoad = getAll();
                toLoad.remove(t);
                Customers customersList = new Customers();
                customersList.setCustomerList(toLoad);
                marshaller.marshal(customersList, Files.newOutputStream(path));
                return true;
            } catch (JAXBException | IOException exception) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, exception.toString(), exception);
            }
        }
        return false;
    }
}
