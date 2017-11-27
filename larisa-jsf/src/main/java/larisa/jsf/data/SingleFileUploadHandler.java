package larisa.jsf.data;

import larisa.entity.File;
import larisa.entity.IGetFile;
import larisa.jsf.File2StreamedContent;
import larisa.repository.FileRepository;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.webflow.engine.RequestControlContext;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by home on 27.02.17.
 */
@Configurable
public class SingleFileUploadHandler<T extends IGetFile> implements Serializable {

    @Autowired
    transient FileRepository fileRepository;

    T target;

    public SingleFileUploadHandler() {
    }

    public SingleFileUploadHandler(T target) {
        this.target = target;
    }

    public StreamedContent getImage(T selected) {
        if (selected != null) {
            return File2StreamedContent.get().apply(selected.getFile());
        }
        return null;
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        File f = create(file);
        f = fileRepository.save(f);
        target.setFile(f);
        RequestContext requestContext = RequestContextHolder.getRequestContext();
        RequestControlContext rec = (RequestControlContext) requestContext;
        rec.handleEvent(new Event(this, "save"));
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
