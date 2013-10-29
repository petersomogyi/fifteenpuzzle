package trie.fifteenpuzzle.eit.eu;

public class Trie{

	public TrieNode root;
	
	public class TrieNode {
	    //TrieNode[] links;
		TrieNode left;
		TrieNode right;
		TrieNode up;
		TrieNode down;
	    boolean fullWord;
	    
	    public TrieNode(TrieNode root, boolean fullWord) {
	        left = right = up = down = root;
	        this.fullWord = fullWord;
	    }
	}
	
    public Trie()
    {
    	root = new TrieNode(null, false);
    }


	//Returns 
	//	null if it found and fullword
    //	a node if it found and not fullword
	//	if a branch ends it starts from the root again
    public TrieNode nextNode(TrieNode start, char c)
    {
    	TrieNode curNode = start;
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
            return null;
        
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
	public void insertWord(String word) {
        int l = word.length();
        TrieNode curNode = root;
        char c;
        
        for (int i = 0; i < l; i++) {
        	c = word.charAt(i);
            switch (c) {
    	    	case 'd': 
    	    		if (curNode.down == root || curNode.down == null)
    	    			curNode.down = new TrieNode(root, i == l-1 ? true : false);
    	    		curNode = curNode.down;
    	    		break;
    	    	case 'l': 
    	    		if (curNode.left == root || curNode.left == null)
    	    			curNode.left = new TrieNode(root, i == l-1 ? true : false);
    	    		curNode = curNode.left;
    	    		break;
    	    	case 'r': 
    	    		if (curNode.right == root || curNode.down == null)
    	    			curNode.right = new TrieNode(root, i == l-1 ? true : false);
    	    		curNode = curNode.right;
    	    		break;
    	    	case 'u': 
    	    		if (curNode.up == root || curNode.down == null)
    	    			curNode.up = new TrieNode(root, i == l-1 ? true : false);
    	    		curNode = curNode.up;
    	    		break;
    		}
        }
		
	}

}
