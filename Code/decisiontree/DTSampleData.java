package handwriting.learners.decisiontree;

import java.util.Iterator;

import handwriting.core.SampleData;
import search.core.Duple;

public class DTSampleData extends SampleData {
	public DTSampleData() {super();}
	
	public DTSampleData(SampleData src) {
		for (int i = 0; i < src.numDrawings(); i++) {
			this.addDrawing(src.getLabelFor(i), src.getDrawing(i));
		}
	}
	
	public double getGini() {
		// TODO: Implement Gini coefficient for this set
		double total = 0;
		Iterator<String> labels = this.allLabels().iterator();
		for(int i = 0; i < this.numLabels(); i++){
			double calc = this.numDrawingsFor(labels.next());
			calc = calc/this.numDrawings();
			total += (calc*calc);
		}
		
		double gini = 1-total;
		
		return gini;
	}
	
	public Duple<DTSampleData,DTSampleData> splitOn(int x, int y) {
		DTSampleData on = new DTSampleData();
		DTSampleData off = new DTSampleData();
		for(int i = 0; i < this.numDrawings(); i++){
			if(this.getDrawing(i).isSet(x, y)){
				on.addDrawing(this.getLabelFor(i), this.getDrawing(i));
			}
			else{
				off.addDrawing(this.getLabelFor(i), this.getDrawing(i));
			}
		}
		// TODO: Add all elements with (x, y) set to "on"
		//       Add all elements with (x, y) not set to "off"
		return new Duple<>(on, off);
	}
}
