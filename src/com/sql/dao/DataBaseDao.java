package com.sql.dao;

import org.springframework.data.repository.CrudRepository;

import com.sql.domain.DataBasePo;

public interface DataBaseDao extends CrudRepository<DataBasePo, String>{

}
