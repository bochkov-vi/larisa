package com.bochkov.admin.page.maker;

import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.page.EntityEditPage;
import com.bochkov.admin.page.IEditPageCreator;
import larisa.entity.Maker;
import larisa.repository.FileRepository;
import larisa.repository.MakerRepository;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Optional;

@MountPath("maker")
public class MakerEditPage extends EntityEditPage<Maker, Integer> {

    @Inject
    MakerRepository repository;

    @Inject
    FileRepository fileRepository;

    public MakerEditPage() {
    }

    public MakerEditPage(IModel<Maker> model) {
        super(model);
    }

    public MakerEditPage(PageParameters parameters) {
        super(parameters);
    }

    protected static LabeledLink<Maker> createMakerLink(String id, IModel<Maker> model, Page backPage) {
        return createMakerLink(id, model, LambdaModel.of(model, Maker::getName), MakerEditPage::new, backPage);
    }

    protected static LabeledLink<Maker> createMakerLink(String id, IModel<Maker> model, IModel<String> linkLabelModel, IEditPageCreator editPageCreator, Page backPage) {
        return new LabeledLink<Maker>(id, model, linkLabelModel) {
            @Override
            public void onClick(Optional<AjaxRequestTarget> target) {
                setResponsePage(editPageCreator.createEditPage(model).setBackNavigateAction((circle, model1) -> circle.setResponsePage(backPage)));
            }
        };
    }

    public static <T extends Serializable> IColumn<T, String> createMakerColumn(String property,
                                                                                Page backPage,
                                                                                IEditPageCreator editPageCreator) {
        return createMakerColumn(new ResourceModel("maker.id"), property, backPage, editPageCreator);
    }

    public static <T extends Serializable> IColumn<T, String> createMakerColumn(IModel<String> header,
                                                                                String property,
                                                                                Page backPage,
                                                                                IEditPageCreator editPageCreator) {
        return new PropertyColumn<T, String>(header, property, property) {
            @Override
            public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel) {
                LabeledLink link = createMakerLink(componentId, getDataModel(rowModel), LambdaModel.of(getDataModel(rowModel), Maker::getName), editPageCreator, backPage);
                item.add(link);
            }

            @Override
            public IModel<Maker> getDataModel(IModel<T> rowModel) {
                return (IModel<Maker>) super.getDataModel(rowModel);
            }
        };
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        MakerInput input = new MakerInput("input", getModel());
        form.add(input);
    }

    @Override
    public MakerEditPage createEditPage(IModel<Maker> entityModel) {
        return new MakerEditPage(entityModel);
    }

    protected void onSubmit(AjaxRequestTarget target) {
        save();
        target.add(feedback);

    }

    public void save() {
        repository.save(getModelObject());
        success(new StringResourceModel("maker.saved", this, getModel()).getObject());
    }

    @Override
    protected MakerRepository getRepository() {
        return repository;
    }

}
