package sbml;
import java.io.*;
import org.sbml.libsbml.*;

public class SimulateSBML {
	
	SBMLReader reader = new SBMLReader();
	SBMLDocument d = reader.readSBML("mapk.xml");
	Model model = d.getModel();
	int i=0;
	int j=0;
	int kk=0;
	int l=0;
	private String[] name = new String[20];//反応名
	private String [] Spiecies = new String[20];//分子名
	private double [] Svalue = new double[20];//分子の値
	private ASTNode[] k = new ASTNode[20];//AST
	private ListOfReactions lor = model.getListOfReactions();
	private ListOfSpecies los = model.getListOfSpecies();
	private ListOfParameters lop;
	private int lorsize = (int)lor.size();//反応の数
	private int lossize = (int)los.size();//Speciesの数
	private double dt = 1;
	private float t = 0;
	private double dv[] = new double[50];
	private double results[][] = new double[100][4050];
	
	private double [] k1 = new double[lossize];
	private double [] k2 = new double[lossize];
	private double [] k3 = new double[lossize];
	private double [] k4 = new double[lossize];
	
	public int compare(String a,String b){
		if(a.equals(b)){
			return 0;
		}
		else{
			return 1;
		}
	}
	public void k1(){
		for(i = 0 ; i < lossize ; i++){
			k1[i] = 0;
		}
		for(int ii = 0 ; ii < lorsize ; ii++){
			dv[ii] = AST(k[ii],lossize,1);
		}
		//System.out.println(dv[0]);
		for(int ii = 0 ; ii < lorsize ; ii++){
				
			int reactants = (int)lor.get(ii).getListOfReactants().size();
			int products = (int)lor.get(ii).getListOfProducts().size();
				
			for(int ll = 0 ; ll < reactants ; ll++){
				for(j = 0 ; j < lossize ; j++){
					if(lor.get(ii).getListOfReactants().get(ll).getSpecies().equals(Spiecies[j])) {
						k1[j] -= dv[ii];
					}
				}
			}	
			for(int ll = 0 ; ll < products ; ll ++){
				for(j = 0 ; j < lossize ; j++){
					if(lor.get(ii).getListOfProducts().get(ll).getSpecies().equals(Spiecies[j])){
						k1[j] += dv[ii];
					}
				}
			}
		}
	}
	public void k2(){
		for(i = 0 ; i < lossize ; i++){
			k2[i] = 0;
		}
		for(int ii = 0 ; ii < lorsize ; ii++){
			dv[ii] = AST(k[ii],lossize,2);
		}
		for(int ii = 0 ; ii < lorsize ; ii++){
			
			int reactants = (int)lor.get(ii).getListOfReactants().size();
			int products = (int)lor.get(ii).getListOfProducts().size();
						
			for(int ll = 0 ; ll < reactants ; ll++){
				for(j = 0 ; j < lossize ; j++){
					if(lor.get(ii).getListOfReactants().get(ll).getSpecies().equals(Spiecies[j])) {
						k2[j] -= dv[ii];
					}
				}
			}	
			for(int ll = 0 ; ll < products ; ll ++){
				for(j = 0 ; j < lossize ; j++){
					if(lor.get(ii).getListOfProducts().get(ll).getSpecies().equals(Spiecies[j])){
						k2[j] += dv[ii];
					}
				}
			}
		}
	}
	public void k3(){
		for(i = 0 ; i < lossize ; i++){
			k3[i] = 0;
		}
		for(int ii = 0 ; ii < lorsize ; ii++){
			dv[ii] = AST(k[ii],lossize,3);
		}
		for(int ii = 0 ; ii < lorsize ; ii++){
			
			int reactants = (int)lor.get(ii).getListOfReactants().size();
			int products = (int)lor.get(ii).getListOfProducts().size();
						
			for(int ll = 0 ; ll < reactants ; ll++){
				for(j = 0 ; j < lossize ; j++){
					if(lor.get(ii).getListOfReactants().get(ll).getSpecies().equals(Spiecies[j])) {
						k3[j] -= dv[ii];
					}
				}
			}	
			for(int ll = 0 ; ll < products ; ll ++){
				for(j = 0 ; j < lossize ; j++){
					if(lor.get(ii).getListOfProducts().get(ll).getSpecies().equals(Spiecies[j])){
						k3[j] += dv[ii];
					}
				}
			}
		}
	}
	public void k4(){
		for(i = 0 ; i < lossize ; i++){
			k4[i] = 0;
		}
		for(int ii = 0 ; ii < lorsize ; ii++){
			dv[ii] = AST(k[ii],lossize,4);
		}
		for(int ii = 0 ; ii < lorsize ; ii++){
			
			int reactants = (int)lor.get(ii).getListOfReactants().size();
			int products = (int)lor.get(ii).getListOfProducts().size();
						
			for(int ll = 0 ; ll < reactants ; ll++){
				for(j = 0 ; j < lossize ; j++){
					if(lor.get(ii).getListOfReactants().get(ll).getSpecies().equals(Spiecies[j])) {
						k4[j] -= dv[ii];
					}
				}
			}	
			for(int ll = 0 ; ll < products ; ll ++){
				for(j = 0 ; j < lossize ; j++){
					if(lor.get(ii).getListOfProducts().get(ll).getSpecies().equals(Spiecies[j])){
						k4[j] += dv[ii];
					}
				}
			}
		}
	}
	public double parameter(ASTNode a,int lossize,int c){
		String id = a.getName();
		for(i = 0 ; i < lossize ; i++){
			if(compare(id,los.get(i).getId())== 0){
				if(c == 1){
					return Svalue[i];
				}
				else if(c == 2){
					return (Svalue[i] + dt/2 * k1[i]);
				}
				
				else if(c == 3){
					return (Svalue[i] + dt/2 * k2[i]);
				}
				else{
					return (Svalue[i] + dt * k3[i]);
				}
			}
		}
		for(i = 0 ; i <lorsize ; i++){
			lop = lor.get(i).getKineticLaw().getListOfParameters();
			//パラメータ名と一致したらその値を返す。
			for(int j = 0 ; j<(int)lop.size() ; j++){
				if(lop.get(j).getId().equals(id)){
					return lop.get(j).getValue();
				}
			}
		}
		return 0;
	}
	public double Calc(ASTNode a,double left,double right){
		if(a.getType() == 43){
			return left+right;
		}	
		else if(a.getType() == 45){
			return left-right;
		}
		else if(a.getType() == 42){
			return left*right;
		}
		else if(a.getType() == 47){
			return left/right;
		}
		else{
			return Math.pow(left, right);
		} 
	}
	
	public double AST(ASTNode k,int lossize,int a){
		double left = 0;
		double right = 0;
		if(k.getNumChildren() == 0){
			if(k.isNumber() == true){
				if(k.isInteger() == true){
					return k.getInteger();
				}
				else{
					return k.getReal();
				}
			}
			
			else{
				return parameter(k,lossize,a);
			}
		}
		left = AST(k.getLeftChild(),lossize,a);
		right = AST(k.getRightChild(),lossize,a);
		return Calc(k,left,right);
	}

	//オイラーシミュレーション
	public void euller(Model model) {
		int i=0;
		int j=0;
		ListOfReactions lor = model.getListOfReactions();
		ListOfSpecies los = model.getListOfSpecies();
		
		for(i = 0 ; i < lorsize ; i ++){
			//反応名をまとめる
			name[i] = lor.get(i).getId();
		}
		 
		for(i = 0 ; i < lorsize ; i ++){
			//AST
			k[i] = lor.get(i).getKineticLaw().getMath();
		}
		
		for(i = 0 ; i < lossize ; i++){
			Spiecies[i] = los.get(i).getId();
			Svalue[i] = los.get(i).getInitialAmount();
			results[i][0] = los.get(i).getInitialAmount();
		}
		
		for(i=1; i<=4000; i++){
			for(int ii = 0 ; ii < lorsize ; ii++){
				dv[ii] = AST(k[ii],lossize,1);
			}
			for(int ii = 0 ; ii < lorsize ; ii++){
				
				int reactants = (int)lor.get(ii).getListOfReactants().size();
				int products = (int)lor.get(ii).getListOfProducts().size();
				
				for(int ll = 0 ; ll < reactants ; ll++){
					for(j = 0 ; j < lossize ; j++){
						if(lor.get(ii).getListOfReactants().get(ll).getSpecies().equals(Spiecies[j])) {
							Svalue[j] -= dv[ii] * dt;
							results[j][i] = Svalue[j];
						}
					}
				}
				
				for(int ll = 0 ; ll < products ; ll ++){
					for(j = 0 ; j < lossize ; j++){
						if(lor.get(ii).getListOfProducts().get(ll).getSpecies().equals(Spiecies[j])){
							Svalue[j] += dv[ii] * dt;
							results[j][i] = Svalue[j];
						}
					}
				}
			}
		}	
		try {
			// 出力ファイルの作成
			FileWriter fw = new FileWriter("imada_euller.csv", false);
			try (PrintWriter pw = new PrintWriter(new BufferedWriter(fw))) {
				pw.print("time");
				for(i = 0 ; i < lossize ; i ++){
					//分子名を出力
					pw.print(",");
					pw.print(los.get(i).getId());
				}
				pw.println();
				for(i = 0 ; i<=4000 ; i ++){
					pw.print((float)t);
					for(j = 0 ; j < lossize ; j++){
						pw.print(",");
						pw.print(results[j][i]);
					}
					t += 1;
					pw.println();
				}
				pw.close();
			}	
		}catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	//ルンゲシミュレーション
	public void runge(Model model){
		t = 0;
		int i = 0;
		int j = 0 ;
		ListOfReactions lor = model.getListOfReactions();
		ListOfSpecies los = model.getListOfSpecies();
		 for(i = 0 ; i < lorsize ; i ++){
			  //反応名をまとめる
			  name[i] = lor.get(i).getId();
		 }
		 
		for(i = 0 ; i < lorsize ; i ++){
			//AST
			k[i] = lor.get(i).getKineticLaw().getMath();
		}
		for(i = 0 ; i < lossize ; i++){
			Spiecies[i] = los.get(i).getId();
			Svalue[i] = los.get(i).getInitialAmount();
			results[i][0] = los.get(i).getInitialAmount();
		}
		for(i=1; i<=100; i++){
			k1();
			k2();
			k3();
			k4();
			System.out.println("k1:"+k1[0]);
			System.out.println("k2:"+k2[0]);
			System.out.println("k3:"+k3[0]);
			System.out.println("k4:"+k4[0]);
			//System.out.println(k1[0]);
			for(j = 0 ; j < lossize ; j++){
				Svalue[j] += (k1[j] + 2*k2[j] + 2*k3[j] + k4[j] )/6 * dt;
				results[j][i] = Svalue[j];
			}
		}
		
		try {
			// 出力ファイルの作成
			FileWriter fw = new FileWriter("imada_runge.csv", false);
			try (// PrintWriterクラスのオブジェクトを生成。文字のバッファリングも同時に行なっている。
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw))) {
				//ヘッダーの指定
				//ヘッダーの指定
				pw.print("time");
				for(i = 0 ; i < lossize ; i ++){
					//分子名を出力
					pw.print(",");
					pw.print(los.get(i).getId());
				}
				pw.println();   
				for(i = 0 ; i<=4000 ; i ++){
					 pw.print((float)t);
					 for(j = 0 ; j < lossize ; j++){
						 pw.print(",");
						 pw.print(results[j][i]);
					 }
					 t += 1;
					 pw.println();
				 }
				pw.close();
			}
		}catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}