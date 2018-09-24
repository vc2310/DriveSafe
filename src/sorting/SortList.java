/**
 * Class for sorting violation objects based on lat/lng
 * @author Vaibhav Chadha and Usman Irfan
 * 
 */
package sorting;

import java.util.ArrayList;

import javafx.concurrent.Task;
import traffic.Violation;

public class SortList extends Task<Void>{
	
   private ArrayList<Violation> data;
   private int flag;
   
   public SortList(ArrayList<Violation> data, int flag) {
	   this.data = data; //store the reference
	   this.flag = flag;
	// TODO Auto-generated constructor stub
}
	
	public static void sort(ArrayList<Violation> a, int flag) {
		sort(a, 0, a.size() - 1 , flag);
	}
	
	/**
	 * Comparing 2 objects using Comparable
	 * 
	 * @param v
	 *            -first one to compare
	 * @param w
	 *            -second one to compare
	 * @return 0 if they are equal, 1 if v > w, -1 if v < w. if the return value is
	 *         less than 0, it will return false
	 */
	/*
	private static int less(String v, String w) {
		float v_float = Float.parseFloat(v);
		float w_float = Float.parseFloat(w);
		if (v_float > w_float) return 1;
		if (v_float < w_float) return -1;
		return 0;
	}
	*/
	private static boolean less(String v, String w){
		Double v_double = Double.parseDouble(v);
		Double w_double = Double.parseDouble(w);
		if (v_double < w_double) return true;
		if (v_double > w_double) return false;
		return false;
	}

	/*
	 * private static boolean less(ArrayList<Violation> v, ArrayList<Violation> w) {
	 * if(v < w) return true; else return false; }
	 */

	/**
	 * Exchanging 2 integers using ArrayList
	 * 
	 * @param a
	 *            -the input arrayList containing Strings that need to be exchanged
	 * @param i
	 *            -index of first integer to be changed
	 * @param j
	 *            -index of second integer to be changed
	 * 
	 *            will end up interchanging the objects at index i and j
	 */
	private static void exchange(ArrayList<Violation> a, int i, int j) {
		Violation t = a.get(i);
		a.set(i, a.get(j));
		a.set(j, t);
	}

	/**
	 * Quicksort using ArrayList
	 * 
	 * @param a
	 *            - the input arraylist containing Strings that need to be sorted.
	 * @param lo
	 *            -left most side of array
	 * @param hi
	 *            -right most side of array
	 * 
	 */
	private static void sort(ArrayList<Violation> a, int lo, int hi, int flag) {
		if (hi <= lo)
			return;
		int j = partition(a, lo, hi, flag);
		sort(a, lo, j - 1, flag);
		sort(a, j + 1, hi, flag);
	}

	/**
	 * Divides the array using the randomly chosen partition element.
	 * 
	 * @param a
	 *            - the input arraylist containing Strings that need to be sorted.
	 * @param lo
	 *            -left most side of arrayList
	 * @param hi
	 *            - right most side of arrayList
	 * @return
	 */
	private static int partition(ArrayList<Violation> a, int lo, int hi, int flag) {
		int i = lo, j = hi + 1;
		String v = a.get(lo).getLatlong()[flag];
		while (true) {
			while (less(a.get(++i).getLatlong()[flag], v))
				if (i == hi)
					break;
			while (less(v , a.get(--j).getLatlong()[flag]))
				if (j == lo)
					break;
			if (i >= j)
				break;
			exchange(a, i, j);
		}
		exchange(a, lo, j);
		return j;
	}
	
	public static boolean isSorted(String v, String w){
		float v_float = Float.parseFloat(v);
		float w_float = Float.parseFloat(w);
		if (v_float > w_float){
			return false;
		}
		return true;
	}

	@Override
	protected Void call() throws Exception {
		sort(data, flag);
		return null;
	}
}
