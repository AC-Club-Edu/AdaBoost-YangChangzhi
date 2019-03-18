import java.util.ArrayList;
/*
 * Adaboost算法需要的运算结果
 * 		每个弱分类器结果和其对应的权重和弱分类器对应的阈值与特征值下标与判断方法
 */
public class AdaboostResult {
	//用于记录选取的是第几个特征和对应的阈值，还有本次弱分类器对应的判断分类的方法
	private ArrayList<double[]> threshold;
	//每个弱分类器每一个特征的权重
	private ArrayList<ArrayList<Double>> weakClassifierSet;  
	//每个弱分类器的权重
	private ArrayList<Double> classifierWeightSet; 
	
	
    public ArrayList<ArrayList<Double>> getWeakClassifierSet() {  
        return weakClassifierSet;  
    }  
    public void setWeakClassifierSet(ArrayList<ArrayList<Double>> weakClassifierSet) {  
        this.weakClassifierSet = weakClassifierSet;  
    }  
    public ArrayList<Double> getClassifierWeightSet() {  
        return classifierWeightSet;  
    }  
    public void setClassifierWeightSet(ArrayList<Double> classifierWeightSet) {  
        this.classifierWeightSet = classifierWeightSet;  
    }
	public ArrayList<double[]> getThreshold() {
		return threshold;
	}
	public void setThreshold(ArrayList<double[]> threshold) {
		this.threshold = threshold;
	}
}