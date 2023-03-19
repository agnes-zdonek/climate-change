public class CoalMine extends Ressource implements impactOnClimateChange {

    //variable pour la quantité de dioxyde de carbone que la mine émet dans l’air
    private long carbonProduction =  -999999999;

    //constructeur qui crée une mine
    public CoalMine(String type){
        super(type, 1);
    }

    //constructeur qui crée une mine
    public CoalMine(String type, long carbonProduction){
        super(type, 1);
        this.carbonProduction = carbonProduction;
    }

    //cette fonction renvoie la quantité de sioxyde de carbone émise dans l’air par année
    public long impactOnTerrain(){ 
        return carbonProduction; 
    }

    //mutateur pour la variable carbonProduction
    public void setCarbonProduction(long carbonProduction){
        this.carbonProduction = carbonProduction;
    }
}
