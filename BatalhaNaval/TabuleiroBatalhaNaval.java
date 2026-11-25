package BatalhaNaval;

import java.util.Random;
import java.util.Scanner;

public class TabuleiroBatalhaNaval {
    public static void main(String[] args) {
        new TabuleiroBatalhaNaval();
    }

    public TabuleiroBatalhaNaval() {
        Scanner sc = new Scanner(System.in);
        // CONFIGURAÇÕES DO JOGO
        int sizeBoard = 8;
        int limiteTentativas = 30;
        int totalNavios = 10;

        // DADOS DOS NAVIOS A SEREM CRIADOS
        char[] tiposNavios = { 'P', 'C', 'C', 'D', 'D', 'D', 'S', 'S', 'S', 'S' };
        int[] tamanhoNavios = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 };
        int[] acertosNavio = new int[totalNavios];

        // TABULEIROS
        // tabuleiro escondido
        char[][] board = new char[sizeBoard][sizeBoard];
        // tabuleiro visivel pro jogador
        char[][] boardVisivel = new char[sizeBoard][sizeBoard];
        // tabuleiro que guarda o indice de cada navio de 0 a 9 e -1 para água
        int[][] idNavio = new int[sizeBoard][sizeBoard];

        // ESTATÍSTICAS
        int tentativas = 0;
        int acertos = 0;
        int naviosAfundados = 0;
        int erros = 0;

        iniciarTabuleiro(board, boardVisivel, idNavio);
        posicionarNavios(board, idNavio, tiposNavios, tamanhoNavios);
        while (tentativas < limiteTentativas && naviosAfundados < totalNavios) {
            mostrarTabuleiro(boardVisivel);

            int linha = lerLinha(sc, sizeBoard);
            int coluna = lerColuna(sc, sizeBoard);

            int resultado = atirar(board, boardVisivel, idNavio, acertosNavio, tamanhoNavios, linha, coluna);
            if (resultado != 0) {
                tentativas++;
            }
            if (resultado == 1) {
                erros++;
            }
            if (resultado == 2 || resultado == 3) {
                acertos++;
            }
            if (resultado == 3) {
                naviosAfundados++;
            }
            mostrarDados(tentativas, limiteTentativas, acertos, naviosAfundados);
            mostrarResultadoTiro(resultado, board[linha][coluna]);
        }
        mostrarResultadoFinal(tentativas, limiteTentativas, acertos, erros, naviosAfundados, totalNavios);
        mostrarTabuleiroNavios(board);

    }

    // Utilizado para ganhar o jogo mais facilmente, colocar na entre
    // "posicionarNavios" e o While no construtor
    private void mostrarTabuleiroNaviosID(int[][] idNavio) {
        System.out.println();
        System.out.print("    ");
        for (int j = 1; j <= idNavio.length; j++) {
            System.out.print(j + "  ");
        }
        System.out.println();
        System.out.println("  _________________________");
        for (int i = 0; i < idNavio.length; i++) {
            System.out.print((char) (i + 65) + " | ");
            for (int j = 0; j < idNavio[i].length; j++) {
                System.out.print(idNavio[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private String mostrarClassificacao(int pontos) {
        String classificacao;
        if (pontos > 400) {
            classificacao = "Excelente!";
        } else if (pontos >= 300) {
            classificacao = "Bom!";
        } else if (pontos >= 200) {
            classificacao = "Regular!";
        } else {
            classificacao = "Precisa melhorar!";
        }
        return classificacao;
    }

    private void mostrarPontuacao(int tentativa, int totalNavios, int acertos, int naviosAfundados, int erros) {
        int pontos;
        boolean rapido = false;
        if (tentativa < 25 && naviosAfundados == totalNavios) {
            pontos = (acertos * 10) + (naviosAfundados * 50) - (erros * 2) + 100;
            rapido = true;
        } else {
            pontos = (acertos * 10) + (naviosAfundados * 50) - (erros * 2);
        }
        System.out.println("Pontuação final: " + pontos);
        System.out.println("Acertos: " + acertos + " x 10 = " + acertos * 10);
        System.out.println("Navios afundados: " + naviosAfundados + " x 50 = " + naviosAfundados * 50);
        System.out.println("Penalidade de erros: " + erros + " x -2 = " + (erros * 2) * -1);
        if (rapido) {
            System.out.println("Bônus de vitória rápida: +100");
        }
        System.out.println("Classificação: " + mostrarClassificacao(pontos));
    }

    private void mostrarResultadoFinal(int tentativas, int limiteTentativas, int acertos, int erros,
            int naviosAfundados, int totalNavios) {
        System.out.println();
        System.out.println();
        if (naviosAfundados == totalNavios) {
            System.out.println("Status: VITÓRIA");
        } else {
            System.out.println("Status: DERROTA");
        }
        System.out.println("Tentativas usadas: " + tentativas + "/" + limiteTentativas);
        System.out.println("Total de acertos: " + acertos);
        System.out.println("Total de erros: " + erros);
        System.out.printf("Taxa de acerto: %.1f%%%n", calcularAcertos(acertos, tentativas));
        System.out.println("Navios afundados: " + naviosAfundados + "/" + totalNavios);
        mostrarPontuacao(tentativas, totalNavios, acertos, naviosAfundados, erros);
    }

    private void mostrarTabuleiroNavios(char[][] board) {
        System.out.println();
        System.out.print("    ");
        for (int j = 1; j <= board.length; j++) {
            System.out.print(j + "  ");
        }
        System.out.println();
        System.out.println("  _________________________");
        for (int i = 0; i < board.length; i++) {
            System.out.print((char) (i + 65) + " | ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void mostrarDados(int tentativas, int limiteTentativas, int acertos, int naviosAfundados) {
        System.out.println();
        System.out.println();
        System.out.println("--- Estatísticas ---");
        System.out.println("Tentativas: " + tentativas + "/" + limiteTentativas);
        System.out.println("Total de acertos: " + acertos);
        System.out.println("Total de navios afundados: " + naviosAfundados);
        System.out.printf("Taxa de acerto: %.1f%%%n", calcularAcertos(acertos, tentativas));
    }

    private double calcularAcertos(int acertos, int tentativas) {
        double taxa = 0;
        if (tentativas > 0) {
            taxa = (acertos * 100.0) / tentativas;
        }
        return taxa;
    }

    private void mostrarResultadoTiro(int resultado, char tipo) {
        if (resultado == 0) {
            System.out.println("Você ja atirou nesta posição, tente outra");
        } else if (resultado == 1) {
            System.out.println("Você errou");
        } else if (resultado == 2) {
            System.out.println("Você acertou um " + nomeNavio(tipo) + "!");
        } else {
            System.out.println("Você afundou um " + nomeNavio(tipo) + "!");
        }
    }

    private String nomeNavio(char tipo) {
        String nome;
        switch (tipo) {
            case 'P':
                nome = "Porta-Aviões";
                break;
            case 'C':
                nome = "Cruzador";
                break;
            case 'D':
                nome = "Destroyer";
                break;
            default:
                nome = "Submarino";
                break;
        }
        return nome;
    }

    private int atirar(char[][] board, char[][] boardVisivel, int[][] idNavio, int[] acertosNavio, int[] tamanhoNavios,
            int linha, int coluna) {
        int resultado;
        if (boardVisivel[linha][coluna] != '~') {
            resultado = 0;// ja atirou na posição
        } else if (board[linha][coluna] == '~') {
            boardVisivel[linha][coluna] = 'X';// errou
            resultado = 1;
        } else {
            boardVisivel[linha][coluna] = 'A';// acertou
            // soma uma parte atringida do tipo de navio que estava nessa coordenada
            int n = idNavio[linha][coluna];
            acertosNavio[n]++;
            if (acertosNavio[n] == tamanhoNavios[n]) {// se todas as partes do navio foram atingidas ele afunda
                resultado = 3;
            } else {
                resultado = 2;// apenas avisa que acertou um navio e qual acertou
            }
        }
        return resultado;
    }

    private int lerLinha(Scanner sc, int sizeBoard) {
        char ultimaLetra = (char) (65 + sizeBoard - 1);// aqui ele delimita que a ultima letra aceitável é H utilizando
                                                       // a tabela de código hexadecimal
        int linha;
        do {
            System.out.print("Linha (A-" + ultimaLetra + "):");
            linha = sc.next().toUpperCase().charAt(0);
            if (linha < 65 || linha > ultimaLetra) {// se o código for menor que 65 ou maior que 72 ele ve que está
                                                    // fora do limite aceito e pede o número novamente
                System.out.print("Linha inválida! Digite uma letra de A a " + ultimaLetra);
            }
        } while (linha < 65 || linha > ultimaLetra);
        return linha - 65;// se estiver dentro dos números aceitos ele retorna a posição da linha que o
                          // jogador escolheu
    }

    private int lerColuna(Scanner sc, int sizeBoard) {
        int coluna;
        do {
            System.out.print("Coluna (1-" + sizeBoard + "): ");
            coluna = sc.nextInt();
            if (coluna < 1 || coluna > sizeBoard) {
                System.out.print("Número inválido! Digite um número de 1 a " + sizeBoard);
            }
        } while (coluna < 1 || coluna > sizeBoard);
        return coluna - 1;
    }

    private void mostrarTabuleiro(char[][] boardVisivel) {
        System.out.println();
        System.out.print("    ");
        for (int j = 1; j <= boardVisivel.length; j++) {
            System.out.print(j + "  ");
        }
        System.out.println();
        System.out.println("  _________________________");
        for (int i = 0; i < boardVisivel.length; i++) {
            System.out.print((char) (i + 65) + " | ");
            for (int j = 0; j < boardVisivel[i].length; j++) {
                System.out.print(boardVisivel[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void posicionarNavios(char[][] board, int[][] idNavio, char[] tiposNavios, int[] tamanhoNavios) {
        Random random = new Random();
        for (int n = 0; n < tiposNavios.length; n++) {
            boolean posicionado = false;
            while (!posicionado) {
                int linha = random.nextInt(board.length);
                int coluna = random.nextInt(board.length);
                boolean horizontal = random.nextInt(2) == 0;
                if (podePosicionar(board, linha, coluna, tamanhoNavios[n], horizontal)) {
                    for (int i = 0; i < tamanhoNavios[n]; i++) {
                        int l = linha;
                        int c = coluna;
                        if (horizontal) {
                            c = coluna + i;
                        } else {
                            l = linha + i;
                        }
                        board[l][c] = tiposNavios[n];
                        idNavio[l][c] = n;
                    }
                    posicionado = true;
                }
            }
        }
    }

    private boolean podePosicionar(char[][] board, int linha, int coluna, int tamanhoNavio, boolean horizontal) {
        boolean pode = true;
        for (int i = 0; i < tamanhoNavio; i++) {
            int l = linha;
            int c = coluna;
            if (horizontal) {
                c = coluna + i;
            } else {
                l = linha + i;
            }
            if (l >= board.length || c >= board.length) {
                pode = false;
            } else if (board[l][c] != '~') {
                pode = false;
            }
        }
        return pode;
    }

    // Este método preenche o board principal e o board visível com ~ e o board que
    // define qual navio está em cada posição com -1
    private void iniciarTabuleiro(char[][] board, char[][] boardVisivel, int[][] idNavio) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '~';
                boardVisivel[i][j] = '~';
                idNavio[i][j] = -1;
            }
        }
    }
}
