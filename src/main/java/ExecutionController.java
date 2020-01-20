
public class ExecutionController {
/**
 * This class is the controller class , it execute Minermanager thread */
    public static void main(String[] args) {

        int maxMinerCount = 10;
        Node rootNode = new Node("http://www.bbc.co.uk/news");

        MinerManager minerManager = new MinerManager(rootNode, maxMinerCount);
        Thread t1 = new Thread(minerManager);
        t1.start();
    }
}
