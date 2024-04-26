
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GravarTurmas {

    public static final String ARQUIVO_TURMA = "turmas.txt";

    public List<String> recuperaDadosDeTurma() throws IOException{
        List<String> dadosTurma = recuperaTextoDoArquivo(ARQUIVO_TURMA);
        return dadosTurma;
    }

    public void gravaDadosDeTurmas(List<String> dadosDeTurmas) throws IOException{
        this.gravaTextoEmArquivo(dadosDeTurmas, ARQUIVO_TURMA);
    }

    public List<String> recuperaTextoDoArquivo(String nomeArquivo) throws IOException {
        BufferedReader leitor = null;
        List<String> textoLido = new ArrayList<String>();
        try {
            leitor = new BufferedReader (new FileReader(nomeArquivo));
            String texto = null;
            do {
                texto = leitor.readLine();
                if (texto!= null){
                    textoLido.add(texto);
                }
            } while (texto!=null);


        } finally {
            if (leitor!=null)
                leitor.close();
        }
        return textoLido;
    }

    public void gravaTextoEmArquivo (List <String> texto, String nomeArquivo) throws IOException{
        BufferedWriter gravador = null;
        try {
            gravador = new BufferedWriter (new FileWriter(nomeArquivo));
            for (String s : texto){
                gravador.write(s+"\n");
            }

        } finally {
            if(gravador != null){
                gravador.close();
            }
        }
    }
}
