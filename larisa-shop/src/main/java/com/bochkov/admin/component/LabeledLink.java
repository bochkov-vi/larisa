package com.bochkov.admin.component;

import com.google.common.collect.Sets;
import org.apache.wicket.ClassAttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.java.util.function.serializable.SerializableConsumer;
import org.wicketstuff.lambda.components.ComponentFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public abstract class LabeledLink<T extends Serializable> extends GenericPanel<T> {

    Collection<String> labelClasses;

    public LabeledLink(String id, String label, boolean ajaxified) {
        this(id, null, Model.of(label), ajaxified);
    }

    public LabeledLink(String id, IModel<T> model, IModel labelModel) {
        this(id, model, labelModel, false);
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
            link = ComponentFactory.link("linkId", lnk -> onClick(Optional.empty()));
        }
        add(link.add(new Label("labelId", labelModel).add(new ClassAttributeModifier() {
            @Override
            protected Set<String> update(Set<String> oldClasses) {
                if(labelClasses!=null){
                    oldClasses.addAll(labelClasses);
                }
                return oldClasses;
            }
        })));
    }

    public LabeledLink(String id, T label) {
        this(id, null, Model.of(label), false);
    }

    public static <T extends Serializable> LabeledLink<T> of(String id, T label, SerializableConsumer<Optional<AjaxRequestTarget>> onClick) {
        return new LabeledLink<T>(id, label) {
            @Override
            public void onClick(Optional<AjaxRequestTarget> target) {
                onClick.accept(target);
            }
        };
    }

    public abstract void onClick(Optional<AjaxRequestTarget> target);

    public Collection<String> getLabelClasses() {
        return labelClasses;
    }

    public void setLabelClasses(Collection<String> labelClasses) {
        this.labelClasses = labelClasses;
    }
    public LabeledLink<T> setLabelClasses(String... labelClass) {
        this.labelClasses = Sets.newHashSet(labelClass);
        return this;
    }
}