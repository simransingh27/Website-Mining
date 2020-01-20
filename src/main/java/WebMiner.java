import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebMiner implements Runnable {
    public Node node;
    static List<String> result;
    public static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();// Shared resource
    public static List<String> keywords = Arrays.asList("university", "sports", "holidays", "britain", "brexit");

    static {
        for (String keyword : keywords) {
            map.put(keyword, 0);
        }
    }

    public static volatile int totalKeywordCount;//shared resource.

    public WebMiner(Node node) {
        this.node = node;
    }

    @Override
    public void run() {

        int temp = 0;

        try {
            mine(node.getId());

            for (Integer eachValue : map.values()) {
                temp = temp + eachValue;
            }
            totalKeywordCount = temp;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method should record:
     *
     * (1) All pages it visited marks a URL as 'visited' by adding it to 'visited'
     * list
     *
     * (2) total keyword counts
     *
     * (3) keyword frequency for every page it visited (case-insensitive)
     *
     * (4) keyword frequency for all pages it visited (case-insensitive)
     *
     * Note: skip URLs that have been visited by other miners
     *
     * @param URL
     * @throws InterruptedException
     */
    public void mine(String URL) throws InterruptedException {

        String content = Utils.getTextFromAddress(node.getId());
        String text = Utils.getPlainText(content);

        Map<String, Integer> statsMapForNode = Utils.calculate(keywords, text);

        for (String key : map.keySet()) {
            map.put(key, map.get(key) + statsMapForNode.get(key));
        }
    }


}
