package dataHandler;

import java.util.ArrayList;

import sorting.SortList;
import traffic.Violation;

/**
 * Class representing a BoundingBox
 * @author prithvi
 *
 */
public class BoundBox {
	
	
	
	private static ArrayList<Violation> refined_data_X;
	private static ArrayList<Violation> refined_data;
	
	public BoundBox(){
		
	}
	
	/**
	 * Returns the violations elements that fall within the bounding box made by the given coordinates
	 * @param a dataset
	 * @param x_lo 
	 * @param x_hi
	 * @param y_lo
	 * @param y_hi
	 * @return violation elements within the box
	 */
	public ArrayList<Violation> Bounding(ArrayList<Violation> a, double x_lo, double x_hi , double y_lo, double y_hi){
		

		
		int first_index = 0;
		int last_index = 75;
		first_index = firstIndex(a,x_lo,0);
		
		last_index = lastIndex(a,x_hi,0);
		
		
		
		
		if(first_index == -1 || last_index == -1){
			return a;
		}
		else{
			refined_data_X = new ArrayList<Violation>();
			for (int i = first_index; i <= last_index; i++){
				refined_data_X.add(a.get(i));
			}
		}
		
		SortList.sort(refined_data_X, 1);
		

		first_index = firstIndex(refined_data_X, y_lo, 1);
		
		last_index = lastIndex(refined_data_X, y_hi, 1);
	
		
		if(first_index == 0 || last_index == 0){
			
			return a;
		}
		else{
			refined_data = new ArrayList<Violation>();
			for (int i = first_index; i <= last_index; i++){
				refined_data.add(refined_data_X.get(i));
			}	
		}

		return refined_data;
	}

	/**
	 * Returns the first index that bounds the dataset
	 * @param arraylist
	 * @param lo
	 * @param flag
	 * @return
	 */
	private static int firstIndex(ArrayList<Violation> arraylist,double lo, int flag) {
		for (int i = 0; i < arraylist.size() - 1; i++){
            if (Double.parseDouble(arraylist.get(i).getLatlong()[flag]) <= lo &&  lo <= Double.parseDouble(arraylist.get(i+1).getLatlong()[flag]))
                return i+1;
		}
		return 0;
	}
	
	
	
	
	private static int lastIndex(ArrayList<Violation> arraylist,double hi, int flag) {
		for (int i = arraylist.size() - 1; i >= 0; i--){
			if (Double.parseDouble(arraylist.get(i-1).getLatlong()[flag]) <= hi &&  hi <= Double.parseDouble(arraylist.get(i).getLatlong()[flag]))
                return i-1;
		}
		return 0;
	}



	
	

	
	
	

}
