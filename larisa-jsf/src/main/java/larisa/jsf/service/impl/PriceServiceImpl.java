package larisa.jsf.service.impl;

import larisa.entity.Price;
import larisa.entity.ProductType;
import larisa.jsf.service.PriceService;
import larisa.repository.PriceRepository;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

;


/**
 * Created by home on 25.02.17.
 */
@Service("priceService")
public class PriceServiceImpl extends DefaultServiceImpl<Price, Integer> implements PriceService {
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

    @Override
    public Price createNewInstance(ProductType productType) {
        Price price = createNewInstance();
        price.setProductType(productType);
        return price;
    }

    @Override
    public Price createNewInstance() {
        Price price = super.createNewInstance();
        price.setDateFrom(new LocalDate(DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone())));
        return price;
    }

    @Override
    public Price save(Price price) {
        Price prev = findPrev(price);
        Price next = findNext(price);
        if (prev != null) {
            prev.setDateTo(price.getDateFrom());
            getRepository().save(prev);
        }
        if (next != null) {
            price.setDateTo(next.getDateFrom());
        }
        return super.save(price);
    }

    @Override
    public void delete(Price price) {
        super.delete(price);
        Price prev = findPrev(price);
        Price next = findNext(price);
        if (prev != null) {
            prev.setDateTo(null);
            if (next != null) {
                prev.setDateTo(next.getDateFrom());
            }
            getRepository().save(prev);
        }
    }


    Price findPrev(Price price) {
        if (price.getDateFrom() != null && price.getProductType() != null) {
            return getRepository().findOne((root, query, cb) -> cb.and(cb.lessThan(root.get("dateFrom"), price.getDateFrom()), cb.equal(root.get("productType").get("id"), price.getProductType().getId())), new Sort(Sort.Direction.DESC, "dateFrom"));
        }
        return null;
    }

    Price findNext(Price price) {
        if (price.getDateFrom() != null && price.getProductType() != null) {
            return getRepository().findOne((root, query, cb) -> cb.and(cb.greaterThan(root.get("dateFrom"), price.getDateFrom()), cb.equal(root.get("productType").get("id"), price.getProductType().getId())), new Sort(Sort.Direction.ASC, "dateFrom"));
        }
        return null;
    }


}
