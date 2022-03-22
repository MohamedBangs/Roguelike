/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import config.CheminVers;
import config.Dim_Enum;
import modele.plateau.entites.entitefille.*;
import outils.Chargeur;

import java.util.Observable;


public class Jeu extends Observable implements Runnable {

    private int pause = 200, dallepickeoktime=3000; // période de rafraichissement
    private int debutPartie, finPartie, niveau,partie;
    private String[] terrain;
    private Heros heros;
    private boolean gameover, dallepickeok;
    private boolean processChangeNiveau,processChangePartie,finChargementPartie, stopjeu;

    private EntiteStatique[][] grilleEntitesStatiques = new EntiteStatique[Dim_Enum.largeur][Dim_Enum.longueurmax];

    public Jeu() {
        this.niveau=1;
        this.debutPartie=0;
        this.finPartie=10;
        this.terrain= Chargeur.getTerrainDuNiveau(CheminVers.niveaux+niveau+".txt");
        this.initJeu();
        this.processChangeNiveau=false;
        this.processChangePartie=false;
        this.finChargementPartie=false;
        this.partie=1;
        this.stopjeu=false;
        this.gameover=false;
        this.dallepickeok=true;
        this.timerDallePick();
    }

    public Heros getHeros() {
        return heros;
    }
    public int getDebutPartie(){
        return this.debutPartie;
    }
    public int getFinPartie(){
        return this.finPartie;
    }
    public boolean getProcessChangePartie(){
        return this.processChangePartie;
    }
    public void setProcessChangePartie(boolean b){
        this.processChangePartie=b;
    }
    public boolean getProcessChangeNiveau(){
        return this.processChangeNiveau;
    }
    public void setProcessChangeNiveau(boolean b){
        this.processChangeNiveau=b;
    }
    public String getNiveau() {
        return String.valueOf(this.niveau);
    }
    public int getPartie(){
        return this.partie;
    }
    public boolean getDallepickeok(){
        return this.dallepickeok;
    }
    public void setStopjeu(boolean b){
        this.stopjeu=b;
    }
    public void setGameover(boolean b){this.gameover=b;}
    public boolean getGameover(){return this.gameover;}


    public EntiteStatique[][] getGrille() {
        return grilleEntitesStatiques;
    }

	public EntiteStatique getEntite(int x, int y) {
		if (x < 0 || x >= Dim_Enum.largeur || y < 0 || y >= Dim_Enum.longueurmax) {
			// L'entité demandée est en-dehors de la grille
			return null;
		}
		return grilleEntitesStatiques[x][y];
	}
	public void setEntite(int x, int y, EntiteStatique statique){
        this.grilleEntitesStatiques[x][y]=statique;
    }

	private void initJeu(){
        this.initialisationDesEntites();
    }
    private void initialisationDesEntites() {
        for (int y = this.debutPartie; y < this.finPartie; y++) {
            String uneRanger=terrain[y];
            for (int x = 0; x < uneRanger.length(); x++) {
                switch (uneRanger.charAt(x)){
                    case 'n':
                        //case normale
                        grilleEntitesStatiques[x][y] = new CaseNormale(this);
                        break;
                    case 'm':
                        //case mur
                        grilleEntitesStatiques[x][y] = new Mur(this);
                        break;
                    case 'b':
                        //case mur_cassable
                        grilleEntitesStatiques[x][y] = new Mur(this, Dim_Enum.EffetCase.MUR_CASSABLE);
                        break;
                    case 'd':
                        //case dalle
                        grilleEntitesStatiques[x][y] = new DalleInflammable(this);
                        break;
                    case 'v':
                        //case vide
                        grilleEntitesStatiques[x][y] = new Vide(this);
                        break;
                    case 'u':
                        // casseur mur
                        grilleEntitesStatiques[x][y] = new Casseur(this);
                        break;
                    case 'f':
                        //case feu
                        grilleEntitesStatiques[x][y] = new Feux(this);
                        break;
                    case 'p':
                        //case porte
                        grilleEntitesStatiques[x][y] = new Porte(this);
                        break;
                    case 'q':
                        //case porte a cle
                        grilleEntitesStatiques[x][y] = new Porte(this, true);
                        break;
                    case 'c':
                        //case cle
                        grilleEntitesStatiques[x][y] = new Cle(this);
                        break;
                    case 's':
                        //case capsule a eau
                        grilleEntitesStatiques[x][y] = new Capsule(this);
                        break;
                    case 'r':
                        //case coffre
                        grilleEntitesStatiques[x][y] = new Coffres(this, Dim_Enum.EffetCase.COF_CAP_CLE);
                        break;
                    case 'l':
                        //dalle a pick
                        grilleEntitesStatiques[x][y] = new DallePick(this);
                        break;
                    default:
                        //case position joueur
                        //le cas d'un j-> jouer,
                        //ne s'execute qu'une seule fois
                        if(this.processChangePartie)
                            heros.initHeros(x,y);
                        else
                            heros = new Heros(this, x, y);
                        //on met une case vide
                        grilleEntitesStatiques[x][y] = new CaseNormale(this);
                        break;
                }

            }
        }

    }

    public void reprendrePartie(){
        this.finChargementPartie=false;
        this.processChangePartie=true;
        //le jeu attends que la fenetre se remette a jour pour cette partie
        this.initialisationDesEntites();
        this.finChargementPartie=false;
    }
    public void chargeNouvellePartie(){

        this.processChangePartie=true;
        //on change de partie
        this.partie++;
        //si y'a pas d'autre partie, on change de niveau
        if(this.finPartie==Dim_Enum.longueurmax){
            //si les nouveau ne sont pas fini alors:
            if(niveau<Dim_Enum.maxniveau){
                niveau++;
                this.partie=1;
                terrain= Chargeur.getTerrainDuNiveau(CheminVers.niveaux+niveau+".txt");
                this.reprendrePartie();
            }else{
                //le jeu est terminer
                this.setStopjeu(true);
                this.setGameover(true);
            }
        }else{
            //on change la partie
            this.debutPartie=this.finPartie;
            this.finPartie=this.debutPartie+10;
        }
            //le jeu attends que la fenetre se remette a jour pour la nouvelle partie
            this.initialisationDesEntites();
            this.finChargementPartie=true;
    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {

        while(true) {
            if(!this.stopjeu){
                if(this.processChangePartie){
                    //on attends que le jeu se mette a jour
                    if(this.finChargementPartie){
                        setChanged();
                        notifyObservers();
                        this.finChargementPartie=false;
                        try {
                            synchronized (this){
                                this.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }else if(this.processChangeNiveau){
                    //on attends que le jeu se mette a jour pour ce niveau
                    if(this.finChargementPartie){
                        setChanged();
                        notifyObservers();
                        this.finChargementPartie=false;
                        try {
                            synchronized (this){
                                this.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    setChanged();
                    notifyObservers();
                }

                try {
                    Thread.sleep(pause);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                setChanged();
                notifyObservers();
            }

        }

    }
    private void timerDallePick(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true){
                    try {
                        if(dallepickeok) {
                            dallepickeok=false;
                        }
                        else {
                            dallepickeok=true;
                        }
                        Thread.sleep(dallepickeoktime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void addEntiteStatique(EntiteStatique e, int x, int y) {
        grilleEntitesStatiques[x][y] = e;

    }

}
