package com.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.DaySimulationJob;
import com.comfig.ImportConfig;
import com.sql.domain.CBigCalculatePo;
import com.util.MathHelper.IGetValue;

public class Helper {
	public static <T> T avgList(List<T> list, Class<T> clazz) {

		;
		Field[] fields = getFieds(clazz);
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
		Field[] fields = getFieds(clazz);
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
		Field[] fields = getFieds(clazz);
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
		Field[] fields = getFieds(clazz);
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
		Field[] fields = getFieds(clazz);
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
		Field[] fields = getFieds(clazz);
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

		Field[] fields = getFieds(clazz);
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
		Field[] fields = getFieds(clazz);
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
		Field[] fields = getFieds(clazz);
		try {
			T result = clazz.newInstance();

			for (Field field : fields) {
				if (field.getType().toString().contains("double")) {
					field.setAccessible(true);

					double t = (field.getDouble(a) - field.getDouble(b));

					field.setDouble(result, t);

				}
			}

			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void showMem() {
		DecimalFormat df = new DecimalFormat("0.00");
		long totalMem = Runtime.getRuntime().totalMemory();
		System.out.println(df.format(totalMem / 1000000F) + " MB");
		long maxMem = Runtime.getRuntime().maxMemory();
		System.out.println(df.format(maxMem / 1000000F) + " MB");
		long freeMem = Runtime.getRuntime().freeMemory();
		System.out.println(df.format(freeMem / 1000000F) + " MB");
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

	public static Map<String, Field[]> fieldCache = new HashMap<>();

	public static Field[] getFieds(Class clazz) {
		String name = clazz.getName();
		if (fieldCache.containsKey(name)) {
			return fieldCache.get(name);
		}
		fieldCache.put(name, clazz.getDeclaredFields());
		return fieldCache.get(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> void eachField(Object obj, Class<?> clazz, EachField<T> filed,
			Class<? extends Annotation> filter) {
		;
		Field[] fields = getFieds(clazz);
		try {

			for (Field field : fields) {
				field.setAccessible(true);
				filed.each(field, field.getName(), field.get(obj), (T) field.getAnnotation(filter));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static interface EachField<T> {
		public void each(Field field, String name, Object value, T filter);
	}

	public static CalculateNode randomNode(CalculateNode node) {

		if (random.nextFloat() >= ImportConfig.getInstance().getSwap_per()) {
			return normalRandomNode(node);
		}

		else {
			CBigCalculatePo po = getRandomCalculatePo();
			if (po == null || po.getDataBase() == null || po.getDataBase().length() == 0) {
				return normalRandomNode(node);
			} else {
				List<CalculateNode> nodes = null;

				try {
					nodes = JSONArray.parseArray(po.getDataBase(), CalculateNode.class);
				} catch (Exception e) {
					e.printStackTrace();
					nodes = new ArrayList<>();
				}

				DaySimulationJob dj = new DaySimulationJob();

				CalculateNode mother = new CalculateNode();

				Random random = new Random();
				if (nodes.size() >= ImportConfig.getInstance().getSave_size()) {
					mother = nodes.get(random.nextInt(nodes.size()));
					Helper.initNode(mother, ImportConfig.getInstance().getSampleSize());

				} else {
					return normalRandomNode(node);
				}
				return switchRandomNode(node, mother);
			}
		}
	}

	public static CalculateNode initNode(CalculateNode node, int size) {

		if (node.getParMapList().size() == 0) {
			if (node.getTodayP() != null) {
				node.getParMapList().add(copyMap(node.getTodayP()));
			} else {
				Map<String, Float> map = new HashMap<>();
				node.getParMapList().add(map);
			}
			if (node.getYestodayP() != null) {
				node.getParMapList().add(copyMap(node.getYestodayP()));
			} else {
				Map<String, Float> map = new HashMap<>();
				node.getParMapList().add(map);
			}
		}
		//System.err.println( "i:"+node.getParMapList().size());
		for (int i = node.getParMapList().size(); i <= size; i++) {
			Map<String, Float> map = new HashMap<>();
			node.getParMapList().add(map);
		}
	//	System.err.println("node:"+JSON.toJSONString(node));
		return node;
	}

	private static Map<String, Float> copyMap(Map<String, Float> fo) {
		Map<String, Float> map = new HashMap<>();
		for (String k : fo.keySet()) {
			map.put(k, fo.get(k));
		}
		return map;
	}

	private static CalculateNode switchRandomNode(CalculateNode father, CalculateNode mother) {
		System.err.println("swap !!!");
		CalculateNode result = new CalculateNode();
		result.setScore(0);
		int[] samples=  ImportConfig.getInstance().getSimples();
		initNode(result, samples[samples.length-1]);
		for (int i = 0; i <samples.length; i++) {
			Map<String, Float> fatherMap = father.getParMapList().get(samples[i]);
			Map<String, Float> motherMap = mother.getParMapList().get(samples[i]);
			Map<String, Float> somMap = new HashMap<>();
			for (String k : fatherMap.keySet()) {
				if (random.nextFloat() < 0.5f) {

					somMap.put(k, fatherMap.get(k));

				} else {
					if (motherMap.containsKey(k)) {
						somMap.put(k, motherMap.get(k));
					} else {
						somMap.put(k, 0f);
					}
				}

			}
			result.getParMapList().set(samples[i], somMap);
		}
		//System.err.println("random:"+JSON.toJSONString(result));

		return result;
	}
	
	private static CalculateNode normalRandomNode(CalculateNode node) {
		CalculateNode result = new CalculateNode();
		int i = 0;
		result.setScore(node.getScore());

		int[] samples=  ImportConfig.getInstance().getSimples();
		
		initNode(result, samples[samples.length-1]);
		for (int j = 0; j < samples.length; j++) {
			Map<String, Float> map = node.getParMapList().get( samples[j]);
			for (String k : map.keySet()) {
				if (random.nextFloat() < ImportConfig.getInstance().getPre_random()) {
					float next = nextRandom();
					map.put(k, next);
				} else {
					map.put(k, map.get(k));
				}

			}

			result.getParMapList().set(samples[i], map);
		}
	//	System.err.println("random:"+JSON.toJSONString(result));
		// for (String k : node.getTodayP().keySet()) {
		// if (random.nextFloat() < ImportConfig.getInstance().getPre_random())
		// {
		// float next = nextRandom();
		// result.getTodayP().put(k, next);
		// i++;
		// } else {
		// result.getTodayP().put(k, node.getTodayP().get(k));
		// i++;
		// }
		//
		// }
		// for (String k : node.getYestodayP().keySet()) {
		// if (random.nextFloat() < ImportConfig.getInstance().getPre_random())
		// {
		// i++;
		// result.getYestodayP().put(k, nextRandom());
		// } else {
		// result.getYestodayP().put(k, node.getYestodayP().get(k));
		// i++;
		// }
		// }
		// System.err.println("i:" + i);
		return result;
	}

	private static int nextInt() {
		return random.nextInt((int) ImportConfig.getInstance().getMax_size()) + 1;

	}

	public static CBigCalculatePo getRandomCalculatePo() {

		int id = nextInt();
		CBigCalculatePo cb = AppContextUtil.instance.getCBigCalculateDao().findOne(id);
		if (cb == null) {
			cb = new CBigCalculatePo();
		}
		return cb;

	}

	private static Random random = new Random();

	public static float nextRandom() {
		int size = (int) ((ImportConfig.getInstance().getMax_random() - ImportConfig.getInstance().getMin_random())
				/ ImportConfig.getInstance().getStep_random()) + 1;
		int ran = random.nextInt(size);
		float result = ImportConfig.getInstance().getMin_random() + ran * ImportConfig.getInstance().getStep_random();

		return result;
	}

}
