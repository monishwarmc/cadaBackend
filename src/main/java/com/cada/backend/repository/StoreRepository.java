package com.cada.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cada.backend.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}

