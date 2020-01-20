import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MinerManager implements Runnable {

    public static int MAX_PAGES_NUM = 50;
    public static int TIME_OUT = 30;
    public static int MAX_QUEUE_SIZE = 20000;
    public static int MAX_WORD_COUNT = 100000;
    public static Queue<Node> queue = new LinkedList<Node>();
    private Node rootNode;
    public static ExecutorService executorService;

    public static Set<String> visited = new HashSet<>();

    public static long start;

    public MinerManager(Node rootNode, int maxMinerCount) {
        this.rootNode = rootNode;
        executorService = Executors.newFixedThreadPool(maxMinerCount);
    }

    public void breadthFirstSearch() {
        Node actualNode = null;

        String content;
        ArrayList<String> urls;
        ArrayList<Node> nodeURLS;
        WebMiner webMiner;

        queue.add(rootNode);

        while (!queue.isEmpty()) {
            if (!checkConditions()) {
                break;
            }
            actualNode = queue.remove();
            webMiner = new WebMiner(actualNode);
            executorService.execute(webMiner);
            visited.add(actualNode.getId());

            nodeURLS = new ArrayList<>();
            content = Utils.getTextFromAddress(actualNode.getId());
            urls = Utils.extractHyperlinks(actualNode.getId(), content);
            for (String url : urls) {
                if (!visited.contains(url))
                    nodeURLS.add(new Node(url));
            }
            actualNode.addChildren(nodeURLS);

            for (Node temp : actualNode.getChildren()) {
                queue.add(temp);
            }
        }
    }

    @Override
    public void run() {
        start = System.currentTimeMillis();
        System.out.println("Started At: " + LocalDateTime.now());
        breadthFirstSearch();

        try {

            if (executorService.awaitTermination(TIME_OUT, TimeUnit.SECONDS)) {
                System.out.println("Terminated");
                showTotalStatistics();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkConditions() {
        boolean word = WebMiner.totalKeywordCount < MAX_WORD_COUNT;
        boolean visit = visited.size() < MAX_PAGES_NUM;
        boolean time = System.currentTimeMillis() - start < TIME_OUT * 1000;

        boolean valid =  word && visit && time;

        if (!valid) {
            if (!executorService.isShutdown()) {
                executorService.shutdown();

                System.out.println("Completed At: " + LocalDateTime.now());
            }
        }
        return valid;
    }

    // * The output should look like:
    //
    // ==========
    // Total number of keywords:91
    // Pages visited:43
    // Pending pages no visited yet: 67
    //
    // University = 25
    // Britain = 473
    // Brexit = 130
    // Holidays = 102
    // Sports = 34
    // =============
    //
    //
    // *The results may vary depending on your
    // *search strategy.
    // *
    // * @throws InterruptedException
    // * @return
    // */
    public static void showTotalStatistics() throws InterruptedException {

        System.out.println("============");

        System.out.println("Total number of keywords: " + WebMiner.totalKeywordCount);
        System.out.println("Pages visited: " + visited.size());
        System.out.println("Pages unvisited: " + queue.size());
        for (String eachKey : WebMiner.map.keySet()) {
            System.out.println(eachKey + " = " + WebMiner.map.get(eachKey));
        }
        System.out.println("============");

    }

}
