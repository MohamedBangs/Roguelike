package modele.plateau.entites.entitefille;

import config.Dim_Enum;
import modele.plateau.Jeu;
import modele.plateau.entites.entitemere.ComposantSalle;

public class Mur extends ComposantSalle {
    private Dim_Enum.EffetCase effetCase;
    public Mur(Jeu _jeu) {
        super(_jeu);
        this.effetCase=Dim_Enum.EffetCase.MUR;
    }
    public Mur(Jeu _jeu, Dim_Enum.EffetCase effetCas) {
        super(_jeu);
        this.effetCase=effetCas;
    }
    public Dim_Enum.EffetCase getTypeMur(){
        return this.effetCase;
    }

    @Override
    public Dim_Enum.EffetCase traversable() {
        return this.effetCase;
    }

}
