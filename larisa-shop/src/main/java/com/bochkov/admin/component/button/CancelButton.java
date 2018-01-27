package com.bochkov.admin.component.button;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.markup.html.form.Form;

public class CancelButton extends FontAwesomeButton {
    public CancelButton(String id, Form<?> form) {
        super(id, form);
        setIconType(FontAwesomeIconType.reply);
        setDefaultFormProcessing(false);
    }

}
