package com.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.bigcalculate.cell.CalculateNode;
import com.check.cells.LycjssFlagData;
import com.comfig.ImportConfig;
import com.sql.domain.CBigCalculatePo;
import com.util.MathHelper.IGetValue;

public class Helper {

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

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
		public void each(Field field, String name, Object value, T filter) throws Exception;
	}

	public static CalculateNode randomNode(CalculateNode node) {
		float swap_per = random.nextFloat();
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

		// if (node.getParMapList().size() == 0) {
		// if (node.getTodayP() != null) {
		// // node.getParMapList().add(copyMap(node.getTodayP()));
		// } else {
		// Map<String, Float> map = new HashMap<>();
		// node.getParMapList().add(map);
		// }
		// if (node.getYestodayP() != null) {
		// node.getParMapList().add(copyMap(node.getYestodayP()));
		// } else {
		// Map<String, Float> map = new HashMap<>();
		// node.getParMapList().add(map);
		// }
		// }
		// System.err.println( "i:"+node.getParMapList().size());
		for (int i = node.getParMapList().size(); i <= size; i++) {
			LycjssFlagData map = new LycjssFlagData();
			node.getParMapList().add(map);
		}
		for (int i = node.getPar2MapList().size(); i <= size; i++) {
			LycjssFlagData map = new LycjssFlagData();
			node.getPar2MapList().add(map);
		}

		// for (int i = node.getParPwoMapList().size(); i <= size; i++) {
		// Map<String, Float> map = new HashMap<>();
		// node.getParPwoMapList().add(map);
		// }
		// for (int i = node.getParPowAMapList().size(); i <= size; i++) {
		// Map<String, Float> map = new HashMap<>();
		// node.getParPowAMapList().add(map);
		// }
		// System.err.println("node:"+JSON.toJSONString(node));
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
		int[] samples = ImportConfig.getInstance().getSimples();
		initNode(result, samples[samples.length - 1]);
		try {

			for (int i = 0; i < samples.length; i++) {
				{
					LycjssFlagData fatherMap = father.getParMapList().get(samples[i]);
					LycjssFlagData motherMap = mother.getParMapList().get(samples[i]);
					LycjssFlagData somMap = new LycjssFlagData();
					Helper.eachField(fatherMap, LycjssFlagData.class, (f, n, v, flter) -> {
						if (flter != null && !((RandomConfig) flter).enable()) {
							return;
						}

						if (random.nextFloat() < 0.5f) {
							f.setFloat(somMap, f.getFloat(fatherMap));

						} else {
							f.setFloat(somMap, f.getFloat(motherMap));
						}

					},  RandomConfig.class);
					result.getParMapList().set(samples[i], somMap);
				}
				{
					LycjssFlagData fatherMap = father.getPar2MapList().get(samples[i]);
					LycjssFlagData motherMap = mother.getPar2MapList().get(samples[i]);
					LycjssFlagData somMap = new LycjssFlagData();
					Helper.eachField(fatherMap, LycjssFlagData.class, (f, n, v, flter) -> {
						if (flter != null && !((RandomConfig) flter).enable()) {
							return;
						}

						if (random.nextFloat() < 0.5f) {
							f.setFloat(somMap, f.getFloat(fatherMap));

						} else {
							f.setFloat(somMap, f.getFloat(motherMap));
						}

					},  RandomConfig.class);
					result.getPar2MapList().set(samples[i], somMap);
				}

				// System.err.println("random:"+JSON.toJSONString(result));
			}

			{
				LycjssFlagData fatherMap = father.getPara();
				LycjssFlagData motherMap = mother.getPara();
				LycjssFlagData somMap = new LycjssFlagData();
				Helper.eachField(fatherMap, LycjssFlagData.class, (f, n, v, flter) -> {
					if (flter != null && !((RandomConfig) flter).enable()) {
						return;
					}

					if (random.nextFloat() < 0.5f) {
						f.setFloat(somMap, f.getFloat(fatherMap));

					} else {
						f.setFloat(somMap, f.getFloat(motherMap));
					}

				},  RandomConfig.class);
				result.setPara(somMap);
			}

			{
				LycjssFlagData fatherMap = father.getParb();
				LycjssFlagData motherMap = mother.getParb();
				LycjssFlagData somMap = new LycjssFlagData();
				Helper.eachField(fatherMap, LycjssFlagData.class, (f, n, v, flter) -> {
					if (flter != null && !((RandomConfig) flter).enable()) {
						return;
					}

					if (random.nextFloat() < 0.5f) {
						f.setFloat(somMap, f.getFloat(fatherMap));

					} else {
						f.setFloat(somMap, f.getFloat(motherMap));
					}

				},  RandomConfig.class);
				result.setParb(somMap);
			}

			{
				LycjssFlagData fatherMap = father.getTodayP();
				LycjssFlagData motherMap = mother.getTodayP();
				LycjssFlagData somMap = new LycjssFlagData();

				Helper.eachField(fatherMap, LycjssFlagData.class, (f, n, v, flter) -> {
					if (flter != null && !((RandomConfig) flter).enable()) {
						return;
					}

					if (random.nextFloat() < 0.5f) {
						f.setFloat(somMap, f.getFloat(fatherMap));

					} else {
						f.setFloat(somMap, f.getFloat(motherMap));
					}

				},  RandomConfig.class);

				result.setTodayP(somMap);
			}
			if (random.nextFloat() < 0.5f) {
				result.setPc(father.getPc());
			} else {
				result.setPc(mother.getPc());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static CalculateNode normalRandomNode(CalculateNode node) {
		System.err.println("nomal raandom");
		CalculateNode result = new CalculateNode();
		// int i = 0;
		try {

			HashSet<String> all = new HashSet<>();
			HashSet<String> changeCachae = new HashSet<>();
			result.setScore(node.getScore());
			result.setsScore(node.getsScore());
			result.setDscore(node.getDscore());
			int[] samples = ImportConfig.getInstance().getSimples();
			initNode(result, samples[samples.length - 1]);
			for (int j = 0; j < samples.length; j++) {
				{
					LycjssFlagData map = node.getParMapList().get(samples[j]);

					Helper.eachField(map, LycjssFlagData.class, (f, n, v, flter) -> {
						if (flter != null && !((RandomConfig) flter).enable()) {
							return;
						}

						all.add(f.getName());
						if (random.nextFloat() < ImportConfig.getInstance().getPre_random()) {
							changeCachae.add(f.getName());
							float next = nextRandom(f.getFloat(map));
							f.setFloat(map, next);
						}

					},  RandomConfig.class);

					result.getParMapList().set(samples[j], map);
				}
				{
					LycjssFlagData map = node.getPar2MapList().get(samples[j]);

					Helper.eachField(map, LycjssFlagData.class, (f, n, v, flter) -> {
						if (flter != null && !((RandomConfig) flter).enable()) {
							return;
						}

						all.add(f.getName());
						if (random.nextFloat() < ImportConfig.getInstance().getPre_random()) {
							changeCachae.add(f.getName());
							float next = nextRandom(f.getFloat(map));
							f.setFloat(map, next);
						}

					},  RandomConfig.class);

					result.getPar2MapList().set(samples[j], map);
				}

			}
			{
				LycjssFlagData map = node.getPara();

				Helper.eachField(map, LycjssFlagData.class, (f, n, v, flter) -> {
					if (flter != null && !((RandomConfig) flter).enable()) {
						return;
					}

					all.add(f.getName());
					if (random.nextFloat() < ImportConfig.getInstance().getPre_random()) {
						changeCachae.add(f.getName());
						float next = nextRandom(f.getFloat(map));
						f.setFloat(map, next);
					}

				},  RandomConfig.class);
				result.setPara(map);
			}
			{
				LycjssFlagData map = node.getParb();

				Helper.eachField(map, LycjssFlagData.class, (f, n, v, flter) -> {
					if (flter != null && !((RandomConfig) flter).enable()) {
						return;
					}

					all.add(f.getName());
					if (random.nextFloat() < ImportConfig.getInstance().getPre_random()) {
						changeCachae.add(f.getName());
						float next = nextRandom(f.getFloat(map));
						f.setFloat(map, next);
					}

				},  RandomConfig.class);
				result.setParb(map);
			}

			{
				LycjssFlagData map = node.getTodayP();

				Helper.eachField(map, LycjssFlagData.class, (f, n, v, flter) -> {
					if (flter != null && !((RandomConfig) flter).enable()) {
						return;
					}

					all.add(f.getName());
					if (random.nextFloat() < ImportConfig.getInstance().getPre_random()) {
						changeCachae.add(f.getName());
						float next = nextRandom(f.getFloat(map));
						f.setFloat(map, next);
					}

				},  RandomConfig.class);
				result.setTodayP(map);
			}

			if (random.nextFloat() < ImportConfig.getInstance().getPre_random()) {
				node.setPc(nextRandom(node.getPc()));
			}
			System.err.println("all:" + all.size() + " change:" + changeCachae.size());
			// System.err.println("random:"+JSON.toJSONString(result));
			// for (String k : node.getTodayP().keySet()) {
			// if (random.nextFloat() <
			// ImportConfig.getInstance().getPre_random())
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
			// if (random.nextFloat() <
			// ImportConfig.getInstance().getPre_random())
			// {
			// i++;
			// result.getYestodayP().put(k, nextRandom());
			// } else {
			// result.getYestodayP().put(k, node.getYestodayP().get(k));
			// i++;
			// }
			// }
			// System.err.println("i:" + i);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static float nextRandom(float befor) {
		float result = 0;
		if (random.nextFloat() < ImportConfig.getInstance().getMulPer()) {

			result = befor * (1 + (random.nextFloat() - 0.5f) * 2.0f * ImportConfig.getInstance().getMulStep());
			return result;
		}
		if (random.nextFloat() < ImportConfig.getInstance().getGaussianPer()) {

			result = befor + (float) (random.nextGaussian() * ImportConfig.getInstance().getGaussianMul());
		} else {
			int size = (int) ((ImportConfig.getInstance().getMax_random()
					- ImportConfig.getInstance().getMin_random()));
			result = ImportConfig.getInstance().getMin_random() + random.nextFloat() * ((float) size);
		}
		return result;
	}

	/**
	 * 0~max
	 * 
	 * @param befor
	 * @param max
	 * @return
	 */
	public static float nextRandom(float befor, float max) {
		float result = 0;
		if (random.nextFloat() < ImportConfig.getInstance().getMulPer()) {

			result = (float) (random.nextFloat() * max);
			return result;
		}
		if (random.nextFloat() < ImportConfig.getInstance().getGaussianPer()) {

			result = befor + (float) (random.nextGaussian() * ImportConfig.getInstance().getGaussianMul());
		} else {
			int size = (int) ((ImportConfig.getInstance().getMax_random()
					- ImportConfig.getInstance().getMin_random()));
			result = ImportConfig.getInstance().getMin_random() + random.nextFloat() * ((float) size);
		}
		if (result < 0) {
			result = 0;

		} else if (result > max) {
			result = max;
		}
		return result;
	}

	public static double pow(double v, double m) {

		if (v >= 0) {
			return Math.pow(v, m);
		} else {
			return -Math.pow(-v, m);
		}
	}

}
