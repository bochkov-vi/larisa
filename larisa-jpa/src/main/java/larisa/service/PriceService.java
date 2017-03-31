package larisa.service;

import larisa.entity.Price;
import larisa.entity.ProductType;
import org.entity3.service.EntityService;

import org.joda.time.LocalDate;

/**
 * Created by home on 24.02.17.
 */
public interface PriceService extends EntityService<Price, Integer> {
    Double price(ProductType productType);

    Double price(ProductType productType, LocalDate localDate);

    Price findOne(ProductType productType);

    Price findOne(ProductType productType, LocalDate localDate);
}
