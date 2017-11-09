package larisa.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart extends DefaultEntity<Long> {
    @Id
    @GeneratedValue(generator = "cart")
    @TableGenerator(name = "cart", initialValue = 1, allocationSize = 1)
    Long id;

    @Override
    public Long getId() {
        return id;
    }
}
