package com.tenx.ms.storesmanagement.products.converter;


import com.tenx.ms.storesmanagement.products.domain.ProductEntity;
import com.tenx.ms.storesmanagement.products.rest.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public static ProductEntity convertToProductEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setStoreId(productDTO.getStoreId());
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setSku(productDTO.getSku());
        productEntity.setPrice(productDTO.getPrice());
        return productEntity;
    }

    public static ProductDTO convertToProductDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productEntity.getProductId());
        productDTO.setStoreId(productEntity.getStoreId());
        productDTO.setName(productEntity.getName());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setSku(productEntity.getSku());
        productDTO.setPrice(productEntity.getPrice());
        return productDTO;
    }
}
