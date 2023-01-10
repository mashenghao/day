package graph.struct;

import java.util.*;

/**
 * 用十字链表结构存储图模型
 *
 * @author mahao
 * @date 2023/01/08
 */
public class Graph {

    private Map<Integer, Node> nodeMap = new TreeMap<>(Comparator.naturalOrder());

    public void addNode(int id) {
        nodeMap.put(id, new Node(id));
    }

    public void addEdge(int head, int tail, Object edgeId) {
        Node headNode = nodeMap.get(head);
        Node tailNode = nodeMap.get(tail);
        if (headNode == null || tailNode == null) {
            throw new NullPointerException("边的点不能为空");
        }
        Edge edge = new Edge(headNode, tailNode, edgeId);
        headNode.addOutEdge(edge);
        tailNode.addInEdge(edge);
    }

    public void printGraph() {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodeMap.values()) {
            //"v(1)-[e1]->v(2)" 出度边
            Edge p = node.firstOut;
            while (p != null) {
                sb.append(String.format("v(%s)-[%s]->v(%s)", p.head.data, p.data, p.tail.data)).append("\n");
                p = p.tlink;
            }
            //"v(1)-[e1]->v(2)" 入度边
            p = node.firstIn;
            while (p != null) {
                sb.append(String.format("v(%s)<-[%s]-v(%s)", p.tail.data, p.data, p.head.data)).append("\n");
                p = p.hLink;
            }
        }
        System.out.println(sb);
    }


    StringBuilder sb = new StringBuilder();

    /**
     * 使用深度遍历这个图结构
     */
    public void printDfsGraph() {

        Set<Node> visited = new HashSet<>(this.nodeMap.size());
        for (Node node : nodeMap.values()) {
            if (!visited.contains(node)) {
                visited.add(node);
                System.out.println("访问点" + node.data);
                dfs(node, visited);
                sb.append("\n");
            }
        }
    }

    private void dfs(Node node, Set<Node> visited) {
        //v(1)-[e1]->v(2)-[e2]->v(3);
        Edge p = node.firstOut;
        while (p != null) {
            Node tail = p.tail;
            if (!visited.contains(tail)) {
                System.out.println("访问点" + tail.data);
                visited.add(tail);
                dfs(tail, visited);
            }
            p = p.tlink;
        }
    }

    /**
     * 广度优先遍历
     */
    public void printBfs() {
        Set<Node> visited = new HashSet<>(this.nodeMap.size());
        Queue<Node> queue = new LinkedList<>();
        for (Node node : nodeMap.values()) {
            if (!visited.contains(node)) {

                //当前元素进队列
                queue.offer(node);
                while (!queue.isEmpty()) {
                    Node poll = queue.poll();
                    System.out.println("访问节点：" + poll.data);
                    visited.add(poll);

                    //访问完成后，将其一度点添加到队列中
                    Edge p = poll.firstOut;
                    while (p != null) {
                        if (!visited.contains(p.tail))
                            queue.offer(p.tail);
                        p = p.tlink;
                    }
                    p = poll.firstIn;
                    while (p != null) {
                        if (!visited.contains(p.head))
                            queue.offer(p.head);
                        p = p.hLink;
                    }
                }
            }
        }
    }

}
