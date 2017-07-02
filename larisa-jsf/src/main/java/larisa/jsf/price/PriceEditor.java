package larisa.jsf.price;

import larisa.entity.Price;
import larisa.entity.ProductType;
import larisa.jsf.ContextHolder;
import larisa.jsf.DefaultJsfService;
import larisa.jsf.productType.ProductTypeEditor;
import org.joda.time.LocalDate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Created by home on 24.02.17.
 */
@Component
@Scope("view")
public class PriceEditor extends DefaultJsfService<Price, Integer> {


    public PriceEditor() {

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
            next.setDateTo(next.getDateFrom());
        } else {
            next.setDateTo(null);
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

    @Override
    public Price createNewInstance() {
        ProductType productType = ContextHolder.context.getBean(ProductTypeEditor.class).entityFromRequest();
        Price price = super.createNewInstance();
        price.setDateFrom(new LocalDate());
        price.setProductType(productType);
        Price template = entityFromRequest("clone");
        if (template != null) {
            BeanUtils.copyProperties(template, price, "id", "createdDate");
        }
        return price;
    }
}
