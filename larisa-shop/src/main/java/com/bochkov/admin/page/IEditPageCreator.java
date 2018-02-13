package com.bochkov.admin.page;

import larisa.entity.DefaultEntity;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

public interface IEditPageCreator<T extends DefaultEntity<? extends Serializable>> {
    EntityEditPage<T , ? extends Serializable> createEditPage(IModel<T> entityModel);
}
