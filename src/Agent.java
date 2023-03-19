import java.util.ArrayList;

/**
* Agents move on the grid and renforce/stop climate change based on Agent's role
*/

public class Agent{

    /**
    * @param x first coordinate
    * @param y second coordinate
    * @param nbDep number of trips made
    */
    private int x,y,nbDep;

    /**
     * contructor that initialises an Agent
     * they start to exist but aren't placed on a grid
     */
    public Agent(){
        x = -1;
        y = -1;
        nbDep = 0;
    }

    /**
     * contructor that initialises an Agent on given coordinates
     * @param x first coordinate
     * @param y second coordinate
     */
    public Agent(int x, int y){
        this.x = x;
        this.y = y;
        nbDep = 0;
    }

    /**
     * returns coordinate x
     * @return x first coordinate
     */
    public int getAgentX(){
        return x;
    }

    /**
     * returns coordinate y
     * @return y second coordinate
     */
    public int getAgentY(){
        return y;
    }

    /**
     * returns number of trips made
     * @return nbDep number of trips made
     */
    public int getNumDeplacements(){
        return nbDep;
    }

    /** 
     * returns distace between the place where Agent is now and some other place in the grid
     * @param x first coordinate
     * @param y second coordinate
     * @return distace between the place where Agent is now and some other place in the grid
     */
    public double distance(int x, int y){
        int a = Math.abs(this.x - x);
        int b = Math.abs(this.y - y);
        return Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
    }

    
    /**
     * returns true if there is an agent on given coordinates, else returns false, if there isn't an Agent on a given place, current Agent makes a trip to that given place
     * @param xnew first coordinate
     * @param ynew second coordinate
     * @param agent list of Agents
     * @return true or false
     */
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

    

    /**
     *
     * this function represents what the agent can do during the year
     * first we find where is the least amount of carbon
     * then we either go to that place or to a random one (based on a given probability)
     * if a current agent can do there it goes there:
     * - if there is a coal mine or nothing, they close it and build a forest 
     * - if there is a forest they plant more trees 
     * each action generates an amount of co2 we can absorb to better the enviroment
     * 
     * @param carbon - array of carbon dioxide - tells us how much carbon there is in each place in a grid
     * @param carbonAvrege - an avreage of carbon dioxide in one place on a grid
     * @param carbonGoal - a desired amount of carbon dioxide in one place on a grid
     * @param agent - a list of one type of agents - good agents
     * @param earth - our enviroment which contains a grid
     * 
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


    /**
     *
     * this function represents what the agent can do during the year
     * we generate a random place
     * if it's close enough : we check if it's free and if it is: 
     * - if there is a coal mine, they make it generate more co2
     * - if probability is on our side and there is a forest, they cut out all the trees
     * each action generates an amount of co2 that worsens out enviroment
     * 
     * @param carbon - array of carbon dioxide - tells us how much carbon there is in each place in a grid
     * @param agent - a list of one type of agents - bad agents
     * @param earth - our enviroment which contains a grid
     * 
     */
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
