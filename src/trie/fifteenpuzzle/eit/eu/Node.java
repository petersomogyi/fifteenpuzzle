package trie.fifteenpuzzle.eit.eu;

public class Node {
	Node left;
	Node right;
	Node up;
	Node down;
    boolean fullWord;
    int depth;
    
    public Node(Node root, boolean fullWord, int d) {
        left = right = up = down = root;
        this.fullWord = fullWord;
        depth = d;
    }
}
