package com.tenx.ms.storesmanagement.stores.converter;


import com.tenx.ms.storesmanagement.stores.domain.StoreEntity;
import com.tenx.ms.storesmanagement.stores.rest.dto.StoreDTO;
import org.springframework.stereotype.Component;

@Component
public abstract class StoreConverter {

    public static StoreEntity convertToStoreEntity(StoreDTO storeDTO) {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setName(storeDTO.getName());
        storeEntity.setAddress(storeDTO.getAddress());
        storeEntity.setPhone(storeDTO.getPhone());
        storeEntity.setNotes(storeDTO.getNotes());
        return storeEntity;
    }

    public static StoreDTO convertToStoreDTO(StoreEntity storeEntity) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(storeEntity.getStoreId());
        storeDTO.setName(storeEntity.getName());
        storeDTO.setAddress(storeEntity.getAddress());
        storeDTO.setPhone(storeEntity.getPhone());
        storeDTO.setNotes(storeEntity.getNotes());
        return storeDTO;
    }
}
