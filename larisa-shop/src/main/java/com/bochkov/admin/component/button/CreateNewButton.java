package com.bochkov.admin.component.button;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.markup.html.form.Form;

public  class CreateNewButton extends FontAwesomeButton {
    public CreateNewButton(String id, Form<?> form) {
        super(id, form);
        setIconType(FontAwesomeIconType.plus);
    }
}
