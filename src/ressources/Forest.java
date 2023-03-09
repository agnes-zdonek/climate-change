
import java.util.ArrayList;

public class Forest extends Flora implements impactOnClimateChange{


    private ArrayList<Tree> trees;
    //variable pour la quantité maximale de dioxyde de carbone qui peut être absorbée en un an
    private int maxCarbon;
    //variable pour la quantité de dioxyde de carbone qui a été absorbée pendant l’année en cours
    private int carbonAbsorbed;


    //constructeur pour la forêt
    public Forest(String type){
        super(type, 0);
        trees = new ArrayList<Tree>();
        maxCarbon = 0;
        carbonAbsorbed = 0;
    }

    //constructeur pour la forêt
    public Forest(String type, int quantite){
        super(type, quantite);
        trees = new ArrayList<Tree>();
        for(int i = 0; i < quantite; i++){
            trees.add(new Tree());
        }
        maxCarbon = 22*quantite;
        carbonAbsorbed = 0;
    }

    //constructeur pour la forêt
    public Forest(){
        super("F", 0);
        trees = new ArrayList<Tree>();
        maxCarbon = 0;
        carbonAbsorbed = 0;
    }

    //constructeur par copie qui modifie la quantité d’arbres dans la nouvelle forêt
    public Forest(Forest forest){
        super(forest.type, forest.getQuantite()/5);
        trees = new ArrayList<Tree>(forest.getTrees());
        for(Tree t : trees){
            t.setAge(0);
        }
        maxCarbon = (forest.getQuantite()/5)*22;
        carbonAbsorbed = 0;
    }
    
    //accesseur pour les arbres
    public ArrayList<Tree> getTrees(){
        return trees;
    }

    //cette fonction prend un arbre et l’ajoute à une forêt
    public void plantTree(Tree tree) {
        trees.add(tree);
        this.setQuantite(this.getQuantite()+1);
        maxCarbon+=22;

    }
    
    //cette fonction prend une forêt et retourne combien de dioxyde de carbone une forêt peut absorber dans une année
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

    //mutateur pour l'âge des arbres
    public void ageTrees(){
        for(Tree t : trees){
            t.setAge(t.getAge() + 1);
        }
    }

    //accesseur pour le nombre des arbres
    public int getNumTrees(){
        return trees.size();
    }
    
    public String toString(){
        return "Foret : "+type+", nombre d'arbres : "+ this.getQuantite();
    }

    ////accesseur pour la variable maxCarbon
    public int getMaxcarbon(){
        return maxCarbon;
    }  

}
