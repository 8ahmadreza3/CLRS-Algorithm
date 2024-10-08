package S06_GraphAlgorithms.Chapter22;

import java.util.*;

public class Graph {
    int V;
    static int WHITE = 0, GRAY = 1, BLACK = 2;
    List<Integer>[] adjList;
    String[] color;
    int[] d, p;

    public Graph(int v) {
        V = v;
        adjList = new LinkedList[v];
        for (int i = 0; i < v; i++)
            adjList[i] = new LinkedList<Integer>();
    }

    void addEdge(int u, int v) {
        adjList[u].add(v);
    }

    void BFSSingleSource(int s) {
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        d[s] = 0;
        color[s] = "green";
        while (!q.isEmpty()) {
            int u = q.poll();
            System.out.print(u + " ");
            for (Object index : adjList[u]) {
                int i = (int) index ;
                if (Objects.equals(color[i], "white")) {
                    color[i] = "green";
                    d[i] = d[u] + 1;
                    p[i] = u;
                    q.add(i);
                }
            }
            color[u] = "dark_green";
        }
        System.out.println();
    }

    void BFSFull(int n) {
        color = new String[n];
        d = new int[n];
        p = new int[n];
        Arrays.fill(color, "white");
        Arrays.fill(d, 0);
        Arrays.fill(p, -1);
        for (int i = 0; i < n; i++) {
            if (color[i] == "white")
                BFSSingleSource(i);
        }
    }

    boolean DFSUtil(int u, int[] color) {
        color[u] = GRAY;
        for (Integer in : adjList[u]) {
            if (color[in] == GRAY)
                return true;

            if (color[in] == WHITE && DFSUtil(in, color))
                return true;
        }

        color[u] = BLACK;
        return false;
    }

    boolean isCyclic(Graph g) {
        int[] color = new int[g.V];
        for (int i = 0; i < g.V; i++) {
            color[i] = WHITE;
        }
        for (int i = 0; i < g.V; i++) {
            if (color[i] == WHITE)
                if(DFSUtil(i, color))
                    return true;
        }
        return false;
    }

    void DFSRec(boolean[] visited, int s){
        visited[s] = true;
        System.out.print(s + " ");

        for (int i : adjList[s]) {
            if (!visited[i])
                DFSRec(visited, i);
        }
    }

    void DFS(int s) {
        boolean[] visited = new boolean[adjList.length];
        DFSRec(visited, s);
    }

    void topologicalSortUtil(boolean[] visited, Stack<Integer> stack) {
        visited[V] = true;

        for (int i : adjList[V]) {
            if (!visited[i])
                topologicalSortUtil(visited, stack);
        }
        stack.push(V);
    }

    void topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (!visited[i])
                topologicalSortUtil(visited, stack);
        }
        while (!stack.empty())
            System.out.print(stack.pop() + " ");

    }

    boolean dfs(int curr, int des, List<Integer> vis) {
        if (curr == des)
            return true;

        vis.set(curr, 1);
        for (int x : adjList[curr])
            if (vis.get(x) == 0)
                if (dfs(x, des, vis))
                    return true;

        return false;
    }

    boolean isPath(int src, int des) {
        List<Integer> vis = new ArrayList<>(adjList.length + 1);
        for (int i = 0; i <= adjList.length; i++) {
            vis.add(0);
        }
        return dfs(src, des, vis);
    }

    List<List<Integer>> findSCC(int n, List<List<Integer>> a) {
        List<List<Integer>> ans = new ArrayList<>();

        List<Integer> is_scc = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            is_scc.add(0);
        }

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (List<Integer> edge : a)
            adj.get(edge.get(0)).add(edge.get(1));

        for (int i = 1; i <= n; i++) {
            if (is_scc.get(i) == 0) {
                List<Integer> scc = new ArrayList<>();
                scc.add(i);
                for (int j = i + 1; j <= n; j++) {
                    if (is_scc.get(j) == 0 && isPath(i, j) && isPath(j, i)) {
                        is_scc.set(j, 1);
                        scc.add(j);
                    }
                }
                ans.add(scc);
            }
        }
        return ans;
    }
}

