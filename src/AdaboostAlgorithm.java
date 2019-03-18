import java.util.ArrayList;
/*
 * Adaboost学习构架
 */
public class AdaboostAlgorithm {
	
	private static final int T=30; // 迭代次数 
	
	//PerceptronApproach pa = new PerceptronApproach(); // 感知器弱分类器  
	public static AdaboostResult adaboostClassify(ArrayList<ArrayList<Double>> dataSet, ArrayList<Double> dataWeightSet) { 
		
		//存储强分类器的所有结果
        AdaboostResult res = new AdaboostResult();  
        //没有任何数据集
        if(null == dataSet) 
            return null;  
        
        //存储每个弱分类器的权重  
        ArrayList<Double> classifierWeightSet = new ArrayList<Double>();     
        //存储每个弱分类器的分类结果 
        ArrayList<ArrayList<Double>> weakClassifierSet = new ArrayList<ArrayList<Double>>();
        //记录阈值及其特征值下标与判断采用的方法
        ArrayList<double[]> thresholdSet = new ArrayList<double[]>();
        //计算总错误率所需的和记录
        ArrayList<Double> aggErrors = new ArrayList<Double>();
        //记录上一次的结果错误率
        double LastError_P=1;
        
        
        //开始迭代
        for(int i=0; i<T; ) {  
        	System.out.println("\n\n样本权重分布：" + dataWeightSet);
            //记录最佳单层决策树的正确分布
        	ArrayList<Double> errorSet = MDT.main(dataSet, dataWeightSet);
            //记录弱分类器的阈值及其特征值下标与判断采用的方法 
        	double[] threshold = MDT.threshold;
        	//记录本次最佳单层决策树的分类结果
            ArrayList<Double> sensorWeightVector = new ArrayList<Double>();
            //将正确分布更新为结果分布
            for(int j=0; j<dataSet.size(); j++)
            	if(errorSet.get(j)==0.0) 
            		sensorWeightVector.add(j, dataSet.get(j).get(dataSet.get(0).size()-1));
            	else 
            		sensorWeightVector.add(j, -dataSet.get(j).get(dataSet.get(0).size()-1));            	
        	System.out.println("弱分类器分类结果： "+sensorWeightVector);       
            //计算弱分类器误差  
            double error=0; 
            //正确数量  
            int rightClassifyNum = 0;
            for(int j=0; j<dataSet.size(); j++) {   
                if(errorSet.get(j)==0)
                	rightClassifyNum++;
                else
                	error=error+dataWeightSet.get(j);
            }
            //记录弱分类器分类结果	
            weakClassifierSet.add(sensorWeightVector);  
            //记录阈值和对应特征值下标和判断的方法
            thresholdSet.add(threshold); 
            i++;
            System.out.println("第" + i + "次迭代");
            //如果全部正确，说明这一次的弱分类就足够分清这个数据集，清空其他的单层决策树情况
            if(dataSet.size() == rightClassifyNum) {  
            	thresholdSet.clear();
                classifierWeightSet.clear();  
                weakClassifierSet.clear();  
                thresholdSet.add(threshold);
                classifierWeightSet.add(1d);  
                weakClassifierSet.add(sensorWeightVector);  
                break;  
            }  
            
            //计算这一次弱分类器占强分类器的权重  
            double currentWeight = (0.5*Math.log((1-error)/error));  
            classifierWeightSet.add(currentWeight);  
            System.out.println("classifier weight(本次弱分类器的权重): " + currentWeight);
            
            //计算总的分类错误率
            for(int j=0; j<sensorWeightVector.size(); j++) {
            	if(aggErrors.size()==sensorWeightVector.size()) {
	            	double one = aggErrors.remove(j);
	            	aggErrors.add(j, one+currentWeight*sensorWeightVector.get(j));
            	}
            	else
            		aggErrors.add(currentWeight*sensorWeightVector.get(j));
            }
            System.out.println("本次迭代后的加权结果： " + aggErrors);
            double error_P = 1;
            double error_N = 0;
            for(int j=0; j<aggErrors.size(); j++) {
            	if((aggErrors.get(j)<=0) && (dataSet.get(j).get(dataSet.get(0).size()-1)!=-1))
            		error_N+=1;
            	else if((aggErrors.get(j)>0) && (dataSet.get(j).get(dataSet.get(0).size()-1)!=1))
            		error_N+=1;
            }
            error_P=error_N/aggErrors.size();
            System.out.println("错误率: " + error_P);
            System.out.println("------------------------------------------------------------------------");
            //已经能完全分类正确
            if(error_P==0)
            	break;
            
            
//            //过拟合（过学习）
//            if(error_P>LastError_P)
//            	break;
//            else
//            	LastError_P=error_P;
            
            
            //更新数据集中每条数据的权重并归一化  
            double dataWeightSum = 0;  
            for(int j=0; j<dataSet.size(); j++) {  
                dataWeightSet.set(j, dataWeightSet.get(j) * Math.pow(Math.E, (-1)*currentWeight*sensorWeightVector.get(j)*dataSet.get(j).get(dataSet.get(0).size()-1))); 
                dataWeightSum += dataWeightSet.get(j);  
            }  
            for(int j = 0; j < dataSet.size(); j++)  
                dataWeightSet.set(j, dataWeightSet.get(j) / dataWeightSum);             
        }  
          
        res.setClassifierWeightSet(classifierWeightSet);  
        res.setWeakClassifierSet(weakClassifierSet);  
        res.setThreshold(thresholdSet);;
        return res;  
    }  
	
	//根据目前的所有弱分类器对一个样本进行类别判断
	public static double computeResult(ArrayList<Double> data, AdaboostResult classifier) {  
        double results = 0;    
        ArrayList<double[]> thresholdSet = classifier.getThreshold();
        ArrayList<Double> classifierWeightSet = classifier.getClassifierWeightSet();
    	for(int i=0; i<thresholdSet.size(); i++) { 
    		if(thresholdSet.get(i)[2]==0) {
        		if(data.get((int) thresholdSet.get(i)[0])<=thresholdSet.get(i)[1])
        			results+=-1*classifierWeightSet.get(i);
        		else
        			results+=1*classifierWeightSet.get(i);
        	}
        	else {
        		if(data.get((int) thresholdSet.get(i)[0])>thresholdSet.get(i)[1])
        			results+=-1*classifierWeightSet.get(i);
        		else
        			results+=1*classifierWeightSet.get(i);
        	}
    	} 
    	if(results>0)
    		results=1;
    	else
    		results=-1;
        return results;
	}
}
