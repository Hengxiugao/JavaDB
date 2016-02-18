

import java.util.Comparator;

public class DataTuple implements Comparable {
	int id;
	String company;
	String drug_id;
	int trials;
	int patients;
	int dosage_mg;
	float reading;
	byte fiveboolean;
	
	boolean deleted_flag=false;
	boolean double_blind;
	boolean controlled_study;
	boolean govt_funded;
	boolean fda_approved;
	
	int address;
	
	static int sortbase = 0;
	static boolean[] printlist = new boolean[]{false,false,false,false,false, false,false,false,
		                                       false,false, false,false};
	public int getvalue(int a){
		switch (sortbase){
			case 0:
				return id;
			case 3:
				return trials;
			case 4:
				return patients;
			case 5:
				return dosage_mg;
			default:
				return -1;
		}
	}
	
	public String getvalue(String a){
		switch (sortbase){
			case 1:
				return company;
			case 2:
				return drug_id;			
			default:
				return null;
		}
	}
	
	public float getvalue(float a){
		return reading;
	}
	
	public boolean getvalue(boolean a){

		switch (sortbase){
			case 7:
				return deleted_flag;
			case 8:
				return double_blind;	
			case 9:
				return controlled_study;					
			case 10:
				return govt_funded;	
			case 11:
				return fda_approved;	
			default:
				return false;
		}
	}

	
	public DataTuple(int id, String company, String drug_id, int trials, int patients, int dosage_mg, float reading, byte fiveboolean){
		this.id = id;
		this.company = company;
		this.drug_id = drug_id;
		this.trials = trials;
		this.patients = patients;
		this.dosage_mg = dosage_mg;
		this.reading = reading;
		this.fiveboolean = fiveboolean;
		BooleanOperation(fiveboolean);
	}
/*	public DataTuple(int id, int base){
		this.sortbase=base;
		switch (sortbase){
			case 0:this.id = id; break;
			case 3:this.trials = id; break;
			case 4:this.patients = id;break;
			case 5:this.dosage_mg = id;break;
			default:;
		}
	}*/
	
	public DataTuple(String str, int base){
		this.sortbase=base;
		switch (sortbase){
			case 0:this.id = Integer.parseInt(str); break;
			case 3:this.trials = Integer.parseInt(str); break;
			case 4:this.patients = Integer.parseInt(str);break;
			case 5:this.dosage_mg = Integer.parseInt(str);break;
		
			case 1:this.company = str;break;
			case 2:this.drug_id = str;break;
			
			case 6:this.reading=Float.parseFloat(str); break;
			
			case 7:this.deleted_flag=Boolean.parseBoolean(str); break;
			case 8:this.double_blind=Boolean.parseBoolean(str); break;
			case 9:this.controlled_study=Boolean.parseBoolean(str); break;
			case 10:this.govt_funded=Boolean.parseBoolean(str); break;
			case 11:this.fda_approved=Boolean.parseBoolean(str); break;
			default:;
			
		}
	}
	public DataTuple(int base){
		this.address=base;
	} 
	
	void BooleanOperation(byte inputbyte){
		byte deletfla = (byte) 0x80;
		byte doublind = (byte) 0x08;
		byte cs = (byte) 0x04;
		byte gf = (byte) 0x02;
		byte fa = (byte) 0x01;
		
		this.deleted_flag=(deletfla & inputbyte)!=0;
		this.double_blind=(doublind & inputbyte)>0;
		this.controlled_study=(cs & inputbyte)>0;
		this.govt_funded=(gf & inputbyte)>0;
		this.fda_approved=(fa & inputbyte)>0;
	}
	
	public static void DisplayTitle(){
		int i=0;
		for(i=0;i<12;i++){
			if(printlist[i]==true){
				switch (i){
				case 0:	System.out.print("id\t"); break;
				case 1:	System.out.print("company\t"); break;
				case 2:	System.out.print("drug_id\t"); break;
				case 3:	System.out.print("trials\t"); break;
				case 4:	System.out.print("patients\t"); break;
				case 5:	System.out.print("dosage_mg\t"); break;
				case 6:	System.out.print("reading\t"); break;
				case 7:	System.out.print("deleted_flag\t"); break;
				case 8:	System.out.print("double_blind\t"); break;
				case 9:	System.out.print("controlled_study\t");break; 
				case 10:	System.out.print("govt_funded\t"); break;
				case 11:	System.out.print("fda_approved\t"); break;
				}
				
			}
		}
		System.out.print("\n");
	}
	
	public void DisplayData(){
		int i=0;
		if(deleted_flag==true)
			return;
		for(i=0;i<12;i++){
			if(printlist[i]==true){
				switch (i){
					case 0:	System.out.print(id+"\t"); break;
					case 1:	System.out.print(company+"\t"); break;
					case 2:	System.out.print(drug_id+"\t"); break;
					case 3:	System.out.print(trials+"\t"); break;
					case 4:	System.out.print(patients+"\t"); break;
					case 5:	System.out.print(dosage_mg+"\t"); break;
					case 6:	System.out.print(reading+"\t"); break;
					case 7:	System.out.print(deleted_flag+"\t");  break;
					case 8:	System.out.print(double_blind+"\t"); break;
					case 9:	System.out.print(controlled_study+"\t");break;
					case 10:	System.out.print(govt_funded+"\t"); break;
					case 11:	System.out.print(fda_approved+"\t"); break;
				}
				
			}		
		}
		System.out.print("\n");
	}
	
	public void  DisplayData(String str){
		
					System.out.print(id+"\t"); 
					System.out.print(company+"\t");
					System.out.print(drug_id+"\t"); 
				System.out.print(trials+"\t"); 
					System.out.print(patients+"\t");
					System.out.print(dosage_mg+"\t");
					System.out.print(reading+"\t"); 
					System.out.print(deleted_flag+"\t"); 
					System.out.print(double_blind+"\t"); 
				System.out.print(controlled_study+"\t");
					System.out.print(govt_funded+"\t"); 
						System.out.print(fda_approved+"\t");
				
		System.out.print("\n");
	}
	static public Comparator<DataTuple> getCompar()
	{
		return new compar();
	}
	
	public int compareTo(DataTuple dt2){
		switch (sortbase){
		case 0:
			return this.id-dt2.id;
		case 1:
			return this.company.toLowerCase().compareTo(dt2.company.toLowerCase());
		case 2:
			return this.drug_id.toLowerCase().compareTo(dt2.drug_id.toLowerCase());
		case 3:
			return this.trials-dt2.trials;
		case 4:
			return this.patients-dt2.patients;
		case 5:
			return this.dosage_mg-dt2.dosage_mg;
		case 6:
			return Float.compare(this.reading,dt2.reading);
		case 7:
			return Boolean.compare(this.deleted_flag, dt2.deleted_flag);
		case 8:
			return Boolean.compare(this.double_blind, dt2.double_blind);
		case 9:
			return Boolean.compare(this.controlled_study, dt2.controlled_study);
		case 10:
			return Boolean.compare(this.govt_funded, dt2.govt_funded);
		case 11: 
			return Boolean.compare(this.fda_approved, dt2.fda_approved);
		default:
			return this.id-dt2.id;
	}
	}
	static public class compar implements Comparator<DataTuple>{
	
		public int compare(DataTuple dt1, DataTuple dt2){
			switch (sortbase){
				case 0:
					return dt1.id-dt2.id;
				case 1:
					return dt1.company.toLowerCase().compareTo(dt2.company.toLowerCase());
				case 2:
					return dt1.drug_id.toLowerCase().compareTo(dt2.drug_id.toLowerCase());
				case 3:
					return dt1.trials-dt2.trials;
				case 4:
					return dt1.patients-dt2.patients;
				case 5:
					return dt1.dosage_mg-dt2.dosage_mg;
				case 6:
					return Float.compare(dt1.reading,dt2.reading);
				case 7:
					return Boolean.compare(dt1.deleted_flag, dt2.deleted_flag);
				case 8:
					return Boolean.compare(dt1.double_blind, dt2.double_blind);
				case 9:
					return Boolean.compare(dt1.controlled_study, dt2.controlled_study);
				case 10:
					return Boolean.compare(dt1.govt_funded, dt2.govt_funded);
				case 11: 
					return Boolean.compare(dt1.fda_approved, dt2.fda_approved);
				default:
					return dt1.id-dt2.id;
			}
		}
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
