import java.util.ArrayList;

/**
 * regroups all the classes to produce a simulation where forests can be planted, can grow and coal mines work
 * agents interact and fight for their goals
 */
public class Simulation {

  /**
   * @param mf - number of forests
   * @param mm - number of coal mines
   * @param ng - number of eco friendly agents
   * @param nb - number of agents damaging our earth
   */
  private int mf, mm, ng, nb;

  /**
   * initialises a simulation of an earth with given arguments
   * if arguments aren't given within a range, program throws an exception and in the catch modyfies them 
   * 
   * @param mf - number of forests
   * @param mm - number of coal mines
   * @param ng - number of eco friendly agents
   * @param nb - number of agents damaging our earth
   */
  public Simulation(int mf, int mm, int ng, int nb){

    int[] temp = new int[4];
    temp[0] = mf;
    temp[1] = mm;
    temp[2] = ng;
    temp[3] = nb;
    try {

      for(int i = 0; i < temp.length; i++){
        if(temp[i]<=0 || temp[i]>20){throw new InvalidInputException();}
      }
  
      this.mf = mf;
      this.mm = mm;
      this.ng = ng;
      this.nb = nb;

    } catch (InvalidInputException iie) {
      System.out.println("All numbers must be between > 0 and <=20\n");
      System.out.println("Values <= 0 -> become 5, values > 20 become 10");

      for(int i = 0; i < temp.length; i++){
        if(temp[i]<=0){temp[i] = 5;}
        else if(temp[i] > 20){temp[i] = 10;}
      }

      this.mf = temp[0];
      this.mm = temp[1];
      this.ng = temp[2];
      this.nb = temp[3];


    } finally {
     System.out.println("Let's proceed!!!");
  }
      
   
  }
  
  /**
   * simulates climate changes in our enviroment
   * we contrustruct an earth and a list of all ressources (forests and coal mines)
   * we initialise forests in  @param mf number, coal mines in @param mm number, eco agents in @param ng number and bad agents in @param nb number
   * each year coal mines produce CO2, forests grow and new trees are planted by seed dispersal
   * after a century of upsupervised mining too much CO2 will be in the air
   * now eco friendly agents and damadging agents do their assigned taskts utill we get an amount of carbon that our earth can sustain
   *  
   */
  public void simulateClimateChange() {


    Earth earth = Earth.getInstance();

    ArrayList<Ressource> ressources = new ArrayList<Ressource>();

    Forest[] forests = new Forest[mf];
      for(int i = 0; i < mf; i++){
        String s = "F"+i;
        int r = (int)(Math.random()*(1000)+1000);
        forests[i] = new Forest(s,r);
        earth.plantRessource(forests[i]);
      }

    CoalMine[] coalmines = new CoalMine[mm];
    for(int i = 0; i < mm; i++){
      String s = "CM"+i;
      coalmines[i] = new CoalMine(s);
      earth.plantRessource(coalmines[i]);
    }

    ArrayList<Agent> goodAgents = new ArrayList<Agent>();
    ArrayList<Agent> badAgents = new ArrayList<Agent>();
    

    for(int i = 0; i < ng; i++){
      goodAgents.add(new Agent());
    }

    for(int i = 0; i < nb; i++){
      badAgents.add(new Agent());
    }

    ArrayList<Agent> agents = new ArrayList<Agent>();
        agents.addAll(goodAgents);
        agents.addAll(badAgents);

    System.out.println("Paris at the beggining of the simulation");
    for(Forest f : forests){
      System.out.println(f);
    }
    earth.afficheEarth();
    System.out.println("------------------------------------------------");
    System.out.println("We begin a century of Forests' growth and unsupervised mining...");

    long [][] carbon = new long[20][20];
        for(int year = 0; year < 100; year++){
            ArrayList<Ressource> temp = earth.getTerrain().lesRessources();
            for(int i = 0; i < temp.size(); i++){
                if(temp.get(i)!=null && temp.get(i) instanceof Forest){
                    ((Forest)temp.get(i)).ageTrees();
                    earth.plantForestsBySeedDispersal((Forest)temp.get(i));
                }
            }
            earth.carbonProduction(carbon);
            earth.carbonDispersion(carbon);
    }

    System.out.println("Paris after a century");
    earth.afficheEarth();
    System.out.println("How much CO2 there is on avreage in one case:" + earth.carbonAvrege(carbon));
    System.out.println("How much can Paris sustain: " + earth.carbonGoal(carbon));

    System.out.println("\n******************\n");
    System.out.println("Now eco friendly agents and damadging agents will do their work!");

    int yearsittook = 0;

    while(earth.carbonAvrege(carbon) < earth.carbonGoal(carbon) ){

      ArrayList<Ressource> temp = earth.getTerrain().lesRessources();
      for(int i = 0; i < temp.size(); i++){
          if(temp.get(i)!=null && temp.get(i) instanceof Forest){
              ((Forest)temp.get(i)).ageTrees();
              earth.plantForestsBySeedDispersal((Forest)temp.get(i));
              }
          }
          earth.carbonProduction(carbon);
          earth.carbonDispersion(carbon);
          
  for(int i = 0; i <  goodAgents.size(); i++){
  goodAgents.get(i).fixingClimateChange(carbon, earth.carbonAvrege(carbon), earth.carbonGoal(carbon),agents , earth);
  }

  for(int i = 0; i <  badAgents.size(); i++){
    badAgents.get(i).makingClimateChangeWorse(carbon, agents, earth);
  }

  yearsittook++;
  }

  System.out.println("It took "+ yearsittook+ " years to balance CO2 in the atmosphere");
  earth.afficheEarth();

  System.out.println("How much CO2 there is on avreage in one case :" + earth.carbonAvrege(carbon));
  

  


  }

}
