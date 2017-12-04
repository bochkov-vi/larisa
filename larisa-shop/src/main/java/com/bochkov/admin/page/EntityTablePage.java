package com.bochkov.admin.page;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Toolbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class EntityTablePage<T> extends TitledPage<T> {
    public EntityTablePage() {
    }

    public EntityTablePage(IModel model) {
        super(model);
    }

    public EntityTablePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(createToolbar("toolbar"));
    }

    public Toolbar createToolbar(String componentId) {
        Toolbar toolbar = new Toolbar(componentId);
        RepeatingView view = new RepeatingView("button");
        toolbar.add(view);
        BootstrapButton btnCreateNew = new BootstrapButton(view.newChildId(), Buttons.Type.Default) {
            @Override
            public void onSubmit() {
                onSubmitCreateNew();
            }

            @Override
            public void onError() {
                onErrorCreateNew();
            }
        }.setIconType(GlyphIconType.plus);
        view.add(btnCreateNew);
        BootstrapButton btnCancel = new BootstrapButton(view.newChildId(), Buttons.Type.Default) {
            @Override
            public void onSubmit() {
                onSubmitCancel();
            }

            @Override
            public void onError() {
                onErrorCancel();
            }
        }.setIconType(GlyphIconType.bancircle);
        view.add(btnCancel);
        return toolbar;
    }

    public void onSubmitCreateNew() {

    }

    public void onErrorCreateNew() {

    }

    public void onSubmitCancel() {

    }

    public void onErrorCancel() {

    }

}
