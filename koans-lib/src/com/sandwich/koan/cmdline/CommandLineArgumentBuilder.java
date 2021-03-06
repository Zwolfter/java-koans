package com.sandwich.koan.cmdline;

import static com.sandwich.koan.constant.KoanConstants.DATA_FOLDER;
import static com.sandwich.koan.constant.KoanConstants.FILESYSTEM_SEPARATOR;
import static com.sandwich.koan.constant.KoanConstants.PROJ_MAIN_FOLDER;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sandwich.koan.constant.ArgumentType;
import com.sandwich.koan.constant.KoanConstants;
import com.sandwich.util.io.DynamicClassLoader;
import com.sandwich.util.io.FileUtils;

public class CommandLineArgumentBuilder extends LinkedHashMap<ArgumentType, CommandLineArgument> {
	
	private static final long serialVersionUID = 7635285665311420603L;

	public CommandLineArgumentBuilder(String...args){
		for(int index = 0; index < args.length;){
			String stringArg = args[index];
			if(stringArg == null || stringArg.trim().length() == 0){
				index++;
				continue;
			}
			// is incremented in test of ternary
			String stringArgPlusOne = args.length <= ++index ? null : args[index];
			stringArgPlusOne = stringArgPlusOne == null || stringArgPlusOne.trim().length() == 0
				? null : stringArgPlusOne;
			ArgumentType argumentType = ArgumentType.findTypeByString(stringArg);
			ArgumentType argumentTypePlusOne = ArgumentType.findTypeByString(stringArgPlusOne);
			if(argumentType != null){ // matches an anticipated argument string
				// so does next value, must be an argument too, reevaluate w/ index incremented only once above
				if(argumentTypePlusOne instanceof ArgumentType){ 
					put(argumentType, new CommandLineArgument(argumentType, null));
					// intentionally increment past next argument (only one) and
					// evaluate in next iteration with its following argument
					// - in other words, argumentType will be argumentTypePlusOne in next iteration
					continue;
				}
				// ok 2nd argument wasn't a recongized argument type - go
				// ahead and see if it's a class, if not, it must be a
				// method - or bogus
				else if(stringArgPlusOne != null){
					guessAtMethodAndClass(this, stringArgPlusOne);
				}
				else{
					put(argumentType, new CommandLineArgument(argumentType, null));
				}
				index++;
				continue;
			}else if(stringArg != null){
				// no flag, but a string - likely class or class and method
				guessAtMethodAndClass(this, stringArg);
				// do not increment again, bump stringArgPlusOne into stringArg from prior increment
				continue;
			}
		}
		applyAssumedStartupBehaviors();
	}
	
	private static void guessAtMethodAndClass(
			Map<ArgumentType, CommandLineArgument> commandLineArguments,
			String potentialClassOrMethod) {
		boolean hasMethod = commandLineArguments.containsKey(ArgumentType.METHOD_ARG);
		boolean hasClass = commandLineArguments.containsKey(ArgumentType.CLASS_ARG);
		try{
			if(potentialClassOrMethod != null){
				if(!hasClass){
					commandLineArguments.put(ArgumentType.CLASS_ARG, 
						new CommandLineArgument(ArgumentType.CLASS_ARG, 
								new DynamicClassLoader().loadClass(potentialClassOrMethod).getName()));
				}else if(!hasMethod){
					commandLineArguments.put(ArgumentType.METHOD_ARG, 
							new CommandLineArgument(ArgumentType.METHOD_ARG, potentialClassOrMethod));
				}
			}
		}catch(Exception cnfe2){
			if(!hasMethod){
				commandLineArguments.put(ArgumentType.METHOD_ARG, 
					new CommandLineArgument(ArgumentType.METHOD_ARG, potentialClassOrMethod));
			}else{
				throw new IllegalArgumentException(potentialClassOrMethod
						+ " does not match an expected argument, nor value.");
			}
		}
	}
	
	static private boolean isFirstTimeAppHasBeenRun() {
		File dataDirectory = new File(FileUtils.makeAbsoluteRelativeTo(
				new StringBuilder(PROJ_MAIN_FOLDER).append(FILESYSTEM_SEPARATOR).append(DATA_FOLDER).toString()));
		return !dataDirectory.exists();
	}

	void applyAssumedStartupBehaviors() {
		if(isFirstTimeAppHasBeenRun()){
			ArgumentType.BACKUP.run(null);
			// really hackish - but this will clear console for first run, not necessary after
			for(int i = 0; i < 80; i++){
				System.out.println();
			}
		}
		if(isEmpty() || !containsKey(ArgumentType.RUN_KOANS) && containsKey(ArgumentType.CLASS_ARG)){
			if(KoanConstants.DEBUG){
				System.out.println("Planting default run target.");
				for(Entry<ArgumentType, CommandLineArgument> argEntry : entrySet()){
					System.out.println("Key: '"+argEntry.getKey()+"'");
					System.out.println("Value: '"+argEntry.getValue()+"'");
				}
			}
			put(ArgumentType.RUN_KOANS, new CommandLineArgument(ArgumentType.RUN_KOANS, null, true));
		}
	}
}
