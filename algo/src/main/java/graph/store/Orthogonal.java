package graph.store;

import java.util.*;

/**
 * 十字链表存储图
 * 十字链表边 可以获取到起点与终点， 从一个节点触发，能够获取到所有的出度与入度。
 *
 * @author mahao
 * @date 2023/01/05
 */
public class Orthogonal {

    public static void main(String[] args) {

    }

    static class Node {

        Object data;

        //出度边
        Edge firstOut;
        //入度边
        Edge firstIn;


        public Node(Object data) {
            this.data = data;
        }

        public void addOutEdge(Edge edge) {
            assert edge.head == this;
            if (firstOut == null) {
                firstOut = edge;
            } else {
                edge.tlink = firstOut;
                firstOut = edge;
            }
        }

        //添加一个入边
        public void addInEdge(Edge edge) {
            assert edge.tail == this;
            if (firstIn == null) {
                firstIn = edge;
            } else {
                edge.hLink = firstIn;
                firstIn = edge.hLink;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(data, node.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }

    static class Edge {

        Object data;

        //边的始点
        Node head;

        Edge hLink;

        //边的终点
        Node tail;
        //边的下一个
        Edge tlink;

        public Edge(Node head, Node tail, Object data) {
            this.data = data;
            this.head = head;
            this.tail = tail;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Objects.equals(data, edge.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }


    static class Graph {

        Map<Integer, Node> nodeMap = new TreeMap<>(Comparator.naturalOrder());

        public void addNode(int id) {
            nodeMap.put(id, new Node(id));
        }

        public void addEdge(int head, int tail, int edgeId) {
            Node headNode = nodeMap.get(head);
            Node tailNode = nodeMap.get(tail);
            Edge edge = new Edge(headNode, tailNode, edgeId);
            headNode.addOutEdge(edge);
            tailNode.addInEdge(edge);
        }
    }

}
