package larisa.entity;

import org.entity3.converter.JodaLocalDateConverter;
import org.joda.time.LocalDate;

import javax.persistence.*;

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
    @Convert(converter = JodaLocalDateConverter.class)
    @Column(name = "date", nullable = false)
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
