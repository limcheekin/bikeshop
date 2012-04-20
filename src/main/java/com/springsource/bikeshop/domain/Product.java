package com.springsource.bikeshop.domain;

import com.springsource.bikeshop.reference.ProductType;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
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
import org.springframework.roo.classpath.operations.jsr303.RooUploadedFile;

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooEquals
public class Product {

    @NotNull
    @Size(max = 25)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull
    @Enumerated
    private ProductType productType;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date releaseDate;

    @DecimalMin("0.0")
    @DecimalMax("9.99")
    private BigDecimal weight;

    @RooUploadedFile(contentType = "image/jpeg")
    @Lob
    private byte[] image;

    @NotNull
    @ManyToOne
    private Supplier supplier;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

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

	public boolean equals(Object obj) {
        if (!(obj instanceof Product)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Product rhs = (Product) obj;
        return new EqualsBuilder().append(description, rhs.description).append(id, rhs.id).append(name, rhs.name).append(productType, rhs.productType).append(releaseDate, rhs.releaseDate).append(supplier, rhs.supplier).append(weight, rhs.weight).isEquals();
    }

	public int hashCode() {
        return new HashCodeBuilder().append(description).append(id).append(name).append(productType).append(releaseDate).append(supplier).append(weight).toHashCode();
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getDescription() {
        return this.description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public ProductType getProductType() {
        return this.productType;
    }

	public void setProductType(ProductType productType) {
        this.productType = productType;
    }

	public Date getReleaseDate() {
        return this.releaseDate;
    }

	public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

	public BigDecimal getWeight() {
        return this.weight;
    }

	public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

	public byte[] getImage() {
        return this.image;
    }

	public void setImage(byte[] image) {
        this.image = image;
    }

	public Supplier getSupplier() {
        return this.supplier;
    }

	public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
