import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class TestSimulation {
    public static void main(String[] args)throws InvalidInputException, IOException {
        
        int checkForInt;
        
        if (args.length != 4){
            System.err.println("You must provide 4 arguments: number of forests, number of coal mines, number of eco friendly agents, number of agents damaging our earth\nAll numbers must be between > 0 and <=20\n");
            System.exit(1);
        }
                
        for(int i = 0; i < 4; i++){
            try {
                checkForInt =Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }
        

        System.out.println("All termianl output is in TerminalOutput directory in the src folder");

        File file = new File("./TerminalOutput/terminal.log");
        PrintStream stream = new PrintStream(file);
        System.setOut(stream);
        Simulation sim = new Simulation(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[2]));
        sim.simulateClimateChange();

        //Simulation sim = new Simulation(1, 21, -299, 2990);
        //sim.simulateClimateChange();

        // C'est trop de tests pour le terminal, lancez les un par un
        //Simulation sim = new Simulation(5, 10, 1, 3);
        //sim.simulateClimateChange();

        //Simulation sim = new Simulation(20, 20, 1, 1);
        //sim.simulateClimateChange();

        //Simulation sim = new Simulation(3, 3, 3, 3);
        //sim.simulateClimateChange();
    }
}
