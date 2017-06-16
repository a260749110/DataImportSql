package com.util;

import java.text.DecimalFormat;
import java.util.List;

public class MathHelper {

	public static	DecimalFormat doubleFormat00_00 = new DecimalFormat("######0.00");

	public static <T> double Variance(List<T> list, int index, int size, IGetValue<T> valueFun) {
		double avg = 0;
		double sum = 0;
		double sqrSum = 0;
		for (int i = 0; i < size; i++) {
			T t = list.get(index - i);
			double v = valueFun.getValue(t);
			sum += v;

		}

		avg = sum / size;
		for (int i = 0; i < size; i++) {
			T t = list.get(index - i);
			double v = valueFun.getValue(t);
			sqrSum += Math.pow(v - avg, 2);
		}
		return sqrSum / size;
	}

	public static <T> T removeMost(List<T> datas, IGetValue<T> getValue) {
		T result = null;
		for (T t : datas) {
			if (result == null) {
				result = t;
			}
			if (getValue.getValue(t) > getValue.getValue(result)) {
				result = t;
			}
		}
		if (result != null)
			datas.remove(result);
		return result;
	}

	public static interface IGetValue<T> {
		public double getValue(T t);
	}
}
