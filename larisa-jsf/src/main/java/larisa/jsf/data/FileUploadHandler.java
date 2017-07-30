package larisa.jsf.data;

import com.google.common.collect.Iterables;
import larisa.entity.File;
import larisa.entity.IGetFiles;
import larisa.jsf.File2StreamedContent;
import larisa.repository.FileRepository;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by home on 27.02.17.
 */
@Configurable
public class FileUploadHandler<T extends IGetFiles> implements Serializable {

    @Autowired
    transient FileRepository fileRepository;

    T target;

    public FileUploadHandler() {
    }

    public FileUploadHandler(T target) {
        this.target = target;
    }

    public List<StreamedContent> getImages(T selected) {
        return File2StreamedContent.get().getImages(selected);
    }

    public StreamedContent getImage(T selected) {
        return Iterables.getFirst(File2StreamedContent.get().getImages(selected), null);
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        File f = create(file);
        target.getFiles().add(fileRepository.save(f));
    }

    public File create(UploadedFile uf) {
        File f = new File();
        f.setName(uf.getFileName());
        f.setData(uf.getContents());
        f.setType(uf.getContentType());
        ImageIO.getImageReadersByMIMEType(uf.getContentType());
        try (InputStream in = new ByteArrayInputStream(uf.getContents())) {
            BufferedImage img = ImageIO.read(in);
            f.setImageHeight(img.getHeight());
            f.setImageWidth(img.getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }
}
