package com.sql.util;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.json.JSONArray;

import com.sql.dao.ParameterDao;
import com.sql.domain.ParameterPo;
import com.sql.dto.ParameterDto;
import com.util.AppContextUtil;

public class ParameterService {
	private static ParameterDao parameterDao ;
	private static final String name="test1";
	private static ReadWriteLock lock=new ReentrantReadWriteLock();
	public static ParameterDto getNow() {
		lock.readLock().lock();;
		try {
			ParameterDto dto = new ParameterDto();
			ParameterPo po=getDao().findOne(name);
			JSONArray jsonArray=new JSONArray(po.getValues());
			double[] valuse=new double[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				valuse[i]=jsonArray.getDouble(i);
			}
			
			dto.setLearning(po.getLearning());
			dto.setRate(po.getRate());
			dto.setValues(valuse);
			return dto;
		} finally
		{
			lock.readLock().unlock();
		}
	
	}
	
	public static void save(ParameterDto dto)
	{
		lock.writeLock().lock();
		try {
			ParameterPo po=getDao().findOne(name);
			if(po.getRate()<=dto.getRate())
			{
				return ;
			}
			JSONArray jsonArray=new JSONArray();
			for (int i = 0; i < dto.getValues().length; i++) {
				jsonArray.put(dto.getValues()[i]);
			}
			po.setValues(jsonArray.toString());
			po.setRate(dto.getRate());
			getDao().save(po);
		} finally {
			lock.readLock().unlock();
		}
	}
	private static ParameterDao getDao()
	{
		if(parameterDao==null)
		{
			parameterDao=AppContextUtil.getContext().getBean(ParameterDao.class);
		}
		return parameterDao;
	}
}
