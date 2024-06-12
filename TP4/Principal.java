import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

//Código usado para teste
//Deve ser ignorado ou iniciado a partir da pasta TP2
public class Principal {

    // Pega o nome do backup mais recente (é o primeiro)
    private static String bpRecente() {
        RandomAccessFile bplist;
        String na = "";
        try {
            bplist = new RandomAccessFile("backups/bp_list.bd", "rw");
            na = bplist.readUTF();
            bplist.close();
        } catch (Exception e) {
            MyIO.println("" + e);
        }
        return na;
    }

    // Pega o nome de todos os backups (menos o primeiro)
    private static String[] listabp() {
        RandomAccessFile bplist;
        ArrayList<String> lista = new ArrayList<>();
        String[] resp;
        try {
            bplist = new RandomAccessFile("backups/bp_list.bd", "rw");
            bplist.readUTF();
            while (bplist.getFilePointer() < bplist.length()) {
                lista.add(bplist.readUTF());
            }
            bplist.close();
        } catch (Exception e) {
            MyIO.println("" + e);
        }
        resp = new String[lista.size()];
        for (String s : lista) {
            resp[lista.indexOf(s)] = s;
        }
        return resp;
    }

    // Mostra todos os backups e retorna o escolhido
    private static String escolherbp() {
        String resp = "";
        int escolha = 0;
        String[] lista = listabp();

        MyIO.println("----------------------------------------------");
        int i = 0;

        for (String s : lista) {
            MyIO.println(i + 1 + " - " + s);
            i++;
        }

        escolha = MyIO.readInt("Escolha o numero do backup: ");
        MyIO.println("----------------------------------------------");
        resp = lista[escolha - 1];

        return resp;
    }

    // Pequeno método para a criação de novo livro
    private static Livro criarLivro() {
        Livro novo = new Livro();
        String leitura = "";
        float preco = 0;

        MyIO.println("----------------------------------------------");
        leitura = MyIO.readLine("Digite o titulo: ");
        novo.setTitulo(leitura);
        leitura = MyIO.readLine("Digite o ISBN: ");
        novo.setIsbn(leitura);
        preco = MyIO.readFloat("Digite o preço: ");
        novo.setPreco(preco);
        MyIO.println("----------------------------------------------");

        return novo;
    }

    // Pequeno método para a atualização de livro
    private static Livro atualizarLivro(Livro atual) {
        String leitura = "";
        float preco = 0;
        MyIO.println("----------------------------------------------");
        leitura = MyIO.readLine("Digite o titulo: ");
        atual.setTitulo(leitura);
        leitura = MyIO.readLine("Digite o ISBN: ");
        atual.setIsbn(leitura);
        preco = MyIO.readFloat("Digite o preço: ");
        atual.setPreco(preco);
        MyIO.println("----------------------------------------------");

        return atual;
    }

    public static void main(String[] args) {

        File f = new File("dados/livros.db");
        f.delete();
        ArquivoLivro arqTeste;
        Livro criar;
        boolean saida = false;
        int opcao = -1;
        int alvo = -1;
        try {
            arqTeste = new ArquivoLivro("dados/livros.db");
            while (!saida) {
                // Menu de opções
                MyIO.println("================================================");
                MyIO.println("0 - Inserir");
                MyIO.println("1 - Deletar");
                MyIO.println("2 - Atualizar");
                MyIO.println("3 - Ler");
                MyIO.println("4 - Comprimir BD");
                MyIO.println("5 - Restaurar mais recente");
                MyIO.println("6 - Escolher backup");
                MyIO.println("7 - Sair");
                MyIO.println("================================================");

                opcao = MyIO.readInt("Escolha uma opcao: ");

                // Switch case para todas as diferentes opções do menu

                switch (opcao) {
                    case 0: // Criar livro
                        criar = criarLivro();
                        arqTeste.createLivro(criar);
                        break;
                    case 1: // Deletar livro
                        alvo = MyIO.readInt("Escolha quem deletar: ");
                        if (arqTeste.deleteLivro(alvo)) {
                            MyIO.println("Livro " + alvo + "deletado com sucesso!");
                        } else
                            MyIO.println("Erro ao deletar");
                        break;
                    case 2: // Atualizar livro
                        alvo = MyIO.readInt("Escolha quem atualizar: ");
                        criar = arqTeste.read(alvo);
                        criar = atualizarLivro(criar);
                        if (arqTeste.updateLivro(criar)) {
                            MyIO.println("Livro " + alvo + " atualizado com sucesso");
                        } else
                            MyIO.println("Erro ao atualizar");
                        break;
                    case 3: // Ler livro
                        alvo = MyIO.readInt("Escolha quem ler: ");
                        criar = arqTeste.readLivro(alvo);
                        if (criar != null)
                            MyIO.println(criar.toString());
                        else
                            MyIO.println("Erro ao ler");
                        break;
                    case 4: // Comprimir arquivo
                        MyIO.println("Comprimindo arquivo");
                        arqTeste.comprime();
                        break;
                    case 5: // Restaurar backup mais recente
                        MyIO.println("Restaurando backup mais recente");
                        arqTeste = new ArquivoLivro("dados/livros.db");
                        arqTeste.descomprime(bpRecente());
                        break;
                    case 6: // Restaurar backup escolhido
                        arqTeste.descomprime(escolherbp());
                        break;
                    case 7: // Sair
                        saida = true;
                        break;
                    default: // Saida alternativa
                        saida = true;
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
