// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.springsource.bikeshop.web.converter;

import com.springsource.bikeshop.domain.Product;
import com.springsource.bikeshop.domain.ProductRepository;
import com.springsource.bikeshop.web.converter.ProductConverter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;

privileged aspect ProductConverter_Roo_Converter {
    
    declare parents: ProductConverter implements Converter;
    
    declare @type: ProductConverter: @FacesConverter("com.springsource.bikeshop.web.converter.ProductConverter");
    
    @Autowired
    ProductRepository ProductConverter.productRepository;
    
    public Object ProductConverter.getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return productRepository.findOne(id);
    }
    
    public String ProductConverter.getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Product ? ((Product) value).getId().toString() : "";
    }
    
}
