package com.bochkov.admin.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class LabeledLink extends Panel {

    public LabeledLink(String id, String label) {
        this(id, null, new Model(label), true);
    }

    public LabeledLink(String id, String label, boolean ajaxified) {
        this(id, null, new Model(label), ajaxified);
    }

    public LabeledLink(String id, IModel model, IModel labelModel) {
        this(id, model, labelModel, true);
    }

    public LabeledLink(String id, IModel model, IModel labelModel, boolean ajaxified) {
        super(id);

        Link link;
        if (ajaxified) {
            link = new IndicatingAjaxFallbackLink("linkId", model) {

                @Override
                public void onClick(AjaxRequestTarget target) {
                    LabeledLink.this.onClick(target);
                }
            };
        } else
            link = new Link("linkId", model) {

                @Override
                public void onClick() {
                    LabeledLink.this.onClick(null);
                }
            };
        add(link.add(new Label("labelId", labelModel)));
    }

    public void onClick(AjaxRequestTarget target) {
    }
}