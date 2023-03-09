

public class TestSimulation {
    public static void main(String[] args)throws InvalidInputException {
        Simulation sim = new Simulation(1, 21, -299, 2990);
        sim.simulateClimateChange();

        // C'est trop de tests pour le terminal, lancez les un par un
        //Simulation sim = new Simulation(5, 10, 1, 3);
        //sim.simulateClimateChange();

        //Simulation sim = new Simulation(20, 20, 1, 1);
        //sim.simulateClimateChange();

        //Simulation sim = new Simulation(3, 3, 3, 3);
        //sim.simulateClimateChange();
    }
}
