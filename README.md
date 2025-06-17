# Desafio Batalha Naval em Java (Console)

Este repositório contém a implementação de um jogo de Batalha Naval em Java, jogado diretamente no console. O projeto foi desenvolvido para atender aos requisitos de um desafio da disciplina de [Nome da Disciplina, se houver].

## Sumário

* [Visão Geral](#visão-geral)
* [Funcionalidades Implementadas](#funcionalidades-implementadas)
* [Modos de Jogo](#modos-de-jogo)
* [Funcionalidades a Serem Implementadas/Melhoradas](#funcionalidades-a-serem-implementadasmelhoradas)
* [Estrutura do Projeto](#estrutura-do-projeto)
* [Como Compilar e Executar](#como-compilar-e-executar)
* [Requisitos](#requisitos)
* [Tecnologias Utilizadas](#tecnologias-utilizadas)
* [Autor](#autor)
* [Licença](#licença)

## Visão Geral

Este projeto simula o clássico jogo de Batalha Naval. Cada jogador posiciona sua frota de navios em um tabuleiro oculto e, em turnos alternados, tenta adivinhar a localização dos navios do oponente atirando em coordenadas específicas. O objetivo é afundar todos os navios do adversário antes que ele afunde os seus.

O jogo é executado no terminal, utilizando caracteres e cores ANSI para representar o tabuleiro e o status da partida.

## Funcionalidades Implementadas

* Tabuleiro de Batalha Naval de 10x10.
* Posicionamento de uma frota padrão de navios:
    * 1 Encouraçado (tamanho 4)
    * 2 Cruzadores (tamanho 3)
    * 3 Destruidores (tamanho 2)
    * 4 Submarinos (tamanho 1)
* Detecção de acertos e erros ao atirar.
* Registro de navios atingidos e afundados.
* Alternância de turnos entre os jogadores.
* Detecção de vitória (quando todos os navios de um oponente são afundados).
* Interface de console com cores para melhor visualização (navios, acertos, erros).
* Limpeza de tela (`clearScreen`) para manter a interface organizada a cada turno.

## Modos de Jogo

O jogo oferece dois modos de jogo:

1.  **Jogador vs. Computador:** Um jogador humano contra uma IA simples que atira em posições aleatórias.
2.  **Jogador vs. Jogador:** Dois jogadores humanos podem se enfrentar, com o console sendo limpo entre os turnos para ocultar os tabuleiros.

## Funcionalidades a Serem Implementadas/Melhoradas

* **Posicionamento Manual de Navios:** Melhorar a interface para o posicionamento manual, talvez com um loop de feedback mais claro para posições inválidas sem a necessidade de "Pressione ENTER".
* **IA do Computador:** Aprimorar a inteligência artificial do computador para que ela utilize estratégias mais avançadas, como:
    * Continuar atirando em torno de um acerto até afundar o navio.
    * Evitar atirar em posições já atingidas (já é feito, mas a IA atual tenta, e a exceção o impede, o que é ineficiente).
    * Estratégias de "caça e destruição" mais eficientes.
* **Validações de Entrada:** Tornar a leitura de entrada do usuário ainda mais robusta para cobrir todos os possíveis erros de formato.
* **Interface Gráfica (GUI):** Migrar o jogo para uma interface gráfica (usando JavaFX ou Swing) para uma experiência visualmente mais rica e intuitiva.
* **Mais Tipos de Navios:** Adicionar mais variedades de navios com diferentes tamanhos.
* **Exibir Navios Afundados:** Mostrar quais navios foram afundados para cada jogador.
* **Opção de Reiniciar/Sair:** Adicionar opções no menu principal para reiniciar a partida ou sair do jogo de forma mais graciosa.
* **Multiplayer em Rede:** Implementar funcionalidade de jogo multiplayer sobre a rede.

## Estrutura do Projeto

O projeto está organizado em pacotes para melhor modularidade e separação de responsabilidades:

desafio-batalha-naval/
├── src/
│   ├── application/      # Contém a classe principal (Program), interface de usuário (UI) e modos de jogo (GameMode)
│   │   ├── Program.java
│   │   ├── UI.java
│   │   └── GameMode.java
│   ├── board/            # Classes que representam o tabuleiro e suas células/posições
│   │   ├── Board.java      # O tabuleiro principal do jogo
│   │   ├── Square.java     # Representa uma única célula do tabuleiro
│   │   └── Position.java   # Coordenadas (linha, coluna)
│   ├── game/             # Lógica central da partida e gerenciamento de jogadores
│   │   ├── Game.java       # Orquestra a partida (turnos, ataques, vitória)
│   │   └── Player.java     # Representa um jogador (mão, tabuleiro, visão do inimigo)
│   ├── ships/            # Definição e tipos de navios
│   │   ├── Ship.java       # Classe abstrata base para todos os navios
│   │   ├── Battleship.java # Implementação do Encouraçado
│   │   ├── Cruiser.java    # Implementação do Cruzador
│   │   ├── Destroyer.java  # Implementação do Destruidor
│   │   └── Submarine.java  # Implementação do Submarino
│   └── exceptions/       # Exceções personalizadas do jogo
│       └── GameException.java


## Como Compilar e Executar

Certifique-se de ter o **Java Development Kit (JDK)** instalado em sua máquina.

1.  **Baixe ou clone este repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/desafio-batalha-naval-java.git](https://github.com/seu-usuario/desafio-batalha-naval-java.git)
    cd desafio-batalha-naval
    ```

2.  **Navegue até a pasta `src`:**
    ```bash
    cd src
    ```

3.  **Compile todos os arquivos Java:**
    ```bash
    javac application/*.java board/*.java game/*.java ships/*.java exceptions/*.java
    ```
    *Se você estiver usando uma IDE (como IntelliJ IDEA ou Eclipse), a compilação é geralmente automática.*

4.  **Execute o jogo:**
    Permaneça na pasta `src` e execute a classe `Program` do pacote `application`:
    ```bash
    java application.Program
    ```

## Requisitos

* Java Development Kit (JDK) 11 ou superior.

## Tecnologias Utilizadas

* Java

## Autor

[Darcimarcos Valerio Leite]
[www.linkedin.com/in/darcimarcos]

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
