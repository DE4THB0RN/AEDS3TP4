import java.util.Arrays;
import java.util.Comparator;

public class Criptografar {

    private String chave;

    public Criptografar(String chave) {
        this.chave = chave;

    }

    public byte[] criptografar(byte[] escrita) {
        
        byte[] resposta = transposicao(escrita);

        resposta = vigenere(resposta);

        return resposta;
    }

    public byte[] descriptografar(byte[] cifra) {

        byte[] resposta = descriptografarVigenere(cifra);

        resposta = descriptografarTransposicao(resposta);

        return resposta;
    }

    private byte[] vigenere(byte[] palavra) {

        byte[] resposta = new byte[palavra.length];
        int tamanho_chave = chave.length();

        for (int i = 0; i < palavra.length; i++) {
            int index = i % tamanho_chave;
            byte by = (byte) chave.charAt(index);
            resposta[i] = (byte) ((palavra[i] + by) % 256);
        }

        return resposta;
    }

    private byte[] descriptografarVigenere(byte[] cifra) {
        byte[] resposta = new byte[cifra.length];
        int tamanho_chave = chave.length();

        for (int i = 0; i < cifra.length; i++) {
            int index = i % tamanho_chave;
            byte by = (byte) chave.charAt(index);
            resposta[i] = (byte) ((cifra[i] - by + 256) % 256);
        }

        return resposta;
    }

    private byte[] transposicao(byte[] palavra) {
        byte[] resposta = new byte[palavra.length];

        int numcoluna = chave.length();
        int numlinha = (int) Math.ceil((double) palavra.length / numcoluna); 
        Integer[] ordena_chave = new Integer[chave.length()];
        int aux = 0,pos;

        for(int i = 0; i < ordena_chave.length; i++){
            ordena_chave[i] = i;
        }

        Arrays.sort(ordena_chave,Comparator.comparing(i -> chave.charAt(i)));

        for(int i = 0, k = 0; i < numcoluna; i++){

            aux = ordena_chave[i];
            for(int j = 0; j < numlinha; j++){

                pos = j * numcoluna + aux;
                if(pos < resposta.length) resposta[k++] = palavra[pos];

            }
        }

        return resposta;
    }

    private byte[] descriptografarTransposicao(byte[] cifra) {
        byte[] resposta = new byte[cifra.length];

        int numcoluna = chave.length();
        int numlinha = (int) Math.ceil((double) cifra.length / numcoluna);

        Integer[] ordena_chave = new Integer[chave.length()];
        int aux = 0,pos;

        for(int i = 0; i < ordena_chave.length; i++){
            ordena_chave[i] = i;
        }

        Arrays.sort(ordena_chave,Comparator.comparing(i -> chave.charAt(i)));

        for(int i = 0, k = 0; i < numcoluna; i++){

            aux = ordena_chave[i];
            for(int j = 0; j < numlinha; j++){

                pos = j * numcoluna + aux;
                if(pos < resposta.length) resposta[pos] = cifra[k++];

            }
        }

        return resposta;
    }
}
