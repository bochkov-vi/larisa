package larisa.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "maker")
public class Maker extends AbstractEntity<Integer> implements IGetFiles {
    @Id
    @Column(name = "id_maker")
    @GeneratedValue(generator = "maker")
    @TableGenerator(name = "maker", initialValue = 1, allocationSize = 1)
    Integer id;

    @Column(name = "name", unique = true,columnDefinition = "VARCHAR_IGNORECASE NOT NULL")
    String name;

    @Column(name = "note",columnDefinition = "VARCHAR_IGNORECASE")
    String note;

    @ManyToMany
    @JoinTable(name = "maker_file", joinColumns = @JoinColumn(name = "id_maker"), inverseJoinColumns = @JoinColumn(name = "id_file"))
    List<File> files;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
