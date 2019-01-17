import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.Writer;
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
    JFrame frame;
    JPanel panel;
    Icon icon1;
    Icon icon2;
    Icon icon3;
    Icon icon4;
    public CreateDictionaryClass(){
        drawAndshowGui();

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
        labelLeftarrow.setBounds(240, 345, 150, 150);
        icon4 = new ImageIcon("src/arrow_right.png");
        labelRightarrow = new JLabel(icon4);
        labelRightarrow.setBounds(240, 345, 150, 150);
        panel.add(textField);
        panel.add(translateButton);
        panel.add(clearButton);
        panel.add(addButton);
        panel.add(textArea);

        frame.getContentPane().add(panel);
        frame.setSize(650, 650);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                new CreateDictionaryClass();
            }
        });
    }
}


