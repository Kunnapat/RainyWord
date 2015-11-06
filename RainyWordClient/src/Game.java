import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Game extends JFrame {
    static int windowWidth = 1000;
    static int windowHeight = 700;
    JPanel gamePanel, optionPanel, infoPanel;
    JLabel playerNameLabel;
    JFrame popUpFrame;
    LinkedList wordList = new LinkedList();
    LinkedListItr itr;
    String[] color = {"red", "black", "white", "grey", "green", "yellow", "orange", "purple", "pink"};
    String inputWord;
    JTextField inputField;
    JButton startButton;
    boolean gameStarted = false;
    Thread t1;
    int score = 0;

    public Game(String playerName) {
        super("Rainy Word V1.1");
        setPlayerName(playerName);
        createGamePanel();
        createOptionPanel();
        createInfoPanel();
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(optionPanel, BorderLayout.SOUTH);

    }

    public static Game createAndShowGUI(String playerName) {
        Game frame = new Game(playerName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight); // set the size of GUI
        frame.getContentPane().setBackground(new Color(0, 0, 0, 50));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        return frame;
    }

    private void setPlayerName(String playerName) {
        playerNameLabel = new JLabel("Player Name: " + playerName);
    }

    private void createInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setSize(new Dimension(1000, 300));
        infoPanel.setBackground(Color.PINK);
        infoPanel.add(playerNameLabel);

    }

    private void createGamePanel() {
        gamePanel = new GamePanel();
        gamePanel.setSize(new Dimension(1000, 300));
        itr = wordList.zeroth();
        int temp = 0;
        for (int i = 0; i < color.length; i++) {
            wordList.insert(new Word(temp * -200, color[i]), itr);
            temp++;
        }

    }

    private void createOptionPanel() {
        optionPanel = new JPanel();
        optionPanel.setSize(new Dimension(1000, 200));
        optionPanel.setBackground(new Color(31, 48, 121));
        startButton = new JButton("START");


        t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (gameStarted) {
                    repaint();
                    try {
                        Thread.sleep(8);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (wordList.isEmpty()) {
                        createPopUpFrame();
                        gameStarted = false;
                        repaint();
                        popUpFrame.setVisible(true);
                    }
                }

            }

        });
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gameStarted = true;
                startButton.setEnabled(false);
                t1.start();
            }


        });
        optionPanel.add(startButton);
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(500, 20));
        inputField.setBackground(Color.white);
        inputField.setForeground(Color.black);
        inputField.setFont(new Font("Menlo", Font.PLAIN, 12));
        inputField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                inputWord = inputField.getText().toLowerCase();
                inputField.setText("");
                System.out.println(inputWord);
                LinkedListItr itr1 = wordList.first();
                while (!itr1.isPastEnd()) {
                    String temp = itr1.current.element.word.toLowerCase();
                    if (inputWord.equals(temp)) {
                        playSound("./RainyWordClient/src/correct.wav");
                        System.out.println("correct");
                        wordList.remove(itr1);
                        score = score + 1;
                        break;
                    }
                    itr1.advance();
                    if (itr1.isPastEnd()) {
                        playSound("./RainyWordClient/src/wrong.wav");
                    }
                }

            }

        });
        optionPanel.add(inputField);


    }

    public void playSound(String s) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private void createPopUpFrame() {
        popUpFrame = new JFrame();
        popUpFrame.setSize(400, 150);
        popUpFrame.setResizable(false);
        popUpFrame.setLocationRelativeTo(null);
        popUpFrame.setLayout(new GridLayout(3, 1));
        JLabel winLabel = new JLabel("Your score is " + score + "/" + color.length + ".");
        popUpFrame.add(winLabel);
        JButton closeButton = new JButton("OK");
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }

        });
        closeButton.setSize(new Dimension(80, 40));
        popUpFrame.add(closeButton);
        popUpFrame.setVisible(false);

    }

    class GamePanel extends JPanel {
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            g2.drawRect(0, 0, windowWidth, windowHeight);
            g2.setFont(new Font("Menlo", Font.PLAIN, 18));
            g2.setColor(Color.CYAN);
            LinkedListItr itr1 = wordList.first();
            while (!itr1.isPastEnd()) {
                itr1.current.element.paint(g2);
                itr1.advance();
            }
            update();
        }

        public void update() {
            LinkedListItr itr1 = wordList.first();
            while (!itr1.isPastEnd()) {
                itr1.current.element.update();
                if (itr1.current.element.getYLocation() > windowHeight) {
                    wordList.remove(itr1);
                    playSound("src/wrong.wav");
                }
                itr1.advance();
            }
        }
    }


}