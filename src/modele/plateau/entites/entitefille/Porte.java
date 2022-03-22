package modele.plateau.entites.entitefille;

import config.Dim_Enum;
import modele.plateau.Jeu;
import modele.plateau.entites.entitemere.ComposantSalle;

public class Porte extends ComposantSalle {

    private boolean porteacle;
    //typeporte==true: porte a cle
    //si non porte normale
    public Porte(Jeu _jeu) {
        super(_jeu);
        this.porteacle=false;
    }
    public Porte(Jeu _jeu, boolean porteacle) {
        super(_jeu);
        this.porteacle=porteacle;
    }
    public boolean getEtatPorte(){
        return this.porteacle;
    }

    @Override
    public Dim_Enum.EffetCase traversable() {
        return (this.porteacle)? Dim_Enum.EffetCase.PORTEACLE: Dim_Enum.EffetCase.PORTE;
    }

}
