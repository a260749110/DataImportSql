package com.bigcalculate;

import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.Caluculate;

public class BigCalculateMain {

	public static void main(String[] args) {
		long id = 1;
		CalculateNode node = new CalculateNode();

		Caluculate caluculate = new Caluculate(node);
		caluculate.run(id);

	}
}
