import java.util.*;

// класс для таблицы декодирования
public class EncodingTable {
    private Map<Character, String> table;
    EncodingTable(Map<Character, String> table) {
        this.table = table;
    }
    public String encode(String message) {
        StringBuilder encodedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            encodedMessage.append(table.get(c));
        }
        return encodedMessage.toString();
    }
    public String decode(String encodedMessage, Node root) {
        StringBuilder decodedMessage = new StringBuilder();
        Node current = root;
        for (char c : encodedMessage.toCharArray()) {
            if (c == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
            if (current.left == null && current.right == null) {
                decodedMessage.append(current.symbol);
                current = root;
            }
        }
        return decodedMessage.toString();
    }
}