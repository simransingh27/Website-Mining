import java.util.ArrayList;
import java.util.List;

public class Node {

    private String id;
    private List<Node> children;

    public Node(String id) {
        this.id = id;
        children = new ArrayList<>();
    }

    public String getId() {

        return id;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChildren(List<Node> children) {
        this.children = children;
    }



}