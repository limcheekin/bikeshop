package com.springsource.bikeshop.domain;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Product.class)
public interface ProductRepository {
}
