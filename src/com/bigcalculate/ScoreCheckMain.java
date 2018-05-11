package com.bigcalculate;

import java.text.ParseException;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.BigCalculateJob;
import com.bigcalculate.job.DaySimulationJob.ConfigAndReesult;
import com.bigcalculate.job.LiSanDaySimulateJob;
import com.comfig.ImportConfig;
import com.sql.domain.CBigCalculatePo;
import com.util.AppContextUtil;

public class ScoreCheckMain {
	public static void main(String[] args) {
		CalculateNode node = null;
		for (int i = 0; i < (int) ImportConfig.getInstance().getMax_size(); i++) {

			CBigCalculatePo po = AppContextUtil.instance.getCBigCalculateDao().findOne(i + 1);
			try {
				List<CalculateNode> nodes = JSONArray.parseArray(po.getDataBase(), CalculateNode.class);
				for (CalculateNode n : nodes) {
					if (node == null || n.getDscore() > node.getDscore()) {
						System.err.println("use:" + po.getId());
						node = n;

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LiSanDaySimulateJob dj = new LiSanDaySimulateJob();
		BigCalculateJob bj = new BigCalculateJob();
		bj.init();
		bj.run(node, dj);
		try {
			ConfigAndReesult result = dj.toSql();
			System.err.println(result.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
