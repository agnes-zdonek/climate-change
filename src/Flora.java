
/**
 * abstract class Flora represents any type of greenery 
 * it was created to create classes similar to forests in the future ex: meadows, parks
 */
public abstract class Flora extends Ressource{
   
   

   /**
    * constructor for Flora
    * @param type type - ex forest, meadow etc or a name 
    * @param quantite - how many flora do we want to construct
    */
   public Flora(String type, int quantite){
    super(type, quantite);
   }

   /**
    * plants a tree 
    * @param tree tree
    */
   public abstract void plantTree(Tree tree);

   /**
    * how much carbon can one instace get rid of in a year
    * @return how much carbon can one instace get rid of in a year
    */
   public abstract int getMaxcarbon();
   

}
