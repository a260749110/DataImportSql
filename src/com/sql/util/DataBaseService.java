package com.sql.util;

import com.alibaba.fastjson.JSON;
import com.check.cells.SaveOtherData;
import com.sql.domain.CSaveValuePo;
import com.util.AppContextUtil;

public class DataBaseService {

	public static SaveOtherData getSaveOtherData(int id)
	{
		CSaveValuePo po=AppContextUtil.instance.getCSaveValueDao().findOne(id);
		SaveOtherData	data=null;
		if(po!=null)
		 data=JSON.parseObject(po.getOthers(), SaveOtherData.class);
		else
		{
			data=new SaveOtherData();
		}
		return data;
	}
	public static void saveSaveOtherData(int id,SaveOtherData data)
	{
		CSaveValuePo po=new CSaveValuePo();
		po.setId(id);
		po.setOthers(JSON.toJSONString(data));
		AppContextUtil.instance.getCSaveValueDao().save(po);
	
	}
}
