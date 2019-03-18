import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
/*
 * 主函数，进行调用Adaboost学习架构
 */
public class Adaboost {
	public static void main(String[] args) throws FileNotFoundException { 
		//记录测试集
		Map<ArrayList<Double>, Integer> test = new HashMap<ArrayList<Double>, Integer>();
		//初始化数据集
		ArrayList<ArrayList<Double>> dataSet = Dataset.main();  
		//初始化每一条数据权重
		ArrayList<Double> weightSet = new ArrayList<Double>();
        for(int i=0; i<dataSet.size(); i++)
        	weightSet.add((double)1/dataSet.size());
        AdaboostResult res = AdaboostAlgorithm.adaboostClassify(dataSet, weightSet);
		
        
//		//导入文件测试集
//		File file = new File("E:\\Learn\\A&C\\Adaboost\\data\\test.txt");
//		Scanner in = new Scanner(file);
//		while(in.hasNext()) {
//			String s = in.nextLine();
//			String[] str = s.split(",");
//			ArrayList<Double> one = new ArrayList<Double>();
//			for(int i=0; i<str.length-1; i++) {
//				one.add(Double.valueOf(str[i].toString()));
//			}
//			double d = Double.valueOf(str[str.length-1].toString());
//			int integer = (int)d;
//			test.put(one,integer);
//		}
//		in.close();
		

//        //计算正确率
//        int right=0;
//        for(Entry<ArrayList<Double>, Integer> entry: test.entrySet()) {
//        	ArrayList<Double> data = entry.getKey();
//        	double result = AdaboostAlgorithm.computeResult(data, res);
//        	if(result==entry.getValue())
//        		right++;
//        }
//        System.out.println("\n\n正确率" + right + "%");
    }
}