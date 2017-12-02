package com.bochkov;

import com.bochkov.shop.page.HomePage;
import larisa.entity.File;
import larisa.repository.FileRepository;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.ContextRelativeResourceReference;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.settings.ResourceSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import javax.inject.Inject;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 */

public class WicketApplication extends WebApplication {
    public static String DB_IMAGE_KEY = "dbimage";

    @Inject
    FileRepository fileRepository;

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */


    @Override
    public void init() {
        super.init();
        ResourceSettings resourceSettings = getResourceSettings();
        resourceSettings.getResourceFinders().add(new WebApplicationPath(getServletContext(), "wicket"));
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getMarkupSettings().setStripWicketTags(true);
        getJavaScriptLibrarySettings().setJQueryReference(
                new ContextRelativeResourceReference("js/jquery.js")
        );
        getSharedResources().add(DB_IMAGE_KEY, new DynamicImageResource("JPEG") {
            @Override
            protected byte[] getImageData(Attributes attributes) {
                Integer id = attributes.getParameters().get("id").toOptionalInteger();
                byte[] data = new byte[0];
                if (id != null) {
                    File file = fileRepository.findOne(id);
                    data = file.getData();
                }
                return data;
            }
        });
        mountResource("db", getSharedResources().get(DB_IMAGE_KEY));
    }

}
