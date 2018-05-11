package com.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sql.dao.CBigCalculateDao;
import com.sql.dao.CBuySellDao;
import com.sql.dao.CDataBaseDao;
import com.sql.dao.CDataBaseResultDao;
import com.sql.dao.CHistoryBuySellDao;
import com.sql.dao.CSaveValueDao;

public class AppContextUtil {
	public static final AppContextUtil instance = new AppContextUtil();

	private static ApplicationContext context;

	private AppContextUtil() {

		context = (new ClassPathXmlApplicationContext("classpath*:spring.xml"));

	}

	public static ApplicationContext getContext() {
		return context;
	}

	public <T> T getBean(Class<T> c) {
		return getContext().getBean(c);
	}

	public CHistoryBuySellDao getCHistoryBuySellDao() {
		return getBean(CHistoryBuySellDao.class);
	}

	public CBuySellDao getCBuySellDao() {
		return getBean(CBuySellDao.class);
	}

	public CBigCalculateDao getCBigCalculateDao() {
		return getBean(CBigCalculateDao.class);
	}

	public CDataBaseDao getCDataBaseDao() {
		return getBean(CDataBaseDao.class);
	}

	public CSaveValueDao getCSaveValueDao() {
		return getBean(CSaveValueDao.class);
	}

	public CDataBaseResultDao getCDataBaseResultDao() {
		return getBean(CDataBaseResultDao.class);
	}

}
