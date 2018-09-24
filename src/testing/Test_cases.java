package testing;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import sorting.SortList;
import traffic.Violation;
/**
 * 
 * @author usmanirfan and Vaibhav Chadha
 * @version Eclipse Oxygen
 */
class Test_cases {

	private static ArrayList<Violation> testObjs;
	
	
	/**
	 * 
	 * @return ArrayList of testObjs.
	 * @throws Exception
	 */
	@Before
	public ArrayList<Violation> setUp() throws Exception {

		testObjs = new ArrayList<>();

		for (int i = 0; i < 1000; i++) {
			Math.random();
			double lat = 33.2251 + (Math.random() * 100);
			double lng = -76.12412 + (Math.random() * 100);
			Violation v = new Violation("12/03/2016", "13:37", "YO",
					new String[] { Double.toString(lat), Double.toString(lng) }, "Car crash", "hell", "MD");
			testObjs.add(v);
		}
		return testObjs;
	}

//	@After
//	public void tearDown() throws Exception {
//	}
	/**
	 * Testing the sorting functions
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		testObjs = setUp();

		SortList.sort(testObjs, 0);

		for (int i = 0; i < testObjs.size()-1; i++) {
			SortList.isSorted(testObjs.get(i).getLatlong()[0], testObjs.get(i+1).getLatlong()[0]);
		}
		
	}
}

