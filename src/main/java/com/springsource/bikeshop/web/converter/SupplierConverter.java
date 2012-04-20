package com.springsource.bikeshop.web.converter;

import com.springsource.bikeshop.domain.Supplier;
import com.springsource.bikeshop.domain.SupplierRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@FacesConverter("com.springsource.bikeshop.web.converter.SupplierConverter")
@Configurable
@RooJsfConverter(entity = Supplier.class)
public class SupplierConverter implements Converter {

	@Autowired
    SupplierRepository supplierRepository;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return supplierRepository.findOne(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Supplier ? ((Supplier) value).getId().toString() : "";
    }
}
