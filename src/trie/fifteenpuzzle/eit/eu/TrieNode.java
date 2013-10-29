package trie.fifteenpuzzle.eit.eu;

public class TrieNode {

	
	char letter;
    //TrieNode[] links;
	TrieNode left;
	TrieNode right;
	TrieNode up;
	TrieNode down;
    boolean fullWord;
    
    public TrieNode(char letter, boolean fullWord)
    {
        this.letter = letter;
        left = right = up = down = null;
        this.fullWord = fullWord;
    }
    
	static public TrieNode createTrie() {
		return(new TrieNode('\0', false));
	}

	//Returns 
	//	null if it found and fullword
    //	a node if it found and not fullword
	//	if a branch ends it starts from the root again
    static public TrieNode nextNode(TrieNode root, TrieNode start, char c)
    {
        TrieNode curNode = start == null ? root : start;
        
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
        if (curNode == null) {
            switch (c) {
    	    	case 'd': 
    	    		curNode = root.down;
    	    		break;
    	    	case 'l': 
    	    		curNode = root.left;
    	    		break;
    	    	case 'r': 
    	    		curNode = root.right;
    	    		break;
    	    	case 'u': 
    	    		curNode = root.up;
    	    		break;
    		}
        }
        
        if (curNode != null && curNode.fullWord)
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
	static public void insertWord(TrieNode root, String word) {
        int l = word.length();
        TrieNode curNode = root;
        char c;
        
        for (int i = 0; i < l; i++)
        {
        	c = word.charAt(i);
            switch (c) {
    	    	case 'd': 
    	    		if (curNode.down == null)
    	    			curNode.down = new TrieNode(c, i == l-1 ? true : false);
    	    			curNode = curNode.down;
    	    		break;
    	    	case 'l': 
    	    		if (curNode.left == null)
    	    			curNode.left = new TrieNode(c, i == l-1 ? true : false);
    	    			curNode = curNode.left;
    	    		break;
    	    	case 'r': 
    	    		if (curNode.right == null)
    	    			curNode.right = new TrieNode(c, i == l-1 ? true : false);
    	    			curNode = curNode.right;
    	    		break;
    	    	case 'u': 
    	    		if (curNode.up == null)
    	    			curNode.up = new TrieNode(c, i == l-1 ? true : false);
    	    			curNode = curNode.up;
    	    		break;
    		}
        }
		
	}
	

}
