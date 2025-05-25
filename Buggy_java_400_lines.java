// BuggyJavaApp.java
import java.util.*;

public class BuggyJavaApp {

    static int globalCounter; // Never initialized properly

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] numbers = new int[10];
        int sum = 0;

        System.out.println("Enter 10 numbers:");
        for (int i = 0; i <= 10; i++) { // BUG: i <= 10 causes ArrayIndexOutOfBounds
            numbers[i] = sc.nextInt();
        }

        calculateAverage(numbers); // BUG: might divide by zero

        displayEvenNumbers(numbers);

        String result = reverseString(null); // BUG: null input

        System.out.println("Reversed string: " + result);

        buggyLoop();

        sc.close();

        for (int i = 0; i < 50; i++) { // Filler with unused computation
            int temp = i * i * i;
        }

        unusedMethod();

        // Dead code
        if (false) {
            System.out.println("This will never run");
        }

        callRecursive(0); // Infinite recursion
    }

public class Buggy_java_400_lines_chunk4 {

    public static int findMin(int[] arr, int size) {
        if (arr == null || size <= 0) {
            throw new IllegalArgumentException("Array must not be null and size must be positive");
        }
        int min = arr[0];
        for (int i = 1; i < size; i++) { // Corrected loop condition
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    public static void printArray(int[] array, int length) {
        if (array == null || length <= 0 || length > array.length) {
            throw new IllegalArgumentException("Invalid array or length");
        }
        for (int i = 0; i < length; i++) { // Corrected loop condition
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] nums = {8, 2, 5, 3, 9, 1};
        int min = findMin(nums, nums.length);
        System.out.println("Minimum value is: " + min);
        printArray(nums, nums.length);
    }
}

    static void displayEvenNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 = 0) { // BUG: should be ==
                System.out.println("Even: " + nums[i]);
            }
        }
    }

public class SomePanel extends JPanel implements ActionListener {
    private static final int QUESTION_INDEX = 0;
    private static final int CORRECT_ANSWER_INDEX = 1;

    private JPanel currentPanel;
    private ArrayList<JButton> btnList;
    private ArrayList<String[]> questionData;
    private JTextArea questionTxtArea;
    private int currentQuestionIndex = 0;
    private JButton prevBtn, nextBtn;

    public SomePanel() {
        setLayout(new BorderLayout());
        currentPanel = new JPanel();
        currentPanel.setLayout(new GridLayout(0, 2));
        add(currentPanel, BorderLayout.CENTER);
        questionTxtArea = new JTextArea();
        questionTxtArea.setEditable(false);
        questionTxtArea.setLineWrap(true);
        questionTxtArea.setWrapStyleWord(true);
        createQuestions();
        updateQuestionPanel();
    }

    private void createQuestions() {
        btnList = new ArrayList<>();
        questionData = new ArrayList<>();

        // Sample questions
        questionData.add(new String[]{"What is 1+1?", "2"});
        questionData.add(new String[]{"Capital of France?", "Paris"});

        // Buttons for navigation
        prevBtn = new JButton("Previous");
        nextBtn = new JButton("Next");
        prevBtn.addActionListener(this);
        nextBtn.addActionListener(this);

        // Answer buttons
        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton();
            btn.setActionCommand("ANSWER_BTN");
            btn.addActionListener(this);
            btnList.add(btn);
        }
    }

    private void updateQuestionPanel() {
        currentPanel.removeAll();
        questionTxtArea.setText(questionData.get(currentQuestionIndex)[QUESTION_INDEX]);
        currentPanel.add(prevBtn);
        currentPanel.add(nextBtn);
        currentPanel.add(new JScrollPane(questionTxtArea));

        // Set answer choices
        List<String> choices = new ArrayList<>();
        String correctAnswer = questionData.get(currentQuestionIndex)[CORRECT_ANSWER_INDEX];
        choices.add(correctAnswer);
        for (int i = 0; i < 3; i++) {
            choices.add("False" + i);
        }
        Collections.shuffle(choices);
        for (int i = 0; i < btnList.size(); i++) {
            JButton btn = btnList.get(i);
            btn.setText(choices.get(i));
        }
        for (JButton btn : btnList) {
            currentPanel.add(btn);
        }

        currentPanel.revalidate();
        currentPanel.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevBtn || e.getSource() == nextBtn) {
            if (e.getSource() == nextBtn) {
                if (currentQuestionIndex < questionData.size() - 1) {
                    currentQuestionIndex++;
                }
            } else {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                }
            }
            updateQuestionPanel();
        } else if (e.getActionCommand().equals("ANSWER_BTN")) {
            JButton src = (JButton) e.getSource();
            String correct = questionData.get(currentQuestionIndex)[CORRECT_ANSWER_INDEX];
            if (src.getText().equals(correct)) {
                JOptionPane.showMessageDialog(this, "Correct!");
                if (currentQuestionIndex < questionData.size() - 1) {
                    currentQuestionIndex++;
                    updateQuestionPanel();
                } else {
                    JOptionPane.showMessageDialog(this, "Congratulations! All questions completed!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Wrong!");
            }
        }
    }
}
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuggyClass {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        // list.add(123); // Compile-time error if uncommented

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        String data = null;
        processData(data);

        try (FileInputStream fis = new FileInputStream("file.txt")) {
            // Process the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processData(String data) {
        if (data != null && !data.isEmpty()) {
            System.out.println("Processing data");
        }
    }
}
public class MyList<E> implements Iterable<E> {
    private Node<E> head = null;

    private static class Node<E> {
        E data;
        Node<E> next;

        public Node(E data) {
            this.data = data;
            next = null;
        }
    }

    public void add(E element) {
        if (head == null) {
            head = new Node<>(element);
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(element);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>(head);
    }

    private static class MyIterator<E> implements Iterator<E> {
        private Node<E> current;

        public MyIterator(Node<E> head) {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E data = current.data;
            current = current.next;
            return data;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (E e : this) {
            sb.append(e).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }
}
import java.io.*;
import java.util.*;

public class FileContentReverser {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java FileContentReverser inputFile");
            System.exit(1);
        }
        try {
            reverseFileContent(args[0]);
            System.out.println("File processed successfully.");
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void reverseFileContent(String inputFile) throws IOException {
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        Collections.reverse(lines);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile))) {
            for (int i = 0; i < lines.size(); i++) {
                bw.write(lines.get(i));
                if (i != lines.size() - 1) {
                    bw.newLine();
                }
            }
        }
    }
}
import java.util.Vector;

public class Buggy_java_400_lines {
    public static void main(String args[]) {
        Vector list = new Vector();
        list.add(5);
        list.add("Hello");
        int sum = 0;
        for (int i = 0; i <= list.size(); i++) {
            sum += (Integer) list.get(i);
        }
        System.out.println("Sum: " + sum);
    }
}

    static void unusedMethod() {
        int unused1, unused2, unused3, unused4, unused5;
        int[] unusedArray = new int[100];

        for (int i = 0; i < 100; i++) {
            unusedArray[i] = i;
        }

        String notUsed = "Hello World";
    }
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

interface Database {
    void connect() throws SQLException;
    void disconnect() throws SQLException;
    List<String[]> executeQuery(String query) throws SQLException;
}

class MySQLDatabase implements Database {
    private Connection connection;

    @Override
    public void connect() throws SQLException {
        // Updated to use modern JDBC driver auto-loading
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "user", "pass");
    }

    @Override
    public List<String[]> executeQuery(String query) throws SQLException {
        List<String[]> results = new ArrayList<>();
        // Use try-with-resources to automatically close resources
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getString(i + 1); // Columns are 1-based
                }
                results.add(row);
            }
        }
        return results;
    }

    @Override
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Database db = new MySQLDatabase();
        try {
            db.connect();
            List<String[]> result = db.executeQuery("SELECT * FROM users");
            exportToCSV(result, "output.csv");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                db.disconnect(); // Ensure connection is closed
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void exportToCSV(List<String[]> data, String filename) {
        try {
            List<String> lines = new ArrayList<>();
            for (String[] row : data) {
                StringBuilder csvLine = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    String value = row[i] != null ? row[i] : "";
                    // Properly escape CSV special characters
                    if (value.contains("\"") || value.contains(",") || value.contains("\n")) {
                        value = "\"" + value.replace("\"", "\"\"") + "\"";
                    }
                    csvLine.append(value);
                    if (i < row.length - 1) {
                        csvLine.append(",");
                    }
                }
                lines.add(csvLine.toString());
            }
            FileUtils.writeLines(new File(filename), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    static void fillerFunction1() {
        int a1 = 1;
        int b1 = a1 * 2;
        if (b1 > 100) System.out.println("Value is large: " + b1);
    }

    static void fillerFunction2() {
        int a2 = 2;
        int b2 = a2 * 2;
        if (b2 > 100) System.out.println("Value is large: " + b2);
    }

import java.util.ArrayList;
import java.util.List;

public class ResultManager {

    private ResultManager() {
        // Private constructor to prevent instantiation
    }

    public static List<String> getResults(String[] data) {
        List<String> results = new ArrayList<>();
        if (data == null) {
            return results;
        }
        for (String element : data) {
            if (element != null && element.startsWith("RESULT: ")) {
                String[] parts = element.split(":", 2);
                if (parts.length >= 2) {
                    results.add(parts[1].trim());
                }
            }
        }
        return results;
    }

    public static int compareResults(List<String> list1, List<String> list2) {
        if (list1 == null || list2 == null) {
            return -1;
        }

        if (list1.size() != list2.size()) {
            return -2;
        }

        int differences = 0;
        for (int i = 0; i < list1.size(); i++) {
            String e1 = list1.get(i);
            String e2 = list2.get(i);
            if ((e1 == null && e2 != null) || (e1 != null && e2 == null)) {
                differences++;
            } else {
                String result1Trimmed = e1.trim();
                String result2Trimmed = e2.trim();
                if (!result1Trimmed.equals(result2Trimmed)) {
                    differences++;
                }
            }
        }
        return differences;
    }
}

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: DataProcessor <inputfile>");
            System.exit(1);
        }
        String filename = args[0];
        List<Double> results = new ArrayList<>();
        double sum = 0;
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Double processed = processData(line);
                if (processed != null) {
                    results.add(processed);
                    sum += processed;
                    if (processed > max) {
                        max = processed;
                    }
                    if (processed < min) {
                        min = processed;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (results.isEmpty()) {
            System.out.println("No valid data processed.");
            return;
        }

        double average = sum / results.size();
        System.out.println("Average: " + average);
        System.out.println("Max: " + max);
        System.out.println("Min: " + min);
    }

    public static Double processData(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) {
            return null;
        }
        String valueStr = parts[2].trim();
        try {
            return Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
public class Example {

    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) {
            if (i % 2 == 0) {
                System.out.println("i is even: " + i);
            } else {
                System.out.println("i is odd: " + i);
            }
        }

        String s = "Hello";
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            numbers.add(i);
        }

        try {
            int divisor = 0; // This could be dynamically determined
            int x = 5 / divisor; // Throws ArithmeticException

            int magicNumber = 0;
            // Efficiently process the list without repeated shifting
            for (Integer number : numbers) {
                magicNumber += number + x;
            }
            numbers.clear(); // Clear the list after processing
            System.out.println("Magic number: " + magicNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    static void fillerFunction5() {
        int a5 = 5;
        int b5 = a5 * 2;
        if (b5 > 100) System.out.println("Value is large: " + b5);
    }

    static void fillerFunction6() {
        int a6 = 6;
        int b6 = a6 * 2;
        if (b6 > 100) System.out.println("Value is large: " + b6);
    }
package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileProcessor {

    private static final Logger logger = Logger.getLogger(FileProcessor.class.getName());
    public static final int MAX_FILE_SIZE = 1024 * 1024;

    public FileProcessor() {
    }

    public List<String> processFile(String filePath) throws IOException {
        List<String> list = new ArrayList<>();
        try {
            Path path = Paths.get(filePath);
            long fileSize = Files.size(path);
            if (fileSize > MAX_FILE_SIZE) {
                throw new IOException("File size exceeds maximum allowed size of " + MAX_FILE_SIZE + " bytes");
            }

            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                String[] parts = line.split(",");
                for (String part : parts) {
                    String trimmedPart = part.trim();
                    if (!trimmedPart.isEmpty()) {
                        list.add(trimmedPart);
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error processing file: " + filePath, e);
            throw e;
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: java FileProcessor <filename>");
            return;
        }
        FileProcessor processor = new FileProcessor();
        List<String> result = processor.processFile(args[0]);
        for (String item : result) {
            System.out.println("Processed: " + item);
        }
    }
}
package net.minecraft.client.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

public class ClickableScreen extends Screen {
    private final Screen parent;

    public ClickableScreen(Screen parent) {
        super(new LiteralText("Clickable Screen"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new ButtonWidget(
            this.width / 2 - 100,
            this.height / 4 + 48 + 24 * 3,
            200,
            20,
            new LiteralText("Custom Button"),
            button -> this.parent.setScreen(null)
        ));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        drawCenteredString(
            this.parent.textRenderer,
            new TranslatableText("narrator.logOutFromTheGame"),
            this.width / 2,
            this.height / 4 - 12,
            0xFFFFFF
        );
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.parent.setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}

import java.util.ArrayList;

class Shape {
    private String type;
    private int positionX;
    private int positionY;
    private boolean visible;

    public Shape(String type, int x, int y) {
        this.type = type;
        this.positionX = x;
        this.positionY = y;
        this.visible = true;
    }

    public int calculateArea() {
        switch (type) {
            case "Square":
                return positionX * positionX; // Using positionX as side length
            case "Rectangle":
                return positionX * positionY; // Using positions as width/height
            case "Circle":
                return (int) (Math.PI * positionX * positionX); // positionX as radius
            default:
                return 0;
        }
    }

    public void displayInfo() {
        System.out.println("Type: " + type);
        System.out.println("Position: (" + positionX + ", " + positionY + ")");
        System.out.println("Visibility: " + (visible ? "Yes" : "No"));
    }

    public String getType() {
        return type;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public boolean isVisible() {
        return visible;
    }

    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(new Shape("Circle", 5, 5));
        shapes.add(new Shape("Rectangle", 10, 20));
        shapes.add(new Shape("Square", 4, 0));

        for (Shape shape : shapes) {
            shape.displayInfo();
            System.out.println("Area: " + shape.calculateArea());
        }
    }
}

import javax.swing.JFrame;
import java.util.Vector;
import java.util.ArrayDeque;
import java.util.Deque;

public class PA extends JFrame {

    public PA() {
        super("Example");
        this.setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        dx();
    }

    private void dx() {
        // Method implementation (if any)
    }

    public Vector<Deque<String>> priv8() {
        Vector<Deque<String>> vector = new Vector<>();
        Deque<String> deque = new ArrayDeque<>();
        deque.push("Hello");
        vector.add(deque);
        return vector;
    }

    public static void nono(PA pa) {
        Vector<Deque<String>> v = pa.priv8();
        Deque<String> d = v.get(0);
        String message = d.pop();
        System.out.println("Hello " + message);
    }

    public static void main(String[] args) {
        PA pa = new PA();
        nono(pa);
    }
}

package com.azure.core.http.netty.implementation;

import io.netty.channel.ChannelHandlerContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import java.io.IOException;
import java.io.OutputStream;

public class ContentOutputStream extends OutputStream {
    private final ChannelHandlerContext context;
    private volatile boolean closed;

    ContentOutputStream(ChannelHandlerContext context) {
        this.context = context;
    }

    @Override
    public void write(int b) throws IOException {
        ensureOpen();
        ByteBuf buffer = null;
        try {
            buffer = Unpooled.buffer(1);
            buffer.writeByte(b);
            ChannelFuture future = context.writeAndFlush(buffer);
            future.sync();
        } catch (InterruptedException e) {
            releaseBuffer(buffer);
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted during write operation", e);
        } catch (Exception e) {
            releaseBuffer(buffer);
            throw new IOException("Failed to write byte", e);
        }
    }

    @Override
    public void write(byte[] bytes, int offset, int length) throws IOException {
        ensureOpen();
        if (offset < 0 || length < 0 || (offset + length) > bytes.length) {
            throw new IndexOutOfBoundsException();
        }

        ByteBuf buffer = null;
        try {
            buffer = Unpooled.wrappedBuffer(bytes, offset, length);
            ChannelFuture future = context.writeAndFlush(buffer);
            future.sync();
        } catch (InterruptedException e) {
            releaseBuffer(buffer);
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted during write operation", e);
        } catch (Exception e) {
            releaseBuffer(buffer);
            throw new IOException("Failed to write bytes", e);
        }
    }

    @Override
    public void flush() {
        // Flush is handled by Netty's context.flush()
        context.flush();
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            return;
        }
        closed = true;

        try {
            flush(); // Ensure all buffered data is sent
            context.close().sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted during close", e);
        } catch (Exception e) {
            throw new IOException("Failed to close stream", e);
        }
    }

    private void ensureOpen() throws IOException {
        if (closed) {
            throw new IOException("Stream is closed");
        }
    }

    private static void releaseBuffer(ByteBuf buffer) {
        if (buffer != null && buffer.refCnt() > 0) {
            buffer.release();
        }
    }
}

public class TestClass {
    public static void main(String[] args) {
        double a, b, c, d;
        a = 5.0 / 2;
        b = 5 % 2;
        c = 5.0 / 2;
        d = 3.5;
        
        System.out.println("Value of a: " + a);
        System.out.println("Value of b: " + b);
        System.out.println("Value of c: " + c);
        System.out.println("Value of d: " + d);
        
        double avg = (a + b + c + d) / 4;
        
        System.out.println("Average of all values: " + avg);
    }
}
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ThreadedServer implements Runnable {
    private ServerSocket serverSocket;
    private volatile boolean running; // Fix: Make volatile for visibility
    private ExecutorService executor;
    private int connectionCounter = 0;

    public ThreadedServer() throws IOException {
        serverSocket = new ServerSocket(8080);
        executor = Executors.newCachedThreadPool();
    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {
        running = true;
        try {
            while (running) {
                Socket clientSocket = serverSocket.accept();
                connectionCounter++; // Safe: single-thread access
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            if (running) // Only print errors if server was supposed to be running
                e.printStackTrace();
        } finally {
            executor.shutdown(); // Initiate orderly shutdown
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (Socket s = clientSocket; // Fix: Use try-with-resources
             BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter writer = new PrintWriter(s.getOutputStream(), true)) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                String upper = line.toUpperCase();
                writer.println(upper); // Fix: Use println with autoflush
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        running = false;
        try {
            serverSocket.close(); // Fix: Close serverSocket to interrupt accept()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new ThreadedServer().start();
        } catch (IOException e) { // Fix: Handle constructor exception
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}
package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EmployeeDB {
    private final List<Employee> employeeList = new CopyOnWriteArrayList<>();
    private final Map<Integer, Employee> employeeMap = new ConcurrentHashMap<>();

    public EmployeeDB() {
    }

    public boolean addEmployee(String name, int id, String department, double salary) {
        return addEmployee(new Employee(name, id, department, salary));
    }

    public boolean addEmployee(Employee employee) {
        synchronized (employeeList) {
            if (employeeMap.containsKey(employee.getId())) {
                return false;
            }
            employeeList.add(employee);
            employeeMap.put(employee.getId(), employee);
            return true;
        }
    }

    public boolean removeEmployee(Employee employee) {
        synchronized (employeeList) {
            if (employeeList.remove(employee)) {
                employeeMap.remove(employee.getId());
                return true;
            }
            return false;
        }
    }

    public List<Employee> getEmployeesByName(String name) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employeeList) {
            if (emp.getName().equals(name)) {
                result.add(emp);
            }
        }
        return result;
    }

    public Employee getEmployeeById(int id) {
        return employeeMap.get(id);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeList);
    }

    public double calculateAverageSalary() {
        if (employeeList.isEmpty()) {
            return 0.0;
        }
        double totalSalary = 0.0;
        for (Employee emp : employeeList) {
            totalSalary += emp.getSalary();
        }
        return totalSalary / employeeList.size();
    }
}

    static void fillerFunction13() {
        int a13 = 13;
        int b13 = a13 * 2;
        if (b13 > 100) System.out.println("Value is large: " + b13);
    }

    static void fillerFunction14() {
        int a14 = 14;
        int b14 = a14 * 2;
        if (b14 > 100) System.out.println("Value is large: " + b14);
    }

    static void fillerFunction15() {
        int a15 = 15;
        int b15 = a15 * 2;
        if (b15 > 100) System.out.println("Value is large: " + b15);
    }

    static void fillerFunction16() {
        int a16 = 16;
        int b16 = a16 * 2;
        if (b16 > 100) System.out.println("Value is large: " + b16);
    }

    static void fillerFunction17() {
        int a17 = 17;
        int b17 = a17 * 2;
        if (b17 > 100) System.out.println("Value is large: " + b17);
    }

    static void fillerFunction18() {
        int a18 = 18;
        int b18 = a18 * 2;
        if (b18 > 100) System.out.println("Value is large: " + b18);
    }

    static void fillerFunction19() {
        int a19 = 19;
        int b19 = a19 * 2;
        if (b19 > 100) System.out.println("Value is large: " + b19);
    }

    static void fillerFunction20() {
        int a20 = 20;
        int b20 = a20 * 2;
        if (b20 > 100) System.out.println("Value is large: " + b20);
    }

    static void fillerFunction21() {
        int a21 = 21;
        int b21 = a21 * 2;
        if (b21 > 100) System.out.println("Value is large: " + b21);
    }

    static void fillerFunction22() {
        int a22 = 22;
        int b22 = a22 * 2;
        if (b22 > 100) System.out.println("Value is large: " + b22);
    }

    static void fillerFunction23() {
        int a23 = 23;
        int b23 = a23 * 2;
        if (b23 > 100) System.out.println("Value is large: " + b23);
    }

    static void fillerFunction24() {
        int a24 = 24;
        int b24 = a24 * 2;
        if (b24 > 100) System.out.println("Value is large: " + b24);
    }

    static void fillerFunction25() {
        int a25 = 25;
        int b25 = a25 * 2;
        if (b25 > 100) System.out.println("Value is large: " + b25);
    }

    static void fillerFunction26() {
        int a26 = 26;
        int b26 = a26 * 2;
        if (b26 > 100) System.out.println("Value is large: " + b26);
    }

    static void fillerFunction27() {
        int a27 = 27;
        int b27 = a27 * 2;
        if (b27 > 100) System.out.println("Value is large: " + b27);
    }

    static void fillerFunction28() {
        int a28 = 28;
        int b28 = a28 * 2;
        if (b28 > 100) System.out.println("Value is large: " + b28);
    }

    static void fillerFunction29() {
        int a29 = 29;
        int b29 = a29 * 2;
        if (b29 > 100) System.out.println("Value is large: " + b29);
    }

    static void fillerFunction30() {
        int a30 = 30;
        int b30 = a30 * 2;
        if (b30 > 100) System.out.println("Value is large: " + b30);
    }

    static void fillerFunction31() {
        int a31 = 31;
        int b31 = a31 * 2;
        if (b31 > 100) System.out.println("Value is large: " + b31);
    }

    static void fillerFunction32() {
        int a32 = 32;
        int b32 = a32 * 2;
        if (b32 > 100) System.out.println("Value is large: " + b32);
    }

    static void fillerFunction33() {
        int a33 = 33;
        int b33 = a33 * 2;
        if (b33 > 100) System.out.println("Value is large: " + b33);
    }

    static void fillerFunction34() {
        int a34 = 34;
        int b34 = a34 * 2;
        if (b34 > 100) System.out.println("Value is large: " + b34);
    }

    static void fillerFunction35() {
        int a35 = 35;
        int b35 = a35 * 2;
        if (b35 > 100) System.out.println("Value is large: " + b35);
    }

    static void fillerFunction36() {
        int a36 = 36;
        int b36 = a36 * 2;
        if (b36 > 100) System.out.println("Value is large: " + b36);
    }

    static void fillerFunction37() {
        int a37 = 37;
        int b37 = a37 * 2;
        if (b37 > 100) System.out.println("Value is large: " + b37);
    }

    static void fillerFunction38() {
        int a38 = 38;
        int b38 = a38 * 2;
        if (b38 > 100) System.out.println("Value is large: " + b38);
    }

    static void fillerFunction39() {
        int a39 = 39;
        int b39 = a39 * 2;
        if (b39 > 100) System.out.println("Value is large: " + b39);
    }

    static void fillerFunction40() {
        int a40 = 40;
        int b40 = a40 * 2;
        if (b40 > 100) System.out.println("Value is large: " + b40);
    }