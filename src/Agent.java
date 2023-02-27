
import java.util.ArrayList;

public class Agent{

    /*
    x, y - coordonnées d’un agent
    nbDep - combien de fois il a déménagé 
    */
    private int x,y,nbDep;

    //constructeur
    public Agent(){
        x = -1;
        y = -1;
        nbDep = 0;
    }

    //constructeur
    public Agent(int x, int y){
        this.x = x;
        this.y = y;
        nbDep = 0;
    }

    //accesseur pour x 
    public int getAgentX(){
        return x;
    }

    //accesseur pour y
    public int getAgentY(){
        return y;
    }

    //accesseur pour nbDep
    public int getNumDeplacements(){
        return nbDep;
    }

    public double distance(int x, int y){
        int a = Math.abs(this.x - x);
        int b = Math.abs(this.y - y);
        return Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
    }

    

    public boolean seDeplacer(int xnew, int ynew, ArrayList<Agent> agent){
        for(Agent a : agent){
            if(a.getAgentX() == xnew && a.getAgentY() == ynew){
                return false;
            }
        }

        x = xnew;
        y = ynew; 
        nbDep++;
        return true;
    }

    

    /*
     * cette fonction prend un tableau de dioxyde de carbone, un avreage de dioxyde de carbone, 
     * une quantité désirée de dioxyde de carbone, une liste d’agents et une place qu’ils sont dans 
     * 
     * compte tenu de ces variables, un agent se déplace là où il se trouve 
     * et tente de planter des forêts ou de détruire des mines afin d’atteindre la quantité désirée de dioxyde de carbone
     * cette fonction représente ce que l’agent peut faire dans l’année
     */
    public void fixingClimateChange(long[][] carbon, double carbonAvrege, double carbonGoal, ArrayList<Agent> agent, Earth earth){
        
        int x = -1;
        int y = -1;

        long min = carbon[0][0];
        int xm = -1;
        int ym = -1;
        for(int i = 0; i < carbon.length; i++){
            for(int j = 0; j < carbon[0].length; j++){
                if (carbon[i][j] < min){
                    min = carbon[i][j];
                    x = i;
                    y = j;
                }
            }
        }
        
        
        if(Math.random() < 0.6){
            x = xm;
            y = ym;
        } else {
            x = (int)(Math.random()*carbon.length);
            y = (int)(Math.random()*carbon[0].length);
        }
        


        boolean var = seDeplacer(x, y , agent);
        if(var){
            if(earth.getTerrain().getCase(x, y) instanceof CoalMine || earth.getTerrain().caseEstVide(x, y)){
                earth.getTerrain().videCase(x, y);
                int r = (int)(Math.random()*(1000)+1000);
                earth.plantRessource(new Forest("FA", r),x,y);
            }

            else if (earth.getTerrain().getCase(x, y) instanceof Forest){
                if(((Forest)(earth.getTerrain().getCase(x, y))).getNumTrees() < 4000){
                    int r = (int)(Math.random()*(300)+ 200);
                    for(int a = 0; a < r; a++){
                    ((Forest)earth.getTerrain().getCase(x, y)).plantTree(new Tree());
                    }
                }
               
            }

        }

    }

    /*c
    ette fonction prend un tableau de dioxyde de carbone, une liste d’agents et une place 
    si un agent se trouve dans une mine, il produit plus de dioxyde de carbone 
    s’il est dans une forêt, il la détruit */
    public void makingClimateChangeWorse(long[][] carbon, ArrayList<Agent> agent, Earth earth){

        int x = (int)(Math.random()*carbon.length);
        int y = (int)(Math.random()*carbon[0].length);

        if(this.distance(x, y) < carbon.length*0.5){
        boolean var = seDeplacer(x, y , agent);
        if(var){
            if(earth.getTerrain().getCase(x, y) instanceof CoalMine){
                long temp = ((CoalMine)earth.getTerrain().getCase(x, y)).impactOnTerrain();
                ((CoalMine)earth.getTerrain().getCase(x, y)).setCarbonProduction(temp*(long)1.5);
            }

            else if (Math.random()<0.7){
                if(earth.getTerrain().getCase(x, y) instanceof Forest){
                (earth.getTerrain()).videCase(x,y);
                }
            }
        }
    }
    }




}
