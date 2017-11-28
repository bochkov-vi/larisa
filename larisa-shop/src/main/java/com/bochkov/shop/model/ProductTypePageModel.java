package com.bochkov.shop.model;

import larisa.entity.ProductType;
import larisa.repository.ProductTypeRepository;

import javax.inject.Inject;

public class ProductTypePageModel extends PageModel<ProductType> {
    @Inject
    transient ProductTypeRepository repository;

    public ProductTypePageModel() {
    }

    @Override
    public ProductTypeRepository getRepository() {
        return repository;
    }
}
