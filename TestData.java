package com.trade.settlements;

/**
* The TestData program implements an application that 
* create a Sample Test Data for Trade Settlements.
*
* @author  Bala S Rayala
* @version 1.1
*/

import java.util.List;
import java.util.ArrayList;

public class TestData {

	/*
	 * Create a testdata for this application
	 * 
	 * @return list of values
	 * 
	 */

	public static List<String> createData() {

		List<String> list = new ArrayList();

		list.add("test1_B_0.25_INR_03 Apr 2018_03 Apr 2018_100_150.50");
		list.add("test2_S_0.50_SGP_05 Apr 2018_07 Apr 2018_100_75.25");
		list.add("test3_B_0.75_INR_10 Apr 2018_11 Apr 2018_10_150.25");
		list.add("test4_S_1.75_AER_12 Apr 2018_13 Apr 2018_99_100.25");
		list.add("test5_B_2.75_SAR_14 Apr 2018_18 Apr 2018_11_99.25");
		list.add("foo_B_1.75_AER_16 Apr 2018_18 Apr 2018_100_11.25");
		list.add("bar_S_2.75_SAR_18 Apr 2018_21 Apr 2018_50_100.25");
		list.add("boo_B_1.75_SAR_21 Apr 2018_23 Apr 2018_150_99.25");

		return list;

	}
}
