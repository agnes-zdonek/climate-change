
import java.util.ArrayList;



public class Simulation {

  /*mf - nombre de forêts 
  mm - nombre de mines
  ng - nombre d’agents qui aide
 nb - qui détruisent */

  private int mf, mm, ng, nb;

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
      System.out.println("Chaque argument doit etre > 0 et <=20");
      System.out.println("Les valeurs <= 0 -> deviennent 5, les valeurs > 20 deviennent 10");

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

    System.out.println("Terrain au debut d'une simulation");
    for(Forest f : forests){
      System.out.println(f);
    }
    earth.afficheEarth();
    System.out.println("------------------------------------------------");
    System.out.println("Nous commencons un siecle de croissance forestiere sans surveillance et d'exploitation miniere illimitee...");

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

    System.out.println("Terrain apres");
    earth.afficheEarth();
    System.out.println("Combien de dioxyde de carbone nous avons en moyenne dans une case :" + earth.carbonAvrege(carbon));
    System.out.println("combien notre terre peut supporter" + earth.carbonGoal(carbon));

    System.out.println("------------------------------------------------");
    System.out.println("Maintenant les bons agents vont essayer de regler la situation mais ils doivent travailler aux cotes des agents qui detruisent leur travail");

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

  System.out.println("il a fallu "+ yearsittook+ " annees pour regler la situation");
  earth.afficheEarth();

  System.out.println("Combien de dioxyde de carbone nous avons en moyenne dans une case :" + earth.carbonAvrege(carbon));
  

  


  }

}
