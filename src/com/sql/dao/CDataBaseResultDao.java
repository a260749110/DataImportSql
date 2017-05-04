package com.sql.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sql.domain.CDataResultPo;
import com.sql.domain.CDataResultPoPK;

public interface CDataBaseResultDao extends PagingAndSortingRepository<CDataResultPo, CDataResultPoPK> {

//	@Query(value = "select * from c_data_result where `id`=:id and `date`>'2010-01-01 00:00:00'", nativeQuery = true)
//	CDataResultPo findById(@Param("id")long id);
}
