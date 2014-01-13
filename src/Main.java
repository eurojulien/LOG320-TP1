import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Main {

	public static void main(String[] args) {
		
		// Verifie qu'il y a un nom de fichier en parametre
		if (args.length == 0){
			System.out.println("Provide a text file !");
		}
		
		else{
			
			FileParser(args[0]);
		}

	}
	
	// Ouvre un fichier texte a partir du nom de fichier
	// Lit le fichier texte octet par octet (8 bits)
	public static void FileParser(String fileName){
		
		// Lecteur de fichier
		FileReader fileReader;
		BufferedReader buffer;
		File txtFile;
		
		// Ouverture du fichier
		try {
			
			txtFile = new File(fileName);
			fileReader = new FileReader(txtFile);
			buffer = new BufferedReader(fileReader, (int)txtFile.length());
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		int asciiLetter = 0;
		
		// Lecture du fichier
		do{
			try {
				asciiLetter = buffer.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Impression de chaque lettre (mode test)
			if(asciiLetter>0){
				System.out.println(Character.toChars(asciiLetter));
			}
			
		}while (asciiLetter > 0);
		
		// Fermeture du fichier
		try {
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
