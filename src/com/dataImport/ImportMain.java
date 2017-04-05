package com.dataImport;

import java.util.List;

import com.comfig.ImportConfig;
import com.sql.dao.CDataBaseDao;
import com.sql.domain.CDataBasePo;
import com.util.AppContextUtil;

public class ImportMain {

	public static void main(String[] args) {
		ImportConfig config = AppContextUtil.getContext().getBean(ImportConfig.class);
		System.err.println(config.getDir());
		CDataBaseDao baseDao=AppContextUtil.getContext().getBean(CDataBaseDao.class);
		List<CDataBasePo> basePo=baseDao.findAll();
		System.err.println(basePo.iterator().hasNext());
		ImportJob importJob=new ImportJob(EImportType.INSERT_NEW, config.getDir());
		importJob.runJob();
	}
}
