package com.bochkov;

import com.bochkov.admin.page.StartPage;
import com.google.javascript.jscomp.CompilationLevel;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.RenderJavaScriptToFooterHeaderResponseDecorator;
import de.agilecoders.wicket.core.request.resource.caching.version.Adler32ResourceVersion;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.extensions.javascript.GoogleClosureJavaScriptCompressor;
import de.agilecoders.wicket.extensions.javascript.YuiCssCompressor;
import de.agilecoders.wicket.less.BootstrapLess;
import de.agilecoders.wicket.less.ContextRelativeLessResourceReference;
import larisa.repository.FileRepository;
import org.apache.wicket.ResourceBundles;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.caching.FilenameWithVersionResourceCachingStrategy;
import org.apache.wicket.request.resource.caching.NoOpResourceCachingStrategy;
import org.apache.wicket.request.resource.caching.version.CachingResourceVersion;
import org.apache.wicket.serialize.java.DeflatedJavaSerializer;
import org.apache.wicket.settings.RequestCycleSettings;
import org.apache.wicket.settings.ResourceSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.wicketstuff.wicket.mount.AutoMountWebApplication;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 */

public class WicketApplication extends AutoMountWebApplication {
    public static String DB_IMAGE_KEY = "dbimage";

    @Inject
    FileRepository fileRepository;

    /**
     * @see org.apache.wicket.Application#init()
     */


    public static WicketApplication get() {
        return (WicketApplication) WebApplication.get();
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return StartPage.class;
    }

    @Override
    public void init() {
        super.init();
        ResourceSettings resourceSettings = getResourceSettings();
        resourceSettings.getResourceFinders().add(new WebApplicationPath(getServletContext(), "wicket"));
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getMarkupSettings().setStripWicketTags(true);

        getSharedResources().add(DB_IMAGE_KEY, new DynamicImageResource("JPEG") {
            @Override
            protected byte[] getImageData(Attributes attributes) {
                Integer id = attributes.getParameters().get("id").toOptionalInteger();
                byte[] data = Optional.ofNullable(id).map(i -> fileRepository.findById(i).get()).map(f -> f.getData()).orElse(new byte[0]);
                return data;
            }
        });
        mountResource("db", getSharedResources().get(DB_IMAGE_KEY));
        configureBootstrap();
//        optimizeForWebPerformance();
    }


    /**
     * optimize wicket for a better web performance
     */
    private void optimizeForWebPerformance() {
        if (usesDeploymentConfig()) {
            getResourceSettings().setCachingStrategy(new FilenameWithVersionResourceCachingStrategy(
                    "-v-",
                    new CachingResourceVersion(new Adler32ResourceVersion())
            ));

            getResourceSettings().setJavaScriptCompressor(new GoogleClosureJavaScriptCompressor(CompilationLevel.SIMPLE_OPTIMIZATIONS));
            getResourceSettings().setCssCompressor(new YuiCssCompressor());

            getFrameworkSettings().setSerializer(new DeflatedJavaSerializer(getApplicationKey()));
        } else {
            getResourceSettings().setCachingStrategy(new NoOpResourceCachingStrategy());
        }

        setHeaderResponseDecorator(new RenderJavaScriptToFooterHeaderResponseDecorator());
        getRequestCycleSettings().setRenderStrategy(RequestCycleSettings.RenderStrategy.ONE_PASS_RENDER);
    }

    /**
     * configure all resource bundles (css and js)
     */
    private void configureResourceBundles() {
        ResourceBundles bundles = getResourceBundles();
        /*bundles.addJavaScriptBundle(WicketApplication.class, "core.js",
                (JavaScriptResourceReference) getJavaScriptLibrarySettings().getJQueryReference(),
                (JavaScriptResourceReference) getJavaScriptLibrarySettings().getWicketEventReference(),
                (JavaScriptResourceReference) getJavaScriptLibrarySettings().getWicketAjaxReference(),
                ModernizrJavaScriptReference.instance()
        );

        bundles.addJavaScriptBundle(WicketApplication.class, "bootstrap.js",
                (JavaScriptResourceReference) Bootstrap.getSettings().getJsResourceReference(),
                (JavaScriptResourceReference) PrettifyJavaScriptReference.INSTANCE,
                ApplicationJavaScript.INSTANCE
        );

      *//*  getResourceBundles().addJavaScriptBundle(WicketApplication.class, "bootstrap-extensions.js",
                JQueryUIJavaScriptReference.instance(),
                Html5PlayerJavaScriptReference.instance()
        );*//*

        bundles.addCssBundle(WicketApplication.class, "bootstrap-extensions.css",
                Html5PlayerCssReference.instance(),
                OpenWebIconsCssReference.instance()
        );

        bundles.addCssBundle(WicketApplication.class, "application.css",
                (CssResourceReference) PrettifyCssResourceReference.INSTANCE,
                FixBootstrapStylesCssResourceReference.INSTANCE
        );*/
    }

    /**
     * configures wicket-bootstrap and installs the settings.
     */
    private void configureBootstrap() {
        final IBootstrapSettings settings = new BootstrapSettings();
        settings.setCssResourceReference(new ContextRelativeLessResourceReference("less/bootsrap.less"));
        Bootstrap.builder().withBootstrapSettings(settings).install(this);
        BootstrapLess.install(this);
        /*final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Flatly);

        settings.setJsResourceFilterName("footer-container")
                .setThemeProvider(themeProvider)
                .setActiveThemeProvider(new CookieThemeProvider());

       */
    }

}
