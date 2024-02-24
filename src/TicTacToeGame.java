import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame extends JFrame implements ActionListener {
    private final int BOARD_SIZE = 3;
    private final JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
    private boolean isPlayerX = true;
    private int movesLeft = BOARD_SIZE * BOARD_SIZE;
    private int scorePlayerX = 0;
    private int scorePlayerO = 0;
    private JLabel labelScoreX;
    private JLabel labelScoreO;

    public TicTacToeGame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500); 
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK); 

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTop.setBackground(Color.BLACK);
        labelScoreX = new JLabel("Player X Score: " + scorePlayerX);
        labelScoreX.setForeground(Color.WHITE); 
        labelScoreO = new JLabel("Player O Score: " + scorePlayerO);
        labelScoreO.setForeground(Color.WHITE); 
        panelTop.add(labelScoreX);
        panelTop.add(labelScoreO);
        add(panelTop, BorderLayout.NORTH);

        JPanel panelBoard = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        panelBoard.setBackground(Color.BLACK);
        initializeButtons(panelBoard);
        add(panelBoard, BorderLayout.CENTER);

        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeButtons(JPanel panelBoard) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                buttons[row][col].setForeground(Color.WHITE); 
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].addActionListener(this);
                panelBoard.add(buttons[row][col]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (!clickedButton.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid Move! Cell already occupied.");
            return;
        }
        if (isPlayerX) {
            clickedButton.setText("X");
            clickedButton.setForeground(Color.BLUE); 
        } else {
            clickedButton.setText("O");
            clickedButton.setForeground(Color.RED); 
        }
        movesLeft--;

        if (checkWin() || movesLeft == 0) {
            endGame();
        } else {
            isPlayerX = !isPlayerX;
        }
    }

    private boolean checkWin() {
        String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = buttons[i][j].getText();
            }
        }
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2])) {
                return true;
            }
        }
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i])) {
                return true;
            }
        }
       
        if (checkRowCol(board[0][0], board[1][1], board[2][2]) || checkRowCol(board[0][2], board[1][1], board[2][0])) {
            return true;
        }
        return false;
    }

    private boolean checkRowCol(String c1, String c2, String c3) {
        return !c1.isEmpty() && c1.equals(c2) && c2.equals(c3);
    }

    private void endGame() {
        if (checkWin()) {
            if (isPlayerX) {
                JOptionPane.showMessageDialog(this, "Player X wins!");
                scorePlayerX++;
            } else {
                JOptionPane.showMessageDialog(this, "Player O wins!");
                scorePlayerO++;
            }
        } else {
            JOptionPane.showMessageDialog(this, "It's a draw!");
        }
        updateScores();
        resetGame();
    }

    private void updateScores() {
        labelScoreX.setText("Player X Score: " + scorePlayerX);
        labelScoreO.setText("Player O Score: " + scorePlayerO);
    }

    private void resetGame() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setText("");
            }
        }
        isPlayerX = true;
        movesLeft = BOARD_SIZE * BOARD_SIZE;
    }

    public static void main(String[] args) {
        new TicTacToeGame();
    }
}
