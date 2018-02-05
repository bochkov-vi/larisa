package com.bochkov.admin.page.productreceipt;

import com.bochkov.admin.component.details.Details;
import com.bochkov.admin.page.IDetailed;
import larisa.entity.ProductReceipt;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;

public interface IProductReceiptDetailed extends IDetailed<ProductReceipt> {
    @Override
    default Component createDetailsPanel(String id, IModel<ProductReceipt> model) {
        Details<ProductReceipt> details = new Details<ProductReceipt>(id, model){
            @Override
            public Component createTitleLabel(IModel<ProductReceipt> model) {
                return new Label("title", LambdaModel.of(model,productReceipt->productReceipt.getDate()));
            }
        };
        return details;
    }
}
