import java.util.*;

/**
 * This BinarySearchTree object defines a reference based binary search tree
 * 
 * @author  
 * @version 
 */

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T>
{
    protected BinaryNode<T> root;      // reference to the root of this BST
    
    // Creates an empty Binary Search Tree object
    public BinarySearchTree()
    {
        root = null;
    }

    // Returns true if this BST is empty; otherwise, returns false.
    public boolean isEmpty()
    {
        return root == null;
    }

    // Returns the number of elements in this BST.
    public int size()
    {
        return size(root);
    }
    private int size(BinaryNode<T> root)
    {
        if (root == null) return 0;
        else return size(root.getLeft()) + size(root.getRight()) + 1;
    }

    // Adds element to this BST. The tree retains its BST property.
    public void add (T element)
    {
        root = add(element, root);
    }
    private BinaryNode<T> add(T element, BinaryNode<T> tree)
    {
        if (tree == null) tree = new BinaryNode(element);
        else if (element.compareTo(tree.getInfo()) <= 0) tree.setLeft(add(element, tree.getLeft()));
        else tree.setRight(add(element,tree.getRight()));
        return tree;
    }

    // Returns true if this BST contains an element e such that 
    // e.compareTo(element) == 0; otherwise, returns false.
    public boolean contains (T element)
    {
        BinaryNode<T> temp = root;
        while (temp != null)
        {
            if (element.compareTo(temp.getInfo()) == 0) return true;
            else if (element.compareTo(temp.getInfo()) < 0)
            {
                if (temp.getLeft() == null) break;
                else temp = temp.getLeft();
            }
            else
            {
                if (temp.getRight() == null) break;
                else temp = temp.getRight();
            }
        }
        return false;
    }

    // Returns a graphical representation of the tree
    public String toString()
    {
        return toString(root, 0);
    }

    private String toString(BinaryNode<T> tree, int level)
    {
        String str = "";
        if (tree != null)
        {
            str += toString(tree.getRight(), level + 1);
            for (int i = 1; i <= level; ++i)
                str = str + "| ";
            str += tree.getInfo().toString() + "\n";
            str += toString(tree.getLeft(), level + 1);
        }
        return str;
    }

    // Returns a list of elements from a preorder traversal
    public List<T> preorderTraverse()
    {
        List<T> list = new ArrayList<T>();
        BinaryNode<T> temp = root;
        list = preorderTraversal(root, list);
        return list;
    }
    private List<T> preorderTraversal(BinaryNode<T> node, List<T> list)
    {
        list.add(node.getInfo());
        if (node.getLeft() != null) preorderTraversal(node.getLeft(),list);
        if (node.getRight() != null) preorderTraversal(node.getRight(), list);
        return list;
    }
    
    // Returns a list of elements from a inorder traversal
    public List<T> inorderTraverse()
    {
        List<T> list = new ArrayList<T>();
        list = inorderTraversal(root, list);
        return list;
    }
    private List<T> inorderTraversal(BinaryNode<T> node, List<T> list)
    {
        if (node.getLeft() != null) inorderTraversal(node.getLeft(),list);
        list.add(node.getInfo());
        if (node.getRight() != null) inorderTraversal(node.getRight(), list);
        return list;
    }
    
    // Returns a list of elements from a postorder traversal
    public List<T> postorderTraverse()
    {
        List<T> list = new ArrayList<T>();
        list = postorderTraversal(root, list);
        return list;
    }
    private List<T> postorderTraversal(BinaryNode<T> node, List<T> list)
    {
        if (node.getLeft() != null) postorderTraversal(node.getLeft(),list);
        if (node.getRight() != null) postorderTraversal(node.getRight(), list);
        list.add(node.getInfo());
        return list;
    }
    
    // Removes an element e from this BST such that e.compareTo(element) == 0
    public void remove(T element)
    {
        root = remove(element, root);
    }
    private BinaryNode<T> remove(T element, BinaryNode<T> tree)
    {
        if (element.compareTo(tree.getInfo()) < 0) tree.setLeft(remove(element, tree.getLeft()));
        else if (element.compareTo(tree.getInfo()) > 0) tree.setRight(remove(element, tree.getRight()));
        else tree = removeNode(tree);
        return tree;
    }
    private BinaryNode<T> removeNode(BinaryNode<T> node)
    {
        if (node.getLeft() == null && node.getRight() == null) return null;
        else if (node.getLeft() == null) return node.getRight();
        else if (node.getRight() == null) return node.getLeft();
        else
        {
            T predecessorInfo = getPredecessor(node);
            node.setInfo(predecessorInfo);
            node.setLeft(remove(predecessorInfo, node.getLeft()));
            return node;
        }
    }
    private T getPredecessor(BinaryNode<T> node)
    {
        while (node.getRight() != null) node = node.getRight();
        return node.getInfo();
    }

    // Restructures this BST to be optimally balanced
    public void balance()
    {
        List<T> list = inorderTraverse();
        root = null;
        refillTree(0, list.size() - 1, list);
    }
    private void refillTree(int low, int high, List<T> list)
    {
        if (low == high) add(list.get(low));
        else if (low + 1 == high)
        {
            add(list.get(low));
            add(list.get(high));
        }
        else
        {
            int mid = (low + high) / 2;
            add(list.get(mid));
            refillTree(low, mid - 1, list);
            refillTree(mid + 1, high, list);
        }
    }
}