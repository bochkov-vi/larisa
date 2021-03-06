package larisa.entity;


import javax.persistence.*;
import java.util.List;

/**
 * Created by bochkov
 */
@Entity
@Table(name = "customer")
public class Customer extends DefaultEntity<Integer> implements IGetFiles {
    @Id
    @GeneratedValue
    @Column(name = "id_customer")
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "patronymic")
    String patronymic;

    @Column(name = "family")
    String family;

    @Column(name = "address")
    String address;

    @Column(name = "phone")
    String phone;

    @Column(name = "email")
    String email;

    @Column(name = "phones")
    @ElementCollection
    @CollectionTable(name = "customer_phone")
    List<String> phones;

    @Column(name = "email")
    @ElementCollection
    @CollectionTable(name = "customer_email")
    List<String> emails;

    @OneToMany
    @JoinTable(name = "customer_file", joinColumns = @JoinColumn(name = "id_customer"), inverseJoinColumns = @JoinColumn(name = "id_file"))
    List<File> files;

    @OneToMany(mappedBy = "customer")
    List<CustomerOrder> customerOrders;

    @OneToOne(mappedBy = "customer", optional = false)
    private Account account;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    @Override
    public List<File> getFiles() {
        return files;
    }

    @Override
    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

}
