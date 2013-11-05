package trie.fifteenpuzzle.eit.eu;

public class Trie{

	private Node root;
	
	
    public Trie()
    {
    	root = new Node(null, false);
    }

    public Node getRootNode() {
    	return root;
    }

	//Returns 
	//	null if it found and fullword
    //	a node if it found and not fullword
	//	if a branch ends it starts from the root again
    public Node next (Node start, char c) throws CycleFoundException
    {
    	Node curNode = start;
        switch (c) {
	    	case 'd': 
	    		curNode = curNode.down;
	    		break;
	    	case 'l': 
	    		curNode = curNode.left;
	    		break;
	    	case 'r': 
	    		curNode = curNode.right;
	    		break;
	    	case 'u': 
	    		curNode = curNode.up;
	    		break;
		}
        
        if (curNode.fullWord)
            throw new CycleFoundException();
        
        return curNode;
    }

	/*
	1. Set current node to root node. The root node does not contain any letter 
	(initialized to the null character for convenience).
	2. Set the current letter to the first letter in the word. 
	3. If the current node already has an existing reference to the current letter 
	(through one of the elements in the "links" field) then set current node to that referenced node; 
	else create a new node, set the letter to current letter, and set current node to this new node.
	4. Repeat step 3 until all letters in the current word has been processed.
	 */
	public void add(String word) {
        int l = word.length();
        Node curNode = root;
        char c;
        
        for (int i = 0; i < l; i++) {
        	c = word.charAt(i);
            switch (c) {
    	    	case 'd': 
    	    		if (curNode.down == root || curNode.down == null)
    	    			curNode.down = new Node(root, i == l-1 ? true : false);
    	    		curNode = curNode.down;
    	    		break;
    	    	case 'l': 
    	    		if (curNode.left == root || curNode.left == null)
    	    			curNode.left = new Node(root, i == l-1 ? true : false);
    	    		curNode = curNode.left;
    	    		break;
    	    	case 'r': 
    	    		if (curNode.right == root || curNode.down == null)
    	    			curNode.right = new Node(root, i == l-1 ? true : false);
    	    		curNode = curNode.right;
    	    		break;
    	    	case 'u': 
    	    		if (curNode.up == root || curNode.down == null)
    	    			curNode.up = new Node(root, i == l-1 ? true : false);
    	    		curNode = curNode.up;
    	    		break;
    		}
        }
		
	}

}
