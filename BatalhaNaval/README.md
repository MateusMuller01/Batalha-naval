# BATALHA NAVAL

Jogo de Batalha naval no terminal, feito 100% em java, com tabuleiro 8x8 e navios posicionados de forma aleatória.

## Funcionalidades

- Posionamento aletória dos navios a cada partida.
- Tabuleiro que mostra os acertos com "A" e os erros com "X".
- Aviso de quando um navio é atingido ou afundado.
- Pontuação e classificação ao final do jogo.
## Navios
| Navio | Tamanho | Quantidade | Símbolo |
|---|---|---|---|
| Porta-aviões | 4 | 1 | P |
| Cruzador | 3 | 2 | C |
| Destroyer | 2 | 3 | D |
| Submarino | 1 | 4 | S |
## Como jogar
O jogador tem 30 tentativas para afundar todos os navios, a cada rodada ele deve escolher uma linha(A-H) e uma coluna(1-8)
# Funcionamento do código
## Criação do tabuleiro
### Configurações do jogo
```(java)
    int sizeBoard = 8;
    int limiteTentativas = 30;
    int totalNavios = 10;
```
### Vetores dos dados dos navios
```(java)
    char[] tiposNavios = { 'P', 'C', 'C', 'D', 'D', 'D', 'S', 'S', 'S', 'S' };
    int[] tamanhoNavios = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 };
    int[] acertosNavio = new int[totalNavios];
```
### Matrizes dos tabuleiros
```(java)
    char[][] board = new char[sizeBoard][sizeBoard];
    char[][] boardVisivel = new char[sizeBoard][sizeBoard];
    int[][] idNavio = new int[sizeBoard][sizeBoard];
```
### Estatísticas para os cálculos
```(java)
    int tentativas = 0;
    int acertos = 0;
    int naviosAfundados = 0;
```
### Preenchimento dos tabuleiros
```(java)
     private void iniciarTabuleiro(char[][] board, char[][] boardVisivel, int[][] idNavio)
```
Neste método todos os tabuleiros são preenchidos, o board(escondido) e o boardVisivel são preenchidos totalmente com "~" ja o tabuleiro 
idNavios é preenchido com "-1".
### Posicionamento dos navios de forma aleatória
```(java)
    private boolean podePosicionar(char[][] board, int linha, int coluna, int tamanhoNavio, boolean horizontal)
```
Este método é um booleano que retorna para o método `posicionarNavios` se ali é possível ou não posicionar um navio.
```(java)
    private void posicionarNavios(char[][] board, int[][] idNavio, char[] tiposNavios, int[] tamanhoNavios) 
        Random random = new Random();
```
Sorteia uma posição para linha, coluna e orientação após verifica se o método `podePosicionar` é true, caso seja, insere o navio a partir da coordenada sorteada, caso não seja volta para o while e sorteia novamente uma coordenada.
## Jogabilidade
### Tabuleiro e coordenadas
```(java)
     private void mostrarTabuleiro(char[][] boardVisivel)
```
Imprime o `boardVisivel`, nele todas as posições são ocupada por "~", imprime também de 1 a 8 para coluna e de A até H para linha, utilizando a tabela ASCII para a conversão dos índices das matrizes em letras.

```(java)
     private int lerLinha(Scanner sc, int sizeBoard)
```
Pede para o jogador digitar uma linha de A até H, o código utiliza a tabela ASCII para a conversão do char em Int, caso o jogador escolha uma letra fora do intervalo determinado, é pedido para que ele digite novamente.
```(java)
     private int lerColuna(Scanner sc, int sizeBoard)
```
Pede para o jogador digitar uma coluna de 1 até 8, subtraindo 1 do número digitado para assim se igualar ao índice desejado, caso o jogador escolha um número fora do intervalo determinado, é pedido para que ele digite novamente.
     <p align="center">
  ![Tabuleiro com linha/coluna](<Imagens/Captura de tela 2026-11-23 211833.png>) 
</p>  

### Estatísticas e tabuleiro após uma rodada

```(java)
     private int atirar(char[][] board, char[][] boardVisivel, int[][] idNavio, int[] acertosNavio, int[] tamanhoNavios,int linha, int coluna)
```
retorna um `Int resultado` para definir oque acontece com o tiro, sendo eles 0 para repetido, 1 para água, 2 para acerto e 3 para navio afundado.
```(java)
private String nomeNavio(char tipo)
```
Só é chamado quando o resultado é 2 ou 3, recebe um char com a letra que representa o navio(P, C, D, S) e gera o nome por extenso do navio em questão
```(java)
private void mostrarResultadoTiro(int resultado, char tipo)
```
Retorna para o jogador o resultado do tiro, caso o `Int resultado` seja 0, informa que ja atirou no local, caso seja 1, informa que errou o tiro, caso seja 2, informa que acertou um navio, senão informa que um navio foi afundado.



```(java)
private double calcularAcertos(int acertos, int tentativas)
```
Calcula a taxa de acerto a cada rodada e retorna um double.
```(java)
private void mostrarDados(int tentativas, int limiteTentativas, int acertos, int naviosAfundados)
```
Mostra os dados a cada rodada, total de tentativas até o momento, total de acertos até o momente, total de navios afundados e a taxa de acerto.

<div align="center">

![alt text](<Imagens/Captura de tela 2026-11-23 212351.png>)
![alt text](<Imagens/Captura de tela 2026-11-23 212055.png>)
![alt text](<Imagens/Captura de tela 2026-11-23 212140.png>)
![alt text](<Imagens/Captura de tela 2026-11-23 212259.png>)
</div>

### Resultados finais
```(java)
 private void mostrarPontuação(int tentativa, int totalNavios, int acertos, int naviosAfundados, int erros)
```
Este método faz o calculo da pontuação final e exibe todas as estatísticas referentes à pontuação final, puxa o método `mostrarClassificacao` para exibir seu desempenho.

```(java)
private String mostrarClassificacao(int pontos)
```
Verifica sua pontuação e retorna sua classificação baseada na pontuação atingida em forma de um comentário do tipo String.

```(java)
private void mostrarResultadoFinal(int tentativas, int limiteTentativas, int acertos, int erros, int naviosAfundados, int totalNavios)
```
Exibe todas as estatísticas ao final do jogo e puxa o método `mostrarPontuacao` para exibir também a classificação e a pontuação.

<div align="center">
  
  ![alt text](<Imagens/Captura de tela 2026-11-24 231227.png>)
  ![alt text](<Imagens/Captura de tela 2026-11-24 231611.png>)
</div> 

```(java)
private void mostrarTabuleiroNavios(char[][] board)
```
Exibe o board com os navios posicionados ao fim de cada jogo.
    <p align="center">
    ![alt text](<Imagens/Captura de tela 2026-11-24 231724.png>)
    </p>

## Tecnologias utilizadas
- Java
## Autor
Mateus Müller Ribeiro