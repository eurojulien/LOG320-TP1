package LettreControl;

public class Noeud implements Comparable {

	private char caracter;
	private int caracNbOccurence;
	
	public int getCaracNbOccurence() {
		return caracNbOccurence;
	}

	private Noeud noeudGauche;
	public Noeud getNoeudGauche() {
		return noeudGauche;
	}

	public void setNoeudGauche(Noeud noeudGauche) {
		this.noeudGauche = noeudGauche;
	}

	public Noeud getNoeudDroit() {
		return noeudDroit;
	}

	public void setNoeudDroit(Noeud noeudDroit) {
		this.noeudDroit = noeudDroit;
	}

	private Noeud noeudDroit;
	
	public Noeud(char caracter, int caracNbOccurence){
		this.caracter = caracter;
		this.caracNbOccurence = caracNbOccurence;
	}
	
	public Noeud(char caracter, int caracNbOccurence, Noeud noeudGauche, Noeud noeudDroit){
		this.caracter = caracter;
		this.caracNbOccurence = caracNbOccurence;
		this.noeudGauche = noeudGauche;
		this.noeudDroit = noeudDroit;
	}

	@Override
	public int compareTo(Object o) {
		//on compare les valeurs
		int paramCompare = ((Noeud) o).caracNbOccurence;
		return (caracNbOccurence > paramCompare ? 1 : (caracNbOccurence == paramCompare ? 0 : -1));
	}
	
	
	
}
