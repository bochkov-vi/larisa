package com.bochkov.admin.page;

import org.apache.wicket.model.IModel;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface IEditPageCreator<T extends Persistable<? extends Serializable>> {
    EntityEditPage<T , ? extends Serializable> createEditPage(IModel<T> entityModel);
}
