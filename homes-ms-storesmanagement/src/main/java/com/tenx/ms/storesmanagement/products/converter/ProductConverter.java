package com.tenx.ms.storesmanagement.products.converter;


import com.tenx.ms.storesmanagement.products.domain.ProductEntity;
import com.tenx.ms.storesmanagement.products.rest.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public ProductEntity convertToProductEntity(ProductDTO storeDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setStoreId(storeDTO.getStoreId());
        productEntity.setName(storeDTO.getName());
        productEntity.setDescription(storeDTO.getDescription());
        productEntity.setSku(storeDTO.getSku());
        productEntity.setPrice(storeDTO.getPrice());
        return productEntity;
    }

    public ProductDTO convertToProductDTO(ProductEntity storeEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(storeEntity.getProductId());
        productDTO.setStoreId(storeEntity.getStoreId());
        productDTO.setName(storeEntity.getName());
        productDTO.setDescription(storeEntity.getDescription());
        productDTO.setSku(storeEntity.getSku());
        productDTO.setPrice(storeEntity.getPrice());
        return productDTO;
    }
}
