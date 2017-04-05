package com.sql.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sql.domain.CDataBasePo;
import com.sql.domain.CDataBasePoPK;

public interface CDataBaseDao extends PagingAndSortingRepository<CDataBasePo, CDataBasePoPK> {
	@Query(value ="SELECT * FROM c_data_base ", nativeQuery = true)
	List<CDataBasePo> findAll();
}
