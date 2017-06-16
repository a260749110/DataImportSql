package com.sql.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sql.domain.CBuySellPo;

public interface CBuySellDao extends PagingAndSortingRepository<CBuySellPo, Integer> {
	@Query(value = "select * from c_buy_sell where `id`=:id and is_sell=0   order by `buy_date` desc ", nativeQuery = true)
	public List<CBuySellPo> findById(@Param("id") long id);

	@Query(value = "select * from c_buy_sell where is_sell=0   order by `buy_date` desc ", nativeQuery = true)
	public List<CBuySellPo> findAll();

	@Query(value = "SELECT `id` FROM c_buy_sell and is_sell=0 GROUP BY `id`  ", nativeQuery = true)
	List<Integer> getAllId();
}
