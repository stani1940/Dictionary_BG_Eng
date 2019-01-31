import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileReader;
import java.util.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class CreateDictionaryClass extends JFrame {
    JTextField textField;
    JTextArea textArea;
    JButton translateButton;
    JButton clearButton;
    JButton addButton;
    JLabel labelEng;
    JLabel labelBg;
    JLabel labelLeftarrow;
    JLabel labelRightarrow;
    JLabel labelcopirighted;
    JFrame frame;
    JPanel panel;
    Icon icon1;
    Icon icon2;
    Icon icon3;
    Icon icon4;

    public static void main(String[] args) {
        new CreateDictionaryClass();
    }

    public CreateDictionaryClass() {
        drawAndshowGui();
        translator();
        clearArea();
    }

    public void drawAndshowGui() {
        frame = new JFrame("Eng-BG Dictionary Преводач");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.blue);
        textField = new JTextField(25);
        textField.setBounds(20, 20, 600, 100);

        textField.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
        textField.setFont(new Font("Calibri", Font.BOLD, 36));

        translateButton = new JButton("Преведи ");
        translateButton.setFont(new Font("Calibri", Font.BOLD, 24));
        translateButton.setBounds(20, 125, 150, 100);
        translateButton.setBackground(Color.gray);

        clearButton = new JButton("Изчисти ");
        clearButton.setFont(new Font("Calibri", Font.BOLD, 24));
        clearButton.setBounds(250, 125, 150, 100);
        clearButton.setBackground(Color.gray);

        addButton = new JButton("Въведи ");
        addButton.setFont(new Font("Calibri", Font.BOLD, 24));
        addButton.setBounds(470, 125, 150, 100);
        addButton.setBackground(Color.GRAY);

        textArea = new JTextArea();
        textArea.setRows(2);
        textArea.setBounds(20, 230, 600, 100);
        textArea.setFont(new Font("Serif", Font.ITALIC, 36));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        icon1 = new ImageIcon("src/eng_flag.png");
        labelEng = new JLabel(icon1);
        labelEng.setBounds(0, 340, 300, 150);
        icon2 = new ImageIcon("src/flag_bg.png");
        labelBg = new JLabel(icon2);
        labelBg.setBounds(350, 310, 300, 200);
        icon3 = new ImageIcon("src/arrow_left.png");
        labelLeftarrow = new JLabel(icon3);
        labelLeftarrow.setBounds(245, 345, 150, 150);
        icon4 = new ImageIcon("src/arrow_right.png");
        labelRightarrow = new JLabel(icon4);
        labelRightarrow.setBounds(245, 345, 150, 150);
        labelcopirighted = new JLabel("developed by Stanislav Ginev - Java Student");
        labelcopirighted.setBounds(50,500,580,50);
        labelcopirighted.setFont(new Font("Courier", Font.BOLD, 24));

        panel.add(textField);
        panel.add(translateButton);
        panel.add(clearButton);
        panel.add(addButton);
        panel.add(textArea);
        panel.add(labelcopirighted);

        frame.getContentPane().add(panel);
        frame.setSize(650, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void clearArea() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                textArea.setText(null);
                panel.remove(labelBg);
                panel.remove(labelEng);
                panel.remove(labelLeftarrow);
                panel.remove(labelRightarrow);
                panel.revalidate();
                panel.repaint();
            }
        });
    }

    public void translator()  {

        translateButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                String text = textField.getText().trim();

                try {

                    HashMap<String, String> map = generateDict();
                    Set<Map.Entry<String, String>> set = map.entrySet();

                    if (!text.isEmpty()) {
                        char text1 = text.charAt(0);
                        addLanguageoption(isCyrillic(text1));
                        panel.repaint();

                        if (!isCyrillic(text1) && !map.containsKey(text)) {
                            JOptionPane.showMessageDialog(frame, "" + "The word is missing Please click button add");
                            addWords(map);
                        }
                        if (isCyrillic(text1) && !map.containsValue(text)) {
                            JOptionPane.showMessageDialog(frame, "" + "Думата липсва в речника Моля натиснете бутона Въведи");
                            addWords(map);
                        }

                        for (String word : text.split(" ")) {
                            for (Map.Entry<String, String> me : set) {

                                if (word.equalsIgnoreCase(me.getKey())) {
                                    text = me.getValue() + " ";
                                } else if (word.equalsIgnoreCase(me.getValue())) {
                                    text = me.getKey() + " ";
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "" + "Моля въведете дума");
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                textArea.append(text);
                textField.selectAll();
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
    }

    public void addLanguageoption(boolean languageOption) {
        if (languageOption) {
            panel.add(labelBg);
            panel.add(labelLeftarrow);
            panel.add(labelEng);
        } else {
            panel.add(labelEng);
            panel.add(labelRightarrow);
            panel.add(labelBg);
        }

        frame.setVisible(true);
    }

    static boolean isCyrillic(char text1) {

        return Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(text1));
    }

    public void addWords(HashMap<String, String> map) {
        Set<Map.Entry<String, String>> set = map.entrySet();
        addButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                String text = textField.getText().trim();
                String text2 = textArea.getText().trim();
                char text1 = text.charAt(0);
                if (isCyrillic(text1)){
                    map.put(text2, text);
                }else{
                    map.put(text, text2);
                }

                //System.out.println(Arrays.asList(map));
                saveTofile(text1);
            }

            public void saveTofile(char text1){
                File sFile = new File("src/test.txt");

                try {
                    PrintWriter printWriter = new PrintWriter(sFile);
                    for (Map.Entry<String, String> map : set) {

                        printWriter.println(map.getKey() + " " + map.getValue());

                    }
                    printWriter.flush();
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    static HashMap<String, String> generateDict() throws Exception {
        File file = new File("src/test.txt");

        HashMap<String, String> map = new HashMap<String, String>();

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2) {
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
            //reader.close();
        }
        return map;
    }
}


