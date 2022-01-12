package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMERS", schema = "andrePadilla_WebStore", catalog = "")
public class Customer {
    private int idCustomer;
    private String name;
    private String telephone;
    private String address;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (idCustomer != customer.idCustomer) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (telephone != null ? !telephone.equals(customer.telephone) : customer.telephone != null) return false;
        if (address != null ? !address.equals(customer.address) : customer.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCustomer;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "USER_ID", nullable = false)
    public User getUsersByIdCustomer() {
        return userByIdCustomer;
    }

    public void setUsersByIdCustomer(User userByIdCustomer) {
        this.userByIdCustomer = userByIdCustomer;
    }
}
