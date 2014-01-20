package LettreControl;

public class lettre implements Comparable{
	private int value = 0;
	private char caractere;

	lettre(char caractere, int value){
		this.value = value;
		this.caractere = caractere;
	}


	public void add(int valeurAjouter){
		this.value += valeurAjouter;
	}


	public char getCaractere(){
		return caractere;
	}

	public int getValue(){
		return value;
	}



	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		//on compare les valeurs
		int paramCompare = ((lettre) o).value;
		return (value > paramCompare ? 1 : (value == paramCompare ? 0 : -1));
	}
}
