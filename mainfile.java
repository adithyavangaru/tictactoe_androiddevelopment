package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private char currentPlayer = 'X';
    private char[][] board = new char[3][3];
    private Button[][] buttons = new Button[3][3];
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);

        initializeBoard();
        initializeButtons();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setTextSize(24);
                buttons[i][j].setOnClickListener(new ButtonClickListener(i, j));

                // Add buttons to the GridLayout
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);
                buttons[i][j].setLayoutParams(params);
                gridLayout.addView(buttons[i][j]);
            }
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        private final int row;
        private final int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View view) {
            if (board[row][col] == ' ' && currentPlayer != ' ') {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                board[row][col] = currentPlayer;

                if (checkWinner(row, col)) {
                    showWinner();
                } else if (isBoardFull()) {
                    showTie();
                } else {
                    switchPlayer();
                }
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private boolean checkWinner(int row, int col) {
        // Check row
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) {
            return true;
        }
        // Check column
        if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) {
            return true;
        }
        // Check diagonals
        if (row == col && board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (row + col == 2 && board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWinner() {
        Toast.makeText(this, "Player " + currentPlayer + " wins!", Toast.LENGTH_SHORT).show();
        resetGame();
    }

    private void showTie() {
        Toast.makeText(this, "It's a tie!", Toast.LENGTH_SHORT).show();
        resetGame();
    }

    private void resetGame() {
        initializeBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
    }
}
