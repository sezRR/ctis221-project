package dev.sezrr.llmchatwrapper.frontendjavafxgui;

public class Instructor implements Comparable<Instructor> {

			protected String Instruction;
	        protected String model;

	        public Instructor(String model , String instruction) {
	            this.Instruction = instruction;
	            this.model = model;
	        }

	        public String getModel() {
	            return model;
	        }

	        public String getInstruction() {
	            return Instruction;
	        }

	        @Override
	        public String toString() {
	            return "Instructor" + "\nModel= " + model + "\nInstruction= " + Instruction;
	        }

	        
	            @Override
	            public int compareTo(Instructor other) {
	                return this.model.compareTo(other.model); 
	            }

	            @Override
	            public boolean equals(Object obj) {
	                if (this == obj) return true;
	                if (!(obj instanceof Instructor)) return false;
	                Instructor other = (Instructor) obj;
	                return model.equals(other.model);
	            }

	            @Override
	            public int hashCode() {
	                return model.hashCode();
	            }

}
