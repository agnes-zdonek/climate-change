public abstract class Flora extends Ressource{
   
   /* cette classe abstraite a été créée afin qu'à l'avenir 
   il soit plus facile de créer des ressources plus simmilaires (par exemple parcs, prairies)
   */

   //constructeur pour la classe
   public Flora(String type, int quantite){
    super(type, quantite);
   }

   //cette fonction prend un arbre et ajoute cet arbre à notre ressource
   public abstract void plantTree(Tree tree);

   //accesseur pour pour la quantité maximale de dioxyde de carbone qu’une ressource peut détruire
   public abstract int getMaxcarbon();
   

}
