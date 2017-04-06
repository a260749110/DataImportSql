package com.sql.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sql.domain.CDataBasePo;
import com.sql.domain.CDataBasePoPK;
import com.sql.domain.CDataResultPo;
import com.sql.domain.CDataResultPoPK;

public interface CDataBaseResultDao extends PagingAndSortingRepository<CDataResultPo, CDataResultPoPK> {
}
