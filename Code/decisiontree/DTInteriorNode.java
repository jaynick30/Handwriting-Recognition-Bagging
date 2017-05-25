package handwriting.learners.decisiontree;

import java.awt.Point;
import java.util.ArrayList;

import handwriting.core.Drawing;
import search.core.Duple;

public class DTInteriorNode implements DTNode{
	
	public DTNode trueNode = null;
	public DTNode falseNode = null;
	
	private DTSampleData initialData;
	private DTSampleData trueData = null;
	private DTSampleData falseData = null;
	
	public Point feature;
	
	DTInteriorNode(DTSampleData data){
		this.initialData = data;
		findFeature();
		/*
		if(trueData.getGini() == 0){
			trueNode = new DTLeaf(trueData.getLabelFor(0));
		}
		else{
			trueNode = new DTInteriorNode(trueData);
		}
		if(falseData.getGini() == 0){
			falseNode = new DTLeaf(falseData.getLabelFor(0));
		}
		else{
			falseNode = new DTInteriorNode(falseData);
		}
		*/
		
	}
	
	public DTSampleData getTrueData(){
		return trueData;
	}
	public DTSampleData getFalseData(){
		return falseData;
	}
	
	
	public void findFeature(){
		int width = initialData.getDrawing(0).getWidth();
		int height = initialData.getDrawing(0).getHeight();
		double gain = 0;
		double newGain = 0;
		
		if(initialData.numDrawings() > 0){
		//for(int i = 0; i < initialData.numDrawings(); i++){
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					Duple<DTSampleData, DTSampleData> splitData = initialData.splitOn(x, y);
					this.trueData = splitData.getFirst();
					this.falseData = splitData.getSecond();
					newGain = findGain();
					if(newGain > gain){
						gain = newGain;
						feature = new Point(x, y);
						//System.out.println("new feature! " + x + "," + y);
					}
				} 
			//}
		}
		//System.out.println("Splitting data at " + feature);
		//System.out.println("num drawings in initial data " + initialData.numDrawings());
		Duple<DTSampleData, DTSampleData> splitData = initialData.splitOn(feature.x, feature.y);
		this.trueData = splitData.getFirst();
		//System.out.println("true data is " + this.trueData.numDrawings());
		this.falseData = splitData.getSecond();
		//System.out.println("false data is " + this.falseData.numDrawings());
		}
	}
	
	private double findGain(){
		double hParent = this.initialData.getGini();
		double hChild1 = this.trueData.getGini();
		double hChild2 = this.falseData.getGini();
		//System.out.println("variables are " +hParent + " , " + hChild1 + " , " + hChild2 );
		
		double gain = hParent - (hChild1 + hChild2);
		
		return gain;
	}
	
	public boolean isHomogenous(){
		for(int i = 0; i < initialData.numDrawings(); i++){
			if(!initialData.getLabelFor(0).equals(initialData.getLabelFor(i))){
				return false;
			}
		}
		return true;
	}

	@Override
	public String classify(Drawing d) {
		System.out.println("classifying");
		String label = "unknown";
		if(d.isSet(this.feature.x, this.feature.y)){
			label = trueNode.classify(d);
		}
		else{
			label = falseNode.classify(d);
		}
		
		return label;
	}

}
