import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Random;

public class MainGameField  extends JPanel implements ActionListener {

    private static final int DELAYMS = 200;
    private final int IMAGE_SIZE = 16;
    private final int RANGE = 480;

    private int[] snakeX;
    private int[] snakeY;
    private int numberOfSnakeElements;
    private int appleX;
    private int appleY;

    private Image apple;
    private Image snake;

    private boolean inGame = true;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    public MainGameField() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(RANGE, RANGE));
        setFocusable(true);
        initGame();
    }

    private void initGame() {
        setImages();
        createApple();
        createSnake();
        Timer timer = new Timer(DELAYMS, this);
        timer.start();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT && !right) {
                    left = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_RIGHT && !left) {
                    right = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_UP && !down) {
                    up = true;
                    left = false;
                    right = false;
                }
                if (key == KeyEvent.VK_DOWN && !up) {
                    down = true;
                    left = false;
                    right = false;
                }
            }
        });
    }

    private void createSnake() {
        snakeX = new int[RANGE];
        snakeY = new int[RANGE];
        numberOfSnakeElements = 3;
        for (int i = 0; i < numberOfSnakeElements; i++) {
            snakeX[i] = 48 - i * IMAGE_SIZE;
            snakeY[i] = 48;
        }
    }

    private void createApple() {
        appleX = new Random().nextInt(16) * IMAGE_SIZE;
        appleY = new Random().nextInt(16) * IMAGE_SIZE;
    }


    private void setImages() {
        ImageIcon appleIcon = new ImageIcon(Objects.requireNonNull(getClass().
                getClassLoader().getResource("apple.png")));
        ImageIcon snakeIcon = new ImageIcon(Objects.requireNonNull(getClass().
                getClassLoader().getResource("dot.png")));
        apple = appleIcon.getImage();
        snake = snakeIcon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < numberOfSnakeElements; i++) {
                g.drawImage(snake, snakeX[i], snakeY[i], this);
            }
        } else {
            String textForEnd = "Game over";
            g.setFont(new Font("Arial", Font.BOLD, 22));
            g.setColor(Color.RED);
            g.drawString(textForEnd, RANGE / 3 + 30, RANGE / 2);
        }
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            moveSnake();
            checkCollisions();
            checkApples();
        }

    }

    private void checkApples() {
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            numberOfSnakeElements++;
            createApple();
        }

    }

    private void checkCollisions() {
        for (int i = numberOfSnakeElements; i > 0; i--) {
            if (numberOfSnakeElements > 4 && snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i])
                inGame = false;
        }
        if (snakeX[0] < 0 || snakeX[0] > RANGE || snakeY[0] < 0 || snakeY[0] > RANGE)
            inGame = false;
    }

    private void moveSnake() {
        for (int i = numberOfSnakeElements; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        if (left) snakeX[0] -= IMAGE_SIZE;
        if (right) snakeX[0] += IMAGE_SIZE;
        if (up) snakeY[0] -= IMAGE_SIZE;
        if (down) snakeY[0] += IMAGE_SIZE;
    }
}
