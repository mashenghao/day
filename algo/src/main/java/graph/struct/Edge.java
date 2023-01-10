package graph.struct;

import java.util.Objects;

/**
 * @author mahao
 * @date 2023/01/08
 */
public class Edge {

    public Object data;

    //边的始点
    public Node head;

    public Edge hLink;

    //边的终点
    public Node tail;
    //边的下一个
    public Edge tlink;

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
