package com.bochkov.admin.component.button;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.markup.html.form.Form;

public  class CloneButton extends FontAwesomeButton {
    public CloneButton(String id, Form<?> form) {
        super(id, form);
        setIconType(FontAwesomeIconType.clone);
    }
}
