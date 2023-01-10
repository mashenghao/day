package graph.store;

import graph.Triple;
import graph.struct.Edge;
import graph.struct.Graph;
import graph.struct.Node;

/**
 * 十字链表存储图(有向图)
 * 十字链表边 可以获取到起点与终点， 从一个节点触发，能够获取到所有的出度与入度。
 *
 * @author mahao
 * @date 2023/01/05
 */
public class Orthogonal {

    public static void main(String[] args) {
        Graph of = of(new Triple<>(1, "e1", 2),
                new Triple<>(1, "e2", 3),
                new Triple<>(1, "e3", 4),
                new Triple<>(1, "e4", 5),
                new Triple<>(6, "e5", 1),
                new Triple<>(7, "e6", 1)
        );
        of.printBfs();
    }

    public static Graph ofEmpty() {
        return new Graph();
    }

    public static Graph of(Triple<Integer, String, Integer>... edges) {
        Graph g = new Graph();
        for (Triple<Integer, String, Integer> edge : edges) {
            g.addNode(edge.a);
            g.addNode(edge.c);
        }
        for (Triple<Integer, String, Integer> edge : edges) {
            g.addEdge(edge.a, edge.c, edge.b);
        }
        return g;
    }


}
