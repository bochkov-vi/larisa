package com.bochkov.admin.page;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

@MountPath("admin")
public class StartPage extends BasePage<Void> {

    /**
     * Construct.
     *
     * @param parameters current backPage parameters
     */
    public StartPage(PageParameters parameters) {
        super(parameters);
    }
}
