
// FileUtil.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static int[] readArrayFromFile(String fileName) {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Remove caracteres não numéricos, mantendo apenas os números e os espaços
                String[] numbers = line.replaceAll("[^0-9\\-\\s]", "").split("\\s+");
                for (String numStr : numbers) {
                    if (!numStr.isEmpty()) {
                        try {
                            list.add(Integer.parseInt(numStr));
                        } catch (NumberFormatException e) {
                            // Ignora números mal formatados
                            System.err.println("Número inválido: " + numStr);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Converte a lista para um array
        int[] array = list.stream().mapToInt(i -> i).toArray();
        return array;
    }
}
