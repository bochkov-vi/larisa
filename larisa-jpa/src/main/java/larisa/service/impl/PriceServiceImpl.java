package larisa.service.impl;

import larisa.entity.Price;
import larisa.entity.ProductType;
import larisa.repository.PriceRepository;
import larisa.service.PriceService;
import org.entity3.service.impl.EntityServiceImpl;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

;


/**
 * Created by home on 25.02.17.
 */
@Service("priceService")
public class PriceServiceImpl extends EntityServiceImpl<Price, Integer> implements PriceService {
    @Autowired
    PriceRepository repository;


    @Override
    public PriceRepository getRepository() {
        return repository;
    }

    @Override
    public Double price(ProductType productType) {
        Price price = findOne(productType);
        if (price != null) {
            return price.getPrice();
        }
        return null;
    }

    @Override
    public Double price(ProductType productType, LocalDate localDate) {
        Price price = findOne(productType, localDate);
        if (price != null) {
            return price.getPrice();
        }
        return null;
    }

    @Override
    public Price findOne(ProductType productType) {
        return findOne(productType, new LocalDate());
    }

    @Override
    public Price findOne(ProductType productType, LocalDate localDate) {
        return getRepository().findOne((root, query, cb) -> {
            Subquery subquery = query.subquery(Price.class);
            Root subRoot = subquery.from(Price.class);
            subquery.select(cb.max(subRoot.get("dateFrom")));
            subquery.where(cb.and(cb.lessThanOrEqualTo(subRoot.get("dateFrom"), localDate),
                    cb.equal(subRoot.get("productType"), root.get("productType"))));
            return cb.and(cb.equal(root.get("productType"), productType),
                    cb.equal(root.get("dateFrom"), subquery));
        }, null);
    }
}
