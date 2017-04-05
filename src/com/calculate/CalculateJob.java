package com.calculate;

import java.util.ArrayList;
import java.util.List;

import com.sql.dto.ParameterDto;

public class CalculateJob {
	protected ParameterDto base;

protected List<ParameterDto> resultList;
public CalculateJob(ParameterDto dto)
{
	base=dto;
	
	resultList=new ArrayList<>();
	}

}
