package handwriting.learners.decisiontree;

import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;

import handwriting.core.SampleData;
import search.core.Duple;
import search.core.Triple;

public class DTTrainer {
	private ArrayBlockingQueue<Double> progress;
	private DTSampleData baseData;
	private double currentProgress, tick;
	
	public DTTrainer(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
		baseData = new DTSampleData(data);
		this.progress = progress;
		this.currentProgress = 0;
		progress.put(currentProgress);
		this.tick = 1.0 / data.numDrawings();
	}
	
	public DTNode train() throws InterruptedException {
		return train(baseData);
	}
	
	private DTNode train(DTSampleData data) throws InterruptedException {
		//System.out.println("training DTNode ");
		if (data.numLabels() == 1) {
			// TODO: Create a leaf node
			// Update the progress bar
			//System.out.println("New Leaf Node");
			DTLeaf leafNode = new DTLeaf(data.getLabelFor(0));
			currentProgress += tick;
			progress.put(currentProgress);
			return leafNode;
		} else {
			//System.out.println("interior Node");
			DTInteriorNode interiorNode = new DTInteriorNode(data);
			currentProgress += tick;
			progress.put(currentProgress);
			if(interiorNode.getTrueData().numDrawings() > 0){
				//System.out.println("training true data");
				interiorNode.trueNode = this.train(interiorNode.getTrueData());
				currentProgress += tick;
				progress.put(currentProgress);
			}
			if(interiorNode.getFalseData().numDrawings() > 0){
				//System.out.println("training false data");
				interiorNode.falseNode = this.train(interiorNode.getFalseData());
				currentProgress += tick;
				progress.put(currentProgress);
			}
			// TODO: Create an interior node
			// Use recursion to create the children of that node
			
			
			return interiorNode;
		}
	}
}
