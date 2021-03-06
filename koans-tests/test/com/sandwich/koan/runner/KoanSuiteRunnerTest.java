package com.sandwich.koan.runner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.sandwich.koan.cmdline.CommandLineArgument;
import com.sandwich.koan.constant.ArgumentType;

public class KoanSuiteRunnerTest {
	
	@Test
	public void testRunSortsAndInvokesByComparableImplInArgumentType() throws Exception {
		// test depends on this - this is to ensure rest of test is true
		{
			assertEquals(-1, ArgumentType.TEST.compareTo(ArgumentType.CLASS_ARG));
		}
		Map<ArgumentType, CommandLineArgument> args = new LinkedHashMap<ArgumentType, CommandLineArgument>();
		final boolean[] called = {false, false, false};
		args.put(ArgumentType.TEST, new CommandLineArgument(ArgumentType.TEST, null){
			@Override public void run(){
				assertFalse(called[0]);
				assertFalse(called[1]);
				assertFalse(called[2]);
				called[0] = true;
			}
		});
		args.put(ArgumentType.CLASS_ARG, new CommandLineArgument(ArgumentType.CLASS_ARG, null){
			@Override public void run(){
				assertTrue(called[0]);
				assertFalse(called[1]);
				assertFalse(called[2]);
				called[1] = true;
			}
		});
		args.put(ArgumentType.RUN_KOANS, new CommandLineArgument(ArgumentType.RUN_KOANS, null){
			@Override public void run(){
				assertTrue(called[0]);
				assertTrue(called[1]);
				assertFalse(called[2]);
				called[2] = true;
			}
		});
		new KoanSuiteRunner(args).run();
		assertTrue(called[0]);
		assertTrue(called[1]);
		assertTrue(called[2]);
	}
	
}
