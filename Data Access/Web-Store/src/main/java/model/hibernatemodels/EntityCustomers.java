package model.hibernatemodels;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "CUSTOMERS", schema = "andrePadilla_WebStore", catalog = "")
public class EntityCustomers {
    private int idCustomer;
    private String name;
    private String telephone;
    private String address;
    private EntityUsers usersByIdCustomer;
    private Collection<EntityPurchases> purchasesByIdCustomer;

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

        EntityCustomers that = (EntityCustomers) o;

        if (idCustomer != that.idCustomer) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

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
    public EntityUsers getUsersByIdCustomer() {
        return usersByIdCustomer;
    }

    public void setUsersByIdCustomer(EntityUsers usersByIdCustomer) {
        this.usersByIdCustomer = usersByIdCustomer;
    }

    @OneToMany(mappedBy = "customersByIdCustomer")
    public Collection<EntityPurchases> getPurchasesByIdCustomer() {
        return purchasesByIdCustomer;
    }

    public void setPurchasesByIdCustomer(Collection<EntityPurchases> purchasesByIdCustomer) {
        this.purchasesByIdCustomer = purchasesByIdCustomer;
    }
}
