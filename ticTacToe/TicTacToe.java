package ticTacToe;

import java.util.Scanner;

public class TicTacToe {

	public static final char TOKEN_X = 'X';
	public static final char TOKEN_O = 'O';
	public static char[][] board = new char[3][3];

	private static String space = "-----";

	//bucle para inicializar el tablero con espacios:
	public static char[][] gameBoard() {
		char[][] ret = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				ret[i][j] = ' ';
			}
		}
		return ret;
	}

	//imprimir el tablero completo, con las fichas:
	public static void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if ((i == 0 && j == 2) || (i == 1 && j == 2) || (i == 2 && j == 2)) {
					System.out.println(board[i][j]);
					if (!(i == 2 && j == 2)) {
						System.out.println(space);
					} else {
						System.out.println();
					}
				} else {
					System.out.print(board[i][j] + "|");
				}
			}
		}
	}

	//preguntar si quiere modo contra máquina o 2 jugaores:
	private static int askForGameMode(Scanner keyboard) {
		int choice = 0;

		do {
			System.out.println(
					"Selecciona el modo de juego:" + "\n	1. Solo" + "\n	2. Multijugador");
			System.out.println("_________________________________________\n");
			try {
				choice = keyboard.nextInt();
			} catch (Exception e) {
				keyboard.nextLine();
				choice = -1;
				System.out.println("- Selecciona una opcion valida [1,2] -");
				;
			}
		} while (choice != 1 && choice != 2);

		return choice;

	}

	private static String askForPlayerName(Scanner keyboard) {
		String playerName = null;
		System.out.print("Ingresa tu nombre: ");
		playerName = keyboard.next();
		return playerName;
	}

	//preguntar dónde quiere colocar la ficha:
	private static int askToMove(Scanner keyboard, String rowOrCol) {
		int ret = 0;
		do {
			System.out.print("	" + rowOrCol);
			try {
				ret = keyboard.nextInt();
				if (ret < 1 || ret > 3) {
					System.out.println("- Selecciona una opcion valida [1,2,3] -");
				}
			} catch (Exception e) {
				keyboard.nextLine();
				ret = -1;
				System.out.println("- Selecciona una opcion valida [1,2,3] -");
			}
		} while (ret < 1 || ret > 3);
		return ret;
	}

	//comprobar si se puede colocar la ficha (que no haya ya una ficha):
	public static boolean isEmpty(int row, int col) {
		boolean ret = false;
		if (board[row - 1][col - 1] != ' ') {
			ret = false;
		} else {
			ret = true;
		}
		return ret;
	}
	
	//poner la ficha en la posición determinada:
	public static void putToken(char token, int row, int col) {
		board[row - 1][col - 1] = token;
	}

	//generar número aleatorio entre 1 y 3; para cuando se juegue contra la máquina:
	public static int getRandom() {
		int ret = 0;
		ret = (int) Math.floor((Math.random() * 3) + 1);
		return ret;
	}
	
	//hacer comprobaciones para ver si hay 3 fichas iguales en línea:
	public static boolean isThreeInLine(char token) {
		boolean ret = false;
		for (int i = 0; i < 3; i++) {
			//comprobar líneas horizontales
			if ((board[i][0] == token) && (board[i][1] == token) && (board[i][2] == token)) {
				ret = true;
			//comprobar líneas verticales
			} else if ((board[0][i] == token) && (board[1][i] == token) && (board[2][i] == token)) {
				ret = true;
			}
		}
		//comprobar diagonales
		if ((board[0][0] == token) && (board[1][1] == token) && (board[2][2] == token)) {
			ret = true;
		} else if ((board[2][0] == token) && (board[1][1] == token) && (board[0][2] == token)) {
			ret = true;
		}
		
		return ret;
	}
	
	//comprobar si el tablero está lleno, para saber si hay empate:
	public static boolean isBoardFull() {
		boolean ret = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == ' ') {
					ret = false;
				}
			}
		}
		return ret;
	}

	private static void showWinner(String name) {
		System.out.println("_________________________________________\n");
		System.out.println("      W I N N E R     ->   " + name);
		System.out.println("_________________________________________\n");
	}

	private static void showTie() {
		System.out.println("_________________________________________\n");
		System.out.println("               L O S E     ");
		System.out.println("_________________________________________\n");
	}

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		boolean exit = false;
		boolean isGameEnd = false;
		int gameMode = 0;
		String playerName = null;
		String playerName2 = null;

		do {
			board = gameBoard();
			System.out.println("_________________________________________\n");
			System.out.println("       T I C      T A C      T O E");
			System.out.println();
			System.out.println("               X         O");
			System.out.println("_________________________________________\n");
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				// no hacer nada aquí
			}

			gameMode = askForGameMode(keyboard);
			
			// MODO CONTRA LA MÁQUINA:
			if (gameMode == 1) {
				System.out.println("- Modo en solitario seleccionado -");
				playerName = askForPlayerName(keyboard);
				System.out.println("> Bienvenido " + playerName + " jugaras con la siguiente ficha: " + TOKEN_X);

				System.out.println("Aqui esta el tablero de juego:");
				printBoard();
				do {
					boolean isValid = false;
					do {
						int row = 0;
						int col = 0;
// ----------------------------------------------------------------------------------------
						//TURNO JUGADOR:
						System.out.println("> Ingrese valores del 1 al 3 para colocar la ficha en el tablero:");
						row = askToMove(keyboard, "-> Fila: ");
						col = askToMove(keyboard, "-> Columna: ");
						if (isEmpty(row, col)) {
							putToken(TOKEN_X, row, col);
							System.out.println("-> You've successfully placed your token:\n");
							printBoard();
							//comprobar si hay tres en línea:
							if (isThreeInLine('X') == true) {
								isGameEnd = true;
								showWinner(playerName);
							//comprobar si se pueden seguir poniendo fichas - tablero lleno?:
							} else if (isBoardFull() == true) {
								isGameEnd = true;
								showTie();
							//si no se cumple lo anterior, le toca mover ficha a la máquina:
							} else {
								do {
// ----------------------------------------------------------------------------------------
									//TURNO DE LA MÁQUINA:
									row = getRandom();
									col = getRandom();
									if (isEmpty(row, col)) {
										putToken(TOKEN_O, row, col);
										System.out.println("-> La maquina tiene esta ficha:\n");
										printBoard();
										if (isThreeInLine('O') == true) {
											isGameEnd = true;
											showWinner("La maquina");
										} else if (isBoardFull() == true) {
											isGameEnd = true;
											showTie();
										} else {
											isGameEnd = false;
										}
										isValid = true;
									}
								} while (!isValid);
							}
							isValid = true;
						} else {
							System.out.println("- The spot's already been taken -");
						}
					} while (!isValid);

				} while (!isGameEnd);
// ----------------------------------------------------------------------------------------
			// MODO 2 JUGADORES:
			} else if (gameMode == 2) {
				System.out.println("- Modo multijugador seleccionado -");
				System.out.print("1. ");
				playerName = askForPlayerName(keyboard);
				System.out.println("> Bienvenido " + playerName + "jugaras con la siguiente ficha: " + TOKEN_X);
				System.out.print("2. ");
				playerName2 = askForPlayerName(keyboard);
				System.out.println("> Bienvenido " + playerName2 + " jugaras con la siguiente ficha: " + TOKEN_O);

				System.out.println("Here's the game board:");
				printBoard();
				do {
					boolean isValid = false;
					do {
						int row = 0;
						int col = 0;
						System.out.println("It's " + playerName + "'s turn");
// ----------------------------------------------------------------------------------------
						//TURNO JUGADOR:
						System.out.println("> Enter values from 1 to 3 to place the token on the board:");
						row = askToMove(keyboard, "-> Row: ");
						col = askToMove(keyboard, "-> Column: ");
						if (isEmpty(row, col)) {
							putToken(TOKEN_X, row, col);
							System.out.println("-> " + playerName + "'s successfully placed your token:\n");
							printBoard();
							if (isThreeInLine('X') == true) {
								isGameEnd = true;
								showWinner(playerName);
							} else if (isBoardFull() == true) {
								isGameEnd = true;
								showTie();
							} else {
								isGameEnd = false;
// ----------------------------------------------------------------------------------------
								//TURNO JUGADOR 2:
								do {
									isValid = false;
									System.out.println("It's " + playerName2 + "'s turn");
									System.out.println("> Enter values from 1 to 3 to place the token on the board:");
									row = askToMove(keyboard, "-> Row: ");
									col = askToMove(keyboard, "-> Column: ");
									if (isEmpty(row, col)) {
										putToken(TOKEN_O, row, col);
										System.out.println("-> " + playerName2 + "'s successfully placed your token:\n");
										printBoard();
										if (isThreeInLine('O') == true) {
											isGameEnd = true;
											showWinner(playerName2);
										} else if (isBoardFull() == true) {
											isGameEnd = true;
											showTie();
										} else {
											isGameEnd = false;
										}
										isValid = true;
									} else {
										System.out.println("- The spot's already been taken -");
										isValid = false;
									}
								} while (!isValid);
							}
							isValid = true;
						} else {
							System.out.println("- The spot's already been taken -");
							isValid = false;
						}
					} while (!isValid);

				} while (!isGameEnd);
			}
// ----------------------------------------------------------------------------------------
			//PREGUNTAR SI SE QUIERE VOLVER A JUGAR
			String respuesta = null;
			do {
				System.out.println("> Do you want to play again? [Y/N]");
				respuesta = keyboard.next().toUpperCase();
				if (respuesta.equals("Y")) {
					exit = false;
				} else if (respuesta.equals("N")) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						// no hacer nada aquí
					}
					System.out.println("_________________________________________\n");
					System.out.println(".  .. ..:  G  O  O  D  B  Y E  :.. ..  .");
					System.out.println("_________________________________________\n");
					exit = true;
				} else {
					System.out.println("- Select a valid option [Y/N] -");
				}

			} while (!respuesta.equals("Y") && !respuesta.equals("N"));
		} while (!exit);
	}
}