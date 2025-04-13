import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Gato {
    // Tamaño de la ventana
    int boardWidth = 600;
    int boardHeight = 650;

    // Declaramos el frame como objeto JFrame
    JFrame frame = new JFrame("Gato");

    // Panel para texto, necesario un label (etiqueta)
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();

    // Creación del tablero
    JPanel boardPanel = new JPanel();

    // Botones para "X" o "O"
    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    // Fin del juego
    boolean gameOver = false;

    // Turnos
    int turns = 0;

    // Constructor del Gato
    Gato() {
        // Configuración de la Ventana
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // BorderLayout() divide el el JFrame en zonas

        // Configuración del Texto
        textLabel.setBackground(Color.white);
        textLabel.setForeground(Color.darkGray);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Gato");
        textLabel.setOpaque(true); // Define si el componente es opaco o no

        // Configuación del Panel del texto
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel); // Se añade el texto al panel
        frame.add(textPanel, BorderLayout.NORTH); // Se añade el Panel del texto al Frame

        // Configuración del Tablero
        boardPanel.setLayout(new GridLayout(3, 3)); // GridLayout(3,3) esquema 3x3
        boardPanel.setBackground(Color.white);
        frame.add(boardPanel); // Se añade el Tablero al Frame

        // Creación de los botones
        for(int r = 0; r < 3; r++) { // Filas
            for(int c = 0; c < 3; c++) { // Columnas
                JButton tile = new JButton(); // Creación de una Casilla
                board[r][c] = tile; // Se asigna la Casilla a la fila r y columna c
                boardPanel.add(tile); // Se añade la casilla al Panel del Tablero
                
                // Configuración de los Botones
                tile.setBackground(Color.white);
                tile.setForeground(Color.black);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.setBorder(new LineBorder(Color.lightGray));

                // ActionListener para que el programa reaccione a los clicks
                tile.addActionListener(new ActionListener() { // Asigna un "escuchador de eventos" al botón
                    public void actionPerformed(ActionEvent event) { // Método se ejecuta cuando ocurre un click
                        if (gameOver) return; 
                        JButton tile = (JButton) event.getSource(); // Obtiene el objeto que generó el evento (en este caso, el botón clickeado)
                        
                        if(tile.getText() == "") {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            
                            if(!gameOver) {
                                currentPlayer = currentPlayer == playerX ? playerO : playerX; 
                                textLabel.setText("Turno de " + currentPlayer); // Muestra el turno del jugador
                            }
                        }
                    }
                });
            }
        }

        // Botón de reinicio
        JButton resetBtn = new JButton("Volver a jugar");
        resetBtn.setBackground(Color.white);
        resetBtn.setForeground(Color.black);
        resetBtn.setFont(new Font("Arial", Font.BOLD, 20));
        resetBtn.setFocusable(false);
        resetBtn.setBorder(new LineBorder(Color.lightGray));
        resetBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                resetGame();
            }
        });

        // Se añade el botón de reincio al frame
        JPanel resetPanel = new JPanel();
        resetPanel.setLayout(new BorderLayout());
        resetPanel.add(resetBtn, BorderLayout.CENTER);
        frame.add(resetPanel, BorderLayout.SOUTH);
    }

    void checkWinner() {
        // Horizontal
        for(int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") continue;

            if(board[r][0].getText() == board[r][1].getText() && 
                board[r][1].getText() == board[r][2].getText())  {
                for(int i = 0; i < 3; i++) {
                    setWinner (board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        // Vertical
        for(int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") continue;

            if(board[0][c].getText() == board[1][c].getText() &&
                board[1][c].getText() == board[2][c].getText()) {
                    for(int i = 0; i < 3; i++) {
                        setWinner(board[i][c]);
                    }
                    gameOver = true;
                    return;
                }
        }

        // Diagonal
        if(board[0][0].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][2].getText() &&
            board[0][0].getText() != "") {
                for(int i = 0; i < 3; i++) {
                    setWinner(board[i][i]);
                }
                gameOver = true;
                return;
            }

        if(board[0][2].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][0].getText() &&
            board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
            }

        if(turns == 9) {
            for(int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.white);
        tile.setBackground(Color.green);
        textLabel.setText("Ganó " + currentPlayer);
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.white);
        tile.setBackground(Color.orange);
        textLabel.setText("¡Empate!");
    }

    void resetGame() {
        for(int r = 0; r < 3; r++) {
            for(int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setEnabled(true);
                board[r][c].setBackground(Color.white);
                board[r][c].setForeground(Color.black);
            }
        }
        currentPlayer = playerX;
        gameOver = false;
        turns = 0;
        textLabel.setText("Turno de " + currentPlayer);
    }
}
