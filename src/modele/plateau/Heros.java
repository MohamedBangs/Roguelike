/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import config.Dim_Enum;
import modele.plateau.entites.*;
import modele.plateau.entites.entitefille.*;


/**
 * Héros du jeu
 */
public class Heros {
    private int x;
    private int y;

    private Jeu jeu;
    private Inventaire inventaire;
    private Dim_Enum.OrientationHeros orientationHeros;
    private boolean entremblement;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Heros(Jeu _jeu, int _x, int _y) {
        jeu = _jeu;
        x = _x;
        y = _y;
        this.inventaire=new Inventaire();
        this.orientationHeros= Dim_Enum.OrientationHeros.DROITE;
        this.entremblement=false;
    }
    public void initHeros(int _x, int _y) {
        x = _x;
        y = _y;
    }
    public boolean droite() {
        if(this.orientationHeros==Dim_Enum.OrientationHeros.DROITE){
            if (traversable(x+1, y)) {
                x ++;
                return true;
            }
        }else {
            //on change tout simplement d'orientation
            this.orientationHeros= Dim_Enum.OrientationHeros.DROITE;
            return true;
        }
        return false;
    }

    public boolean gauche() {
        if(this.orientationHeros==Dim_Enum.OrientationHeros.GAUCHE) {
            if (traversable(x - 1, y)) {
                x--;
                return true;
            }
        }else {
            //on change juste orientation
            this.orientationHeros = Dim_Enum.OrientationHeros.GAUCHE;
            return true;
        }
        return false;
    }

    public boolean bas() {
        if(this.orientationHeros==Dim_Enum.OrientationHeros.BAS) {
            if (traversable(x, y + 1)) {
                y++;
                return true;
            }
        }else {
            this.orientationHeros = Dim_Enum.OrientationHeros.BAS;
            return true;
        }
      return false;
    }

    public boolean haut() {
        if(this.orientationHeros==Dim_Enum.OrientationHeros.HAUT) {
            if (traversable(x, y - 1)) {
                y--;
                return true;
            }
        }else {
            this.orientationHeros = Dim_Enum.OrientationHeros.HAUT;
            return true;
        }
        return false;
    }
    public void useCapsule(){
        if(this.inventaire.getNombreCapsule()>0){
            if(this.orientationHeros== Dim_Enum.OrientationHeros.DROITE && jeu.getEntite(x+1,y) instanceof Feux){
                this.jeu.setEntite(x+1,y, new DalleInflammable(this.jeu));
                this.inventaire.setBonusCapsule(-1);
                this.droite();
            }else if(this.orientationHeros== Dim_Enum.OrientationHeros.GAUCHE && jeu.getEntite(x-1,y) instanceof Feux){
                this.jeu.setEntite(x-1,y, new DalleInflammable(this.jeu));
                this.inventaire.setBonusCapsule(-1);
                this.gauche();
            }else if(this.orientationHeros== Dim_Enum.OrientationHeros.BAS && jeu.getEntite(x,y+1) instanceof Feux){
                this.jeu.setEntite(x,y+1, new DalleInflammable(this.jeu));
                this.inventaire.setBonusCapsule(-1);
                this.bas();
            }else if(this.orientationHeros== Dim_Enum.OrientationHeros.HAUT && jeu.getEntite(x,y-1) instanceof Feux){
                this.jeu.setEntite(x,y-1, new DalleInflammable(this.jeu));
                this.inventaire.setBonusCapsule(-1);
                this.haut();
            }
        }
    }
    public void sauter(){
            EntiteStatique e;
            if(this.orientationHeros== Dim_Enum.OrientationHeros.DROITE && jeu.getEntite(x+1,y) instanceof Vide){
                e=jeu.getEntite(x+2,y);
                if(!((e instanceof Mur && ((Mur) e).getTypeMur()!= Dim_Enum.EffetCase.MUR_CASSABLE) || e instanceof Feux))
                    x=x+1;this.droite();
            }else if(this.orientationHeros== Dim_Enum.OrientationHeros.GAUCHE && jeu.getEntite(x-1,y) instanceof Vide){
                e=jeu.getEntite(x-2,y);
                if(!((e instanceof Mur && ((Mur) e).getTypeMur()!= Dim_Enum.EffetCase.MUR_CASSABLE) || e instanceof Feux))
                    x=x-1;this.gauche();
            }else if(this.orientationHeros== Dim_Enum.OrientationHeros.BAS && jeu.getEntite(x,y+1) instanceof Vide){
                e=jeu.getEntite(x,y+2);
                if(!((e instanceof Mur && ((Mur) e).getTypeMur()!= Dim_Enum.EffetCase.MUR_CASSABLE) || e instanceof Feux))
                    y=y+1;this.bas();
            }else if(this.orientationHeros== Dim_Enum.OrientationHeros.HAUT && jeu.getEntite(x,y-1) instanceof Vide){
                e=jeu.getEntite(x,y-2);
                if(!((e instanceof Mur && ((Mur) e).getTypeMur()!= Dim_Enum.EffetCase.MUR_CASSABLE) || e instanceof Feux))
                    y=y-1;this.haut();
            }
    }
    public boolean getEntremblement(){return this.entremblement;}
    public void setEntremblement(boolean b){
        this.entremblement=b;}
    public Inventaire getInventaire() {
        return this.inventaire;
    }
    public Dim_Enum.OrientationHeros getOrientationHeros() {
        return this.orientationHeros;
    }
    private boolean traversable(int x, int y) {

        if (x >0 && x < Dim_Enum.largeur && y > 0 && y < jeu.getFinPartie()) {
            return this.permitTraverser(jeu.getEntite(x, y).traversable(), x, y);
        } else {
            return false;
        }
    }
    private boolean permitTraverser(Dim_Enum.EffetCase effetCase,int x, int y){
        switch (effetCase){
            case CLE:
                this.jeu.setEntite(x,y, new CaseNormale(this.jeu));
                this.inventaire.setNombreCle(1);
                return true;
            case NORMAL:
                return true;
            case DALLEINFLAMMABLE:
                //case dalleinflammable, après passage devien feux
                this.jeu.setEntite(x,y, new Feux(this.jeu));
                return true;
            case VIDE:
                //case troue..on fait rien
                return false;
            case FEUX:
                //case feu
                if(this.inventaire.getNombreCapsule()>0){
                    this.jeu.setEntite(x,y, new DalleInflammable(this.jeu));
                    this.inventaire.setBonusCapsule(-1);
                    return true;
                }else{
                    //on diminue sa vie et rajoute un capsule d'eau a son inventaire(une vie == 1 capsule d'eau)
                    this.inventaire.setVie(-1);
                    this.inventaire.setBonusCapsule(1);
                    if(this.inventaire.getVie()<=0){
                        jeu.setStopjeu(true);
                        jeu.setGameover(true);
                    }
                    this.entremblement=true;
                    return true;
                }
            case MUR:
                return false;
            case CASSEUR:
                this.getInventaire().setBonuscassable(1);
                this.jeu.setEntite(x,y, new CaseNormale(this.jeu));
                return true;
            case MUR_CASSABLE:
                //mur cassable on verie son nombre de cassable dans inventaire
                if(this.getInventaire().getBonuscassable()>0){
                    //on cassele mur
                    this.jeu.setEntite(x,y, new CaseNormale(this.jeu));
                    this.getInventaire().setBonuscassable(-1);
                    return true;
                }return false;
            case CAPSULE:
                //case capsule
                this.jeu.setEntite(x,y, new CaseNormale(this.jeu));
                this.inventaire.setBonusCapsule(1);
                return true;
            case COF_CAP_CLE:
                //coffre: cle et capsule
                this.jeu.setEntite(x,y, new CaseNormale(this.jeu));
                this.inventaire.setNombreCle(1);
                this.inventaire.setBonusCapsule(1);
                return true;
            case DALLE_PICK:
                //  dalle a pick
                if(jeu.getDallepickeok()){
                    //le joueur est sur la dalle pick
                    //on diminue sa vie
                    this.inventaire.setVie(-1);
                    if(this.inventaire.getVie()<=0){
                        jeu.setStopjeu(true);
                        jeu.setGameover(true);
                    }
                    this.entremblement=true;
                    return false;
                }else{
                    //case normale, aucun degat
                    return true;
                }
            case PORTEACLE:
                //case porte a cle
                if (this.inventaire.getNombreCle()>0){
                    this.inventaire.setNombreCle(-1);
                    //demande à jeu de changer de partie
                    jeu.chargeNouvellePartie();
                    return false;
                }
                return false;
            default:
                //demande à jeu de changer de partie
                jeu.chargeNouvellePartie();
                return false;
        }
    }
}
