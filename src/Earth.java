
import java.util.Random;
import java.util.ArrayList;

public class Earth {

  /*
   * la classe Earth qui contient le terrain et les reressources 
   * elle fait la gestion de dioxyde de carbone dans le terrain
   */

  private static final Earth INSTANCE = new Earth();
  private Terrain terrain;
  //variable pour le nombre de forêts
  public static int numForests = 0;
  //variable pour le nombre de mines
  public static int numMines = 0;
  
  //constructeur pour la classe Earth 
  private Earth() {
    terrain = new Terrain(20,20);

  }

  public static Earth getInstance(){ return INSTANCE; }

  //cette fonction renvoie le terrain
  public Terrain getTerrain(){
    return terrain;
  }

  /*cette fonction prend une ressource et une place dans le terrain
    si cet endroit est vide, la ressource est placée
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

  
  /*cette fonction prend une ressource et la place au premier endroit libre dans le terrain */
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
  


  /*
  cette fonction prend une forêt et plante une nouvelle forêt dans le terrain si la dispersion des graines a réussi,
  en utilisant la fonction de densité elle choisit un endroit dans le terrain pour le placer
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

    /*
     * cette fonction prend un tableau de dioxyde de carbone (dans chaque cas il y a une certaine quantité) 
     * et basé sur les ressources présentes dans le tableau 
     * il met à jour combien de dioxyde de carbone il y a maintenant
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

    /*
     * cette fonction prend un tableau de dioxyde de carbone et le disperse 
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

    /*
     * cette fonction prend un tableau de dioxyde de carbone et renvoie combien il y en a en moyenne dans une case
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

    /*cette fonction prend un tableau de dioxyde de carbone et renvoie quelle est la quantité désirée dans une case */
    public long carbonGoal(long[][] carbon){
        return (long)(-1.00*103921568.00)/(terrain.nbColonnes*terrain.nbLignes);
    }
    
        
    //cette fonction affiche le terrain 
    public void afficheEarth(){
        terrain.affiche(4);
    }

    //this function returns true is the terrain is full
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

