package com.sql.dao;

import org.springframework.data.repository.CrudRepository;

import com.sql.domain.ParameterPo;

public interface ParameterDao extends CrudRepository<ParameterPo, String> {

}
