package com.sql.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sql.domain.CDataBasePo;
import com.sql.domain.CDataBasePoPK;

public interface CDataBaseDao extends PagingAndSortingRepository<CDataBasePo, CDataBasePoPK> {
	@Query(value ="SELECT * FROM c_data_base ", nativeQuery = true)
	List<CDataBasePo> findAll();
	@Query(value = "select * from c_data_base where `id`=:id and `date`>'2006-01-01 00:00:00' order by `date` asc", nativeQuery = true)
	List<CDataBasePo> findById(@Param("id")long id);
	@Query(value ="SELECT `id` FROM c_data_base GROUP BY `id` ", nativeQuery = true)
	List<Long> getAllId();
}
