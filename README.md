# Avaj Launcher

Simulador de aeronaves do projeto 42 Avaj Launcher (versão 5.2). Um cenário define as aeronaves e o número de rodadas de clima. O resultado é gravado em `simulation.txt` no diretório de execução.

## Compilar e executar

Para instalar o Java ou corrigir um problema no `PATH`, consulte o [guia de dependências](guia-dependencias.md).

Use um JDK 17 ou superior, com `javac` e `java` no `PATH`. Na raiz deste projeto:

```sh
find * -name '*.java' > sources.txt
javac @sources.txt
java ro.academyplus.avaj.simulator.Simulator scenario.txt
```

Não há dependências externas, ferramentas de build ou geração de código. Arquivos `.class`, a lista de fontes e a saída da simulação são ignorados pelo Git.

## Formato do cenário

```text
3
Balloon B1 2 3 20
JetPlane J1 23 44 32
Helicopter H1 654 33 20
```

A primeira linha é um inteiro positivo. Cada linha seguinte contém `TYPE NAME LONGITUDE LATITUDE HEIGHT`, com tipo sensível a maiúsculas e minúsculas. Longitude e latitude devem ser inteiros positivos; a altura inicial deve ser um inteiro não negativo. Alturas acima de 100 são limitadas a 100, incluindo a altura `768` do cenário fornecido. Uma aeronave que começa no solo é registrada e pousa imediatamente, sem receber atualizações de clima.

A CLI aceita exatamente um argumento. Se o cenário for inválido, o erro é impresso na saída padrão, a execução termina com código `1` e nenhuma saída de simulação é criada ou sobrescrita.

## Arquitetura

- **Observer:** `WeatherTower` notifica os `Flyable` na ordem de registro. A notificação percorre uma cópia da lista, então um pouso pode remover uma aeronave sem interromper a rodada.
- **Singleton:** `WeatherProvider` calcula o clima a partir das três coordenadas e do número da rodada, sem aleatoriedade. O mesmo cenário gera o mesmo log em execuções separadas.
- **Factory:** `AircraftFactory` é um singleton que instancia o tipo solicitado e atribui IDs sequenciais, independentemente do nome da aeronave.
- **Entrada e saída:** `Scenario` valida o arquivo antes que `Simulator` abra a saída. `InvalidScenarioException` identifica a linha inválida.

A altura é limitada entre 0 e 100 durante o voo. Ao pousar, a aeronave sai do registro e não recebe novas notificações. Quando não há observadores, as rodadas restantes não produzem eventos. As coordenadas horizontais saturam no maior `int` para não transbordar.

## Testes

Depois de compilar, rode o teste do modelo (em Linux ou macOS):

```sh
java -cp .:tests ro.academyplus.avaj.simulator.SimulationTest
```

O teste verifica os 12 movimentos, o teto, o pouso na rodada, a atualização dos demais observadores, o clima por coordenada e os IDs da fábrica.