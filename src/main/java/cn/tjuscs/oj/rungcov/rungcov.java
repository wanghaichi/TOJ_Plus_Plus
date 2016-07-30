package cn.tjuscs.oj.rungcov;
import java.util.*;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;

import java.io.*;

public class rungcov {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		runAndGetMat("testid","2800");
	}
	public static void runAndGetMat(String sid, String pid) throws NumberFormatException, IOException{
		System.out.println("haha");
		//sid = "1131123";
		//pid = "1007";
		String workpath = new File("./").getCanonicalPath();
		System.out.println(workpath);
		String dataPath = "./data/toj_problem_"+pid;
		dataPath = new File(dataPath).getCanonicalPath();
		String casenumFileName = dataPath+"\\splitedTestCases\\" + pid + "_total.txt";
		String inputFileName = dataPath+"\\splitedTestCases\\" + pid + "_";
		String outputFileName = dataPath+"\\splitedTestCases\\output";
		String srcFileName = dataPath+"\\programs\\commit_id_"+sid+"\\"+sid;
		FileReader cnst = new FileReader(casenumFileName);
		BufferedReader fin = new BufferedReader(cnst);
		int casenum = Integer.valueOf(fin.readLine()).intValue();
		ExecuteWindowsCommand CMD = null;
		ExecuteWindowsCommand.execute("g++ "+srcFileName+".cpp -o "+srcFileName + ".exe -ftest-coverage -fprofile-arcs");
		ExecuteWindowsCommand.execute("move "+sid+".gcda "+dataPath+"\\programs\\commit_id_"+sid);
		ExecuteWindowsCommand.execute("move "+sid+".gcno "+dataPath+"\\programs\\commit_id_"+sid);
		ExecuteWindowsCommand.execute("move "+sid+".gcda "+dataPath+"\\programs\\commit_id_"+sid);
		for(int i=0;i<casenum;i++){
			ExecuteWindowsCommand.execute(srcFileName+".exe < "+inputFileName+i+".in > "+outputFileName+i+".out");
			ExecuteWindowsCommand.execute("gcov "+srcFileName+".cpp");
//			try{
//			    Thread thread = Thread.currentThread();
//			    thread.sleep(1500);//暂停1.5秒后程序继续执行
//			}catch (InterruptedException e) {
//			    // TODO Auto-generated catch block
//			    e.printStackTrace();
//			} 
			ExecuteWindowsCommand.execute("ren "+sid+".cpp.gcov "+sid+i+".cpp.gcov");
		}
		System.out.println("hah");
		ExecuteWindowsCommand.execute("python "+workpath+"\\linux和python脚本\\compfile.py "+(workpath+"\\outputs.csv ")+(dataPath+"\\" +2800+ ".out ")+(outputFileName)+" "+casenum);
		ExecuteWindowsCommand.execute("python "+workpath+"\\linux和python脚本\\getMatrixFromGcov.py "+workpath+"\\"+sid+" 1 "+casenum);
	}
}
