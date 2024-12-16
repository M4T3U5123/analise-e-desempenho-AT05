import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Leitura do arquivo de entrada
        List<Integer> numbers = readFile("arq.txt");

        // Criação dos arrays para os algoritmos
        int[] bubbleArray = numbers.stream().mapToInt(Integer::intValue).toArray();
        int[] mergeArray = numbers.stream().mapToInt(Integer::intValue).toArray();

        // Preparação dos arquivos de saída
        BufferedWriter outputBubble = new BufferedWriter(new FileWriter("bubble_output.txt"));
        BufferedWriter outputMerge = new BufferedWriter(new FileWriter("merge_output.txt"));
        
        // Abrir o arquivo de log em modo append para evitar sobrescrever
        BufferedWriter timeMemoryLog = new BufferedWriter(new FileWriter("time_memory_log.txt", true)); // Modo append

        // Medir a memória disponível antes da execução
        long startMemoryBefore = getFreeMemory();

        // Bubble Sort
        long startTime = System.nanoTime();
        BubbleSort.sort(bubbleArray);
        long endTime = System.nanoTime();

        // Medir a memória disponível após a execução do Bubble Sort
        long endMemoryAfterBubbleSort = getFreeMemory();

        // Gravar resultados do Bubble Sort
        for (int num : bubbleArray) {
            outputBubble.write(num + "\n");
        }
        outputBubble.close();

        // Log do tempo e memória do Bubble Sort
        logTimeMemory(timeMemoryLog, "BubbleSort", startTime, endTime, startMemoryBefore, endMemoryAfterBubbleSort);
        timeMemoryLog.newLine(); // Adicionar uma linha em branco após o BubbleSort

        // Merge Sort
        startTime = System.nanoTime();
        MergeSort.sort(mergeArray);
        endTime = System.nanoTime();

        // Medir a memória após a execução do MergeSort
        long endMemoryAfterMergeSort = getFreeMemory();

        // Gravar resultados do Merge Sort
        for (int num : mergeArray) {
            outputMerge.write(num + "\n");
        }
        outputMerge.close();

        // Log do tempo e memória do Merge Sort
        logTimeMemory(timeMemoryLog, "MergeSort", startTime, endTime, endMemoryAfterBubbleSort, endMemoryAfterMergeSort);
        timeMemoryLog.newLine(); // Adicionar uma linha em branco após o MergeSort

        timeMemoryLog.close();
    }

    // Função para ler os números do arquivo de entrada
    private static List<Integer> readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<Integer> numbers = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();  // Remover espaços extras
            if (!line.isEmpty()) {  // Ignorar linhas vazias
                try {
                    numbers.add(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    System.out.println("Linha ignorada (não é um número): " + line);
                }
            }
        }
        br.close();
        return numbers;
    }

    // Função para calcular o uso de memória (usando memória livre)
    private static long getFreeMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.freeMemory(); // Medir memória livre
    }

    // Função para registrar tempo e uso de memória
    private static void logTimeMemory(BufferedWriter logFile, String algorithm, long startTime, long endTime, long startMemory, long endMemory) throws IOException {
        long elapsedTime = (endTime - startTime) / 1000000;  // Convertendo de ns para ms
        long memoryUsed = (startMemory - endMemory) / 1024; // Convertendo de bytes para kbytes

        if (memoryUsed < 0) {
            memoryUsed = 0; // Garantir que não seja negativo
        }

        logFile.write(algorithm + " - Tempo: " + elapsedTime + " ms, Memória: " + memoryUsed + " KB\n");
    }
}
