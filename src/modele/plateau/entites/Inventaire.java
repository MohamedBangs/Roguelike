package modele.plateau.entites;

public class Inventaire {
    private int bonusCle;
    private int bonusCapsule;
    private int bonusvie;
    private int bonuscassable;

    public Inventaire(){
        this.bonusCle=0;
        this.bonusCapsule=0;
        //par defaut le bonus vie du niveau 1 est de 3
        this.bonusvie=3;
        this.bonuscassable=0;
    }
    public int getNombreCle(){
        return this.bonusCle;
    }
    public void setNombreCle(int cle){
        this.bonusCle=this.bonusCle+cle;
    }
    public int getNombreCapsule(){
        return this.bonusCapsule;
    }
    public void setBonusCapsule(int bonus){
        this.bonusCapsule=this.bonusCapsule+bonus;
    }
    public void setBonuscassable(int bonus){
        this.bonuscassable+=bonus;
    }
    public int getBonuscassable(){ return this.bonuscassable;}

    public int getVie() {
        return this.bonusvie;
    }
    public void setVie(int v) {
        this.bonusvie+=v;
    }
    public void initVie(int v) {
        this.bonusvie=v;
    }
    public void changeVie(int v) {
         this.bonusvie=v;
    }
}
