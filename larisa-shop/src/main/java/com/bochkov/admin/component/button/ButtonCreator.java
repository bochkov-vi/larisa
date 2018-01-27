package com.bochkov.admin.component.button;

import org.apache.wicket.markup.html.form.Button;

public interface ButtonCreator {
    Button create(String id);
}
