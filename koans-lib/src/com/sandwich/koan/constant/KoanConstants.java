package com.sandwich.koan.constant;

import java.io.File;

import com.sandwich.util.io.IOConstants;





public abstract class KoanConstants {

	private KoanConstants(){}

	public static boolean 		DEBUG				= false;
//	public static boolean 		DEBUG				= true;
	public static final char 	EXIT_CHARACTER		= 'Q';
	public static final boolean ENABLE_ENCOURAGEMENT= false;
	
	public static String 		PROJ_MAIN_FOLDER 	= "koans";
	public static String 		PROJ_TESTS_FOLDER 	= "koans-tests";

	public static String 		SOURCE_FOLDER 		= "src";
	public static String 		BIN_FOLDER 			= "bin";
	public static String 		DATA_FOLDER 		= "data";
	public static String 		TESTS_FOLDER 		= "test";
	public static String 		LIB_FOLDER 			= "lib";
	
	public static final String  __					= "REPLACE ME";
	public static final String  PATH_XML_NAME 		= "PathToEnlightment.xml";
//	public static final String  PATH_XML			= "src/";  // TODO: Adapt so dev and deployed can use same path
	public static final String FILESYSTEM_SEPARATOR	= System.getProperty("file.separator");
	public static final String PATH_XML_LOCATION 	= new File("").getAbsolutePath().replace("koans-tests", "koans").replace(FILESYSTEM_SEPARATOR+"src", "")
													+ FILESYSTEM_SEPARATOR + "src" + FILESYSTEM_SEPARATOR + PATH_XML_NAME;
	
	public static final String  DESCRIPTION			= "description";
	public static final String  ARGUMENTS			= "args";
	
	public static final String  EOL					= System.getProperty("line.separator");
	public static final String  EOLS				= "[\n\r"+EOL+"]";
	
	public static final String  PERIOD 				= IOConstants.PERIOD;
	public static final String  EXPECTATION_LEFT_ARG= "has expectation as wrong argument!";
	public static final String  EXPECTED_LEFT 		= "expected:<";
	public static final String  EXPECTED_RIGHT 		= ">";
	public static final String  LINE_NO_START		= ".java:";
	public static final String  LINE_NO_END 		= ")";
	
	public static final String  DEFAULT_KOAN_DESC	= "TODO: Add a description of what the koan is intended to teach the pupil";
	public static final String  LEVEL 				= "Level: ";
	public static final String 	APP_NAME 			= "Java Koans";
	public static final String 	ALL_SUCCEEDED		= "Way to go! You've completed all of the koans! Feel like writing any?";
	public static final String 	WHATS_WRONG 		= "What went wrong:";
	public static final String 	CONQUERED			= "You have conquered";
	public static final String 	OUT_OF 				= "out of";
	public static final String 	KOAN				= "koan";
	public static final String 	ENCOURAGEMENT		= "Keep going, you will persevere!";
	public static final String 	PASSING_SUITES		= "Passing Suites:";
	public static final String 	FAILING_SUITES		= "Remaining Suites:";
	public static final String 	INVESTIGATE_IN_THE	= "Ponder what's going wrong in the";
	public static final String 	PROGRESS 			= "Progress:";
	public static final String 	COMPLETE_CHAR 		= "X";
	public static final String 	INCOMPLETE_CHAR		= "-";
	
	public static final int		PROGRESS_BAR_WIDTH	= 50;
	public static final String 	PROGRESS_BAR_START	= "[";
	public static final String 	PROGRESS_BAR_END	= "]";
	
	public static final String  XML_PARAMETER_START = "${";
	public static final String  XML_PARAMETER_END 	= "}";
	
}
