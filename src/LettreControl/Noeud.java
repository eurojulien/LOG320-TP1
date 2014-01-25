package LettreControl;

public class Noeud implements Comparable {

    //Noeud Gauche = 1
    //Noeud droit =0
    private String valeurBitVersParent;
	private char caracter;
	private int caracNbOccurence;
    private Noeud noeudGauche;
    private Noeud noeudDroit;
    private Noeud parent;

    public Noeud(char caracter, int caracNbOccurence){
        //System.out.println("Added node '" + caracter + "'");
        this.caracter = caracter;
        this.caracNbOccurence = caracNbOccurence;
        parent = null;
    }

    public Noeud(char caracter, int caracNbOccurence, Noeud noeudGauche, Noeud noeudDroit){
        this.caracter = caracter;
        this.caracNbOccurence = caracNbOccurence;
        this.noeudGauche = noeudGauche;
        this.noeudGauche.setParent(this);
        this.noeudGauche.setValeurBitVersParent("1");
        this.noeudDroit = noeudDroit;
        this.noeudDroit.setParent(this);
        this.noeudDroit.setValeurBitVersParent("0");
        parent = null;
    }

    public void setParent(Noeud parent){
        this.parent = parent;
    }


    public void setValeurBitVersParent(String valeurBitVersParent){
        this.valeurBitVersParent = valeurBitVersParent;
    }

    public String getValeurBitVersParent(){
        return valeurBitVersParent;
    }

    public Noeud getParent(){
        return this.parent;
    }

	public int getCaracNbOccurence() {
		return caracNbOccurence;
	}

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

    public String getBinaryValue(){
        String chaineRetour = this.valeurBitVersParent;
        Noeud parentArbre = this.parent;

        while(parentArbre != null){
            if(parentArbre.valeurBitVersParent !=null)
                chaineRetour = parentArbre.valeurBitVersParent + chaineRetour;
            parentArbre = parentArbre.getParent();
        }

        return chaineRetour;
    }

    public char getCaracter(){
        return caracter;
    }


    public void setCaracter(char caracter){
        this.caracter=caracter;
    }

    public String getBinaryCaracter(){
        String returnCode = Integer.toBinaryString(caracter);
        for(int i=0;i <= (8 - returnCode.length());i++){
            returnCode = "0" + returnCode;
        }
        if(returnCode.length() > 8){
            returnCode = returnCode.substring(1);
        }
        return returnCode;
    }
	@Override
	public int compareTo(Object o) {
		//on compare les valeurs
		int paramCompare = ((Noeud) o).caracNbOccurence;
		return (caracNbOccurence > paramCompare ? 1 : (caracNbOccurence == paramCompare ? 0 : -1));
	}
}
