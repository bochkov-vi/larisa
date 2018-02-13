package larisa.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bochkov
 */

@MappedSuperclass
public abstract class DefaultEntity<ID extends Serializable> extends DefaultIdableEntity<ID> implements Persistable<ID> ,Serializable{
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public DefaultEntity setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
