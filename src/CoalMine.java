/***
 * CoalMine is a coal mine. It generates C02 into our grid/enviroment
 */

public class CoalMine extends Ressource implements impactOnClimateChange {

    /**
     * @param carbonProduction how much CO2 does a mine produce
     */
    private long carbonProduction =  -999999999;

    /**
     * contructor that initialises a coal mine
     */
    public CoalMine(String type){
        super(type, 1);
    }

    /**
     * contructor that initialises a coal mine
     * @param carbonProduction how much CO2 does a mine produce
     */
    public CoalMine(String type, long carbonProduction){
        super(type, 1);
        this.carbonProduction = carbonProduction;
    }

    /***
     * how much of CO2 a mine produces during one year
     * @return how much CO2 a mine produces during one year
     */
    public long impactOnTerrain(){ 
        return carbonProduction; 
    }

    /***
     * sets new amount of CO2 a mine produces during one year
     * @param carbonProduction amount of CO2 a mine produces during one year
     */
    public void setCarbonProduction(long carbonProduction){
        this.carbonProduction = carbonProduction;
    }
}
