package eu.eit.fifteenpuzzle.trie;


public class Trie{

	private Node root;
	
    public Trie()
    {
    	root = new Node(null, false, 0);
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
    	    			curNode.down = new Node(root, i == l-1 ? true : false, i);
    	    		curNode = curNode.down;
    	    		break;
    	    	case 'l': 
    	    		if (curNode.left == root || curNode.left == null)
    	    			curNode.left = new Node(root, i == l-1 ? true : false, i);
    	    		curNode = curNode.left;
    	    		break;
    	    	case 'r': 
    	    		if (curNode.right == root || curNode.right == null)
    	    			curNode.right = new Node(root, i == l-1 ? true : false, i);
    	    		curNode = curNode.right;
    	    		break;
    	    	case 'u': 
    	    		if (curNode.up == root || curNode.up == null)
    	    			curNode.up = new Node(root, i == l-1 ? true : false, i);
    	    		curNode = curNode.up;
    	    		break;
    		}
        }
	}
	
	public void setLinks(Node start) {
    	Node curr = start;
    		if (!curr.fullWord) {
				setLink(curr.left, root);
				setLink(curr.right, root);
				setLink(curr.down, root);
				setLink(curr.up, root);
				if (curr.depth < curr.left.depth)
					setLinks(curr.left);
				if (curr.depth < curr.right.depth)
					setLinks(curr.right);
				if (curr.depth < curr.down.depth)
					setLinks(curr.down);
				if (curr.depth < curr.up.depth)
					setLinks(curr.up);
    		}
    }
    
    public void setLink(Node curr, Node prev) {
    	if (!curr.fullWord) {
    		if (curr.left != root && prev.left != root && curr.depth < curr.left.depth){
    			setLink(curr.left,prev.left);
    		} else if (curr.left == root && prev.left != root){
    			curr.left = prev.left;
    		}
    		
    		if (curr.right != root && prev.right != root && curr.depth < curr.right.depth) {
    			setLink(curr.right,prev.right);
    		} else if (curr.right == root && prev.right != root){
    			curr.right = prev.right;
    		}
    		
    		if (curr.down != root && prev.down != root && curr.depth < curr.down.depth) {
    			setLink(curr.down,prev.down);
    		} else if (curr.down == root && prev.down != root){
    			curr.down = prev.down;
    		}
    		
    		if (curr.up != root && prev.up != root && curr.depth < curr.up.depth) {
    			setLink(curr.up,prev.up);
    		} else if (curr.up == root && prev.up != root){
    			curr.up = prev.up;
    		}
    			
    	}
    }

}
