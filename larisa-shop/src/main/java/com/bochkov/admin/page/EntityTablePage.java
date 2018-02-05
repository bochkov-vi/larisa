package com.bochkov.admin.page;

import com.bochkov.admin.component.FileImage;
import com.bochkov.admin.component.LabeledLink;
import com.bochkov.admin.component.button.ButtonCreator;
import com.bochkov.admin.component.button.ToolbarPanel;
import com.bochkov.admin.component.selectiontable.SelectRowDataTable;
import com.bochkov.model.EntityDataProvider;
import com.google.common.collect.ImmutableList;
import larisa.entity.IGetFile;
import larisa.entity.ProductType;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.*;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.java.util.function.serializable.SerializableSupplier;
import org.entity3.repository.CustomRepository;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class EntityTablePage<T extends Persistable<? extends Serializable>> extends EntityPage<T> {
    protected Form toolbarForm = new Form("toolbar-form");

    protected ISortableDataProvider<T, String> sortableDataProvider = new EntityDataProvider<T>() {
        @Override
        public IModel<T> model(T entity) {
            return Model.of(entity);
        }

        @Override
        public JpaSpecificationExecutor<T> getRepository() {
            return EntityTablePage.this.getRepository();
        }

        @Override
        public Specification<T> createSpecification() {
            return EntityTablePage.this.createSpecification();
        }
    };

    protected SelectRowDataTable<T, ?> table;


    public EntityTablePage() {
    }

    public EntityTablePage(IModel<T> model) {
        super(model);
    }

    public EntityTablePage(PageParameters parameters) {
        super(parameters);
    }

    protected static <E extends IGetFile> IColumn<E, String> createImageColumn(IModel<String> header) {
        return new PropertyColumn<E, String>(header, "file", "file") {
            @Override
            public void populateItem(Item<ICellPopulator<E>> item, String componentId, IModel<E> rowModel) {
                FileImage image = new FileImage(componentId, new PropertyModel<>(rowModel, "file"));
                item.add(image);
            }

            @Override
            public String getCssClass() {
                return "visible-lg visible-sm visible-md";
            }
        };

    }

    protected static <E extends Persistable> IColumn<E, String> createIdColumn(IModel<String> header) {
        return new PropertyColumn<E, String>(header, "id", "id") {
            @Override
            public String getCssClass() {
                return "visible-lg visible-sm visible-md";
            }
        };

    }

    protected static <T extends Serializable> IColumn<T, String> createProductTypeColumn(IModel<String> header, String property, Page backPage, IEditPageCreator editPageCreator) {
        return new PropertyColumn<T, String>(header, property, property) {
            @Override
            public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel) {
                LabeledLink link = createProductTypeLink(componentId, (IModel<ProductType>) getDataModel(rowModel),new PropertyModel<>(getDataModel(rowModel),property), editPageCreator, backPage);
                item.add(link);
            }
        };
    }

    protected static LabeledLink<ProductType> createProductTypeLink(String id, IModel<ProductType> model,IModel<String> linkLabelModel, IEditPageCreator editPageCreator, Page backPage) {
        return new LabeledLink<ProductType>(id, model,linkLabelModel) {
            @Override
            public void onClick(Optional<AjaxRequestTarget> target) {
                editPageCreator.createEditPage(model).setBackNavigateAction((circle, model1) -> circle.setResponsePage(backPage));
            }
        };
    }

    @Override
    protected void onInitialize() {

        add(toolbarForm);
        toolbarForm.add(createToolbar("toolbar", toolbarForm));
        add(table = createTable("table"));
        table.setOutputMarkupId(true);
        super.onInitialize();
    }

    public ISortableDataProvider getSortableDataProvider() {
        return sortableDataProvider;
    }

    public EntityTablePage<T> setSortableDataProvider(ISortableDataProvider sortableDataProvider) {
        this.sortableDataProvider = sortableDataProvider;
        return this;
    }

    public SelectRowDataTable<T, ?> createTable(String id) {
        SelectRowDataTable<T, ?> table = new SelectRowDataTable<T, String>(id, createColumns(), sortableDataProvider, 50) {
            @Override
            public void onSelect(AjaxRequestTarget target, IModel<T> entity) {
                EntityTablePage.this.onSelect(target, entity);
            }

        };
        table.hover();
        return table;
    }

    public abstract List<? extends IColumn<T, String>> createColumns();

    public void onSelect(AjaxRequestTarget target, IModel<T> entityModel) {
        target.add(toolbarForm);
    }

    @Override
    protected abstract CustomRepository<T, ?> getRepository();

    @Override
    public ToolbarPanel createToolbar(String id, Form form, ButtonCreator... buttonCreators) {
        ToolbarPanel toolbarPanel = super.createToolbar(id, form, buttonCreators);
        return toolbarPanel;
    }

    @Override
    public IModel<Collection<T>> getDeletedModel() {
        return LambdaModel.of((SerializableSupplier<Collection<T>>) () -> Optional.ofNullable(getSelection()).orElse(Model.of((Collection<T>) ImmutableList.<T>of())).orElse(ImmutableList.of()).getObject());
    }

    @Override
    public boolean isDeleteEnabled() {
        return super.isDeleteEnabled() && getDeletedModel().map(c -> !c.isEmpty()).getObject();
    }

    @Override
    public void onDelete(Optional<AjaxRequestTarget> target, IModel<Collection<T>> entityModel) {
        super.onDelete(target, entityModel);
        target.ifPresent(t -> t.add(table));
    }

    public boolean isSelectable() {
        return table.isSelectable();
    }

    public EntityTablePage<T> setSelectable(boolean selectable) {
        table.setSelectable(selectable);
        return this;
    }

    public IModel<Collection<T>> getSelection() {
        return table.getSelection();
    }

    public EntityTablePage<T> setSelection(IModel<Collection<T>> selection) {
        table.setSelection(selection);
        return this;
    }

    public Specification<T> createSpecification() {
        return null;
    }
}
