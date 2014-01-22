package LettreControl;

import java.util.ArrayList;
import java.util.Collections;

public class StructureArbre {

	private ArrayList<Noeud> listeNoeud;

	
	public StructureArbre(ArrayList<lettre> listeOcurrenceLettre){

		for(lettre l : listeOcurrenceLettre){
			Noeud n = new Noeud(l.getCaractere(),l.getValue());
			listeNoeud.add(n);
		}
	}
	
	public Noeud contruireArbre() throws Exception{
			
		while(listeNoeud.size() >= 2){
				
			Noeud ng = listeNoeud.get(0);
			Noeud nd = listeNoeud.get(1);
				
			Noeud nParent = new Noeud('\u0000',ng.getCaracNbOccurence()+ng.getCaracNbOccurence(),ng,nd);
			
			listeNoeud.remove(0);
			listeNoeud.remove(1);
			listeNoeud.add(nParent);
			
			this.trierNoeuds();
		}
		
		
		if(listeNoeud.size() != 1) 
			throw new Exception("Erreur dans la structure de l'arbre : plusieurs racines");
		
		return listeNoeud.get(0);
	}
	
	private void trierNoeuds(){
		Collections.sort(listeNoeud);
	}
	
}
