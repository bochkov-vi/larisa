package com.bochkov.admin.component.button;

import org.apache.wicket.Component;
import org.apache.wicket.util.io.IClusterable;

public interface ComponentCreator extends IClusterable {
    Component create(String id);
}
