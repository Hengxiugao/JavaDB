

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CommandStr {
	String cmdstr;
	static String DBPath = new String("datab.db");
	
	final String[] table = {"id", "company", "drug_id","trials","patients","dosage_mg","reading","deleted_flag","double_blind","controlled_study", "govt_funded","fda_approved"};
	
	public CommandStr(String cmdstr){
		this.cmdstr=cmdstr;
	}
	
	public void commandParse(){
		cmdstr.trim();
		cmdstr = cmdstr.toLowerCase();
		String which = cmdstr.substring(0, cmdstr.indexOf(" "));
		//import command
		if(which.equals("import")){
			System.out.println("import command!");
		    String filename = cmdstr.substring(cmdstr.indexOf(" "), cmdstr.length());
		    
		    System.out.println("filename is: "+filename);
		    
		    this.cmmandImport(filename);
		}
		//select command
		else if(which.equals("select")){
			System.out.println("select cmd: "+cmdstr);
			
			resultOutput(this.cmmandSelect());
		}
		//delete command
		else if(which.equals("delete")){
			System.out.println("Delete cmd: "+cmdstr);
			
			ArrayList<DataTuple> res=this.cmmandSelect();
			writefile.writerDB(DBPath, res, "delete");  // delete  unset
			
		}
		//insert command
		else if(which.equals("insert")){
			System.out.println("Insert cmd: "+cmdstr);
			
			DataTuple instr = parseInsert(cmdstr);
			cmdstr=" where "+"id = "+Integer.toString(instr.id)+";";
			
			ArrayList<DataTuple> res=this.cmmandSelect();
			
			System.out.println("cmdstr:"+cmdstr);
			if(!res.isEmpty()){
				
				instr.address=res.get(0).address;
				res.clear();
				res.add(instr);
				writefile.writerDB_single(DBPath, instr, 1); //overwrite record
			}
			else{
				System.out.println("res is empty:"+res.isEmpty());
				res.clear();
				res.add(instr);
				writefile.writerDB_single(DBPath, instr, 2); //append record to end 
				
			} /* */
			//load DB
			ArrayList<DataTuple> dt = new ArrayList<DataTuple>();
			dt.clear();
			Readfile.readDBfile(dt,"Read through .db file");
			
			upDateNdx(dt);
		}
		//Undefined command
		else
			System.out.println("Undefined!");	

	}
	
	public void upDateNdx(ArrayList<DataTuple> dataT){
		writefile.writerndx("id.ndx",dataT,0,1);			
		writefile.writerndx("trials.ndx",dataT,3,1); // Parameter: file name ,data, sort attributes & output attribute ,overload type
		writefile.writerndx("patients.ndx",dataT,4,1);
		writefile.writerndx("dosage_mg.ndx",dataT,5,1);
		
		writefile.writerndx("company.ndx",dataT,1,"company");
		writefile.writerndx("drug_id.ndx",dataT,2,"drug_id");
		
		writefile.writerndx("reading.ndx",dataT,6,(float)1);
		
		writefile.writerndx("deleted_flag.ndx",dataT,8,true);
		writefile.writerndx("double_blind.ndx",dataT,9,true);
		writefile.writerndx("controlled_study.ndx",dataT,10,true);
		writefile.writerndx("govt_funded.ndx",dataT,11,true);
		writefile.writerndx("fda_approved.ndx",dataT,12,true);
	}
	
	public DataTuple parseInsert(String cmstr){
		String valuefield = cmstr.substring(cmstr.toLowerCase().indexOf("values ")+7, cmstr.indexOf(";"));
		StringTokenizer st = new StringTokenizer(valuefield,","); 
		if(st.countTokens()<8){
			System.out.println("No enough value list! ");
			return null;
		}
/*		DataTuple res=new DataTuple("0", 0);	
		while (st.hasMoreTokens()) {
			 String nextt=st.nextToken();
	         for(int j=0;j<12;j++){
	        	 if(j==7)
	        		 continue;
	        	 
	         }
	     } */
		
		String next1=st.nextToken();
		String next2=st.nextToken();
		String next3=st.nextToken();
		String next4=st.nextToken();
		String next5=st.nextToken();
		
		String next6=st.nextToken();
		String next7=st.nextToken();
		String next8=st.nextToken();
		
		DataTuple res = new DataTuple(Integer.parseInt(next1), next2, next3, 
				Integer.parseInt(next4), Integer.parseInt(next5),
				Integer.parseInt(next6), Float.parseFloat(next7), Byte.parseByte(next8));
//		res.displaydata("Print all!");
		return res;
	}
	
	public void resultOutput(ArrayList<DataTuple> res ){
		String outputf = cmdstr.substring(cmdstr.toLowerCase().indexOf("select ")+7,
				cmdstr.toLowerCase().indexOf(" from"));
		
		if(outputf.contains("*")){
			for(int j=0;j<table.length;j++){
	        		 DataTuple.printlist[j]=true;
	         }
		}
		else{
			StringTokenizer st = new StringTokenizer(outputf," "); 
			while (st.hasMoreTokens()) {
				 String nextt=st.nextToken();
		         for(int j=0;j<table.length;j++){
		        	 if(table[j].equals(nextt))
		        		 DataTuple.printlist[j]=true;
		         }
		     }
		}
		DataTuple.DisplayTitle();
		for(DataTuple dt:res)
			dt.DisplayData();  
	}
	
	public ArrayList<DataTuple> cmmandSelect(){
		int div1=0,div2=0,cmpcase=0;
		
		String condition = cmdstr.substring(cmdstr.indexOf(" where ")+7,cmdstr.indexOf(";"));
		condition =condition.trim();
		
		System.out.println("Condition: "+condition);	
		if(condition.contains(" not ")){
			System.out.println("Condition: "+condition);	
			div1=condition.indexOf(" not ");
			div2=condition.indexOf(" not ")+5;
		}
		else if(condition.contains(">")){
			div1=condition.indexOf(">");
			div2=condition.indexOf(">")+1;
			cmpcase=1;
		}
		else if(condition.contains(">=")){
			div1=condition.indexOf(">=");
			div2=condition.indexOf(">=")+1;
			cmpcase=2;
		}
		else if(condition.contains("<")){
			div1=condition.indexOf("<");
			div2=condition.indexOf("<")+1;
			cmpcase=3;
		}
		else if(condition.contains("<=")){
			div1=condition.indexOf("<=");
			div2=condition.indexOf("<=")+1;
			cmpcase=4;
		}
		else if(condition.contains("=")){
			div1=condition.indexOf("=");
			div2=condition.indexOf("=")+1;
			cmpcase=5;
		}
		else
			System.out.println("Error!");
		
		String tablename=condition.substring(0, div1).trim();
		String value = condition.substring(div2, condition.length()).trim();
		
		System.out.println("value:"+value);
		int i=0;
		while(!tablename.equals(table[i]))//get the num of table name 
			i++;
		
		ArrayList<DataTuple> res=new ArrayList<DataTuple>();
		DataTuple cmpbase = new DataTuple(value,i);
		try{
			File file = new File(table[i]+".ndx");
			InputStream ism =new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ism));
			String readlin = reader.readLine();
		    
			while(readlin!=null){
			    String entry =readlin.substring(0, readlin.indexOf("\t"));
			    readlin=readlin.substring(readlin.indexOf("\t"), readlin.length());//first element is \t
			    DataTuple cmpsample = new DataTuple(entry,i);
			    
			    switch(cmpcase){
			    	case 0:
			    		if(cmpsample.compareTo(cmpbase)!=0){
			    			res.addAll(Readfile.readaddress(readlin));
			    		}
			    		break;
			    	case 1:
			    		if(cmpsample.compareTo(cmpbase)>0){
			    			res.addAll(Readfile.readaddress(readlin));
			    		}
			    		break;
			    	case 2:
			    		if(cmpsample.compareTo(cmpbase)>=0){
			    			res.addAll(Readfile.readaddress(readlin));
			    		}
			    		break;
			    	case 3:
			    		if(cmpsample.compareTo(cmpbase)<0){
			    			res.addAll(Readfile.readaddress(readlin));
			    		}
			    		break;
			    	case 4:
			    		if(cmpsample.compareTo(cmpbase)<=0){
			    			res.addAll(Readfile.readaddress(readlin));
			    		}
			    		break;
			    	case 5:
			    		if(cmpsample.compareTo(cmpbase)==0){
			    			res.addAll(Readfile.readaddress(readlin));
			    		}
			    		break;
			    	default:;
			    }
			    readlin = reader.readLine();
			}
			
			Readfile.readDBfile(res); //result set has been assembled

			
		}catch(Exception e){
			e.printStackTrace();
		}
	    return res;
	}
	
	public void cmmandImport(String filename){
		ArrayList<DataTuple> dataT = new ArrayList<DataTuple>();
		int address=0;
		
		try{
			File file = new File(filename.trim());
			InputStream ism =new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ism));
			String readlin = reader.readLine();
		    readlin = reader.readLine(); //ignore first line
			
			
			while(readlin!=null){
				
				DataTuple tuple=FilePar.lineParse(readlin);
				tuple.address=address;
							
				dataT.add(tuple);
				address=address+22+tuple.company.length();
				readlin = reader.readLine();
			}
	//		dataT.get(500).displaydata();
	//		RandomAccessFile rfile =  new RandomAccessFile(new File(DBPath),"rw");
			writefile.writerDB(DBPath, dataT);
			
			writefile.writerndx("id.ndx",dataT,0,1);			
			writefile.writerndx("trials.ndx",dataT,3,1); // Parameter: file name ,data, sort attributes & output attribute ,overload type
			writefile.writerndx("patients.ndx",dataT,4,1);
			writefile.writerndx("dosage_mg.ndx",dataT,5,1);
			
			writefile.writerndx("company.ndx",dataT,1,"company");
			writefile.writerndx("drug_id.ndx",dataT,2,"drug_id");
			
			writefile.writerndx("reading.ndx",dataT,6,(float)1);
			
			writefile.writerndx("deleted_flag.ndx",dataT,8,true);
			writefile.writerndx("double_blind.ndx",dataT,9,true);
			writefile.writerndx("controlled_study.ndx",dataT,10,true);
			writefile.writerndx("govt_funded.ndx",dataT,11,true);
			writefile.writerndx("fda_approved.ndx",dataT,12,true);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
