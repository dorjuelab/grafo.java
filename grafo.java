import java.util.ArrayDeque;
import java.util.Queue;

public class Graph {

    // Guarda la etiqueta de un vértice y si ya fue visitado
    // durante un recorrido DFS o BFS.
    private static class Vertex {
        char label;
        boolean visited;

        Vertex(char label) {
            this.label = label;
            this.visited = false;
        }
    }

    // Almacena los vértices agregados al grafo.
    private final Vertex[] vertexList;

    // Matriz de adyacencia:
    // adjMat[i][j] == 1 significa que hay una arista entre i y j.
    // adjMat[i][j] == 0 significa que no hay una arista.
    private final int[][] adjMat;

    // Cantidad de vértices que se han agregado.
    private int nVerts;

    // Prepara espacio para un máximo de maxVertices vértices.
    public Graph(int maxVertices) {
        vertexList = new Vertex[maxVertices];
        adjMat = new int[maxVertices][maxVertices];
        nVerts = 0;
    }

    // Agrega un vértice y devuelve su índice.
    // Por ejemplo, el primer vértice agregado tiene índice 0.
    public int addVertex(char label) {
        vertexList[nVerts] = new Vertex(label);
        return nVerts++;
    }

    // Conecta dos vértices.
    // Se asignan ambas posiciones porque el grafo no es dirigido:
    // si start conecta con end, end también conecta con start.
    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }

    // Recorrido en anchura (Breadth-First Search).
    // Visita primero los vecinos cercanos y luego avanza por niveles.
    public void bfs(int start) {
        // Permite ejecutar el recorrido aunque haya visitas anteriores.
        resetVisited();

        // La cola mantiene el orden de los vértices pendientes.
        Queue<Integer> queue = new ArrayDeque<>();

        // Marca el vértice inicial antes de agregarlo,
        // para no volver a encolarlo.
        vertexList[start].visited = true;
        queue.add(start);

        // Continúa mientras queden vértices pendientes.
        while (!queue.isEmpty()) {
            // Saca de la cola el siguiente vértice por visitar.
            int current = queue.remove();

            // Muestra la etiqueta del vértice actual.
            displayVertex(current);

            // Revisa todos los vértices para encontrar sus vecinos.
            for (int neighbor = 0; neighbor < nVerts; neighbor++) {
                // Comprueba si hay una arista de current a neighbor.
                boolean hasEdge = adjMat[current][neighbor] == 1;

                // Comprueba que neighbor aún no haya sido visitado.
                boolean notVisited = !vertexList[neighbor].visited;

                // Si es vecino y no se ha visitado, se marca y se encola.
                if (hasEdge && notVisited) {
                    vertexList[neighbor].visited = true;
                    queue.add(neighbor);
                }
            }
        }

        // Termina el recorrido y pasa a la línea siguiente.
        System.out.println();
    }

    // Recorrido en profundidad (Depth-First Search).
    // Sigue un camino tanto como puede antes de retroceder.
    public void dfs(int start) {
        resetVisited();
        dfsVisit(start);
        System.out.println();
    }

    // Visita recursivamente un vértice y sus vecinos no visitados.
    private void dfsVisit(int current) {
        // Marca el vértice para evitar ciclos o visitas repetidas.
        vertexList[current].visited = true;

        // Muestra el vértice al momento de visitarlo.
        displayVertex(current);

        // Busca todos los vecinos del vértice actual.
        for (int neighbor = 0; neighbor < nVerts; neighbor++) {
            boolean hasEdge = adjMat[current][neighbor] == 1;
            boolean notVisited = !vertexList[neighbor].visited;

            // Si neighbor está conectado y no se ha visitado,
            // empieza el recorrido en profundidad desde ese vecino.
            if (hasEdge && notVisited) {
                dfsVisit(neighbor);
            }
        }
    }

    // Imprime la etiqueta del vértice indicado por su índice.
    private void displayVertex(int vertex) {
        System.out.print(vertexList[vertex].label + " ");
    }

    // Limpia las marcas de visita antes de comenzar otro recorrido.
    private void resetVisited() {
        for (int i = 0; i < nVerts; i++) {
            vertexList[i].visited = false;
        }
    }

    public static void main(String[] args) {
        // Crea un grafo con capacidad para cinco vértices.
        Graph graph = new Graph(5);

        // Agrega vértices; cada llamada devuelve un índice:
        // A=0, B=1, C=2, D=3 y E=4.
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');

        // Agrega aristas entre los índices de los vértices.
        graph.addEdge(0, 1); // A-B
        graph.addEdge(0, 2); // A-C
        graph.addEdge(1, 3); // B-D
        graph.addEdge(2, 4); // C-E

        // Empieza ambos recorridos desde A, cuyo índice es 0.
        System.out.print("BFS: ");
        graph.bfs(0);

        System.out.print("DFS: ");
        graph.dfs(0);
    }
}