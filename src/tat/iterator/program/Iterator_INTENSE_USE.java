package tat.iterator.program;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Iterator_INTENSE_USE {
	
	/**
	 * Aj : AjParametricHasNext.aj
	 * Config : no print in monitors 
	 * Number of exec : 100
	 * 
	 * 					    avg execution time with monitor      (137.14)
	 * Overhead in time = ------------------------------------ = ------- = 8.29
	 *                     avg execution time without monitor    (16.54)
	 */

	public static void main (String args[]) {

		long time = 0;
		int N = 100;
		
		for (int i=0; i<N; i++){
			long tmp = doService();
			time +=tmp;
			System.out.println(i + " / " + tmp);
		}
		
		System.out.println(((double) time) / ((double) N));
		
	}
	
	public static long doService (){
		
		long startTime = System.currentTimeMillis();	

		int N = 500;

		List<Vector<String>> list = new ArrayList<Vector<String>>();
		List<Iterator<String>> list_iterator = new ArrayList<Iterator<String>>();
		for (int i=0; i<N; i++){
			Vector<String> tmp = new Vector<String>();
			for (int j=0; j<N; j++)
				tmp.add("W"+j);
			list.add(tmp);
			list_iterator.add(tmp.iterator());
		}

		for (int k=0; k<N; k++){
			while (list_iterator.get(k).hasNext())
				list_iterator.get(k).next();
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		
		return elapsedTime;
	}

}
