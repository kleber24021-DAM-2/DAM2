package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMERS", schema = "andrePadilla_WebStore", catalog = "")

@NamedQuery(name = "get_all_customers",
        query = "select c from Customer c")
@NamedQuery(name = "get_customer_by_id",
        query = "select c from Customer c where c.idCustomer = :id")
public class Customer {
    private int idCustomer;
    private String name;
    private String telephone;
    private String address;
    private List<Purchase> purchasesByIdCustomer;
    private User userByIdCustomer;

    @Id
    @Column(name = "ID_CUSTOMER")
    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @OneToMany(mappedBy = "customersByIdCustomer", fetch = FetchType.EAGER)
    public List<Purchase> getPurchasesByIdCustomer() {
        return this.purchasesByIdCustomer;
    }

    public void setPurchasesByIdCustomer(List<Purchase> purchasesByIdCustomer) {
        this.purchasesByIdCustomer = purchasesByIdCustomer;
    }

    @OneToOne
    @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "USER_ID", nullable = false)
    public User getUserByIdCustomer() {
        return userByIdCustomer;
    }

    public void setUserByIdCustomer(User user) {
        this.userByIdCustomer = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (idCustomer != customer.idCustomer) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (telephone != null ? !telephone.equals(customer.telephone) : customer.telephone != null) return false;
        if (address != null ? !address.equals(customer.address) : customer.address != null) return false;
        if (purchasesByIdCustomer != null ? !purchasesByIdCustomer.equals(customer.purchasesByIdCustomer) : customer.purchasesByIdCustomer != null)
            return false;
        return userByIdCustomer != null ? userByIdCustomer.equals(customer.userByIdCustomer) : customer.userByIdCustomer == null;
    }

    @Override
    public int hashCode() {
        int result = idCustomer;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (purchasesByIdCustomer != null ? purchasesByIdCustomer.hashCode() : 0);
        result = 31 * result + (userByIdCustomer != null ? userByIdCustomer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "idCustomer=" + idCustomer +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", userByIdCustomer=" + userByIdCustomer +
                '}';
    }
}
