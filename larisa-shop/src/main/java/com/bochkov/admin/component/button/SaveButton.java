package com.bochkov.admin.component.button;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.markup.html.form.Form;

public class SaveButton extends FontAwesomeButton {
    public SaveButton(String id, Form<?> form) {
        super(id, form);
        setIconType(FontAwesomeIconType.save);
    }
}
