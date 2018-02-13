package larisa.entity;


import javax.persistence.*;

/**
 * Created by home on 23.02.17.
 */
@Entity
@Table(name = "file")
public class File extends AbstractAuditableEntity<Integer> {
    @Id
    @Column(name = "id_file")
    @GeneratedValue(generator = "file")
    @TableGenerator(name = "file", initialValue = 1, allocationSize = 1)
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "type")
    String type;

    @Column(name = "encoding")
    String encoding;

    @Lob
    @Column(name = "data", nullable = false)
    byte[] data;

    @Column(name = "image_with")
    int imageWidth;

    @Column(name = "image_height")
    int imageHeight;

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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

}
