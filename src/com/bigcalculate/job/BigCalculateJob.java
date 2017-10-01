package com.bigcalculate.job;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.bigcalculate.cell.CalculateNode;
import com.comfig.ImportConfig;
import com.sql.dao.CDataBaseDao;
import com.sql.domain.CBigCalculatePo;
import com.util.AppContextUtil;
import com.util.Helper;

public class BigCalculateJob {
	private static List<Long> allIds;

	public void init() {
		if (allIds == null)
			allIds = loadIds();

	}

	public CBigCalculatePo run(CBigCalculatePo calculate) {
		List<CalculateNode> nodes = null;
		if (calculate.getDataBase() == null) {
			nodes = new ArrayList<>();

		} else {
			try {
				nodes = JSONArray.parseArray(calculate.getDataBase(), CalculateNode.class);
			} catch (Exception e) {
				e.printStackTrace();
				nodes = new ArrayList<>();
			}
		}
		DaySimulationJob dj = new DaySimulationJob();

		CalculateNode node = new CalculateNode();

		Random random = new Random();
		if (nodes.size() >= ImportConfig.getInstance().getSave_size()) {
			node = nodes.get(random.nextInt(nodes.size()));
		} else {
			System.err.println("new");
			node = new CalculateNode();
		}
		node = Helper.randomNode(node);
		float score = 0;
		for (long id : allIds) {
			Caluculate caluculate = new Caluculate(node, dj);
			caluculate.run(id);
			score += caluculate.resultAll;
		}
		try {
			dj.toSql();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		node.setScore((float) score);
		node.setsScore((float) dj.moneyALL);
		nodes = refreshNods(calculate, nodes);
		nodes.add(node);
		CalculateNode nodeRemove = null;
		CalculateNode best = null;
		for (CalculateNode n : nodes) {
			if (nodeRemove == null) {
				nodeRemove = n;
			} else {
				if (nodeRemove.getsScore() > n.getsScore()) {
					nodeRemove = n;
				}
			}
			if (best == null) {
				best = n;
			} else {
				if (best.getsScore() < n.getsScore()) {
					best = n;
				}
			}
		}
		if (nodes.size() > ImportConfig.getInstance().getSave_size()) {
			if (nodeRemove != null) {
				nodes.remove(nodeRemove);

			}
		}
		calculate.setDataBase(JSONArray.toJSONString(nodes));
		calculate.setScore(best.getScore());
		calculate.setsScore(best.getsScore());
		return calculate;
	}

	public void run(CalculateNode node) {

		Random random = new Random();
		DaySimulationJob dj = new DaySimulationJob();
		float score = 0;
		for (long id : allIds) {
			Caluculate caluculate = new Caluculate(node, dj);
			caluculate.run(id);
			score += caluculate.resultAll;
		}
		try {
			dj.showFlag=true;
			dj.toSql();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
	}

	private List<CalculateNode> refreshNods(CBigCalculatePo calculate, List<CalculateNode> nodes) {
		try {

			if (calculate.getId() <= 0)
				return nodes;
			else {
				CBigCalculatePo now = AppContextUtil.instance.getCBigCalculateDao().findOne(calculate.getId());

				List<CalculateNode> nnodes = null;
				nnodes = JSONArray.parseArray(now.getDataBase(), CalculateNode.class);
				return nnodes;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodes;
	}

	private List<Long> loadIds() {
		List<Long> list = new ArrayList<>();
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		List<Long> ids = dao.getAllId();
		for (int i = 0; i < ids.size(); i++) {
			Long id = Long.valueOf(ids.get(i) + "");
			if (id == 399001L || id > 100000)
				continue;
			list.add(id);
		}

		return list;
	}

}
