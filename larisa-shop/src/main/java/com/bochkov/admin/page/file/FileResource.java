package com.bochkov.admin.page.file;

import larisa.entity.File;
import larisa.entity.IGetFile;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.time.Time;

import javax.servlet.http.HttpServletResponse;

public class FileResource extends AbstractResource {
    IModel<? extends File> fileModel;

    public FileResource(IModel<? extends File> fileModel) {
        this.fileModel = fileModel;
    }

    public static <T extends IGetFile>FileResource of(T getFile) {
        return new FileResource(new PropertyModel<>(getFile, "file"));
    }

    public static FileResource ofProperty(IModel<? extends IGetFile> getFileModel) {
        return new FileResource(new PropertyModel<>(getFileModel, "file"));
    }

    public static FileResource of(IModel<? extends File> fileModel) {
        return new FileResource(fileModel);
    }

   /* public static FileResource of(IModel<? extends IGetFile> fileModel) {
        return new FileResource(new PropertyModel<>(fileModel,"file"));
    }*/

    @Override
    protected ResourceResponse newResourceResponse(final Attributes attributes) {
        final ResourceResponse response = new ResourceResponse();
        File file = fileModel.getObject();
        if (file.getLastModifiedDate() != null) {
            response.setLastModified(Time.millis(file.getLastModifiedDate().getMillis()));
        } else {
            response.setLastModified(Time.now());
        }

        if (response.dataNeedsToBeWritten(attributes)) {
            response.setContentDisposition(ContentDisposition.INLINE);

            final byte[] imageData = file.getData();
            if (imageData == null) {
                response.setError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.setContentType(file.getType());
                response.setWriteCallback(new WriteCallback() {
                    @Override
                    public void writeData(final Attributes attributes) {
                        attributes.getResponse().write(imageData);
                    }
                });
            }
        }

        return response;
    }
}
