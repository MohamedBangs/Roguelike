package outils;

public abstract class Chargeur {

    public static String[] getTerrainDuNiveau(String fichier){

        String[] tableau = new String[0]; //création d'un fichier vide
        try {
            java.io.InputStream inputStream=new java.io.FileInputStream(fichier);
            java.io.InputStreamReader inputStreamReader=new java.io.InputStreamReader(inputStream);
            java.io.BufferedReader bufferedReader=new java.io.BufferedReader(inputStreamReader);
            String ligne;
            //parcour du fichier
            while ((ligne=bufferedReader.readLine())!=null){
                String[] oldTableau = tableau;
                int noligne = oldTableau.length;
                tableau = new String[noligne+1]; //afin d'ajouter la ligne on augmente la capacité du tableau
                System.arraycopy(oldTableau, 0, tableau,0, noligne);//on recopie le contenu de l'ancien tableau dans le nouveau
                tableau[noligne] = ligne; //affectation de la ligne du fichier au dernier élément du tableau
            }
            bufferedReader.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return tableau;
    }
    public static String getTexte(String fichier){

        String texte="";
        try {
            java.io.InputStream inputStream=new java.io.FileInputStream(fichier);
            java.io.InputStreamReader inputStreamReader=new java.io.InputStreamReader(inputStream);
            java.io.BufferedReader bufferedReader=new java.io.BufferedReader(inputStreamReader);
            String ligne;
            //parcour du fichier
            while ((ligne=bufferedReader.readLine())!=null){
                texte+=ligne+"\n";
            }
            bufferedReader.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return texte;
    }
}
