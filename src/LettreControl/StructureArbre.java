package LettreControl;

import java.util.ArrayList;
import java.util.Collections;

public class StructureArbre {

    private int compteur = 0;
	private ArrayList<Noeud> listeNoeud;
    private ArrayList<Noeud> listeLettreRapides;
    private static final String valeurNoeudVide = "00000000";
    private static final String valeurNoeudEOF = "000EOF00";
    private String textEncoder = "";

	public StructureArbre(ArrayList<lettre> listeOcurrenceLettre){
        listeNoeud = new ArrayList<Noeud>();
		for(lettre l : listeOcurrenceLettre){
			Noeud n = new Noeud(l.getCaractere(),l.getValue());
			listeNoeud.add(n);
		}
        // on va garder une copie de la liste de lettre pour utilisation future
        listeLettreRapides = (ArrayList<Noeud>) listeNoeud.clone();
        Noeud parent = null;
        try{
            parent = contruireArbre();
        } catch (Exception ex){
            System.out.println("Une erreur s'est produite lors de la génération de l'arbre");
        }

        String headerFinal = contruireHeader(parent);
        headerFinal += valeurNoeudEOF;
        System.out.println("Il y a " + compteur + " lettres dans l'arbre");
        System.out.println("Voici le header, encoder en " + headerFinal.length() + " Bits");
        System.out.println(headerFinal);
	}


    public String encodeText(String texte) throws Exception{
        // on encode le message
        for(int i=0;i<texte.length();i++){
            // on itere pour chaque lettre compris dans le texte
            char caractere = texte.charAt(i);
            // on prend le caractère désiré
            boolean lettreTrouvee = false;
            for(int j=0;j<listeLettreRapides.size();j++){
                // on compare avec le tableau rapide de lettres
                if(listeLettreRapides.get(j).getCaracter() == caractere){
                    // le bon caractère a été trouver
                    lettreTrouvee = true;
                    textEncoder += listeLettreRapides.get(j).getBinaryValue();
                }
            }

            if(!lettreTrouvee){
                throw new Exception("Erreur dans l'encodage : une lettre n'a pas été trouvée !?");
            }
        }

        System.out.println("Le texte a encoder contient " + texte.length() + " caractères");
        System.out.println("Texte encodé sur " + textEncoder.length() +" bits : ");
        System.out.println(textEncoder);
        return textEncoder;
    }

    private String contruireHeader(Noeud parent){
        String header = "";
        Noeud debut = trouverNoeudPlusGauche(parent);
        header += debut.getBinaryCaracter() + debut.getValeurBitVersParent();
        //System.out.println("Le noeud de Gauche est une Feuille : '" + debut.getCaracter() + "'");
        compteur ++;
        header += construireHeaderTriangle(debut);
        return header;
    }

    private String construireHeaderTriangle(Noeud gauche){
        String header = "";
        //System.out.println("Nouveau triangle : '" +  gauche.getCaracter()+ "'");
        // lecture du triangle
        Noeud currentNodeLeft = gauche;
        Noeud currentParent = currentNodeLeft.getParent();
        Noeud currentNodeRight = currentParent.getNoeudDroit();

        if(currentNodeLeft.getParent().getNoeudDroit() != currentNodeLeft){
            //on vérifie si le côté droit est une feuille ou une branche
            if(currentNodeRight.getCaracter() == ('\u0000')){
                // le noeud est une branche
                // devrais ajouter un deuxieme 1 de suite
                compteur ++;
                //System.out.println("Le noeud de droit est une branche, le noeud gauche : '" + trouverNoeudPlusGauche(currentNodeRight).getCaracter() + "'");
                header += trouverNoeudPlusGauche(currentNodeRight).getBinaryCaracter() + trouverNoeudPlusGauche(currentNodeRight).getValeurBitVersParent();
                header += construireHeaderTriangle(trouverNoeudPlusGauche(currentNodeRight));
            }else{
                // le noeud est une feuille
                //devrais ajouter un 0
                //System.out.println("Le noeud de droit est une Feuille : '" + currentNodeRight.getCaracter() + "'");
                header += currentNodeRight.getBinaryCaracter() + currentNodeRight.getValeurBitVersParent();
                compteur++;
            }
            if(currentParent != null){
                if(currentParent.getParent() != null){
                    //System.out.println("Le noeud parent est une branche");
                    header += valeurNoeudVide + currentParent.getValeurBitVersParent();
                    header += construireHeaderTriangle(currentParent);
                }
            }
        }

        return header;
    }

    private Noeud trouverNoeudPlusGauche(Noeud parent){
        Noeud noeudInspecter = parent;
        while(noeudInspecter.getNoeudGauche() != null){
            noeudInspecter = noeudInspecter.getNoeudGauche();
        }

        return noeudInspecter;
    }


	private Noeud contruireArbre() throws Exception{
			
		while(listeNoeud.size() >= 2){
				
			Noeud ng = listeNoeud.get(0);
            Noeud nd = listeNoeud.get(1);
				
			Noeud nParent = new Noeud('\u0000',ng.getCaracNbOccurence()+ng.getCaracNbOccurence(),ng,nd);
			
			listeNoeud.remove(0);
			listeNoeud.remove(0);
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
