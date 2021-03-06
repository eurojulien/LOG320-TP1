import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import LettreControl.*;

import java.util.ArrayList;

import binary.BinaryStdIn;
import binary.BinaryStdOut;


public class Main {

	public static listControler lstControl = new listControler();
	public static final String CRYPTED 	= ".cry";
	public static final String TEXTE	= ".txt";
	public static final String DECRYPT	= "_D";
	
	// Options des parametres
	// args[0] : (1 : Encripter, 1 : Decripter)
	// args[1] : Nom du fichier texte
	public static void main(String[] args) {
		String texteComplet = "";
		
		int option = Integer.parseInt(args[0]);
		StructureArbre struct;
		
		// Verifie qu'il y a un nom de fichier en parametre
		if (args.length == 0){
			System.out.println("Provide a text file !");
		}
		else{
			
			// Encription
			if (option == 1){
				
				texteComplet = FileParser(args[1]);
				
				// on trie notre liste avec comparable
				lstControl.trierListe();
				
				// on imprime les résultats (on enleve cet affichage pour gagner de la vitesse)
				lstControl.print();

		        // on construit l'arbre
		        System.out.println("Il y a " + lstControl.getArrayList().size() + " lettres dans le texte");
		        struct = new StructureArbre(lstControl.getArrayList());
		        
		        try{
		            struct.encodeText(texteComplet);
		        }catch (Exception ex){
		            System.out.println(ex.getMessage());
		        }
		        
		        // voici le texte encoder (comprenant le header) :
		        System.out.println("Text complet " + struct.getBinaryText());
		        
		        // Ecriture de la chaine dans le fichier
		        BinaryStdOut.WriteBinaryToFile(fileNameWithoutExtention(args[1]) + CRYPTED, struct.getBinaryText());
			}
			
			// Decription
			if (option == 2){

				// Lecture du fichier
				texteComplet = BinaryStdIn.ReadBinaryFromFile(fileNameWithoutExtention(args[1]) + CRYPTED);
				
				// Decriptage fichier cripte
				// TODO : Besoin de la chaine string decriptee
				struct = new StructureArbre(texteComplet);
				
				// Destination fichier decripte 
				BinaryStdOut.setOutputFile(fileNameWithoutExtention(args[1]) + DECRYPT + TEXTE);
				
				// Ecriture dans le fichier output
				// TODO : J'ai utilise getBinaryText parce que je comprends pas comment la stucture decode ?
				for (char letter : struct.getBinaryText().toCharArray()){
					BinaryStdOut.write(letter);
				}
				
				BinaryStdOut.close();
				
		        //String headerForTest = struct.getBinaryText();
		        //struct = new StructureArbre(texteComplet);
			}
            
		}
	}
	
	public static String fileNameWithoutExtention(String fileName){
		
		if(!fileName.contains(".")) return fileName;
		
		else{
			return fileName.substring(0, fileName.indexOf("."));
		}
	}
	
	// Ouvre un fichier texte a partir du nom de fichier
	// Lit le fichier texte octet par octet (8 bits)
	public static String FileParser(String fileName){
		
		// Lecteur de fichier
		FileReader fileReader;
		BufferedReader buffer;
		File txtFile;
        String texteComplet = "";
		
		// Ouverture du fichier
		try {
			
			txtFile = new File(fileName);
			fileReader = new FileReader(txtFile);
			buffer = new BufferedReader(fileReader, (int)txtFile.length());
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failure reading text";
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
                char caractere[] = (Character.toChars(asciiLetter));
                texteComplet += caractere[0];
				lstControl.addlettre(Character.toChars(asciiLetter));
			}
			
		}while (asciiLetter > 0);
		
		// Fermeture du fichier
		try {
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return texteComplet;
	}
	
	public static void WriteBitsInFile(String fileName){
		
		FileWriter writer;
		
		try {
			writer = new FileWriter(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
