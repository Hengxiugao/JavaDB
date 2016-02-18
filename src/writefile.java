

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;


public class writefile {
	public static void writerDB(String DBPath, ArrayList<DataTuple> tuples){
				
		try{
				RandomAccessFile rfile =  new RandomAccessFile(new File(DBPath),"rw");
				for(int i=0;i<tuples.size();i++){
					
					rfile.writeInt( tuples.get(i).id);         //4 byte
					rfile.writeByte(tuples.get(i).company.length());//length of company
					rfile.writeBytes(tuples.get(i).company);   //varchar
					rfile.writeBytes(tuples.get(i).drug_id);   //6 byte
					rfile.writeShort(tuples.get(i).trials);    //2 byte
					rfile.writeShort(tuples.get(i).patients);  //2 byte
					rfile.writeShort(tuples.get(i).dosage_mg); //2 byte
					rfile.writeFloat(tuples.get(i).reading);   //4 byte
					rfile.writeByte(tuples.get(i).fiveboolean);//1 byte
			    }
				rfile.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writerDB_single(String DBPath, DataTuple tuples,int which){
		
		try{
				RandomAccessFile rfile =  new RandomAccessFile(new File(DBPath),"rw");
				if(which==2){   //append record to the end
					rfile.seek(rfile.length());
				}
				else
					rfile.seek(tuples.address);
				rfile.writeInt( tuples.id);         //4 byte
				rfile.writeByte(tuples.company.length());//length of company
				rfile.writeBytes(tuples.company);   //varchar
				rfile.writeBytes(tuples.drug_id);   //6 byte
				rfile.writeShort(tuples.trials);    //2 byte
				rfile.writeShort(tuples.patients);  //2 byte
				rfile.writeShort(tuples.dosage_mg); //2 byte
				rfile.writeFloat(tuples.reading);   //4 byte

				rfile.writeByte((byte)tuples.fiveboolean);//1 byte 
				rfile.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void writerDB(String DBPath, ArrayList<DataTuple> tuples,String str){
		
		try{
				RandomAccessFile rfile =  new RandomAccessFile(new File(DBPath),"rw");
				for(int i=0;i<tuples.size();i++){
					if(str.startsWith("append")){   //append record to the end
						rfile.seek(rfile.length());
					}
					else
						rfile.seek(tuples.get(i).address);
					rfile.writeInt( tuples.get(i).id);         //4 byte
					rfile.writeByte(tuples.get(i).company.length());//length of company
					rfile.writeBytes(tuples.get(i).company);   //varchar
					rfile.writeBytes(tuples.get(i).drug_id);   //6 byte
					rfile.writeShort(tuples.get(i).trials);    //2 byte
					rfile.writeShort(tuples.get(i).patients);  //2 byte
					rfile.writeShort(tuples.get(i).dosage_mg); //2 byte
					rfile.writeFloat(tuples.get(i).reading);   //4 byte
					if(str.startsWith("delete"))
						rfile.writeByte((byte)tuples.get(i).fiveboolean+0x80);
					else if(str.startsWith("unset"))
						rfile.writeByte((byte)tuples.get(i).fiveboolean&0x0f);
					else
						rfile.writeByte((byte)tuples.get(i).fiveboolean);//1 byte 
			    }
				rfile.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writerndx(String ndxPath, ArrayList<DataTuple> tuples,int sortcfg,int a){
		try{                                                              // use int a to triger the overload
			DataTuple.sortbase=sortcfg;//Decide base on which attributes to output and sort
	//		DataTuple.gettype=outcfg; 
			
			Collections.sort(tuples,DataTuple.getCompar());
			FileWriter rfile = new FileWriter(ndxPath);

			int current_entry =tuples.get(0).getvalue(a);
			rfile.write( Integer.toString(current_entry));
			for(int i=0;i<tuples.size();i++){
				if(tuples.get(i).getvalue(a)==current_entry){
					rfile.write('\t');
					rfile.write( Integer.toString(tuples.get(i).address));
				}
				else{
					current_entry =tuples.get(i).getvalue(a);
					rfile.write('\n');
					rfile.write( Integer.toString(current_entry));
					rfile.write('\t');
					rfile.write( Integer.toString(tuples.get(i).address)); //write down address
				}
					
			}
			rfile.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void writerndx(String ndxPath, ArrayList<DataTuple> tuples,int sortcfg,String a){
		try{                                                              // use int a to triger the overload
			DataTuple.sortbase=sortcfg;//Decide base on which attributes to output and sort
			
			Collections.sort(tuples,DataTuple.getCompar());
			FileWriter rfile = new FileWriter(ndxPath);

			String current_entry =tuples.get(0).getvalue(a);
			rfile.write( current_entry);
			for(int i=0;i<tuples.size();i++){
				if(tuples.get(i).getvalue(a).equals(current_entry)){
					rfile.write('\t');
					rfile.write( Integer.toString(tuples.get(i).address));
				}
				else{
					current_entry =tuples.get(i).getvalue(a);
					rfile.write('\n');
					rfile.write( current_entry);
					rfile.write('\t');
					rfile.write( Integer.toString(tuples.get(i).address)); //write down address
				}
					
			}
			rfile.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void writerndx(String ndxPath, ArrayList<DataTuple> tuples,int sortcfg,float a){
		try{                                                              // use int a to triger the overload
			DataTuple.sortbase=sortcfg;//Decide base on which attributes to output and sort
			
			Collections.sort(tuples,DataTuple.getCompar());
			FileWriter rfile = new FileWriter(ndxPath);

			float current_entry =tuples.get(0).getvalue(a);
			rfile.write( Float.toString(current_entry));
			for(int i=0;i<tuples.size();i++){
				if(tuples.get(i).getvalue(a)==current_entry){
					rfile.write('\t');
					rfile.write( Integer.toString(tuples.get(i).address));
				}
				else{
					current_entry =tuples.get(i).getvalue(a);
					rfile.write('\n');
					rfile.write( Float.toString(current_entry));
					rfile.write('\t');
					rfile.write( Integer.toString(tuples.get(i).address)); //write down address
				}
					
			}
			rfile.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void writerndx(String ndxPath, ArrayList<DataTuple> tuples,int sortcfg,boolean a){
		try{                                                              // use int a to triger the overload
			DataTuple.sortbase=sortcfg;//Decide base on which attributes to output and sort
			
			Collections.sort(tuples,DataTuple.getCompar());
			FileWriter rfile = new FileWriter(ndxPath);

			boolean current_entry =tuples.get(0).getvalue(a);
			rfile.write( Boolean.toString(current_entry));
			for(int i=0;i<tuples.size();i++){
				if(tuples.get(i).getvalue(a)==current_entry){
					rfile.write('\t');
					rfile.write( Integer.toString(tuples.get(i).address));
				}
				else{
					current_entry =tuples.get(i).getvalue(a);
					rfile.write('\n');
					rfile.write( Boolean.toString(current_entry));
					rfile.write('\t');
					rfile.write( Integer.toString(tuples.get(i).address)); //write down address
				}
					
			}
			rfile.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
