package com.bochkov.admin.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Optional;

public abstract class LabeledLink<T> extends GenericPanel<T> {

    public LabeledLink(String id, String label) {
        this(id, null, new Model(label), true);
    }

    public LabeledLink(String id, String label, boolean ajaxified) {
        this(id, null, new Model(label), ajaxified);
    }

    public LabeledLink(String id, IModel<T> model, IModel labelModel) {
        this(id, model, labelModel, true);
    }

    public LabeledLink(String id, IModel<T> model, IModel labelModel, boolean ajaxified) {
        super(id);

        Link<T> link;
        if (ajaxified) {
            link = new IndicatingAjaxFallbackLink<T>("linkId", model) {
                @Override
                public void onClick(Optional optional) {
                    LabeledLink.this.onClick(optional);
                }
            };
        } else {
            link = new Link<T>("linkId", model) {

                @Override
                public void onClick() {
                    LabeledLink.this.onClick(null);
                }
            };
        }
        add(link.add(new Label("labelId", labelModel)));
    }

    public abstract void onClick(Optional<AjaxRequestTarget> target) ;
}