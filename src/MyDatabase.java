

import java.util.Scanner;

public class MyDatabase {
	public static void main(String[] args){
		
		while(true){
			System.out.println("\nPlease type in the SQL command:\n");
			System.out.print(">");
			Scanner sc= new Scanner(System.in);
			String cmdstr = sc.next();
			//Read the query insert delete commands end with ';' or import command end with ".csv"
			while(cmdstr.charAt(cmdstr.length()-1)!=';' && !cmdstr.toLowerCase().endsWith(".csv")){
				System.out.print(">");
				cmdstr=cmdstr+" "+sc.next();
			}
			System.out.println('\n'+"Command is:"+cmdstr);//output result display
			
	//		CommandStr cstr = new CommandStr(cmdstr); /*   */
	//		String cmdstr ="Select * from Data where drug_id=WJ-767;";
	//		String cmdstr ="Delete * from Data where drug_id=WJ-767;";
	//		String cmdstr ="Insert into Data values 1001,First Aid Research Corp.,RA-981,28,1366,54,67.0,1;";
	//		String cmdstr ="Import Data.csv";
			CommandStr cstr = new CommandStr(cmdstr);
			cstr.commandParse();
			System.out.println("\nShow all the result over");
	//		while(true);
		}
	}
}
