package com.bochkov.admin.component.button;

import com.bochkov.admin.page.IDetailed;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public abstract class DeleteButtonWithModal<T extends Serializable> extends DeleteButton implements IDetailed<T> {

    Modal<T> deleteModal;

    public DeleteButtonWithModal(String id, Form<?> form, Modal<T> deleteModal) {
        super(id, form);
        this.deleteModal = deleteModal;
    }

    public DeleteButtonWithModal(String id, Form<?> form) {
        super(id, form);
        deleteModal = createDeleteDialog(id);
    }

    public abstract Modal createDeleteDialog(String id);

    @Override
    protected void onSubmit(Optional<AjaxRequestTarget> target) {
        target.ifPresent(t -> deleteModal.show(t));
    }


    public abstract void onDelete(Optional<AjaxRequestTarget> target, IModel<Collection<T>> entityModel);
}
