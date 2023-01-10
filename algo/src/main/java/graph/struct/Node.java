package graph.struct;

import java.util.Objects;

/**
 * @author mahao
 * @date 2023/01/08
 */
public class Node {

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

    //添加一个入边，头插法
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
