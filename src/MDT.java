import java.util.ArrayList;
/*
 *单层决策树弱分类器
 */
public class MDT {
	//记录是第几个特征的阈值，threshold[0]是第几个特征的下标，threshold[1]是对应的阈值，threshold[2]是判断方法
	static double[] threshold = new double[3];
	
	public static ArrayList<Double> main(ArrayList<ArrayList<Double>> dataSet, ArrayList<Double> dataWeightSet) {
		//每个特征的最大值和最小值
		ArrayList<Double> max = new ArrayList<Double>();
		ArrayList<Double> min = new ArrayList<Double>();
		//每个特征对应的迭代步长
		ArrayList<Double> step = new ArrayList<Double>();
		
		//选出每个特征的最大值和最小值，为后面的阈值计算做好准备
		//对应每一个样本
		for(int i=0; i<dataSet.size(); i++)
			//对应每一个特征
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
		
		//将每个特征值的阈值设为10个，每个特征的最小值减一个步长为初始阈值
		for(int i=0; i<max.size(); i++)
			step.add((max.get(i)-min.get(i))/11);
		return MDT.dataResult(min, step, dataSet, dataWeightSet);
	}
	public static ArrayList<Double> dataResult(ArrayList<Double> min, ArrayList<Double> step, ArrayList<ArrayList<Double>> dataSet, ArrayList<Double> dataWeightSet){
		ArrayList<Double> result = new ArrayList<Double>();
		//记录最低错误率,初始化为1
		double error_min = 1.0;
		//对应每一个特征
		for(int i=0; i<min.size(); i++) {
			//对应每个步长
			for(int time=0; time<=11; time++) {
				//阈值
				double len=min.get(i)+(time-1)*step.get(i);
				//对应每个不等号（即两种判断分类的方法）
				for(int c=0; c<2; c++) {
					//记录每个样本是否错误，0为正确，1为错误，默认全部判断正确
					ArrayList<Double> errorSet = new ArrayList<Double>();
					if(c==0) {
						//判断样本类别方法一（小于等于是-1类，大于是1类）
						//针对每一个样本
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
						//判断样本类别方法二（小于等于是1类，大于是-1类）
						//针对每一个样本
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
					//计算加权错误率
					double error=0.0;
					for(int j=0; j<dataSet.size(); j++) {
						error=error+errorSet.get(j)*dataWeightSet.get(j);
					}
//					System.out.println(len + " " + error);
					//更新最优结果
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
		System.out.println("本次弱分类器加权最小错误率： " + error_min);
		return result;
	}
}
