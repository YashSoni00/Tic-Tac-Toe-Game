import javax.swing.*;
import java.awt.*;

public class TicTacToe {

    static boolean turn = true; // true = nought - O, false = cross - X
    Image _nought = new ImageIcon("icon/nought.png").getImage();
    Image _cross = new ImageIcon("icon/cross.png").getImage();
    JLabel nought = new JLabel();
    JLabel cross = new JLabel();

    TicTacToe() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setLayout(new GridLayout(4, 3));

        JLabel turn = new JLabel("Turn: ");
        turn.setHorizontalAlignment(SwingConstants.CENTER);
        turn.setFont(new Font("Arial", Font.BOLD, 30));
        turn.setForeground(Color.BLACK);
        frame.add(turn);

        _nought = _nought.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        nought.setHorizontalAlignment(SwingConstants.CENTER);
        nought.setIcon(new ImageIcon(_nought));
        nought.setBorder(BorderFactory.createMatteBorder(0, 0, 10, 0, Color.RED));
        nought.setFocusable(false);
        frame.add(nought);

        _cross = _cross.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        cross.setHorizontalAlignment(SwingConstants.CENTER);
        cross.setIcon(new ImageIcon(_cross));
        cross.setBorder(BorderFactory.createMatteBorder(0, 0, 10, 0, Color.GREEN));
        cross.setVisible(false);
        cross.setFocusable(false);
        frame.add(cross);

        // Add 9 buttons
        JButton[] button = new JButton[9];
        for (int i = 0; i < 9; i++) {
            button[i] = new JButton();
            button[i].setFocusable(false);
            button[i].setBackground(Color.WHITE);
            button[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button[i].setFont(new Font("Arial", Font.BOLD, 0));
            int finalI = i;
            button[i].addActionListener(e -> {
                changeIcon(button, finalI);
            });
            frame.add(button[i]);
        }

        // Finalize the frame
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void changeIcon(JButton[] button, int finalI) {
        if (button[finalI].getIcon() == null) {
            if (turn) {
                button[finalI].setIcon(new ImageIcon(_nought));
                button[finalI].setText("O");
                cross.setVisible(true);
                nought.setVisible(false);
                turn = false;
            } else {
                button[finalI].setIcon(new ImageIcon(_cross));
                button[finalI].setText("X");
                nought.setVisible(true);
                cross.setVisible(false);
                turn = true;
            }
            checkWinner(button);
            checkBoardFull(button);
        }
    }

    private void checkBoardFull(JButton[] button) {
        boolean full = true;
        for (int i = 0; i < 9; i++) {
            if (button[i].getText().isEmpty()) {
                full = false;
                break;
            }
        }
        if (full) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "It's a draw!" +
                    "\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            clearBoard(button, dialogResult);
        }
    }

    private static void clearBoard(JButton[] button, int dialogResult) {
        if (dialogResult == JOptionPane.YES_OPTION) {
            for (int i = 0; i < 9; i++) {
                button[i].setText("");
                button[i].setIcon(null);
                button[i].setBackground(Color.WHITE);
            }
        } else {
            System.exit(0);
        }
    }

    private void checkWinner(JButton[] button) {
        String player;
        // IMP: If turn is true, player is X, else player is O (opposite)
        //  This is because turn is changed before this method is called
        if (turn) player = "X";
        else player = "O";

        // Check horizontal
        for (int i = 0; i < 9; i += 3) {
            if (button[i].getText().equals(player) && button[i + 1].getText().equals(player) && button[i + 2].getText().equals(player)){
                displayWinner(player, button, i, i + 1, i + 2);
            }
        }

        // Check vertical
        for (int i = 0; i < 3; i++) {
            if (button[i].getText().equals(player) && button[i + 3].getText().equals(player) && button[i + 6].getText().equals(player)){
                displayWinner(player, button, i, i + 3, i + 6);
            }
        }

        // Check diagonal
        if (button[0].getText().equals(player) && button[4].getText().equals(player) && button[8].getText().equals(player)){
            displayWinner(player, button, 0, 4, 8);
        }
        if (button[2].getText().equals(player) && button[4].getText().equals(player) && button[6].getText().equals(player)){
            displayWinner(player, button, 2, 4, 6);
        }
    }

    private void displayWinner(String player, JButton[] button, int i, int j, int k) {
        button[i].setBackground(player.equals("X") ? Color.GREEN : Color.RED);
        button[j].setBackground(player.equals("X") ? Color.GREEN : Color.RED);
        button[k].setBackground(player.equals("X") ? Color.GREEN : Color.RED);

        int dialogResult = JOptionPane.showConfirmDialog(null, "Player " + player + " wins!" +
                "\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        clearBoard(button, dialogResult);
    }
}
