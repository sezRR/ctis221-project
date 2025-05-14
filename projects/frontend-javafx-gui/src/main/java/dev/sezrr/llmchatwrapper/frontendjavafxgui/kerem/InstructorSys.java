package dev.sezrr.llmchatwrapper.frontendjavafxgui;

import java.util.HashSet;
import java.util.TreeSet;

public class InstructorSys {

	 private static HashSet<Instructor> instructors = new HashSet<>();


	 public static boolean addInstructor(String model, String instruction) {
	        for (Instructor I : instructors) {
	            if (I.getModel().equals(model)) {
	                return false;
	            }
	        }

	        Instructor Ins = new Instructor( model, instruction);
	        instructors.add(Ins);
	        return true;
	    }
	 
	 
	 public static Instructor search(String modelName) {	 
	        for (Instructor Ins : instructors) {
	            if (Ins.getModel().equals(modelName))
	                return Ins;
	            }        
        return null;
	}
	 
	 public static boolean removeInstructor(String modelName) {
		    Instructor removed = null;
		    for (Instructor ins : instructors) {
		        if (ins.getModel().equals(modelName)) {
		            removed = ins;
		            break;
		        }
		    }
		    if (removed != null) {
		        instructors.remove(removed);
		        return true;
		    }
		    return false; 
		}

	 
	 public static String displayAll() {

	        TreeSet<Instructor> ts = new TreeSet<Instructor>();
	        ts.addAll(instructors);
	        String res = "";
	        for (Instructor Ins : ts) {
	            res += Ins.toString() + "\n\n";
	        }
	        return res;
	    }
}
