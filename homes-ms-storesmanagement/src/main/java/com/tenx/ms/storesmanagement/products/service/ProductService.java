package com.tenx.ms.storesmanagement.products.service;

import com.tenx.ms.storesmanagement.products.converter.ProductConverter;
import com.tenx.ms.storesmanagement.products.domain.ProductEntity;
import com.tenx.ms.storesmanagement.products.repository.ProductRepository;
import com.tenx.ms.storesmanagement.products.rest.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional (isolation= Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Optional<ProductDTO> getProductByStoreIdAndById(Long storeId, Long productId) {
        return productRepository.findByStoreIdAndProductId(storeId, productId).map(productEntity -> ProductConverter.convertToProductDTO(productEntity));
    }

    @Transactional (isolation= Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public List<ProductDTO> getProductsByStoreId(Long storeId, Pageable pageable) {
        return productRepository.findAllByStoreId(storeId, pageable).getContent().stream().map(productEntity -> ProductConverter.convertToProductDTO(productEntity)).collect(Collectors.toList());
    }

    @Transactional (isolation= Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Long createProduct(ProductDTO productDTO) {
        ProductEntity productEntity = ProductConverter.convertToProductEntity(productDTO);
        productEntity = productRepository.save(productEntity);
        return productEntity.getProductId();
    }
}


