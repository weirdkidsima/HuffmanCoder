// класс для узлов дерева Хаффмана
public class Node implements Comparable<Node> {
    char symbol;
    int frequency;
    Node left;
    Node right;
    Node(char symbol, int frequency, Node left, Node right) {
        this.symbol = symbol;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
    public int compareTo(Node other) {
        return Integer.compare(frequency, other.frequency);
    }
}
