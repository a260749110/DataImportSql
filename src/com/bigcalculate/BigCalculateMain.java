package com.bigcalculate;

import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.Caluculate;
import com.util.AppContextUtil;

public class BigCalculateMain {

	public static void main(String[] args) {
		AppContextUtil.instance.getContext();
		long id = 1;
		CalculateNode node = new CalculateNode();

		Caluculate caluculate = new Caluculate(node);
		caluculate.run(id);

	}
}
