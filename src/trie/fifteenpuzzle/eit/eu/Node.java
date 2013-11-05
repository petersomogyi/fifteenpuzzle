package trie.fifteenpuzzle.eit.eu;

public class Node {
	Node left;
	Node right;
	Node up;
	Node down;
    boolean fullWord;
    
    public Node(Node root, boolean fullWord) {
        left = right = up = down = root;
        this.fullWord = fullWord;
    }
}
