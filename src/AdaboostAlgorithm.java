import java.util.ArrayList;
/*
 * Adaboostѧϰ����
 */
public class AdaboostAlgorithm {
	
	private static final int T=30; // �������� 
	
	//PerceptronApproach pa = new PerceptronApproach(); // ��֪����������  
	public static AdaboostResult adaboostClassify(ArrayList<ArrayList<Double>> dataSet, ArrayList<Double> dataWeightSet) { 
		
		//�洢ǿ�����������н��
        AdaboostResult res = new AdaboostResult();  
        //û���κ����ݼ�
        if(null == dataSet) 
            return null;  
        
        //�洢ÿ������������Ȩ��  
        ArrayList<Double> classifierWeightSet = new ArrayList<Double>();     
        //�洢ÿ�����������ķ����� 
        ArrayList<ArrayList<Double>> weakClassifierSet = new ArrayList<ArrayList<Double>>();
        //��¼��ֵ��������ֵ�±����жϲ��õķ���
        ArrayList<double[]> thresholdSet = new ArrayList<double[]>();
        //�����ܴ���������ĺͼ�¼
        ArrayList<Double> aggErrors = new ArrayList<Double>();
        //��¼��һ�εĽ��������
        double LastError_P=1;
        
        
        //��ʼ����
        for(int i=0; i<T; ) {  
        	System.out.println("\n\n����Ȩ�طֲ���" + dataWeightSet);
            //��¼��ѵ������������ȷ�ֲ�
        	ArrayList<Double> errorSet = MDT.main(dataSet, dataWeightSet);
            //��¼������������ֵ��������ֵ�±����жϲ��õķ��� 
        	double[] threshold = MDT.threshold;
        	//��¼������ѵ���������ķ�����
            ArrayList<Double> sensorWeightVector = new ArrayList<Double>();
            //����ȷ�ֲ�����Ϊ����ֲ�
            for(int j=0; j<dataSet.size(); j++)
            	if(errorSet.get(j)==0.0) 
            		sensorWeightVector.add(j, dataSet.get(j).get(dataSet.get(0).size()-1));
            	else 
            		sensorWeightVector.add(j, -dataSet.get(j).get(dataSet.get(0).size()-1));            	
        	System.out.println("���������������� "+sensorWeightVector);       
            //���������������  
            double error=0; 
            //��ȷ����  
            int rightClassifyNum = 0;
            for(int j=0; j<dataSet.size(); j++) {   
                if(errorSet.get(j)==0)
                	rightClassifyNum++;
                else
                	error=error+dataWeightSet.get(j);
            }
            //��¼��������������	
            weakClassifierSet.add(sensorWeightVector);  
            //��¼��ֵ�Ͷ�Ӧ����ֵ�±���жϵķ���
            thresholdSet.add(threshold); 
            i++;
            System.out.println("��" + i + "�ε���");
            //���ȫ����ȷ��˵����һ�ε���������㹻����������ݼ�����������ĵ�����������
            if(dataSet.size() == rightClassifyNum) {  
            	thresholdSet.clear();
                classifierWeightSet.clear();  
                weakClassifierSet.clear();  
                thresholdSet.add(threshold);
                classifierWeightSet.add(1d);  
                weakClassifierSet.add(sensorWeightVector);  
                break;  
            }  
            
            //������һ����������ռǿ��������Ȩ��  
            double currentWeight = (0.5*Math.log((1-error)/error));  
            classifierWeightSet.add(currentWeight);  
            System.out.println("classifier weight(��������������Ȩ��): " + currentWeight);
            
            //�����ܵķ��������
            for(int j=0; j<sensorWeightVector.size(); j++) {
            	if(aggErrors.size()==sensorWeightVector.size()) {
	            	double one = aggErrors.remove(j);
	            	aggErrors.add(j, one+currentWeight*sensorWeightVector.get(j));
            	}
            	else
            		aggErrors.add(currentWeight*sensorWeightVector.get(j));
            }
            System.out.println("���ε�����ļ�Ȩ����� " + aggErrors);
            double error_P = 1;
            double error_N = 0;
            for(int j=0; j<aggErrors.size(); j++) {
            	if((aggErrors.get(j)<=0) && (dataSet.get(j).get(dataSet.get(0).size()-1)!=-1))
            		error_N+=1;
            	else if((aggErrors.get(j)>0) && (dataSet.get(j).get(dataSet.get(0).size()-1)!=1))
            		error_N+=1;
            }
            error_P=error_N/aggErrors.size();
            System.out.println("������: " + error_P);
            System.out.println("------------------------------------------------------------------------");
            //�Ѿ�����ȫ������ȷ
            if(error_P==0)
            	break;
            
            
//            //����ϣ���ѧϰ��
//            if(error_P>LastError_P)
//            	break;
//            else
//            	LastError_P=error_P;
            
            
            //�������ݼ���ÿ�����ݵ�Ȩ�ز���һ��  
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
	
	//����Ŀǰ����������������һ��������������ж�
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
