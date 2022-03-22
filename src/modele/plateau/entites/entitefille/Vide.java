package modele.plateau.entites.entitefille;

import config.Dim_Enum;
import modele.plateau.Jeu;
import modele.plateau.entites.entitemere.ComposantSalle;

public class Vide extends ComposantSalle {
    public Vide(Jeu _jeu) { super(_jeu); }

    @Override
    public Dim_Enum.EffetCase traversable() {
        return Dim_Enum.EffetCase.VIDE;
    }
}
