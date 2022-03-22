package modele.plateau.entites.entitefille;

import config.Dim_Enum;
import modele.plateau.Jeu;
import modele.plateau.entites.entitemere.Ramassable;

public class Casseur extends Ramassable {
    public Casseur(Jeu _jeu) { super(_jeu); }

    @Override
    public Dim_Enum.EffetCase traversable() {
        return Dim_Enum.EffetCase.CASSEUR;
    }
}
