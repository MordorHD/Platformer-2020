package javaJG.assets;

import java.util.ArrayList;
import java.util.HashMap;

public final class Arrays {

	private Arrays() {

	}

	public static float[] toPrimitive(Float[] arr) {
		float[] prim = new float[arr.length];
		for (int i = 0; i < prim.length; i++)
			prim[i] = arr[i];
		return prim;
	}

	public static int[] toPrimitive(Integer[] arr) {
		int[] prim = new int[arr.length];
		for (int i = 0; i < prim.length; i++)
			prim[i] = arr[i];
		return prim;
	}

	public static double[] toPrimitive(Double[] arr) {
		double[] prim = new double[arr.length];
		for (int i = 0; i < prim.length; i++)
			prim[i] = arr[i];
		return prim;
	}

	public static long[] toPrimitive(Long[] arr) {
		long[] prim = new long[arr.length];
		for (int i = 0; i < prim.length; i++)
			prim[i] = arr[i];
		return prim;
	}

	public static char[] toPrimitive(Character[] arr) {
		char[] prim = new char[arr.length];
		for (int i = 0; i < prim.length; i++)
			prim[i] = arr[i];
		return prim;
	}

	public static short[] toPrimitive(Short[] arr) {
		short[] prim = new short[arr.length];
		for (int i = 0; i < prim.length; i++)
			prim[i] = arr[i];
		return prim;
	}

	public static byte[] toPrimitive(Byte[] arr) {
		byte[] prim = new byte[arr.length];
		for (int i = 0; i < prim.length; i++)
			prim[i] = arr[i];
		return prim;
	}

	@SafeVarargs
	public static <T> T[] array(T... content) {
		return content;
	}

	@SafeVarargs
	public static <T> ArrayList<T> createArrayList(T[]... arrays) {
		ArrayList<T> list = new ArrayList<>();
		for (T[] values : arrays)
			for (T t : values)
				list.add(t);
		return list;
	}

	@SafeVarargs
	public static <T> ArrayList<T> createArrayList(T... values) {
		ArrayList<T> list = new ArrayList<>();
		for (T obj : values)
			list.add(obj);
		return list;
	}

	@SafeVarargs
	public static <K, V> HashMap<K, V> createHashMap(Pair<K, V>... pairs) {
		HashMap<K, V> map = new HashMap<>();

		for (Pair<K, V> p : pairs)
			map.put(p.get(), p.getValue());
		return map;
	}

}
