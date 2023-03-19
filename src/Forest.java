import java.util.ArrayList;

/**
 * class Forest represents a forest
 */
public class Forest extends Flora implements impactOnClimateChange{

    /**
     * @param trees - a list of trees
     * @param maxCarbon - a maximal amount of carbon that can be absorbed in one year
     * @param carbonAbsorbed - carbon absorbed during ongoing year
     */
    private ArrayList<Tree> trees;
    private int maxCarbon;
    private int carbonAbsorbed;


    /**
     * constructor for a Forest, initialises a forest - no trees, no carbon 
     * @param type name for the forest
     */
    public Forest(String type){
        super(type, 0);
        trees = new ArrayList<Tree>();
        maxCarbon = 0;
        carbonAbsorbed = 0;
    }

    /**
     * constructor for a Forest, initialises quantite number of trees
     * @param type - name for a forest
     * @param quantitie - number of trees
     */
    public Forest(String type, int quantite){
        super(type, quantite);
        trees = new ArrayList<Tree>();
        for(int i = 0; i < quantite; i++){
            trees.add(new Tree());
        }
        maxCarbon = 22*quantite;
        carbonAbsorbed = 0;
    }

    /**
     * constructor for a Forest, initialises a forest - no trees, no carbon, calls it "F"
     */
    public Forest(){
        super("F", 0);
        trees = new ArrayList<Tree>();
        maxCarbon = 0;
        carbonAbsorbed = 0;
    }

    /**
     * copy constructor, initialises a new forest from an existing one, with a half amount of trees
     * @param forest forest for a deep copy
     */
    public Forest(Forest forest){
        super(forest.type, forest.getQuantite()/5);
        trees = new ArrayList<Tree>(forest.getTrees());
        for(Tree t : trees){
            t.setAge(0);
        }
        maxCarbon = (forest.getQuantite()/5)*22;
        carbonAbsorbed = 0;
    }
    
    /**
     * returns a list of trees in a forest
     * @return trees in a forest
     */
    public ArrayList<Tree> getTrees(){
        return trees;
    }

    /**
     * adds a given tree to a forest
     * @param tree tree
     */
    public void plantTree(Tree tree) {
        trees.add(tree);
        this.setQuantite(this.getQuantite()+1);
        maxCarbon+=22;

    }
    
    /**
     * takes a forest and calculates how much CO2 can it absorb during one year (based on how many trees there are)
     * @return how much CO2 can a forest absorb during one year
     */
    public long impactOnTerrain(){
        for(Tree t : trees){ 
        
            if(t.getAge() > 100){
                carbonAbsorbed+=11;
            } else {
                carbonAbsorbed+=8; 
            }


        }
        return carbonAbsorbed;
    }

    /**
     * ages all the trees in a forest
     */
    public void ageTrees(){
        for(Tree t : trees){
            t.setAge(t.getAge() + 1);
        }
    }

    /**
     * returns how many trees there are in a forest
     * @return number of trees
     */
    public int getNumTrees(){
        return trees.size();
    }
    
    /**
     * ovveride of a toString method
     * @return string with the name of a forest and a number of trees
     */
    public String toString(){
        return "Foret : "+type+", nombre d'arbres : "+ this.getQuantite();
    }

    /**
     * returns a maximal amount of carbon that can be absorbed in one year
     * @return a maximal amount of carbon that can be absorbed in one year
     */
    public int getMaxcarbon(){
        return maxCarbon;
    }  

}
