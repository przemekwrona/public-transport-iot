package pl.wrona.iot.formatter.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WarsawTree {
    @Getter
    private Node node;

    private boolean isCalendarSection = false;

    public void add(String line) {
        int deep = line.indexOf(line.trim());

        if (line.trim().startsWith("*KD")) {
            this.isCalendarSection = true;
        } else if (line.trim().startsWith("#KD")) {
            this.isCalendarSection = false;
        }

        if (line.trim().startsWith("#")) {
            return;
        }

        if (Objects.isNull(node)) {
            node = new Node(-99, "");
            node.add(deep, line);
        } else {
            node.add(deep, line.trim());
        }
    }

    @Data
    @AllArgsConstructor
    public class Node {
        private int deep;
        private String value;
        private LinkedList<Node> nodes;
        private Node parent;

        public Node(int deep, String value) {
            this.deep = deep;
            this.value = value;
            this.nodes = new LinkedList<>();
        }

        public void add(int deep, String value) {
            if (isCalendarSection) {
                if (nodes != null
                        && !nodes.isEmpty()
                        && nodes.getLast().getDeep() == deep + 1) {
                    deep++;
                }
                if (nodes != null
                        && !nodes.isEmpty()
                        && nodes.getLast().getDeep() == deep + 2) {
                    deep++;
                    deep++;
                }
            }
            if (this.deep == deep) {
                Node node = new Node(deep, value);
                node.setParent(this);
                this.nodes.addLast(node);
            } else if (this.deep < deep) {
                if (this.nodes.isEmpty()) {
                    Node node = new Node(deep, value);
                    node.setParent(this);
                    this.nodes.addLast(node);
                } else {
                    Node lastNode = this.nodes.getLast();
                    if (lastNode.getDeep() == deep) {
                        Node newNode = new Node(deep, value);
                        node.setParent(lastNode.getParent());
                        this.nodes.addLast(newNode);
                    } else {
                        lastNode.add(deep, value);
                    }
                }
            }
        }

        public Node getNode(String prefix) {
            for (Node node : nodes) {
                if (node.getValue().startsWith(prefix)) {
                    return node;
                }
            }
            return null;
        }

        public List<Node> getNodes(String prefix) {
            WarsawTree.Node node = getNode(prefix);
            return Objects.nonNull(node) ? node.getNodes() : List.of();
        }

        public Node first() {
            return nodes.getFirst();
        }
    }
}
