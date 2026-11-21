# Análise do enunciado: Avaj Launcher

**Fonte:** [Avaj Launcher, versão 5.2](Avaj%20Launcher.pdf). As seções abaixo usam a numeração do PDF. A análise descreve o que deve ser entregue e como verificar os requisitos; não é um laudo de conformidade da implementação.

## Objetivo e escopo (§ I–II)

O exercício propõe um simulador de aeronaves em Java para praticar a leitura de UML e a aplicação de Observer, Singleton e Factory. O cenário do aeroporto de Frankfurt motiva o exercício, mas o enunciado não pede modelagem de pistas, filas ou métricas de congestionamento. O produto é um programa de terminal que lê um cenário e registra os eventos de voo.

## Restrições de entrega (§ III e VI)

- Usar Java com recursos até a versão LTS vigente, sem bibliotecas externas, ferramentas de build ou geradores de código. Usar pacotes nomeados, seguindo as convenções de Java.
- Garantir que o projeto compile, a partir da raiz, com `find * -name '*.java' > sources.txt` e `javac @sources.txt`. A execução deve funcionar com `java` no terminal.
- Entregar os arquivos necessários no repositório Git. Não versionar arquivos `.class`. Conferir nomes de arquivos, diretórios e pacotes antes da defesa.

## Contrato obrigatório (§ IV)

| Referência | Requisito | Critério de verificação |
| --- | --- | --- |
| § IV, UML | Implementar as classes do diagrama sem alterar a hierarquia ou a visibilidade dos membros especificados. Classes e atributos adicionais são permitidos. | Comparar código, assinaturas e modificadores com o [UML](global.puml) e sua [imagem](avaj_uml.png). |
| § IV.1, CLI | Receber exatamente um argumento: o caminho do cenário. Uma execução válida gera `simulation.txt`. | Executar com zero, um e dois argumentos; confirmar que o cenário válido gera o arquivo. |
| § IV.2, cenário | Ler um inteiro positivo na primeira linha e disparar o número correspondente de rodadas meteorológicas. Nas linhas seguintes, ler `TYPE NAME LONGITUDE LATITUDE HEIGHT`. | Usar um cenário de poucas rodadas com uma aeronave que permaneça em voo e contar as mensagens. Testar contagem ausente, nula ou inválida e linhas incompletas. |
| § IV.3, clima | Gerar `RAIN`, `FOG`, `SUN` e `SNOW` levando em conta longitude, latitude e altura. O algoritmo é livre. | Demonstrar que cada eixo pode influenciar o resultado. O PDF não exige aleatoriedade nem uma sequência específica de clima. |
| § IV.4, movimento | Atualizar as coordenadas de cada aeronave de acordo com o clima, conforme a tabela abaixo. | Fixar o clima em teste e verificar as 12 combinações, inclusive os eixos que não mudam. |
| § IV.5, coordenadas e pouso | Manter coordenadas horizontais positivas e altura entre `0` e `100`. Limitar subidas a `100`; ao chegar a `0` ou descer abaixo disso, pousar, registrar o evento e cancelar a inscrição na torre. | Testar subida a partir de `99`, descida que chega exatamente a `0` e descida que passaria de `0`. Confirmar que os outros observadores ainda recebem a rodada e que a aeronave pousada não recebe a próxima. |
| § IV.5, identidade e log | Atribuir ID único a cada aeronave. Registrar inscrição, cancelamento e reações ao clima. O formato da reação é `TYPE#NAME(UNIQUE_ID): SPECIFIC_MESSAGE`. | Criar aeronaves com o mesmo nome e comparar os IDs. Conferir os eventos e o formato do log sem exigir os textos ilustrativos do PDF. |
| § IV.6, validação | Interromper a execução diante de entrada inválida e imprimir o erro na saída padrão (`stdout`). | Testar arquivo inexistente, valores não inteiros, coordenadas inválidas e tipo desconhecido após uma linha válida. Confirmar que a simulação não começa nesses casos. |

### Movimentos por condição meteorológica (§ IV.4)

Os valores são variações por rodada. Eixos não mencionados permanecem inalterados.

| Aeronave | `SUN` | `RAIN` | `FOG` | `SNOW` |
| --- | --- | --- | --- | --- |
| `JetPlane` | Latitude +10, altura +2 | Latitude +5 | Latitude +1 | Altura −7 |
| `Helicopter` | Longitude +10, altura +2 | Longitude +5 | Longitude +1 | Altura −12 |
| `Balloon` | Longitude +2, altura +4 | Altura −5 | Altura −3 | Altura −15 |

## Leitura dos anexos

- **Observer:** `Tower` mantém os observadores, `WeatherTower` dispara as rodadas e cada `Flyable` reage ao clima. O ponto crítico é permitir que uma aeronave cancele a inscrição ao pousar, sem interromper a notificação das demais.
- **Singleton e Factory:** o UML marca `WeatherProvider` e `AircraftFactory` como singletons. A fábrica concentra a seleção do tipo de aeronave. Gerar IDs sequenciais na fábrica é uma estratégia de implementação, não um requisito de numeração.
- **Notação do UML:** `Flyable` aparece como classe abstrata e possui estado de instância, embora a seta para `Aircraft` use a notação de realização. Em Java, a leitura coerente é `Aircraft extends Flyable`, com as três aeronaves estendendo `Aircraft`. A imagem usa `Baloon`; o PlantUML, o PDF e o cenário usam `Balloon`.
- **Altura inicial acima do teto:** o [cenário fornecido](scenario.txt) inicia o `JetPlane J2` na altura `768`. Aceitar essa entrada e normalizá-la para `100` preserva o exemplo e mantém a altura em faixa; o PDF não detalha separadamente o tratamento desse valor na leitura.
- **Casos não detalhados:** o PDF não especifica se um cenário pode conter apenas o cabeçalho, se linhas em branco são aceitas ou como tratar uma aeronave que já começa na altura `0`. Essas escolhas devem ser documentadas e testadas. Preservar o log anterior em caso de cenário inválido é útil, mas não é uma exigência expressa.

## Bônus e preparação para a defesa (§ V–VI)

O bônus pede exceções personalizadas para tratar comportamentos anormais e só é avaliado após a aprovação integral da parte obrigatória. Antes da defesa, compilar em uma cópia limpa, executar o cenário original e demonstrar um caso de pouso durante a notificação e outro de entrada inválida. Os comandos do projeto estão no [README](README.md).