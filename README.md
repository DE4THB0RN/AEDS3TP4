# TP4 - AEDS III

Trabalho feito por Pedro Augusto de Paula, Léa Tronel e Loïcia Ribeiro

Para realizar a cifragem escolhemos a cifra de Vigenère e a cifragem por transposição por colunas. 
A cifragem foi implementada durante os métodos de conversão de e para array de bytes.
A mais fácil das duas foi a cifra de Vigenère, apenas precisando de simples contas matemáticas para funcionar, porém a cifragem por transposição causou muitos problemas, algumas vezes a cifragem ultrapassava o limite do vetor,e em outras a descifragem, que foi a mais problemática, reorganizava errado o array ou aumentava o tamanho dele sem necessidade, o que então causava erros na leitura de strings codificadas. Depois de muitas reescritas dos métodos, finalmente foi encontrada uma forma que funciona, simulando uma matriz através de uma fórmula para fazer a transposição e depois invertendo, assim o código funcionou.
Obs: O código só vai rodar a partir da pasta TP4

## Há uma função de cifragem em todas as classes de entidades, envolvendo pelo menos duas operações diferentes e usando uma chave criptográfica?
A função de cifragem está sendo utilizada nos métodos fromByteArray e toByteArray da classe Livro, por isso todas as transferências de bytes de livros estão automáticamente cifradas

## Uma das operações de cifragem é baseada na substituição e a outra na transposição?
Sim

## O trabalho está funcionando corretamente?
Sim

## O trabalho está completo?
Sim

## O trabalho é original e não a cópia de um trabalho de um colega?
Sim
