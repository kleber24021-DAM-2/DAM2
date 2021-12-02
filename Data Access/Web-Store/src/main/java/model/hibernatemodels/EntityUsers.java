package model.hibernatemodels;

import javax.persistence.*;

@Entity
@Table(name = "USERS", schema = "andrePadilla_WebStore", catalog = "")
public class EntityUsers {
    private int userId;
    private String username;
    private String password;
    private EntityCustomers customersByUserId;

    @Id
    @Column(name = "USER_ID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityUsers that = (EntityUsers) o;

        if (userId != that.userId) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToOne(mappedBy = "usersByIdCustomer")
    public EntityCustomers getCustomersByUserId() {
        return customersByUserId;
    }

    public void setCustomersByUserId(EntityCustomers customersByUserId) {
        this.customersByUserId = customersByUserId;
    }
}
