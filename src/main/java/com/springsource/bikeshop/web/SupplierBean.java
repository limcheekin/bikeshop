package com.springsource.bikeshop.web;

import com.springsource.bikeshop.domain.Supplier;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@RooSerializable
@RooJsfManagedBean(entity = Supplier.class, beanName = "supplierBean")
public class SupplierBean {
}
