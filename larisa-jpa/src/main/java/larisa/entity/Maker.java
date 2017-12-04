package larisa.entity;

import org.entity3.IGetNamed;

import javax.persistence.*;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "maker")
public class Maker extends DefaultEntity<Integer> implements IGetFile, IGetNamed {
    @Id
    @Column(name = "id_maker")
    @GeneratedValue(generator = "maker")
    @TableGenerator(name = "maker", initialValue = 100, allocationSize = 1)
    Integer id;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    @Column(name = "note")
    String note;

    @OneToOne(fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinTable(name = "maker_file", joinColumns = @JoinColumn(name = "id_maker"), inverseJoinColumns = @JoinColumn(name = "id_file"))
    File file;

    @Override
    public Integer getId() {
        return id;
    }

    public Maker(String name) {
        this.name = name;
    }

    public Maker() {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File files) {
        this.file = files;
    }

    @Override
    public String toString() {
        return name;
    }
}
