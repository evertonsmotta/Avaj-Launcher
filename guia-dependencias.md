# Guia de dependências do Avaj Launcher

## O que instalar

| Item | Necessário? | Para que serve |
| --- | --- | --- |
| **JDK 17 ou superior** | Sim. Java 17 é a versão de referência. | Inclui `javac`, que compila o código, e `java`, que executa o programa compilado. |
| Terminal e `find` | Para seguir os comandos deste guia. | O `find`, normalmente disponível no Ubuntu, lista os arquivos Java para o compilador. |
| Git | Não para compilar ou executar. | Usado para versionar o código e entregar o projeto da 42. |

Não é necessário instalar Maven, Gradle, JUnit, bibliotecas Java externas, extensões do VS Code ou uma IDE. A biblioteca padrão já vem com o JDK. Um **JRE** sozinho executa programas Java, mas não inclui `javac`.

## Instalar no Ubuntu 22.04 ou WSL

No WSL, rode os comandos no terminal **da distribuição Ubuntu**. Uma instalação do Java feita no Windows não configura automaticamente o Java do Ubuntu.

```bash
sudo apt update
sudo apt install openjdk-17-jdk-headless
```

`apt update` atualiza a lista de pacotes disponíveis; `apt install` instala o JDK. A variante *headless* não inclui componentes gráficos desnecessários para este simulador de terminal. No Ubuntu 22.04, prefira o pacote com `17` explícito: o `default-jdk` pode selecionar Java 11, abaixo da versão de referência do projeto.

Confirme a instalação:

```bash
command -v java javac
java -version
javac -version
```

Os dois comandos devem existir e, após a instalação acima, normalmente exibem versões `17.0.x`. Convém usar `java` e `javac` do mesmo JDK para evitar compilar para uma versão que o executor não suporta. Com a instalação via `apt`, geralmente não é preciso configurar `JAVA_HOME`.

## Instalar sem `sudo`

Baixe um arquivo `.tar.gz` de um JDK 17 para Linux de um fornecedor confiável, como Eclipse Temurin ou Amazon Corretto. Use `uname -m` para conferir a arquitetura: `x86_64` corresponde a x64, e `aarch64`, a ARM64. Extraia o JDK em uma pasta sua, por exemplo `~/.local/jdks`. No exemplo abaixo, ajuste `jdk-17` para o nome real da pasta extraída, que deve conter `bin/java` e `bin/javac`:

```bash
export JAVA_HOME="$HOME/.local/jdks/jdk-17"
export PATH="$JAVA_HOME/bin:$PATH"
java -version
javac -version
```

`JAVA_HOME` aponta para a raiz do JDK. `PATH` é a lista de pastas onde o terminal procura comandos; colocar `bin` no começo faz esse JDK ter prioridade. Os `export` acima valem para o terminal atual. Para persistir a configuração no Bash, coloque as duas linhas `export` em `~/.bashrc` e abra um novo terminal.

## Compilar e executar

Dentro da pasta do Avaj Launcher, onde está `scenario.txt`:

```bash
find * -name '*.java' > sources.txt
javac @sources.txt
java ro.academyplus.avaj.simulator.Simulator scenario.txt
head -n 12 simulation.txt
```

`sources.txt` é a lista de fontes que `javac` deve compilar. Se a compilação falhar, corrija o erro antes de seguir para `java`. Uma execução bem-sucedida gera `simulation.txt` no diretório atual e substitui um resultado anterior. Não é necessário acesso à internet para compilar ou executar depois de instalar o JDK.

## Problemas comuns

| Sintoma | O que fazer |
| --- | --- |
| `java` e `javac`: `command not found` | Instale o JDK na mesma distribuição em que você abriu o terminal. Para instalação manual, confira o caminho em `JAVA_HOME` e a presença de `bin` no `PATH`. |
| `java` funciona, mas `javac` não | Você pode ter apenas o JRE. Instale o JDK completo ou aponte o `PATH` para a pasta `bin` do JDK. |
| `java -version` ainda mostra 11, ou aparece `UnsupportedClassVersionError` | Confira os caminhos com `command -v java javac` e configure o `PATH` para priorizar o JDK 17. Em instalação via `apt`, use `update-alternatives --list java` e `update-alternatives --list javac` para listar as instalações disponíveis. Depois confira as duas versões e recompile. |
| `Could not find or load main class` | Confira se a compilação terminou com sucesso e execute `java` na raiz do Avaj Launcher, usando o nome completo da classe. |

Para entender a diferença entre JDK, JVM, classes e pacotes com exemplos do projeto, veja o [guia didático de Java](guia-didatico-java.md).