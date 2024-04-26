import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GravarTurmas {

  public static final String ARQUIVO_TURMAS = "turmas.txt";

  public List<String> recuperaDadosDeTurma () throws IOException {
    List<String> dadosTurma = recuperaTextoEmArquivo (ARQUIVO_TURMAS);
    return dadosTurma;
  }


  
  public List<String> recuperaTextoEmArquivo (String nomeArquivo) throws IOException{
    BufferedReader = null;
    List<String> textoLido = new ArrayList<>();
    try {
      leitor = new BufferedReader (new FileReader (nomeArquivo));
      String texto = null;
      do {
        texto = readLine;
        if(texto != null){
          textoLido.add(texto);
        }
        
      } while (texto != null);
      
    } finally {
      if (leitor != null){
        leitor.close();
      }
    }
    return textoLido;
  }


  public void gravarTextoEmArquivo (List<String> texto, String nomeArquivo) throws IOException{
    BufferedWriter gravador = null;
    try {
      gravador = new BufferedWriter (new FileWriter (nomeArquivo));
      for (String s:texto){
        gravador.writer(s+"\n");
      }
    } finally {
      if (gravador != null){
        gravador.close();
      }
    }
  }
}
