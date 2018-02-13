package larisa.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "customer_order")
public class CustomerOrder extends DefaultEntity<Integer> {
    @Id
    @Column(name = "id_customer_order")
    @GeneratedValue
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
