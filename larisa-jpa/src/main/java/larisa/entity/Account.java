package larisa.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "account")
public class Account extends DefaultEntity<String> {
    @Id
    @Column(name = "login")
    String id;

    @Column(name = "password")
    String password;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    Date expirationDate;

    @ElementCollection
    @CollectionTable(name = "account_role", joinColumns = @JoinColumn(name = "login", referencedColumnName = "login"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    List<Role> roles;

    @OneToOne
    @JoinColumn(name = "id_customer")
    Customer customer;

    public Account() {
    }

    public Account(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }


    public Date getExpirationDate() {
        return expirationDate;
    }


    public List<Role> getRoles() {
        return roles;
    }


    public Customer getCustomer() {
        return customer;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    public Account setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public Account setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Account setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
}
