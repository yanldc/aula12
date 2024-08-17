// RedBlackTree.java
public class RedBlackTree {
    private Node root;

    private class Node {
        int value;
        Node left, right, parent;
        boolean isRed;

        Node(int value) {
            this.value = value;
            this.isRed = true;
        }
    }

    public void insert(int value) {
        root = insert(root, value);
        root.isRed = false;
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
            node.left.parent = node;
        } else if (value > node.value) {
            node.right = insert(node.right, value);
            node.right.parent = node;
        }

        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }

        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    private boolean isRed(Node node) {
        return node != null && node.isRed;
    }

    private Node rotateLeft(Node node) {
        Node x = node.right;
        node.right = x.left;
        if (x.left != null)
            x.left.parent = node;
        x.parent = node.parent;
        if (node.parent == null)
            root = x;
        else if (node == node.parent.left)
            node.parent.left = x;
        else
            node.parent.right = x;
        x.left = node;
        node.parent = x;
        x.isRed = node.isRed;
        node.isRed = true;
        return x;
    }

    private Node rotateRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        if (x.right != null)
            x.right.parent = node;
        x.parent = node.parent;
        if (node.parent == null)
            root = x;
        else if (node == node.parent.left)
            node.parent.left = x;
        else
            node.parent.right = x;
        x.right = node;
        node.parent = x;
        x.isRed = node.isRed;
        node.isRed = true;
        return x;
    }

    private void flipColors(Node node) {
        node.isRed = !node.isRed;
        if (node.left != null)
            node.left.isRed = !node.left.isRed;
        if (node.right != null)
            node.right.isRed = !node.right.isRed;
    }

    public void remove(int value) {
        if (search(value)) {
            root = remove(root, value);
            if (root != null)
                root.isRed = false;
        }
    }

    private Node remove(Node node, int value) {
        if (value < node.value) {
            if (node.left != null && !isRed(node.left) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }
            node.left = remove(node.left, value);
        } else {
            if (isRed(node.left)) {
                node = rotateRight(node);
            }
            if (value == node.value && (node.right == null)) {
                return null;
            }
            if (node.right != null && !isRed(node.right) && !isRed(node.right.left)) {
                node = moveRedRight(node);
            }
            if (value == node.value) {
                Node x = min(node.right);
                node.value = x.value;
                node.right = removeMin(node.right);
            } else {
                node.right = remove(node.right, value);
            }
        }
        return balance(node);
    }

    private Node moveRedLeft(Node node) {
        flipColors(node);
        if (node.right != null && isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    private Node moveRedRight(Node node) {
        flipColors(node);
        if (node.left != null && isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    private Node balance(Node node) {
        if (isRed(node.right)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    private Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            return null;
        }
        if (!isRed(node.left) && !isRed(node.left.left)) {
            node = moveRedLeft(node);
        }
        node.left = removeMin(node.left);
        return balance(node);
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
