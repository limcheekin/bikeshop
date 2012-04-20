package com.springsource.bikeshop.domain;

import com.springsource.bikeshop.reference.ProductType;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.classpath.operations.jsr303.RooUploadedFile;

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
}
