// AVLTree.java
public class AVLTree {
    private Node root;

    private class Node {
        int value;
        Node left, right;
        int height;

        Node(int value) {
            this.value = value;
            this.height = 1;
        }
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int balanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = balanceFactor(node);

        if (balance > 1 && value < node.left.value) {
            return rightRotate(node);
        }

        if (balance < -1 && value > node.right.value) {
            return leftRotate(node);
        }

        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void remove(int value) {
        root = remove(root, value);
    }

    private Node remove(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = remove(node.left, value);
        } else if (value > node.value) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            Node temp = minValueNode(node.right);
            node.value = temp.value;
            node.right = remove(node.right, temp.value);
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = balanceFactor(node);

        if (balance > 1 && balanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        if (balance > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && balanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        if (balance < -1 && balanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public boolean search(int value) {
        return search(root, value) != null;
    }

    private Node search(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            return search(node.left, value);
        } else if (value > node.value) {
            return search(node.right, value);
        } else {
            return node;
        }
    }
}
