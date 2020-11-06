import javax.swing.*;

public class Snake extends JFrame {

    private Snake() {
        setTitle("Snake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(500, 200);
        setResizable(false);
        add(new MainGameField());
        pack();
    }

    public static void main(String[] args) {
        Snake snake = new Snake();
        snake.setVisible(true);
    }

}
