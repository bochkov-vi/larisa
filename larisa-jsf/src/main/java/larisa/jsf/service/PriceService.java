package larisa.jsf.service;

import larisa.entity.Price;
import larisa.entity.ProductType;
import org.joda.time.LocalDate;

/**
 * Created by home on 24.02.17.
 */
public interface PriceService extends DefaultService<Price, Integer> {
    Double price(ProductType productType);

    Double price(ProductType productType, LocalDate localDate);

    Price findOne(ProductType productType);

    Price findOne(ProductType productType, LocalDate localDate);
}
