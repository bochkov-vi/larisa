package larisa.entity;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bochkov
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableEntity<ID extends Serializable> extends DefaultEntity<ID> {

    @ManyToOne
    @JoinColumn(name = "editor", nullable = false)
    @LastModifiedBy
    Account lastModifiedBy;

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    @CreatedBy
    Account createdBy;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    Date lastModifiedDate;


    public Account getLastModifiedBy() {
        return lastModifiedBy;
    }

    public AbstractAuditableEntity setLastModifiedBy(Account lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public AbstractAuditableEntity setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public AbstractAuditableEntity setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }
}
