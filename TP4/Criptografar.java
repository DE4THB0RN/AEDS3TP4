import java.util.HashMap;
import java.util.Map;

public class Criptografar {

    private HashMap<Character, Integer> transpoe = new HashMap<Character, Integer>();
    private HashMap<Character, Integer> destranspoe = new HashMap<Character, Integer>();
    private String chave;

    public Criptografar(String chave) {
        this.chave = chave;
        preencheTranspoe(chave);
        destranspoe = transpoe;
    }

    public byte[] criptografar(byte[] escrita) {
        byte[] resposta = new byte[escrita.length];

        resposta = vigenere(escrita);

        resposta = transposicao(resposta);

        return resposta;
    }

    private byte[] vigenere(byte[] palavra) {
        byte[] resposta = new byte[palavra.length];
        int j = 0;
        byte b;
        for (int i = 0; i < palavra.length; i++) {
            b = (byte) chave.charAt(j);
            resposta[i] = (byte) ((palavra[i] + b) % 256);
            j = j == chave.length() - 1 ? 0 : j + 1;
        }

        return resposta;
    }

    private byte[] transposicao(byte[] palavra) {

        int coluna = chave.length();
        int linha = (int) Math.ceil((double) palavra.length / coluna);
        byte[] resposta = new byte[palavra.length];
        byte[][] matriz = new byte[linha][coluna];

        for (int i = 0, k = 0; i < linha; i++) {
            for (int j = 0; j < coluna;) {
                if (k < palavra.length) {
                    byte b = palavra[k];
                    matriz[i][j] = b;
                    j++;
                    k++;
                } else {
                    matriz[i][j] = '_';
                    j++;
                }
            }
        }
        int ar = 0;
        for (Map.Entry<Character, Integer> entrada : transpoe.entrySet()) {
            int colunaIndex = entrada.getValue();

            for (int i = 0; i < linha; i++) {
                if (matriz[i][colunaIndex] != '_') {
                    resposta[ar] = matriz[i][colunaIndex];
                }
                ar++;
            }
        }

        return resposta;

    }

    private void preencheTranspoe(String chave) {
        for (int i = 0; i < chave.length(); i++) {
            transpoe.put(chave.charAt(i), i);
        }
    }

    public byte[] descriptografar(byte[] cifra) {

        byte[] resposta = new byte[cifra.length];

        resposta = descriptografarTransposicao(cifra);

        resposta = descriptografarVigenere(resposta);

        return resposta;
    }

    private byte[] descriptografarTransposicao(byte[] cifra) {
        int coluna = chave.length();
        int linha = (int) Math.ceil((double) cifra.length / coluna);
        byte[] resposta = new byte[cifra.length];
        byte[][] matriz = new byte[linha][coluna];
        byte b;

        for (int i = 0, k = 0; i < coluna; i++) {
            for (int j = 0; j < linha; j++) {

                b = cifra[k];
                matriz[i][j] = b;
                k++;

            }
        }

        int index = 0;
        for(Map.Entry<Character,Integer> entry : destranspoe.entrySet()){
            entry.setValue(index++);
        }

        byte[][] descifrar = new byte[linha][coluna];
        int indexColuna;
        for(int l = 0; l < chave.length(); l++){
            indexColuna = destranspoe.get(chave.charAt(l));
            for(int i = 0; i < linha; i++){
                descifrar[i][l] = matriz[i][indexColuna];
            }
        }

        for(int i = 0, o = 0; i < linha; i++){
            for(int j = 0; j < coluna; j++){
                if(descifrar[i][j] != '_'){
                    b = descifrar[i][j];
                    resposta[o] = b; 
                    o++;
                }
            }
        }

        return resposta;

    }

    private byte[] descriptografarVigenere(byte[] cifra) {
        byte[] resposta = new byte[cifra.length];
        int j = 0;
        byte b;
        for (int i = 0; i < cifra.length; i++) {
            b = (byte) chave.charAt(j);
            resposta[i] = (byte) ((cifra[i] - b) % 256);
            j = j == chave.length() - 1 ? 0 : j + 1;
        }

        return resposta;
    }
}
