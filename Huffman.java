import java.io.*;
import java.util.*;

public class Huffman {

    public static void main(String[] args) {
        // открываем файл для чтения
        File inputFile = new File("D:\\test.txt");
        try (BufferedReader reader =  new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "Cp1251"))) {
            // читаем сообщение из файла
            int s;
            String message = "";
            while ((s=reader.read())!=-1){
                message+=(char)s;
            }

            // считаем частоты символов в сообщении
            Map<Character, Integer> frequencies = new HashMap<>();
            for (char c : message.toCharArray()) {
                frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
            }

            // строим дерево Хаффмана
            Node root = buildHuffmanTree(frequencies);

            // создаем таблицу кодирования
            Map<Character, String> encodingTable = buildEncodingTable(root);

            // кодируем сообщение
            EncodingTable encoder = new EncodingTable(encodingTable);
            String encodedMessage = encoder.encode(message);

            // выводим таблицу кодирования
            System.out.println("Таблица кодирования:");
            for (Map.Entry<Character, String> entry : encodingTable.entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue()+" ");
            }

            // выводим закодированное сообщение
            System.out.println("Закодированное сообщение: " + encodedMessage);

            // декодируем сообщение
            String decodedMessage = encoder.decode(encodedMessage, root);

            // выводим декодированное сообщение
            System.out.println("Декодированное сообщение: " + decodedMessage);

        } catch (IOException e) {
            System.out.println("Не удалось открыть файл для чтения.");
        }

    }

    // функция для построения дерева Хаффмана
    private static Node buildHuffmanTree(Map<Character, Integer> frequencies) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            pq.offer(new Node(entry.getKey(), entry.getValue(), null, null));
        }
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.frequency + right.frequency, left, right);
            pq.offer(parent);
        }
        return pq.poll();
    }

    // функция для создания таблицы кодирования
    private static Map<Character, String> buildEncodingTable(Node root) {
        Map<Character, String> encodingTable = new HashMap<>();
        buildEncodingTableHelper(root, "", encodingTable);
        return encodingTable;
    }
    private static void buildEncodingTableHelper(Node node, String path, Map<Character, String> encodingTable) {
        if (node.left == null && node.right == null) {
            encodingTable.put(node.symbol, path);
        } else {
            buildEncodingTableHelper(node.left, path + "0", encodingTable);
            buildEncodingTableHelper(node.right, path + "1", encodingTable);
        }
    }
}