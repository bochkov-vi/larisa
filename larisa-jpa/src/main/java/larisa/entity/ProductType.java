package larisa.entity;

import org.entity3.IGetNamed;
import org.entity3.IHierarchical;
import org.entity3.column.ColumnPosition;
import org.entity3.converter.JodaLocalDateConverter;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.List;

/**
 * Created by bochkov
 */
@Entity
@Table(name = "product_type")
public class ProductType extends AbstractAuditableEntity<Integer> implements IGetNamed, IHierarchical<Integer, ProductType>, IGetFiles {
    @Id
    @GeneratedValue(generator = "product_type")
    @TableGenerator(name = "product_type", initialValue = 100, allocationSize = 1)
    @Column(name = "id_product_type")
    @ColumnPosition(1)
    Integer id;

    @Column(name = "name", unique = true, nullable = false)
    @ColumnPosition(2)
    String name;

    @Column(name = "volume_note", length = 45)
    @ColumnPosition(3)
    String volumeNote;

    @Temporal(TemporalType.DATE)
    @Convert(converter = JodaLocalDateConverter.class)
    @Column(name = "sertificated", nullable = true)
    @ColumnPosition(4)
    LocalDate sertificated;


    @ManyToOne
    @JoinColumn(name = "id_maker")
    Maker maker;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_type_file", joinColumns = @JoinColumn(name = "id_product_type"), inverseJoinColumns = @JoinColumn(name = "id_file"))
    List<File> files;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_type_p", joinColumns = @JoinColumn(name = "id_product_type", referencedColumnName = "id_product_type"), inverseJoinColumns = @JoinColumn(name = "id_product_type_parent", referencedColumnName = "id_product_type"))
    List<ProductType> childs;

    @ManyToMany(mappedBy = "childs",fetch = FetchType.EAGER)
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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
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

    public LocalDate getSertificated() {
        return sertificated;
    }

    public void setSertificated(LocalDate sertificated) {
        this.sertificated = sertificated;
    }
}
