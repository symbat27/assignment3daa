package MSTOptimization;

import java.util.*;

public class KruskalMST {
    static class DSU {
        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> rank = new HashMap<>();

        DSU(List<String> nodes) {
            for (String n : nodes) {
                parent.put(n, n);
                rank.put(n, 0);
            }
        }

        String find(String x) {
            if (!parent.get(x).equals(x))
                parent.put(x, find(parent.get(x)));
            return parent.get(x);
        }

        void union(String a, String b) {
            String pa = find(a), pb = find(b);
            if (pa.equals(pb)) return;
            if (rank.get(pa) < rank.get(pb)) parent.put(pa, pb);
            else if (rank.get(pa) > rank.get(pb)) parent.put(pb, pa);
            else {
                parent.put(pb, pa);
                rank.put(pa, rank.get(pa) + 1);
            }
        }
    }

    public static Map<String, Object> runKruskal(Graph graph) {
        long start = System.nanoTime();
        int operations = 0;

        List<Edge> edges = new ArrayList<>(graph.edges);
        Collections.sort(edges);
        DSU dsu = new DSU(graph.nodes);
        List<Edge> mst = new ArrayList<>();
        int totalCost = 0;

        for (Edge e : edges) {
            operations++;
            String u = dsu.find(e.from);
            String v = dsu.find(e.to);
            if (!u.equals(v)) {
                dsu.union(u, v);
                mst.add(e);
                totalCost += e.weight;
            }
        }

        double timeMs = (System.nanoTime() - start) / 1e6;
        Map<String, Object> result = new HashMap<>();
        result.put("mst_edges", mst);
        result.put("total_cost", totalCost);
        result.put("operations_count", operations);
        result.put("execution_time_ms", timeMs);
        return result;
    }
}






