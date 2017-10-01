package com.main;

import com.sql.dao.ParameterDao;
import com.sql.domain.ParameterPo;
import com.util.AppContextUtil;

public class MainCalculation {
	
public static int threadSize=10;	
public static int[] result={0,0,0,0,0,0,0};
public static int beforSize=30;
public static int behindSize=30;
public static void main(String[] args) {

	ParameterDao dao=AppContextUtil.getContext().getBean(ParameterDao.class);
	ParameterPo po=dao.findOne("test1");

	System.err.println("ggaa");
}

}
