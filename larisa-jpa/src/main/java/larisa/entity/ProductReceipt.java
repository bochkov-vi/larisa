package larisa.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "product_receipt")
public class ProductReceipt extends AbstractAuditableEntity<Integer> implements IGetFiles, INotable {
    @Id
    @GeneratedValue(generator = "product_receipt")
    @TableGenerator(name = "product_receipt", initialValue = 100, allocationSize = 1)
    @Column(name = "id_product_receipt")
    Integer id;

    @Column(name = "date", nullable = true)
    @Temporal(TemporalType.DATE)
    Date date;

    @Column(name = "receipt_type", nullable = false, columnDefinition = "VARCHAR_IGNORECASE(255) NOT NULL")
    String receiptType;

    @OneToMany(orphanRemoval = true, mappedBy = "productReceipt")
    List<Product> products;

    @ManyToMany
    @JoinTable(name = "product_receipt_files", joinColumns = @JoinColumn(name = "id_product_receipt"), inverseJoinColumns = @JoinColumn(name = "id_file"))
    List<File> files;

    @Column(name = "note", columnDefinition = "VARCHAR_IGNORECASE(255) DEFAULT NULL")
    String note;

    @Column(name = "seller", columnDefinition = "VARCHAR_IGNORECASE(255) DEFAULT NULL")
    String seller;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public ProductReceipt setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public List<File> getFiles() {
        return files;
    }

    @Override
    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getSeller() {
        return seller;
    }

    public ProductReceipt setSeller(String seller) {
        this.seller = seller;
        return this;
    }
}
