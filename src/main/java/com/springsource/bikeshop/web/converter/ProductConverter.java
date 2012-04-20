package com.springsource.bikeshop.web.converter;

import com.springsource.bikeshop.domain.Product;
import com.springsource.bikeshop.domain.ProductRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@FacesConverter("com.springsource.bikeshop.web.converter.ProductConverter")
@Configurable
@RooJsfConverter(entity = Product.class)
public class ProductConverter implements Converter {

	@Autowired
    ProductRepository productRepository;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return productRepository.findOne(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Product ? ((Product) value).getId().toString() : "";
    }
}
