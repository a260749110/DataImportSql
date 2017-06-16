package com.sql.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sql.domain.CHistoryBuySellPo;
import com.sql.domain.CHistoryBuySellPK;

public interface CHistoryBuySellDao extends PagingAndSortingRepository<CHistoryBuySellPo, CHistoryBuySellPK> {

}
