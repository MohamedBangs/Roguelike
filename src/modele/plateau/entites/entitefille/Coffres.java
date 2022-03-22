package modele.plateau.entites.entitefille;

import config.Dim_Enum;
import modele.plateau.EntiteStatique;
import modele.plateau.Jeu;
import modele.plateau.entites.entitemere.Ramassable;

public class Coffres extends Ramassable {
    Dim_Enum.EffetCase type;
    public Coffres(Jeu _jeu, Dim_Enum.EffetCase typ) {
        super(_jeu);
        this.type=typ;
    }

    @Override
    public Dim_Enum.EffetCase traversable() {
        return (this.type== Dim_Enum.EffetCase.COF_CAP_CLE)?
                Dim_Enum.EffetCase.COF_CAP_CLE: Dim_Enum.EffetCase.COF_CAP_CLE;
    }
}
