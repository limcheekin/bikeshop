package com.springsource.bikeshop.domain;

import com.springsource.bikeshop.reference.ProductType;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@RooDataOnDemand(entity = Product.class)
public class ProductDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Product> data;

	@Autowired
    private SupplierDataOnDemand supplierDataOnDemand;

	@Autowired
    ProductRepository productRepository;

	public Product getNewTransientProduct(int index) {
        Product obj = new Product();
        setDescription(obj, index);
        setImage(obj, index);
        setName(obj, index);
        setProductType(obj, index);
        setReleaseDate(obj, index);
        setSupplier(obj, index);
        setWeight(obj, index);
        return obj;
    }

	public void setDescription(Product obj, int index) {
        String description = "description_" + index;
        if (description.length() > 250) {
            description = description.substring(0, 250);
        }
        obj.setDescription(description);
    }

	public void setImage(Product obj, int index) {
        byte[] image = String.valueOf(index).getBytes();
        obj.setImage(image);
    }

	public void setName(Product obj, int index) {
        String name = "name_" + index;
        if (name.length() > 25) {
            name = name.substring(0, 25);
        }
        obj.setName(name);
    }

	public void setProductType(Product obj, int index) {
        ProductType productType = ProductType.class.getEnumConstants()[0];
        obj.setProductType(productType);
    }

	public void setReleaseDate(Product obj, int index) {
        Date releaseDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setReleaseDate(releaseDate);
    }

	public void setSupplier(Product obj, int index) {
        Supplier supplier = supplierDataOnDemand.getRandomSupplier();
        obj.setSupplier(supplier);
    }

	public void setWeight(Product obj, int index) {
        BigDecimal weight = BigDecimal.valueOf(index);
        if (weight.compareTo(new BigDecimal("0.0")) == -1 || weight.compareTo(new BigDecimal("9.99")) == 1) {
            weight = new BigDecimal("9.99");
        }
        obj.setWeight(weight);
    }

	public Product getSpecificProduct(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Product obj = data.get(index);
        Long id = obj.getId();
        return productRepository.findOne(id);
    }

	public Product getRandomProduct() {
        init();
        Product obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return productRepository.findOne(id);
    }

	public boolean modifyProduct(Product obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = productRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Product' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Product>();
        for (int i = 0; i < 10; i++) {
            Product obj = getNewTransientProduct(i);
            try {
                productRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            productRepository.flush();
            data.add(obj);
        }
    }
}
