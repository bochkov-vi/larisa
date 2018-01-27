package com.bochkov.admin.component.button;

import com.google.common.collect.Sets;
import org.apache.wicket.ClassAttributeModifier;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.Set;

public class ToolbarPanel extends Panel {

    RepeatingView buttonComponents = new RepeatingView("button");

    public ToolbarPanel(String id, ButtonCreator... creators) {
        super(id);
        add(new ClassAttributeModifier() {
            @Override
            protected Set<String> update(Set<String> oldClasses) {
                Set set = Sets.newHashSet(oldClasses);
                set.add("btn-group");
                return set;
            }
        });
        add(buttonComponents);
        add(creators);
    }


    public ToolbarPanel add(ButtonCreator... creators) {
        for (ButtonCreator bc : creators) {
            buttonComponents.add(bc.create(buttonComponents.newChildId()));
        }
        return this;
    }
}
