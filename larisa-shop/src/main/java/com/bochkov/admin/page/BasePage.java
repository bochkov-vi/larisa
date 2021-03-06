package com.bochkov.admin.page;

import com.bochkov.admin.page.maker.MakerTablePage;
import com.bochkov.admin.page.price.PriceTablePage;
import com.bochkov.admin.page.product.ProductTablePage;
import com.bochkov.admin.page.productType.ProductTypeTablePage;
import com.bochkov.admin.page.productreceipt.ProductReceiptTablePage;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameAppender;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.HtmlTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.IeEdgeMetaTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.MobileViewportMetaTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.*;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ITheme;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Base wicket-bootstrap {@link org.apache.wicket.Page}
 *
 * @author miha
 */
abstract public class BasePage<T> extends GenericWebPage<T> {
    final protected NotificationPanel feedback = new NotificationPanel("feedback");

    /*protected NavigateAction<T> backNavigateAction;*/

    public BasePage() {
    }

    public BasePage(IModel<T> model) {
        super(model);
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new HtmlTag("html"));
        MobileViewportMetaTag mvt = new MobileViewportMetaTag("viewport");
        mvt.setWidth("device-width");
        mvt.setInitialScale("1");
        add(mvt);
//        add(new OptimizedMobileViewportNoZoomMetaTag("viewport1"));
        add(new IeEdgeMetaTag("ie-edge"));
        add(newNavbar("navbar"));
        add(newNavigation("navigation"));
        add(new Footer("footer"));
        feedback.setOutputMarkupId(true);
        add(feedback);
    }

    /**
     * creates a new {@link Navbar} instance
     *
     * @param markupId The components markup id.
     * @return a new {@link Navbar} instance
     */
    protected Navbar newNavbar(String markupId) {
        Navbar navbar = new Navbar(markupId) {
            @Override
            protected Component newBrandNameLink(String componentId) {
                Component component = super.newBrandNameLink(componentId);
                component.add(new CssClassNameAppender("visible-lg"));
                return component;
            }
        };

        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);

        // show brand name
        navbar.setBrandName(new ResourceModel("title"));

        navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT,
                new NavbarButton<Void>(StartPage.class, new ResourceModel("Overview")).setIconType(GlyphIconType.home),
                new NavbarButton<Void>(MakerTablePage.class, new ResourceModel("maker.title")),
                new NavbarButton<Void>(ProductTypeTablePage.class, new ResourceModel("productType.title")),
                new NavbarButton<Void>(ProductReceiptTablePage.class, new ResourceModel("productReceipts.title")),
                new NavbarButton<Void>(ProductTablePage.class, new ResourceModel("product.title")),
                new NavbarButton<Void>(PriceTablePage.class, new ResourceModel("price.title"))

//                ,newAticlesDropDownButton()
        ));
        //navbar.addComponents(new NavbarText(navbar.newExtraItemId(), "Plain text").position(Navbar.ComponentPosition.RIGHT));

      /*  DropDownButton dropdown = new NavbarDropDownButton(Model.of("Themes")) {
            @Override
            public boolean isActive(Component item) {
                return false;
            }

            @Override
            protected List<AbstractLink> newSubMenuButtons(final String buttonMarkupId) {
                final List<AbstractLink> subMenu = new ArrayList<>();
                subMenu.add(new MenuHeader(Model.of("all available themes:")));
                subMenu.add(new MenuDivider());

                final IBootstrapSettings settings = Bootstrap.getSettings(getApplication());
                final List<ITheme> themes = settings.getThemeProvider().available();

                for (final ITheme theme : themes) {
                    PageParameters params = new PageParameters();
                    params.set("theme", theme.name());

                    subMenu.add(new MenuBookmarkablePageLink<Void>(getPageClass(), params, Model.of(theme.name())));
                }

                return subMenu;
            }
        }.setIconType(GlyphIconType.book);*/

        //navbar.addComponents(new ImmutableNavbarComponent(dropdown, Navbar.ComponentPosition.RIGHT));

        return navbar;
    }

    /**
     * @return new dropdown button for all addons
     */
    private Component newAticlesDropDownButton() {
        return new NavbarDropDownButton(new ResourceModel("Aticles")) {
            /** serialVersionUID. */
            private static final long serialVersionUID = 1L;

            @Override
            protected List<AbstractLink> newSubMenuButtons(String buttonMarkupId) {
                final List<AbstractLink> subMenu = new ArrayList<>();
                subMenu.add(new MenuBookmarkablePageLink<Void>(MakerTablePage.class, new ResourceModel("maker.title")).setIconType(GlyphIconType.refresh));
                subMenu.add(new MenuBookmarkablePageLink<Void>(ProductTypeTablePage.class, new ResourceModel("productType.title")).setIconType(GlyphIconType.refresh));
                subMenu.add(new MenuBookmarkablePageLink<Void>(ProductTablePage.class, new ResourceModel("product.title")).setIconType(GlyphIconType.refresh));
                subMenu.add(new MenuBookmarkablePageLink<Void>(PriceTablePage.class, new ResourceModel("price.title")).setIconType(FontAwesomeIconType.dollar));
                return subMenu;
            }
        }.setIconType(GlyphIconType.thlarge);
    }

    /**
     * sets the theme for the current user.
     *
     * @param pageParameters current backPage parameters
     */
    private void configureTheme(PageParameters pageParameters) {
        StringValue theme = pageParameters.get("theme");

        if (!theme.isEmpty()) {
            IBootstrapSettings settings = Bootstrap.getSettings(getApplication());
            settings.getActiveThemeProvider().setActiveTheme(theme.toString(""));
        }
    }

    protected ITheme activeTheme() {
        IBootstrapSettings settings = Bootstrap.getSettings(getApplication());

        return settings.getActiveThemeProvider().getActiveTheme();
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        configureTheme(getPageParameters());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(FontAwesomeCssReference.instance()));
//        response.render(CssHeaderItem.forReference(FixBootstrapStylesCssResourceReference.INSTANCE));
//        response.render(new FilteredHeaderItem(JavaScriptHeaderItem.forReference(ApplicationJavaScript.INSTANCE), "footer-container"));
//        response.render(RespondJavaScriptReference.headerItem());
//
//        if ("google".equalsIgnoreCase(activeTheme().name())) {
////            response.render(CssHeaderItem.forReference(DocsCssResourceReference.GOOGLE));
//        }

//        if (!getRequest().getRequestParameters().getParameterValue("bootlint").isNull()) {
//            response.render(BootlintHeaderItem.INSTANCE);
//        }
    }

    protected boolean hasNavigation() {
        return false;
    }

    /**
     * creates a new navigation component.
     *
     * @param markupId The component's markup id
     * @return a new navigation component.
     */
    private Component newNavigation(String markupId) {
        WebMarkupContainer navigation = new WebMarkupContainer(markupId);
        navigation.add(new AffixBehavior("200"));
        navigation.setVisible(hasNavigation());

        return navigation;
    }

}

