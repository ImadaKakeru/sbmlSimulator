package sbml;
import org.sbml.libsbml.ListOfReactions;
import org.sbml.libsbml.ListOfSpecies;
import org.sbml.libsbml.Model;
import org.sbml.libsbml.ASTNode;

public class ReadSBML {
	private String[] name = new String[50];
	private String[] Kinetic = new String[50];
	private String[][] Parameters = new String[100][100];
	private double Parametervalue[][] = new double[100][100];
	private String[] Reactants = new String[100];
	private double Reactantsvalue[] = new double[100];
	private String[] Products = new String[100];
	private double Productsvalue[] = new double[100];
	private ASTNode[] k = new ASTNode[100];
	
  public void createReactionData(Model model){
	  int i = 0 ;
	  int j = 0 ;
	  int k = 0;
	  int l = 0;
	  
	  ListOfReactions lor = model.getListOfReactions();
	  ListOfSpecies los = model.getListOfSpecies();
	  
	  int lorsize = (int)lor.size();
	  int lossize = (int)los.size();
	 
	  for(i = 0 ; i < lorsize ; i ++){
		  //反応名をまとめる
		  this.name[i] = lor.get(i).getId();
		  //速度式をまとめる
		  this.Kinetic[i] = lor.get(i).getKineticLaw().getFormula();
		  this.k[i] = lor.get(i).getKineticLaw().getMath();
		  
		  //ある反応のパラメータの数、反応物、生成物の数を保存
		  int p = (int)lor.get(i).getKineticLaw().getListOfParameters().size();
		  int reactants = (int)lor.get(i).getListOfReactants().size();
		  int products = (int)lor.get(i).getListOfProducts().size();
		  
		  for( l = 0 ; l < p ; l++){
			  //パラメータの名前と値を保存
			  this.Parameters[i][l] = lor.get(i).getKineticLaw().getListOfParameters().get(l).getId();
			  this.Parametervalue[i][l] = lor.get(i).getKineticLaw().getListOfParameters().get(l).getValue();
		  }
		  //反応物の名前と値を保存
		  for(k = 0 ; k < reactants ; k++){
			  for(j = 0 ; j < lossize ; j++){
				  if(lor.get(i).getListOfReactants().get(k).getSpecies().equals(los.get(j).getId())){
					  this.Reactants[j] = los.get(j).getId();
					  this.Reactantsvalue[j] = los.get(j).getInitialAmount();
				  }
			  }
		  }	
		  
		  //生成物の名前と値を保存
		  for(k = 0 ; k < products ; k++){
			  for(j = 0 ; j < lossize ; j++){
				  if(lor.get(i).getListOfProducts().get(k).getSpecies().equals(los.get(j).getId())){
					  this.Products[j] = lor.get(i).getListOfReactants().get(k).getSpecies();
					  this.Productsvalue[j] = los.get(j).getInitialAmount();
				  }	
			  }
		  }
	  }
  }
  
  public void print(String J , Model model){
	  int i = 0 ;
	  int j = 0 ;
	  int k = 0;
	  ListOfReactions lor = model.getListOfReactions();
	  ListOfSpecies los = model.getListOfSpecies();
	  
	  int lorsize = (int)lor.size();
	  int lossize = (int)los.size();
	  
	  for(i = 0 ; i < lorsize ; i ++){
		  if(lor.get(i).getId().equals(J)){
			  //反応名をまとめる
			  System.out.println("###Reaction###");
			  System.out.println(lor.get(i).getId());
			  //速度式をまとめる
			  System.out.println("###KineticLaw###");
			  System.out.println(lor.get(i).getKineticLaw().getFormula());
		  
			  //ある反応のパラメータの数、反応物、生成物の数を保存
			  int reactants = (int)lor.get(i).getListOfReactants().size();
			  int products = (int)lor.get(i).getListOfProducts().size();
		  
			  //反応物の名前と値を保存
			  for(k = 0 ; k < reactants ; k++){
				  for(j = 0 ; j < lossize ; j++){
					  if(lor.get(i).getListOfReactants().get(k).getSpecies().equals(los.get(j).getId())){
						  System.out.println("###Reactants###");
						  System.out.println(lor.get(i).getListOfReactants().get(k).getSpecies());
						  System.out.println(los.get(j).getInitialAmount());
					  }
				  }
			  }	
		  
		  //生成物の名前と値を保存
			  for(k = 0 ; k < products ; k++){
				  for(j = 0 ; j < lossize ; j++){
					  if(lor.get(i).getListOfProducts().get(k).getSpecies().equals(los.get(j).getId())){
						  System.out.println("###Products###");
						  System.out.println(lor.get(i).getListOfProducts().get(k).getSpecies());
						  System.out.println(los.get(j).getInitialAmount());
					  }	
				  }
			  }
		  }
	  }
  }
}