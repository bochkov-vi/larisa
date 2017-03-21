package larisa.jsf.file;

import com.google.common.collect.Iterables;
import larisa.entity.AbstractEntity;
import larisa.entity.File;
import larisa.entity.IGetFiles;
import larisa.jsf.DefaultEditorBean;
import larisa.jsf.File2StreamedContent;
import larisa.repository.FileRepository;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

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
public class FileEditor<T extends AbstractEntity<ID> & IGetFiles, ID extends Serializable> extends DefaultEditorBean<T, ID> {

    @Autowired
    FileRepository fileRepository;

    public List<StreamedContent> getImages() {
        return File2StreamedContent.get().getImages(getSelected());
    }

    public StreamedContent getImage() {
        return Iterables.getFirst(File2StreamedContent.get().getImages(getSelected()), null);
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        File f = create(file);
        getSelected().getFiles().add(fileRepository.save(f));
    }

    File create(UploadedFile uf) {
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
}
