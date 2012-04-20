package com.springsource.bikeshop.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooEquals
public class Supplier {

    @NotNull
    @Size(max = 25)
    private String name;

    @NotNull
    @Size(max = 100)
    private String address;

    private String description;

    @Min(1L)
    @Max(99L)
    private int supplierNumber;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date inceptionDate;

    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "supplier")
    private Set<Product> products = new HashSet<Product>();

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getAddress() {
        return this.address;
    }

	public void setAddress(String address) {
        this.address = address;
    }

	public String getDescription() {
        return this.description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public int getSupplierNumber() {
        return this.supplierNumber;
    }

	public void setSupplierNumber(int supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

	public Date getInceptionDate() {
        return this.inceptionDate;
    }

	public void setInceptionDate(Date inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

	public String getEmail() {
        return this.email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public Set<Product> getProducts() {
        return this.products;
    }

	public void setProducts(Set<Product> products) {
        this.products = products;
    }

	public boolean equals(Object obj) {
        if (!(obj instanceof Supplier)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Supplier rhs = (Supplier) obj;
        return new EqualsBuilder().append(address, rhs.address).append(description, rhs.description).append(email, rhs.email).append(id, rhs.id).append(inceptionDate, rhs.inceptionDate).append(name, rhs.name).append(supplierNumber, rhs.supplierNumber).isEquals();
    }

	public int hashCode() {
        return new HashCodeBuilder().append(address).append(description).append(email).append(id).append(inceptionDate).append(name).append(supplierNumber).toHashCode();
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
