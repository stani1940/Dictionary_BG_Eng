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
    private JTextField textField;
    private JTextArea textArea;
    private JButton translateButton;
    private JButton translateButtonPhrase;
    private JButton clearButton;
    private JButton addButton;
    private JLabel labelEng;
    private JLabel labelBg;
    private JLabel labelLeftArrow;
    private JLabel labelRightArrow;
    private JLabel labelCodiRight;
    private JFrame frame;
    private JPanel panel;
    private Icon iconEngFlag;
    private Icon iconBgFlag;
    private Icon iconLeftArrow;
    private Icon iconRightArrow;

    public static void main(String[] args) {
        new CreateDictionaryClass();
    }

    private CreateDictionaryClass() {
        drawAndShowGui();
        setUpTranslateButton();
        setUpTranslatePhraseButton();;
        setUpClearButton();
    }

    private void drawAndShowGui() {
        frame = new JFrame("Eng-BG Dictionary Преводач");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.blue);
        textField = new JTextField(25);
        textField.setBounds(20, 20, 602, 100);

        textField.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
        textField.setFont(new Font("Calibri", Font.BOLD, 36));

        translateButton = new JButton("<html>" + "Преведи" + "<br>" + " по дума" + "</html>");
        translateButton.setFont(new Font("Calibri", Font.BOLD, 24));
        translateButton.setBounds(20, 125, 150, 100);
        translateButton.setBackground(Color.gray);

        translateButtonPhrase = new JButton("<html>" + "Преведи" + "<br>" + " по фраза" + "</html>");
        translateButtonPhrase.setFont(new Font("Calibri", Font.BOLD, 24));
        translateButtonPhrase.setBounds(171, 125, 150, 100);
        translateButtonPhrase.setBackground(Color.gray);

        clearButton = new JButton("Изчисти ");
        clearButton.setFont(new Font("Calibri", Font.BOLD, 24));
        clearButton.setBounds(322, 125, 148, 100);
        clearButton.setBackground(Color.gray);

        addButton = new JButton("Въведи ");
        addButton.setFont(new Font("Calibri", Font.BOLD, 24));
        addButton.setBounds(472, 125, 150, 100);
        addButton.setBackground(Color.GRAY);

        textArea = new JTextArea();
        textArea.setRows(2);
        textArea.setBounds(20, 230, 602, 100);
        textArea.setFont(new Font("Serif", Font.ITALIC, 36));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        iconEngFlag = new ImageIcon("src/eng_flag.png");
        labelEng = new JLabel(iconEngFlag);
        labelEng.setBounds(0, 340, 300, 150);
        iconBgFlag = new ImageIcon("src/flag_bg.png");
        labelBg = new JLabel(iconBgFlag);
        labelBg.setBounds(350, 310, 300, 200);
        iconLeftArrow = new ImageIcon("src/arrow_left.png");
        labelLeftArrow = new JLabel(iconLeftArrow);
        labelLeftArrow.setBounds(245, 345, 150, 150);
        iconRightArrow = new ImageIcon("src/arrow_right.png");
        labelRightArrow = new JLabel(iconRightArrow);
        labelRightArrow.setBounds(245, 345, 150, 150);
        labelCodiRight = new JLabel("developed by Stanislav Ginev - Java Student");
        labelCodiRight.setBounds(50, 500, 580, 50);
        labelCodiRight.setFont(new Font("Courier", Font.BOLD, 24));

        panel.add(textField);
        panel.add(translateButton);
        panel.add(translateButtonPhrase);
        panel.add(clearButton);
        panel.add(addButton);
        panel.add(textArea);
        panel.add(labelCodiRight);

        frame.getContentPane().add(panel);
        frame.setSize(650, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setUpTranslateButton() {

        translateButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
               translateWord();
            }
        });
    }
    private void translateWord(){
        String text = textField.getText().trim();

        try {

            HashMap<String, String> map = generateDict();
            Set<Map.Entry<String, String>> set = map.entrySet();

            if (!text.isEmpty()) {
                char firstSymbol = text.charAt(0);
                addLanguageOption(isCyrillic(firstSymbol));
                panel.repaint();

                if (!isCyrillic(firstSymbol) && !map.containsKey(text)) {
                    checkNumberWords(text);
                    JOptionPane.showMessageDialog(frame, "" + "The word is missing Please click button add");
                    addWords(map);
                }
                if (isCyrillic(firstSymbol) && !map.containsValue(text)) {
                    checkNumberWords(text);
                    JOptionPane.showMessageDialog(frame, "" + "Думата липсва в речника Моля натиснете бутона Въведи");
                    addWords(map);
                }

                for (String word : text.split(" ")) {
                    for (Map.Entry<String, String> me : set) {

                        if (word.equalsIgnoreCase(me.getKey())) {
                            text = me.getValue();
                        } else if (word.equalsIgnoreCase(me.getValue())) {
                            text = me.getKey();
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
    private void checkNumberWords(String text){
        String arr[] = text.split(" ");
        if (arr.length > 1) {
            JOptionPane.showMessageDialog(frame, "Грешка" + "Думата трябва да е само една");
        }

    }

    private void setUpTranslatePhraseButton() {
        translateButtonPhrase.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                translatePhrase();
            }
        });
    }
    private void translatePhrase(){
        String text = textField.getText().trim();
        char firstSymbol = text.charAt(0);
        try {
            addLanguageOption(isCyrillic(firstSymbol));
            panel.repaint();
            if (isCyrillic(firstSymbol)) {
                text = translateBgToEng(text);
            } else {
                text = translateEnglishToBg(text);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        textArea.append(text);
        textField.selectAll();
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private void setUpClearButton() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                textArea.setText(null);
                panel.remove(labelBg);
                panel.remove(labelEng);
                panel.remove(labelLeftArrow);
                panel.remove(labelRightArrow);
                panel.revalidate();
                panel.repaint();
            }
        });
    }

    private String translateEnglishToBg(String text) throws Exception {

        String str = "";
        StringBuilder sb = new StringBuilder();
        for (String word : text.split(" ")) {

            HashMap<String, String> map = generateDict();
            Set<Map.Entry<String, String>> set = map.entrySet();

            for (Map.Entry<String, String> entry : set) {
                if (word.equalsIgnoreCase(entry.getKey())) {
                    sb.append(entry.getValue());
                    sb.append(" ");
                    str = sb.toString();
                    break;
                }
            }
        }
        text = str;
        return text;
    }

    private String translateBgToEng(String text) throws Exception {

        String str = "";
        StringBuilder sb = new StringBuilder();
        for (String word : text.split(" ")) {

            HashMap<String, String> map = generateDict();
            Set<Map.Entry<String, String>> set = map.entrySet();

            for (Map.Entry<String, String> entry : set) {
                if (word.equalsIgnoreCase(entry.getValue())) {
                    sb.append(entry.getKey());
                    sb.append(" ");
                    str = sb.toString();
                    break;
                }
            }
        }
        text = str;
        return text;
    }

    private void addLanguageOption(boolean languageOption) {
        if (languageOption) {
            panel.add(labelBg);
            panel.add(labelLeftArrow);
            panel.add(labelEng);
        } else {
            panel.add(labelEng);
            panel.add(labelRightArrow);
            panel.add(labelBg);
        }

        frame.setVisible(true);
    }

    private boolean isCyrillic(char firstSymbol) {

        return Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(firstSymbol));
    }


    private void addWords(HashMap<String, String> map) {
        Set<Map.Entry<String, String>> set = map.entrySet();
        addButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                String text = textField.getText().trim();
                String textFromArea = textArea.getText().trim();
                char firstSymbol = text.charAt(0);
                if (isCyrillic(firstSymbol)) {
                    map.put(textFromArea, text);
                } else {
                    map.put(text, textFromArea);
                }
                saveToFile();
            }

            void saveToFile() {
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

    private static HashMap<String, String> generateDict() throws Exception {
        File file = new File("src/test.txt");
        HashMap<String, String> map = new HashMap<>();
        String line;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length >= 2) {
                    String key = parts[0];
                    String value = parts[1];
                    map.put(key, value);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }
}


