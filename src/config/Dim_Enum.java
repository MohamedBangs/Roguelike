package config;

public abstract class Dim_Enum {
    public static int largeur=20;
    public static int longueur=10;
    public static int longueurmax=30;
    public static int widthbutton=120;
    public static int heightbutton=30;
    public static int maxniveau=3;
    public static int largeurMenuDialog=500;
    public static int hauteurMenuDialog=500;
    public static int fenetreWidht=1000;
    public static int fenetreHeight=700;
    public enum EffetCase{
        NORMAL,
        DALLEINFLAMMABLE,
        VIDE,
        FEUX,
        PORTE,
        CLE,
        MUR,
        PORTEACLE,
        CAPSULE,
        COF_CAP_CLE,
        DALLE_PICK,
        CASSEUR,
        MUR_CASSABLE
    };
    public enum OrientationHeros{
        HAUT,
        BAS,
        GAUCHE,
        DROITE
    }
    public enum RetourMenu{
        CONTINUER,
        NOUVELLE_PARTIE,
        NIVEAU,
        QUITTER,
        R,
    }
}
