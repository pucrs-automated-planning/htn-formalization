//
//  run.java
//  JSHOP2 runner in the terminal
//
//  Created by Felipe Meneguzzi on 2020-06-04.
//  Copyright 2020 Felipe Meneguzzi. All rights reserved.
//

import JSHOP2.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.logging.Logger;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class run{

	private static final Logger logger = Logger.getLogger(run.class.getName());

	public static void main(String[] args) {
		ClassLoader classloader = run.class.getClassLoader();
		try {
			Class aClass = classloader.loadClass(args[0]);
			java.lang.reflect.Method method = aClass.getMethod("getPlans");

			// Get time and memory only of invoked method (we are not interested in reflection time)
			Runtime runtime = Runtime.getRuntime();
			NumberFormat fm = DecimalFormat.getInstance();
			fm.setMaximumFractionDigits(2);
			long t1 = System.currentTimeMillis();

			// Invoke method
			Object o = method.invoke(null);

			// Print time and memory usage
			long t2 = System.currentTimeMillis();
			long totalTime = (t2 - t1);
			logger.info("Planning took " + (totalTime) + "ms ( " + (totalTime / 1000) + "s )");
			logger.info("Total memory used: " + fm.format(runtime.totalMemory() / Math.pow(1024, 2)) + "MB");

			// Print plans
			java.util.List l = ((java.util.List)o);
			System.out.println(l.size()+" plans found!");
			System.out.println(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//problem.getPlans();
		//new JSHOP2GUI();
	}
}