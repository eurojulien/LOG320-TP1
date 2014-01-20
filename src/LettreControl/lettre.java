package LettreControl;

public class lettre implements Comparable{
	private int value = 0;
	private char caractere;

	lettre(char caractere, int value){
		this.value = value;
		this.caractere = caractere;
	}


	public void add(int valeurAjouter){
		this.value += value;
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
		return value;
	}
}
