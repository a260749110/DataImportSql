package com.bigcalculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.Caluculate;
import com.util.AppContextUtil;
import com.util.Helper;

public class BigCalculateMain {

	public static void main(String[] args) {
		long id = 1;
		List<CalculateNode> nodes = new ArrayList<>();

		CalculateNode node = new CalculateNode();
		nodes.add(node);
		Random random = new Random();
		for (int i = 0; i < 5000000; i++) {
			node = nodes.get(random.nextInt(nodes.size()));
			node = Helper.randomNode(node);
			nodes.add(node);
			Caluculate caluculate = new Caluculate(node);
			caluculate.run(id);
			node.setScore((float) caluculate.resultAll);
			if (nodes.size() > 100) {
				CalculateNode nodeRemove = null;
				for (CalculateNode n : nodes) {
					if (nodeRemove == null) {
						nodeRemove = n;
					} else {
						if (nodeRemove.getScore() > n.getScore()) {
							nodeRemove = n;
						}
					}
				}
				if (nodeRemove != null) {
					nodes.remove(nodeRemove);
				}
			}
			System.err.println("size:" + nodes.size());
			System.gc();

		}

	}
}
