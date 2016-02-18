

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Readfile {
	public static ArrayList<DataTuple> readaddress(String readlin){
		ArrayList<DataTuple> res =new ArrayList<DataTuple>();
		
		StringTokenizer st = new StringTokenizer(readlin,"\t"); 
		while (st.hasMoreTokens()) {  
			 String nextt=st.nextToken();
	         DataTuple dt = new DataTuple(Integer.parseInt(nextt));
	         res.add(dt);
	     }  
		
		return res;
	}
	
//	public static ArrayList<DataTuple> readDBfile(ArrayList<DataTuple> dt){
	public static void readDBfile(ArrayList<DataTuple> dt){	
		try{ 
			RandomAccessFile rfile =  new RandomAccessFile(new File(CommandStr.DBPath),"r");
			for(int i=0;i<dt.size();i++){
				rfile.seek(dt.get(i).address);
				readDBrecord(dt.get(i),rfile);
			}

			rfile.close();
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
//		for(DataTuple dt1:dt)
//			dt1.displaydata();
//		return dt;
	}
	
	public static void readDBfile(ArrayList<DataTuple> dt,String str){
		try{
			dt.clear();
			RandomAccessFile rfile =  new RandomAccessFile(new File(CommandStr.DBPath),"r");
			while(rfile.getFilePointer()<rfile.length()){
				DataTuple tem = new DataTuple("0",0);
				readDBrecord(tem,rfile);
				dt.add(tem);
			}

			rfile.close();
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void readDBrecord(DataTuple dt,RandomAccessFile rf) throws IOException{
		
		dt.address=(int) rf.getFilePointer();
//		System.out.println("raf readDB :"+dt.address);
		dt.id=rf.readInt();		
		int strlen = rf.readByte();
		
		byte[] temp= new byte[strlen];
		rf.read(temp);
		dt.company = new String(temp);
		
		
		byte[] temp1= new byte[6];
		rf.read(temp1);
		dt.drug_id = new String(temp1);

		dt.trials = rf.readShort();
		dt.patients = rf.readShort();
		dt.dosage_mg = rf.readShort();
		dt.reading =rf.readFloat();
		dt.fiveboolean=rf.readByte();
		dt.BooleanOperation(dt.fiveboolean);
		
		
	}
}
