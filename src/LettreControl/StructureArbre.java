package LettreControl;

import java.util.ArrayList;
import java.util.Collections;

public class StructureArbre {

    private int compteur = 0;
	private ArrayList<Noeud> listeNoeud;
    private ArrayList<Noeud> listeLettreRapides;
    private static final String valeurNoeudVide = "00000000";
    private static final String valeurNoeudEOF = "00000011";
    private String textEncoder = "";
    private String header = "";

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
        header += valeurNoeudEOF;
        System.out.println("Il y a " + compteur + " lettres dans l'arbre");
        System.out.println("Voici le header, encoder en " + headerFinal.length() + " Bits");
        System.out.println(headerFinal);
	}

    public StructureArbre(String text){
        // constructeur du décodage
        Boolean EOF = false;
        Boolean wentDown = false;
        String bitIndicateur = "";
        String bitIndicateurPrecedant = "";
        int step = 0;
        String chaineDeTravail = "";
        //store les noeuds
        Noeud nodeGauche = null;
        Noeud nodeDroite = null;
        Noeud nodeParent = null;
        // lorsque l'arbre commence a creuser dans des sous-branches, on doit se souvenir du point ou
        // l'arborescence a commencer a descendre afin de pouvoir continuer
        ArrayList<Noeud> nodeParentWhenDigging = new ArrayList<Noeud>();
        while(!EOF){
            chaineDeTravail = text.substring(step, step+8);
            //System.out.println("We compare "+ chaineDeTravail + " to : " + valeurNoeudEOF);
            if(chaineDeTravail.equals(valeurNoeudEOF)){
                //on a trouver le end of file.
                // a ce point on a l'arborescence de créer ! whouhou !
                EOF = true;
            }
            else{
                //le EOF n'est pas trouver, on continue a lire
                bitIndicateur = text.substring(step+8,step+9);
                if (bitIndicateurPrecedant.equals(bitIndicateur)){
                    // on entre ou sort d'une sous-branche
                    if(wentDown){
                        // sa veut dire qu'on remonte !
                        nodeGauche = nodeParentWhenDigging.get(nodeParentWhenDigging.size()-1);
                        nodeParentWhenDigging.remove(nodeParentWhenDigging.size()-1);
                        wentDown = false;
                        nodeParent = new Noeud(Character.toChars(Integer.parseInt(valeurNoeudVide, 2))[0],0,nodeGauche,nodeDroite);
                        nodeGauche = nodeParent;
                    }else{
                        // on passe au point gauche en descendant dans une autre branche
                        if(nodeParent != null){
                            // on a déja un parent
                            nodeParentWhenDigging.add(nodeParent);
                        }else{
                            // pas encore de parent
                            nodeParentWhenDigging.add(nodeGauche);
                        }
                        nodeGauche = new Noeud(Character.toChars(Integer.parseInt(chaineDeTravail, 2))[0],0);
                        wentDown = true;
                    }
                }else{
                    //c'est un triangle régulier
                    if(chaineDeTravail.equals(Character.toChars(Integer.parseInt(valeurNoeudVide, 2))[0])){
                        // c'est un noeud parent
                        nodeParent = new Noeud(Character.toChars(Integer.parseInt(valeurNoeudVide, 2))[0],0,nodeGauche,nodeDroite);
                        nodeGauche = nodeParent;
                    }else{
                        //System.out.println(Character.toChars(Integer.parseInt(chaineDeTravail, 2)));
                        if(bitIndicateur.equals("1")){
                            // c'est une node "gauche"
                            nodeGauche = new Noeud(Character.toChars(Integer.parseInt(chaineDeTravail, 2))[0],0);
                        }else{
                            // c'est une node "droite"
                            nodeDroite = new Noeud(Character.toChars(Integer.parseInt(chaineDeTravail, 2))[0],0);
                        }
                    }
                }

                bitIndicateurPrecedant = bitIndicateur;
                step+=9;
            }
        }
        // la boucle est finie et on a notre arbre binaire reconstruit ...
        //nodeParent
        //step
        Noeud nodeActuelle = nodeGauche;
        String texteResultant = "";
        String texteADecoder = text.substring(step,text.length()-1);

        nodeActuelle.setCaracter(Character.toChars(Integer.parseInt(valeurNoeudVide, 2))[0]);

        for(int i=0;i<texteADecoder.length();i++){
            char bit = texteADecoder.charAt(i);
            if(bit == '1'){
                nodeActuelle = nodeActuelle.getNoeudGauche();
            }else{
                nodeActuelle = nodeActuelle.getNoeudDroit();
            }

            if(nodeActuelle.getCaracter() != Character.toChars(Integer.parseInt(valeurNoeudVide, 2))[0]){
                // c'est une lettre !
                texteResultant += nodeActuelle.getCaracter();
                nodeActuelle=nodeParent;
            }

        }

        System.out.println("Voici le texte final :");
        System.out.println(texteResultant);
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

    public String getHeader(){
        return header;
    }

    private String contruireHeader(Noeud parent){
        Noeud debut = trouverNoeudPlusGauche(parent);
        header += debut.getBinaryCaracter() + debut.getValeurBitVersParent();
        //System.out.println("Le noeud de Gauche est une Feuille : '" + debut.getCaracter() + "'");
        compteur ++;
        header += construireHeaderTriangle(debut);
        return header;
    }

    public String getBinaryText(){
        return header + textEncoder;
    }

    private String construireHeaderTriangle(Noeud gauche){
        String currentHeader = "";
        //System.out.println("Nouveau triangle : '" +  gauche.getCaracter()+ "'");
        // lecture du triangle
        Noeud currentNodeLeft = gauche;
        Noeud currentParent = currentNodeLeft.getParent();
        Noeud currentNodeRight = currentParent.getNoeudDroit();

        if(currentNodeLeft.getParent().getNoeudDroit() != currentNodeLeft){
            //on vérifie si le côté droit est une feuille ou une branche
            if(currentNodeRight.getCaracter() == (Character.toChars(Integer.parseInt(valeurNoeudVide, 2))[0])){
                // le noeud est une branche
                // devrais ajouter un deuxieme 1 de suite
                compteur ++;
                //System.out.println("On descend le noeud gauche : '" + trouverNoeudPlusGauche(currentNodeRight).getCaracter() + "' : 1");
                currentHeader += trouverNoeudPlusGauche(currentNodeRight).getBinaryCaracter() + trouverNoeudPlusGauche(currentNodeRight).getValeurBitVersParent();
                currentHeader += construireHeaderTriangle(trouverNoeudPlusGauche(currentNodeRight));
            }else{
                // le noeud est une feuille
                //devrais ajouter un 0
                //System.out.println("Le noeud de droit est une Feuille : '" + currentNodeRight.getCaracter() + "' : 0");
                currentHeader += currentNodeRight.getBinaryCaracter() + currentNodeRight.getValeurBitVersParent();
                compteur++;
            }
            if(currentParent != null){
                if(currentParent.getParent() != null){
                    //System.out.println("Le noeud parent est une branche :" + currentParent.getValeurBitVersParent());
                    currentHeader += valeurNoeudVide + currentParent.getValeurBitVersParent();
                    currentHeader += construireHeaderTriangle(currentParent);
                }
            }
        }

        return currentHeader;
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
				
			Noeud nParent = new Noeud(Character.toChars(Integer.parseInt(valeurNoeudVide, 2))[0],ng.getCaracNbOccurence()+ng.getCaracNbOccurence(),ng,nd);
			
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
