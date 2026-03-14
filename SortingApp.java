import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SortingApp {

    static int[] selectionSort(int[] arr) {
        int[] a = arr.clone();
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++)
                if (a[j] < a[minIdx]) minIdx = j;
            int tmp = a[minIdx]; a[minIdx] = a[i]; a[i] = tmp;
        }
        return a;
    }

    static int[] bubbleSort(int[] arr) {
        int[] a = arr.clone();
        int n = a.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (a[j] > a[j + 1]) {
                    int tmp = a[j]; a[j] = a[j + 1]; a[j + 1] = tmp;
                }
        return a;
    }

    static int[] insertionSort(int[] arr) {
        int[] a = arr.clone();
        int n = a.length;
        for (int i = 1; i < n; i++) {
            int key = a[i], j = i - 1;
            while (j >= 0 && a[j] > key) { a[j + 1] = a[j]; j--; }
            a[j + 1] = key;
        }
        return a;
    }

    static int[] mergeSort(int[] arr) {
        int[] a = arr.clone();
        mergeSortHelper(a, 0, a.length - 1);
        return a;
    }
    static void mergeSortHelper(int[] a, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSortHelper(a, l, m);
            mergeSortHelper(a, m + 1, r);
            merge(a, l, m, r);
        }
    }
    static void merge(int[] a, int l, int m, int r) {
        int[] L = Arrays.copyOfRange(a, l, m + 1);
        int[] R = Arrays.copyOfRange(a, m + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < L.length && j < R.length)
            a[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < L.length) a[k++] = L[i++];
        while (j < R.length) a[k++] = R[j++];
    }

    static int[] quickSort(int[] arr) {
        int[] a = arr.clone();
        quickSortHelper(a, 0, a.length - 1);
        return a;
    }
    static void quickSortHelper(int[] a, int low, int high) {
        if (low < high) {
            int p = partition(a, low, high);
            quickSortHelper(a, low, p - 1);
            quickSortHelper(a, p + 1, high);
        }
    }
    static int partition(int[] a, int low, int high) {
        int pivot = a[high], i = low - 1;
        for (int j = low; j < high; j++)
            if (a[j] <= pivot) { i++; int t = a[i]; a[i] = a[j]; a[j] = t; }
        int t = a[i + 1]; a[i + 1] = a[high]; a[high] = t;
        return i + 1;
    }

    static int[] heapSort(int[] arr) {
        int[] a = arr.clone();
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(a, n, i);
        for (int i = n - 1; i > 0; i--) {
            int t = a[0]; a[0] = a[i]; a[i] = t;
            heapify(a, i, 0);
        }
        return a;
    }
    static void heapify(int[] a, int n, int i) {
        int largest = i, l = 2 * i + 1, r = 2 * i + 2;
        if (l < n && a[l] > a[largest]) largest = l;
        if (r < n && a[r] > a[largest]) largest = r;
        if (largest != i) {
            int t = a[i]; a[i] = a[largest]; a[largest] = t;
            heapify(a, n, largest);
        }
    }

    // ─────────────────────────────────────────────
    //  7. COUNTING SORT – Conteo
    // ─────────────────────────────────────────────
    static int[] countingSort(int[] arr) {
        int[] a = arr.clone();
        int max = Arrays.stream(a).max().getAsInt();
        int min = Arrays.stream(a).min().getAsInt();
        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[a.length];
        for (int v : a) count[v - min]++;
        for (int i = 1; i < range; i++) count[i] += count[i - 1];
        for (int i = a.length - 1; i >= 0; i--) {
            output[--count[a[i] - min]] = a[i];
        }
        return output;
    }

    static int[] radixSort(int[] arr) {
        int[] a = arr.clone();
        int max = Arrays.stream(a).max().getAsInt();
        for (int exp = 1; max / exp > 0; exp *= 10)
            countByDigit(a, exp);
        return a;
    }
    static void countByDigit(int[] a, int exp) {
        int n = a.length;
        int[] output = new int[n], count = new int[10];
        for (int v : a) count[(v / exp) % 10]++;
        for (int i = 1; i < 10; i++) count[i] += count[i - 1];
        for (int i = n - 1; i >= 0; i--) {
            int d = (a[i] / exp) % 10;
            output[--count[d]] = a[i];
        }
        System.arraycopy(output, 0, a, 0, n);
    }

    @SuppressWarnings("unchecked")
    static int[] bucketSort(int[] arr) {
        int[] a = arr.clone();
        int n = a.length;
        if (n == 0) return a;
        int max = Arrays.stream(a).max().getAsInt();
        int min = Arrays.stream(a).min().getAsInt();
        int bucketCount = n;
        java.util.List<Integer>[] buckets = new java.util.ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) buckets[i] = new java.util.ArrayList<>();
        double range = (double)(max - min + 1) / bucketCount;
        for (int v : a) {
            int idx = (int)((v - min) / range);
            if (idx >= bucketCount) idx = bucketCount - 1;
            buckets[idx].add(v);
        }
        int k = 0;
        for (java.util.List<Integer> bucket : buckets) {
            bucket.sort(null);
            for (int v : bucket) a[k++] = v;
        }
        return a;
    }

    // ─────────────────────────────────────────────
    //  UTILIDADES
    // ─────────────────────────────────────────────
    static void printArray(int[] arr) {
        System.out.print("  [ ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println(" ]");
    }

    static int[] generateRandom(int size, int bound) {
        Random rnd = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = rnd.nextInt(bound) + 1;
        return arr;
    }

    static int[] readManual(Scanner sc) {
        System.out.print("  Ingresa los números separados por espacios: ");
        String line = sc.nextLine().trim();
        String[] parts = line.split("\\s+");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) arr[i] = Integer.parseInt(parts[i]);
        return arr;
    }

    // ─────────────────────────────────────────────
    //  manu principal;
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║      APLICACIÓN DE MÉTODOS DE ORDENAMIENTO      ║");
        System.out.println("╚═══════════════════════════════════════════════╝");

        while (running) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│       SELECCIONA UN MÉTODO          │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. Selection Sort  (Selección)     │");
            System.out.println("│  2. Bubble Sort     (Burbuja)       │");
            System.out.println("│  3. Insertion Sort  (Inserción)     │");
            System.out.println("│  4. Merge Sort      (Combinación)   │");
            System.out.println("│  5. Quick Sort      (Rápida)        │");
            System.out.println("│  6. Heap Sort       (Montón)        │");
            System.out.println("│  7. Counting Sort   (Conteo)        │");
            System.out.println("│  8. Radix Sort      (Raíz)          │");
            System.out.println("│  9. Bucket Sort     (Cubo)          │");
            System.out.println("│  0. Salir                           │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("  Opción: ");

            int option;
            try { option = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("  ⚠ Opción no válida."); continue; }

            if (option == 0) { System.out.println("\n  ¡Hasta luego! \n"); break; }
            if (option < 1 || option > 9) { System.out.println("  ⚠ Opción no válida."); continue; }

            // ── Elegir fuente del arreglo ──
            System.out.println("\n  ¿Cómo quieres ingresar los datos?");
            System.out.println("  1. Generar arreglo aleatorio");
            System.out.println("  2. Ingresar manualmente");
            System.out.print("  Opción: ");

            int dataOpt;
            try { dataOpt = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("  ⚠ Opción no válida."); continue; }

            int[] original;
            if (dataOpt == 1) {
                System.out.print("  Tamaño del arreglo: ");
                int size;
                try { size = Integer.parseInt(sc.nextLine().trim()); }
                catch (NumberFormatException e) { System.out.println("  ⚠ Número no válido."); continue; }
                original = generateRandom(size, 100);
            } else if (dataOpt == 2) {
                original = readManual(sc);
            } else {
                System.out.println("  ⚠ Opción no válida."); continue;
            }

            String[] names = {
                "Selection Sort", "Bubble Sort", "Insertion Sort",
                "Merge Sort",     "Quick Sort",  "Heap Sort",
                "Counting Sort",  "Radix Sort",  "Bucket Sort"
            };

            System.out.println("\n  ──────────────────────────────────");
            System.out.println("  Método : " + names[option - 1]);
            System.out.print("  Original:");
            printArray(original);

            long start = System.nanoTime();
            int[] sorted = switch (option) {
                case 1 -> selectionSort(original);
                case 2 -> bubbleSort(original);
                case 3 -> insertionSort(original);
                case 4 -> mergeSort(original);
                case 5 -> quickSort(original);
                case 6 -> heapSort(original);
                case 7 -> countingSort(original);
                case 8 -> radixSort(original);
                case 9 -> bucketSort(original);
                default -> original;
            };
            long elapsed = System.nanoTime() - start;

            System.out.print("  Ordenado:");
            printArray(sorted);
            System.out.printf("  Tiempo   : %.4f ms%n", elapsed / 1_000_000.0);
            System.out.println("  ──────────────────────────────────");
        }

        sc.close();
    }
}