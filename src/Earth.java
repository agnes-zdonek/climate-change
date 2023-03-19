import java.util.Random;
import java.util.ArrayList;

/**
 * class Earth contains a grid on which we place mines and forests - it is our enviroment
 * Earth takes care of what CO2 does on our grid
 */

public class Earth {

  /**
   * @param INSTANCE is a singelton, we can only initialise one enviroment
   * @param terrain our grid
   * @param numForests number of forests
   * @param numMines number of coal mines
   */
  private static final Earth INSTANCE = new Earth();
  private Terrain terrain;
  public static int numForests = 0;
  public static int numMines = 0;
  
  /**
   * constructor for our singelton 
   */
  private Earth() {
    terrain = new Terrain(20,20);
  }

  /**
   * creates a singelton for our enviroment
   * @return singelton
   */
  public static Earth getInstance(){ return INSTANCE; }

  /**
   * returns our grid with ressources 
   * @return grid with ressources 
   */
  public Terrain getTerrain(){
    return terrain;
  }

  /**
   * if a place is empty, place a ressource
   * @param ressource ressource - coal mine or a forest
   * @param i first coordinate
   * @param j second coordinate
   */
  public void plantRessource(Ressource ressource, int i, int j) {
    if (!terrain.caseEstVide(i,j)){
        return;
    }
    terrain.setCase(i,j,ressource);
    if(ressource instanceof Forest){
    numForests++;
    } else {numMines++;}
  }

  
  /**
   * place a ressource on a first empty place in our grid
   * @param ressource ressource - coal mine or a forest
   */
  public void plantRessource(Ressource ressource) {
    for(int i = 0; i < terrain.nbLignes; i++){
        for(int j = 0; j < terrain.nbColonnes;j++){
            if(terrain.caseEstVide(i,j)){
                terrain.setCase(i,j,ressource);
                if(ressource instanceof Forest){
                    numForests++;
                } else {numMines++;}
                return;
            }
        }
    }
    System.out.println("No left cases to plant a forest");
  }
  


 
  /**
   * plants forests in our enviroment using rules of seed dispersal (and uses probability density function)
   * @param forest forest we will use for seed dispesal
   */
  public void plantForestsBySeedDispersal(Forest forest) {

   
    double totalProbability = 0;
    double rememberRandom = Math.random();
    boolean success = rememberRandom < 0.015;

    if (success) {
        double[][] probabilityDis = new double[terrain.nbLignes+1][terrain.nbColonnes+1];

        for (int i = 0; i < terrain.nbLignes; i++) {
            for(int j = 0 ; j < terrain.nbColonnes; j++){
                if(i == forest.getX() && j == forest.getY()){
                    probabilityDis[i][j] = 0.0;
                }else {
                double x = Math.abs(forest.getX() - i);
                double y = Math.abs(forest.getY() - j);
                double distance = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
                probabilityDis[i][j] = 1.0 / (1 + distance);
                totalProbability += probabilityDis[i][j];
                }
            }
          }

          
          Random r = new Random();
          double randomNumber = (totalProbability)* r.nextDouble();
        
        
          for (int i = 0; i < terrain.nbLignes; i++) {
            for(int j = 0 ; j < terrain.nbColonnes; j++){
                if(i == forest.getX() && j == forest.getY()){
                    continue;
                }else {
                    if (randomNumber < probabilityDis[i][j]) {
                        Forest son = new Forest(forest);
                        this.plantRessource(son, i,j);
                        return;
                      } else {
                        randomNumber -= probabilityDis[i][j];
                      }
                    }
                }
            }
          }
          
     
    }


    /**
     * takes an array with how much CO2 there is on each place in the grid and according to what ressources are on the grid we update the array
     * @param carbonDis how much CO2 there is on each place in the grid
     */
    public void carbonProduction(long[][] carbonDis){
    for(int i = 0; i < terrain.nbLignes; i++){
        for(int j = 0 ; j < terrain.nbColonnes; j++){
            if(!terrain.caseEstVide(i,j)){
                if(terrain.getCase(i,j) instanceof Forest){
                carbonDis[i][j]+= ((Forest)terrain.getCase(i,j)).impactOnTerrain();
                }else {
                    carbonDis[i][j]+= ((CoalMine)terrain.getCase(i,j)).impactOnTerrain();
                }
            }
        }
    }
    }

    /**
     * disperses CO2 in our enviroment
     * @param carbonDis how much CO2 there is on each place in the grid
     */
    public void carbonDispersion(long[][] carbonDis){
        //same as terrain
        for(int i = 0; i < carbonDis.length; i++){
            for(int j = 0; j < carbonDis[0].length;j++){
                if(carbonDis[i][j] != 0){
                    long tempOld = carbonDis[i][j];
                    carbonDis[i][j]= (int)(carbonDis[i][j]*(3.00/4.00));
                    int xr = (int)(Math.random()*carbonDis.length);
                    int yr =(int)(Math.random()*carbonDis[0].length);
                    carbonDis[xr][yr] += (int)(tempOld*(1.00/4.00)); 

                }
            }
        }    
        
            
            
            

    }

    /**
     * how much CO2 there is on avreage in one case
     * @param carbon how much CO2 there is on each place in the grid
     * @return CO2 avreage in one case
     */
    public long carbonAvrege(long[][] carbon){
        long max = 0;
        for(int i = 0; i < carbon.length; i++){
            for(int j = 0; j < carbon[0].length; j++){
                max=max+carbon[i][j];
            }
        }

        long h = max/(carbon.length*carbon[0].length);
        return h;
    }

    /**
     * how much CO2 one case in the grid can handle (if we surpass this our enviroment is affected negatively)
     * @param carbon how much CO2 there is on each place in the grid
     * @return how much CO2 one case in the grid can handle 
     */
    public long carbonGoal(long[][] carbon){
        return (long)(-1.00*103921568.00)/(terrain.nbColonnes*terrain.nbLignes);
    }
    
        
    /**
     * prints our enviroment/grid
     */
    public void afficheEarth(){
        terrain.affiche(4);
    }

    /**
     * returs true if each place in the grid is occupied
     * @return true or false
     */
    public boolean isFull(){
        for(int i = 0; i < terrain.nbLignes;i++){
            for(int j = 0; j < terrain.nbColonnes; j++){
                if(terrain.getCase(i,j)== null){
                    return false;
                }
            }
        }
        return true;
    }


  }

