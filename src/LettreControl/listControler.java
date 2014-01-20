package LettreControl;

import java.lang.reflect.Array;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;





public class listControler {

	private List<lettre> lstlettres;
	
	listControler(){
		lstlettres = new ArrayList<lettre>();
	}
	
	
	public void addlettre(char lettre){
	
		boolean found = false;
		
		/* on cherche a ajouter la valeur a la lettre si elle existe*/
		
		for(int i=0;i<lstlettres.size();i++){
		
			if(lstlettres.get(i).getCaractere() == lettre){
				lstlettres.get(i).add(1);
				found = true;
			}
		
		}
		
		if(!found){
		
			/* l'objet n'a pas été trouver, on va donc le creer */
		
			lstlettres.add(new lettre(lettre,1));
		
		}
		
	}
	
	
	public void trierListe(){
	
		Collections.sort(lstlettres);
	
	}
	
	
	public void print() {
	
		for(int i=0;i<lstlettres.size();i++){
		
			System.out.println(lstlettres.get(i).getCaractere() +":" + lstlettres.get(i).getValue());
		
		}
	
	}

}