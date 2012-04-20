package com.springsource.bikeshop.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Supplier.class)
public class SupplierDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Supplier> data;

	@Autowired
    SupplierRepository supplierRepository;

	public Supplier getNewTransientSupplier(int index) {
        Supplier obj = new Supplier();
        setAddress(obj, index);
        setDescription(obj, index);
        setEmail(obj, index);
        setInceptionDate(obj, index);
        setName(obj, index);
        setSupplierNumber(obj, index);
        return obj;
    }

	public void setAddress(Supplier obj, int index) {
        String address = "address_" + index;
        if (address.length() > 100) {
            address = address.substring(0, 100);
        }
        obj.setAddress(address);
    }

	public void setDescription(Supplier obj, int index) {
        String description = "description_" + index;
        obj.setDescription(description);
    }

	public void setEmail(Supplier obj, int index) {
        String email = "foo" + index + "@bar.com";
        obj.setEmail(email);
    }

	public void setInceptionDate(Supplier obj, int index) {
        Date inceptionDate = new Date(new Date().getTime() - 10000000L);
        obj.setInceptionDate(inceptionDate);
    }

	public void setName(Supplier obj, int index) {
        String name = "name_" + index;
        if (name.length() > 25) {
            name = name.substring(0, 25);
        }
        obj.setName(name);
    }

	public void setSupplierNumber(Supplier obj, int index) {
        int supplierNumber = index;
        if (supplierNumber < 1 || supplierNumber > 99) {
            supplierNumber = 99;
        }
        obj.setSupplierNumber(supplierNumber);
    }

	public Supplier getSpecificSupplier(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Supplier obj = data.get(index);
        Long id = obj.getId();
        return supplierRepository.findOne(id);
    }

	public Supplier getRandomSupplier() {
        init();
        Supplier obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return supplierRepository.findOne(id);
    }

	public boolean modifySupplier(Supplier obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = supplierRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Supplier' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Supplier>();
        for (int i = 0; i < 10; i++) {
            Supplier obj = getNewTransientSupplier(i);
            try {
                supplierRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            supplierRepository.flush();
            data.add(obj);
        }
    }
}
