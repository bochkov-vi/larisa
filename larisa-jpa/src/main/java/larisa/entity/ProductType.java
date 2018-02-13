package larisa.entity;


import com.bochkov.hierarchical.IHierarchical;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by bochkov
 */
@Entity
@Table(name = "product_type")
public class ProductType extends AbstractAuditableEntity<Integer> implements IGetNamed, IHierarchical<Integer, ProductType>, IGetFile, INotable, INamable {
    @Id
    @GeneratedValue(generator = "product_type")
    @TableGenerator(name = "product_type", initialValue = 100, allocationSize = 1)
    @Column(name = "id_product_type")
    Integer id;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    @Column(name = "volume_note", length = 45)
    String volumeNote;

    @Temporal(TemporalType.DATE)
    @Column(name = "sertificated")
    Date sertificated;

    @Column(name = "note")
    String note;

    @ManyToOne
    @JoinColumn(name = "id_maker")
    Maker maker;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "product_type_file", joinColumns = @JoinColumn(name = "id_product_type"), inverseJoinColumns = @JoinColumn(name = "id_file"))
    File file;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_type_p", joinColumns = @JoinColumn(name = "id_product_type", referencedColumnName = "id_product_type"), inverseJoinColumns = @JoinColumn(name = "id_product_type_parent", referencedColumnName = "id_product_type"))
    List<ProductType> childs;

    @ManyToMany(mappedBy = "childs", fetch = FetchType.EAGER)
    List<ProductType> parents;


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVolumeNote() {
        return volumeNote;
    }

    public void setVolumeNote(String volumeNote) {
        this.volumeNote = volumeNote;
    }

    @Override
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public List<ProductType> getChilds() {
        return childs;
    }

    public void setChilds(List<ProductType> childs) {
        this.childs = childs;
    }

    @Override
    public List<ProductType> getParents() {
        return parents;
    }

    public void setParents(List<ProductType> parents) {
        this.parents = parents;
    }

    public Maker getMaker() {
        return maker;
    }

    public void setMaker(Maker maker) {
        this.maker = maker;
    }

    @Override
    public String toString() {
        return name;
    }

    public Date getSertificated() {
        return sertificated;
    }

    public void setSertificated(Date sertificated) {
        this.sertificated = sertificated;
    }

    public String getNote() {
        return note;
    }

    public ProductType setNote(String note) {
        this.note = note;
        return this;
    }
}
