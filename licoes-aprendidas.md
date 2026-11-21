# Lições aprendidas: Avaj Launcher

## Use o enunciado, o UML e o cenário em conjunto

O diagrama define a hierarquia, as assinaturas e a visibilidade dos membros. O enunciado define o comportamento, e o cenário fornecido revela detalhes que precisam de uma decisão explícita. Por exemplo, `J2` começa com altura `768`: rejeitar qualquer altura acima de `100` impediria a execução do exemplo. A implementação aplica o teto de `100` já na criação das coordenadas e rejeita alturas negativas na entrada.

## Teste o que acontece quando um observador sai durante a notificação

Uma aeronave pode pousar e se remover da torre enquanto a rodada ainda está em andamento. Iterar sobre uma cópia da lista permite que as demais aeronaves recebam a atualização na ordem de registro. O teste mais útil coloca a aeronave que vai pousar antes de outra e verifica duas coisas: a segunda ainda é notificada na mesma rodada, e a primeira não aparece na seguinte.

## Valide antes de alterar a saída

Abrir `simulation.txt` antes de ler o cenário completo pode apagar um resultado válido mesmo quando o erro está apenas na última linha. A CLI valida toda a entrada antes de abrir o log e informa o número da linha inválida na saída padrão. Na verificação, um cenário com uma aeronave válida seguida de um tipo desconhecido terminou com erro e deixou o log anterior intacto.

## Separe a geração de clima das regras de movimento

O clima depende das coordenadas e da rodada, sem aleatoriedade, o que permite repetir um cenário e obter o mesmo log. Já os testes de movimento usam clima fixo para verificar cada uma das 12 combinações de aeronave e condição climática sem depender da sequência gerada pelo simulador.

## Verifique cedo o ambiente de avaliação

O JDK não estava disponível no `PATH` do ambiente. A validação foi feita com um JDK 17 temporário, usando o comando `javac @sources.txt` exigido no enunciado, sem adicionar ferramentas de build ou dependências. Vale conferir `java -version` e `javac -version` antes de iniciar e repetir a compilação em uma cópia limpa antes da entrega.

## Próximo passo

Transformar as verificações de CLI executadas no terminal em testes automatizados: cenário original, argumentos incorretos, erro em uma linha posterior e preservação do log existente. As instruções atuais de execução e teste estão no [README](README.md).