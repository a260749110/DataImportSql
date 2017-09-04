package com.sql.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sql.domain.CBigCalculatePo;

public interface CBigCalculateDao extends PagingAndSortingRepository<CBigCalculatePo, Integer> {
}
