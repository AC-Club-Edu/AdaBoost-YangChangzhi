import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * ׼�����ݼ�
 */
public class Dataset {
	public static ArrayList<ArrayList<Double>> main() throws FileNotFoundException {
        //��¼����
		ArrayList<ArrayList<Double>> dataSet = new ArrayList<ArrayList<Double>>(); 		        
        //����ѧϰʵս�ϵ����ݼ�
        for(int i=0; i<5; i++) {
        	ArrayList<Double> one = new ArrayList<Double>();
        	switch(i) {
        	case 0:	one.add(1.);
	        		one.add(2.1);
	        		one.add(1.0);
	        		break;
        	case 1: one.add(2.);
        			one.add(1.1);
        			one.add(1.0);
        			break;
        	case 2: one.add(1.3);
        			one.add(1.);
        			one.add(-1.0);
        			break;
        	case 3: one.add(1.);
        			one.add(1.);
        			one.add(-1.0);
        			break;
        	case 4: one.add(2.);
        			one.add(1.);
        			one.add(1.0);
        			break;
        	}
        	dataSet.add(one);
        }
        
        
//      //�ļ�ѵ������ǰ��str��length-1������������str��length�����
//      File file = new File("E:\\Learn\\A&C\\Adaboost\\data\\train.txt");
//      Scanner in = new Scanner(file);
//      while(in.hasNext()) {
//      	String s = in.nextLine();
//      	String[] str = s.split(",");
//      	ArrayList<Double> one = new ArrayList<Double>();
//      	for(int i=0; i<str.length; i++) {
//      		one.add(Double.valueOf(str[i].toString()));
//      	}
//      	dataSet.add(one);
//      }
//      in.close();

        return dataSet;
	}
}





