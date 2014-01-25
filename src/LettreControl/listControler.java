package LettreControl;

import java.lang.reflect.Array;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.Collections;

import java.util.List;


public class listControler {

	private ArrayList<lettre> lstlettres;
	
	public listControler(){
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
	
	public ArrayList<lettre> getArrayList(){
        return lstlettres;
    }

	public void trierListe(){
		Collections.sort(lstlettres);
	}
	
	
	public void print() {
		// on imprime les lettres ainsi que leurs valeurs
		for(int i=0;i<lstlettres.size();i++){
			System.out.println(lstlettres.get(i).getCaractere() +":" + lstlettres.get(i).getValue());
		}
	}


	public void addlettre(char[] chars) {
		// si on passe en argument un tableau de char, on va le décomposé en lettres
		for(int i =0;i<chars.length;i++){
			addlettre(chars[i]);
		}
	}

}