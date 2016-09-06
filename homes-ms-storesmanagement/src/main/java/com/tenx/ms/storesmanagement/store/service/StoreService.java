package com.tenx.ms.storesmanagement.store.service;

import com.tenx.ms.storesmanagement.store.converter.StoreConverter;
import com.tenx.ms.storesmanagement.store.domain.StoreEntity;
import com.tenx.ms.storesmanagement.store.repository.StoreRepository;
import com.tenx.ms.storesmanagement.store.rest.dto.StoreDTO;
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
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Transactional (isolation= Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Optional<StoreDTO> getStoreById(Long storeId) {
        return storeRepository.findOneByStoreId(storeId).map(storeEntity -> StoreConverter.convertToStoreDTO(storeEntity));
    }

    @Transactional (isolation= Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public List<StoreDTO> getStores(Pageable pageable) {
        return storeRepository.findAll(pageable).getContent().stream().map(storeEntity -> StoreConverter.convertToStoreDTO(storeEntity)).collect(Collectors.toList());
    }

    @Transactional (isolation= Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Long createStore(StoreDTO storeDTO) {
        StoreEntity storeEntity = StoreConverter.convertToStoreEntity(storeDTO);
        storeEntity = storeRepository.save(storeEntity);
        return storeEntity.getStoreId();
    }
}

