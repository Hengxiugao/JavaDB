

public class FilePar {
	public static DataTuple lineParse(String readline){
		int id = Integer.parseInt(readline.substring(0, readline.indexOf(',')));
		readline=readline.substring(readline.indexOf(",")+1,  readline.length());
		
		String company;
		if(readline.indexOf('"')>=0){
			company = readline.substring(readline.indexOf('"')+1, readline.lastIndexOf('"'));
			readline = readline.substring(readline.lastIndexOf('"')+2, readline.length());
		}
		else{
			 company = readline.substring(0, readline.indexOf(','));
			 readline = readline.substring(readline.indexOf(',')+1, readline.length());
		}
		
		String drug_id=readline.substring(0, readline.indexOf(','));
		readline=readline.substring(readline.indexOf(',')+1, readline.length());
		//get trials data
		int trials =Integer.parseInt(readline.substring(0, readline.indexOf(',')));
		readline=readline.substring(readline.indexOf(',')+1, readline.length());
		
		int patients =Integer.parseInt(readline.substring(0,readline.indexOf(',')));
		readline=readline.substring(readline.indexOf(',')+1, readline.length());
		
		int dosage_mg = Integer.parseInt(readline.substring(0,readline.indexOf(',')));
		readline=readline.substring(readline.indexOf(',')+1, readline.length());
		
		float reading = Float.parseFloat(readline.substring(0, readline.indexOf(',')));
		readline = readline.substring(readline.indexOf(',')+1, readline.length());
		
		byte fiveboolean = 0;
		byte bitop=0x08;
		for(int i=4;i>0;i--){
			if(readline.toLowerCase().startsWith("true"))
				fiveboolean=(byte)(fiveboolean|bitop);
			
			bitop=(byte)(bitop>>>1);
			readline=readline.substring(readline.indexOf(',')+1, readline.length());
		}
		
		DataTuple res=new DataTuple(id, company, drug_id, trials, patients, dosage_mg, reading, fiveboolean);
		return res;
	}
}
