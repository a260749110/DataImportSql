package com.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.util.MathHelper.IGetValue;

public class Helper {
	public static <T> T avgList(List<T> list, Class<T> clazz) {

		;
		Field[] fields = clazz.getDeclaredFields();
		try {
			T result = clazz.newInstance();
			for (T t : list) {

				for (Field field : fields) {
					if (field.getType().toString().contains("double")) {
						field.setAccessible(true);
						field.setDouble(result, field.getDouble(t) + field.getDouble(result));

					}
				}

			}
			if (list.size() > 0)
				for (Field field : fields) {
					if (field.getType().toString().contains("double")) {
						field.setAccessible(true);
						field.setDouble(result, field.getDouble(result) / list.size());

					}
				}
			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T varianceList(List<T> list, Class<T> clazz) {

		;
		Field[] fields = clazz.getDeclaredFields();
		try {
			T result = clazz.newInstance();

			for (Field field : fields) {
				if (field.getType().toString().contains("double")) {
					field.setAccessible(true);
					double value = 0;
					if (list.size() > 0) {
						value = MathHelper.Variance(list, list.size() - 1, list.size(), new IGetValue<T>() {

							@Override
							public double getValue(T t) {
								// TODO Auto-generated method stub
								try {
									return field.getDouble(t);
								} catch (IllegalArgumentException | IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return 0;
							}

						});
					}
					field.setDouble(result, value);

				}
			}

			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T comperList(List<T> list, Class<T> clazz) {

		;
		Field[] fields = clazz.getDeclaredFields();
		try {
			T result = clazz.newInstance();

			for (Field field : fields) {
				if (field.getType().toString().contains("double")) {
					field.setAccessible(true);
					double value = 0;
					if (list.size() > 0) {
						value = MathHelper.Variance(list, list.size() - 1, list.size(), new IGetValue<T>() {

							@Override
							public double getValue(T t) {
								// TODO Auto-generated method stub
								try {
									return field.getDouble(t);
								} catch (IllegalArgumentException | IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return 0;
							}

						});
					}
					field.setDouble(result, value);

				}
			}

			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * a/(a+b)
	 * 
	 * @param a
	 * @param b
	 * @param clazz
	 * @return
	 */
	public static <T> T difArv(T a, T b, Class<T> clazz) {

		;
		Field[] fields = clazz.getDeclaredFields();
		try {
			T result = clazz.newInstance();

			for (Field field : fields) {
				if (field.getType().toString().contains("double")) {
					field.setAccessible(true);
					double sum = field.getDouble(a) + field.getDouble(b);
					if (sum != 0)
						field.setDouble(result, (field.getDouble(a)) / sum);

				}
			}

			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param clazz
	 * @return a-b
	 */
	public static <T> T incT(T a, T b, Class<T> clazz) {

		;
		Field[] fields = clazz.getDeclaredFields();
		try {
			T result = clazz.newInstance();

			for (Field field : fields) {
				if (field.getType().toString().contains("double")) {
					field.setAccessible(true);

					double t = field.getDouble(a) - field.getDouble(b);

					field.setDouble(result, t);

				}
			}

			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param a
	 * @param value
	 * @param clazz
	 * @return abs(a-value)
	 */
	public static <T> T incAbsT(T a, double value, Class<T> clazz) {

		;
		Field[] fields = clazz.getDeclaredFields();
		try {
			T result = clazz.newInstance();

			for (Field field : fields) {
				if (field.getType().toString().contains("double")) {
					field.setAccessible(true);

					if (field.getDouble(a) != 0) {
						double t = field.getDouble(a) - value;
						field.setDouble(result, Math.abs(t));
					}
				}
			}

			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param a
	 * @param value
	 * @param clazz
	 * @return abs(a-value)
	 */
	public static <T> List<KV> sortT(T a, Class<T> clazz) {

		;

		Field[] fields = clazz.getDeclaredFields();
		try {
			List<KV> result = new ArrayList<Helper.KV>();

			for (Field field : fields) {
				if (field.getType().toString().contains("double")) {
					field.setAccessible(true);

					double v = field.getDouble(a);
					KV kv = new KV();
					kv.k = field.getName();
					kv.v = v;
					result.add(kv);
				}
			}

			result.sort((ta, tb) -> {
				return ta.v > tb.v ? -1 : ta.v == tb.v ? 0 : 1;
			});
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * a>b?1 :0 :-1
	 * 
	 * @param a
	 * @param b
	 * @param clazz
	 * @return
	 */
	public static <T> T containsT(T a, T b, Class<T> clazz) {

		;
		Field[] fields = clazz.getDeclaredFields();
		try {
			T result = clazz.newInstance();

			for (Field field : fields) {
				if (field.getType().toString().contains("double")) {
					field.setAccessible(true);

					double t = (field.getDouble(a) > field.getDouble(b)) ? 1
							: (field.getDouble(a) == field.getDouble(b) ? 0 : -1);

					field.setDouble(result, t);

				}
			}

			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param a
	 * @param b
	 * @param clazz
	 * @return a-b
	 */
	public static <T> T mutT(T a, T b, Class<T> clazz) {

		;
		Field[] fields = clazz.getDeclaredFields();
		try {
			T result = clazz.newInstance();

			for (Field field : fields) {
				if (field.getType().toString().contains("double")) {
					field.setAccessible(true);

					double t = (field.getDouble(a) - field.getDouble(b)) ;

					field.setDouble(result, t);

				}
			}

			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static class KV {
		private String k;
		private double v;

		public String getK() {
			return k;
		}

		public void setK(String k) {
			this.k = k;
		}

		public double getV() {
			return v;
		}

		public void setV(double v) {
			this.v = v;
		}
	}
}
