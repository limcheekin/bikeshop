// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.springsource.bikeshop.domain;

import com.springsource.bikeshop.domain.Product;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect Product_Roo_Equals {
    
    public boolean Product.equals(Object obj) {
        if (!(obj instanceof Product)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Product rhs = (Product) obj;
        return new EqualsBuilder().append(description, rhs.description).append(id, rhs.id).append(name, rhs.name).append(productType, rhs.productType).append(releaseDate, rhs.releaseDate).append(supplier, rhs.supplier).append(weight, rhs.weight).isEquals();
    }
    
    public int Product.hashCode() {
        return new HashCodeBuilder().append(description).append(id).append(name).append(productType).append(releaseDate).append(supplier).append(weight).toHashCode();
    }
    
}