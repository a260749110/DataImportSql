package com.bigcalculate;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.BigCalculateJob;
import com.comfig.ImportConfig;
import com.sql.domain.CBigCalculatePo;
import com.util.AppContextUtil;

public class BestCalculateMain {

	public static void main(String[] args) {
		CalculateNode node = null;
		for (int i = 0; i < (int) ImportConfig.getInstance().getMax_size(); i++) {

			CBigCalculatePo po = AppContextUtil.instance.getCBigCalculateDao().findOne(i + 1);
			try {
				List<CalculateNode> nodes = JSONArray.parseArray(po.getDataBase(), CalculateNode.class);
				for (CalculateNode n : nodes) {
					if (node == null || n.getsScore() > node.getsScore()) {
						node = n;

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BigCalculateJob bj = new BigCalculateJob();
		bj.init();
		bj.run(node);
	}

	
}
