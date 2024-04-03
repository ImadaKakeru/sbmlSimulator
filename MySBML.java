package sbml;
import java.util.ArrayList;
import java.util.Scanner;
//import java.util.*;
import org.sbml.libsbml.*;

public class MySBML{
	
	static {
		try {
			System.loadLibrary("sbmlj");
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static void main(String[] args){
		ReadSBML r = new ReadSBML();
		SimulateSBML s = new SimulateSBML();
		SBMLReader reader = new SBMLReader();
		SBMLDocument d = reader.readSBML(args[0]);
		Model model = d.getModel();
		r.createReactionData(model);
		
		try(Scanner sc = new Scanner(System.in)){
			  ArrayList<String> words = new ArrayList<String>();//String型のデータを格納するArrayList wordsを作成

		        while (sc.hasNextLine()) {
		            String word = sc.nextLine();//一行の単語を受け取る
		            words.add(word);//ArrayListのwordsに受け取った単語を格納する
		        }
		        for (String word:words){
		        	r.print(word,model);
		        	s.euller(model);
		        	s.runge(model);	
		        }
		}
	}
}