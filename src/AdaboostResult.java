import java.util.ArrayList;
/*
 * Adaboost�㷨��Ҫ��������
 * 		ÿ������������������Ӧ��Ȩ�غ�����������Ӧ����ֵ������ֵ�±����жϷ���
 */
public class AdaboostResult {
	//���ڼ�¼ѡȡ���ǵڼ��������Ͷ�Ӧ����ֵ�����б�������������Ӧ���жϷ���ķ���
	private ArrayList<double[]> threshold;
	//ÿ����������ÿһ��������Ȩ��
	private ArrayList<ArrayList<Double>> weakClassifierSet;  
	//ÿ������������Ȩ��
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