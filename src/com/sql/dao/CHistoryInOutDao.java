package com.sql.dao;

import java.util.Date;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sql.domain.CHistoryInOutPo;

public interface CHistoryInOutDao extends PagingAndSortingRepository<CHistoryInOutPo, Date> {

}
