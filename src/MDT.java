import java.util.ArrayList;
/*
 *�����������������
 */
public class MDT {
	//��¼�ǵڼ�����������ֵ��threshold[0]�ǵڼ����������±꣬threshold[1]�Ƕ�Ӧ����ֵ��threshold[2]���жϷ���
	static double[] threshold = new double[3];
	
	public static ArrayList<Double> main(ArrayList<ArrayList<Double>> dataSet, ArrayList<Double> dataWeightSet) {
		//ÿ�����������ֵ����Сֵ
		ArrayList<Double> max = new ArrayList<Double>();
		ArrayList<Double> min = new ArrayList<Double>();
		//ÿ��������Ӧ�ĵ�������
		ArrayList<Double> step = new ArrayList<Double>();
		
		//ѡ��ÿ�����������ֵ����Сֵ��Ϊ�������ֵ��������׼��
		//��Ӧÿһ������
		for(int i=0; i<dataSet.size(); i++)
			//��Ӧÿһ������
			for(int j=0; j<dataSet.get(0).size()-1; j++) {
				if(i==0)
					max.add(dataSet.get(i).get(j));
				else if(dataSet.get(i).get(j)>max.get(j)) {
					max.remove(j);
					max.add(j, dataSet.get(i).get(j));
				}
				if(i==0)
					min.add(dataSet.get(i).get(j));
				else if(dataSet.get(i).get(j)<min.get(j)) {
					min.remove(j);
					min.add(j, dataSet.get(i).get(j));
				}
			}
		
		//��ÿ������ֵ����ֵ��Ϊ10����ÿ����������Сֵ��һ������Ϊ��ʼ��ֵ
		for(int i=0; i<max.size(); i++)
			step.add((max.get(i)-min.get(i))/11);
		return MDT.dataResult(min, step, dataSet, dataWeightSet);
	}
	public static ArrayList<Double> dataResult(ArrayList<Double> min, ArrayList<Double> step, ArrayList<ArrayList<Double>> dataSet, ArrayList<Double> dataWeightSet){
		ArrayList<Double> result = new ArrayList<Double>();
		//��¼��ʹ�����,��ʼ��Ϊ1
		double error_min = 1.0;
		//��Ӧÿһ������
		for(int i=0; i<min.size(); i++) {
			//��Ӧÿ������
			for(int time=0; time<=11; time++) {
				//��ֵ
				double len=min.get(i)+(time-1)*step.get(i);
				//��Ӧÿ�����Ⱥţ��������жϷ���ķ�����
				for(int c=0; c<2; c++) {
					//��¼ÿ�������Ƿ����0Ϊ��ȷ��1Ϊ����Ĭ��ȫ���ж���ȷ
					ArrayList<Double> errorSet = new ArrayList<Double>();
					if(c==0) {
						//�ж�������𷽷�һ��С�ڵ�����-1�࣬������1�ࣩ
						//���ÿһ������
						for(int j=0; j<dataSet.size(); j++) {
							if(dataSet.get(j).get(i)<=len) {
								if(dataSet.get(j).get(dataSet.get(0).size()-1)==-1d)
									errorSet.add(j, 0.0);
								else
									errorSet.add(j, 1.0);
							}
							else {
								if(dataSet.get(j).get(dataSet.get(0).size()-1)==1d)
									errorSet.add(j, 0.0);
								else
									errorSet.add(j, 1.0);
							}
						}
					}
					else {
						//�ж�������𷽷�����С�ڵ�����1�࣬������-1�ࣩ
						//���ÿһ������
						for(int j=0; j<dataSet.size(); j++) {
							if(dataSet.get(j).get(i)>len) {
								if(dataSet.get(j).get(dataSet.get(0).size()-1)==-1d)
									errorSet.add(j, 0.0);
								else
									errorSet.add(j, 1.0);
							}
							else {
								if(dataSet.get(j).get(dataSet.get(0).size()-1)==1d)
									errorSet.add(j, 0.0);
								else
									errorSet.add(j, 1.0);
							}
						}
					}
					//�����Ȩ������
					double error=0.0;
					for(int j=0; j<dataSet.size(); j++) {
						error=error+errorSet.get(j)*dataWeightSet.get(j);
					}
//					System.out.println(len + " " + error);
					//�������Ž��
					if(error_min>error) {
						error_min=error;
						result=errorSet;
						threshold[0]=i;
						threshold[1]=len;
						threshold[2]=c;
					}
				}
			}
		}
		System.out.println("��������������Ȩ��С�����ʣ� " + error_min);
		return result;
	}
}
