package com.springsource.bikeshop.web;

import com.springsource.bikeshop.domain.Product;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@RooSerializable
@RooJsfManagedBean(entity = Product.class, beanName = "productBean")
public class ProductBean {
}
