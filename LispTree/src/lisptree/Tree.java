/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lisptree;

import java.util.*;

/**
 *
 * @author Nikki
 */
public class Tree<V> {
    /** The data held in this Tree node. */
    public V value;
    
    /** The children of this Tree node. */
    private ArrayList<Tree<V>> children;
    
    /**
     * Creates a Tree node with the given value and zero or more children.
     * The order of the <code>initialChildren</code> parameters is the
     * order in which the children of this Tree node will occur.
     * 
     * @param value The data to put in this individual Tree node.
     * @param initialChildren Nodes to be added to this Tree node as children.
     */
    public Tree(V value, Tree<V>... initialChildren) {
        this.value = value;
        children = new ArrayList<Tree<V>>();
        for (Tree<V> child : initialChildren) {
            children.add(child);
        }
    }
    
    /**
     * Returns the first child of this tree (which may be null).
     * 
     * @return The first child, or <code>null</code> if this node has no children.
     */
    public Tree firstChild() {
        if (children.size() > 0) {
            return children.get(0);
        }
        else return null;
    }
    
    /**
     * Returns the last child of this tree (which may be null).
     * 
     * @return The last child, or <code>null</code> if this node has no children.
     */
    public Tree lastChild() {
        if (children.size() > 0) {
            return children.get(children.size() - 1);
        }
        else return null;
    }
    
    /**
     * Returns the number of (immediate) children of this node.
     * 
     * @return The number of children.
     */
    public int numberOfChildren() {
        return children.size();
    }
    
    /**
     * Returns the index-th child of this tree (counting from zero, as with arrays).
     * Throws a <code>NoSuchElementException</code> if index is less than zero or
     * greater than or equal to the number of children.
     * 
     * @param index The index of the child to be returned.
     * @return The <code>index</code>-th child of this Tree node.
     * @throws NoSuchElementException if the index is out of range.
     */
    public Tree<V> child(int index) throws NoSuchElementException {
        if (index < 0 || index >= children.size()) {
            throw new NoSuchElementException("Call of child(" + index + ")");
        }
        else return children.get(index);
    }
    
    /**
     * Returns an iterator for the children of this tree. The iterator supports
     * <code>remove()</code> as well as <code>hasNext()</code> and <code>next()</code>.
     * 
     * @return An iterator for the children of this tree.
     */
    public Iterator<Tree<V>> children() {
        return children.iterator();
    }
    
    /**
     * Adds newChild as the new last child of this tree, provided that the
     * resultant tree is loop-free.
     * 
     * @param newChild The Tree to be added as the last child of this Tree.
     * @throws IllegalArgumentException if adding <code>newChild</code> would
     *       create a loop in this Tree.
     */
    public void addChild(Tree<V> newChild)
            throws IllegalArgumentException {
        if (search(this, newChild)) {
            throw new IllegalArgumentException();
        }
        children.add(newChild);
    }
    
    /**
     * Adds <code>newChild</code> as the new <code>index</code>-th child of
     * this tree (counting from zero), provided that the resultant tree is
     * valid, that is, free of loops. The child previously at this index,
     * and all subsequent children, are "shifted right" (their index is
     * increased) to make room for the new child.
     * <p>
     * If the <code>index</code> is less than zero or greater than (<i>not</i>
     * greater than or equal to) the current number of children, or if the
     * operation would result in an invalid tree, the tree is unchanged and
     * the method throws an IllegalArgumentException.
     * 
     * @param index The location at which to add the new child Tree.
     * @param newChild The Tree to be added as a child.
     * @throws IllegalArgumentException if <code>index</code> is invalid, or
     *      if adding the child would create a loop in this Tree.
     */
    public void addChild(int index, Tree<V> newChild)
            throws IllegalArgumentException {
        if (index < 0 || index > children.size()) {
            throw new IllegalArgumentException();
        }
        children.add(index, newChild);
    }
    
    /**
     * Removes and returns the <code>index</code>-th child of this tree, or
     * throws a <code>NoSuchElementException</code> if the index is illegal.
     * (This method simply removes the child from the list of children of
     * this tree; no major tree surgery is involved. The removed subtree
     * remains intact.
     * 
     * @param index The index of the child Tree to be removed.
     * @return The removed Tree.
     * @throws NoSuchElementException if the given <code>index</code> is invalid.
     */
    public Tree removeChild(int index)
            throws NoSuchElementException {
        if (index < 0 || index >= children.size()) {
            throw new NoSuchElementException();
        }
        return children.remove(index);
    }
    
    /**
     * Returns <code>true</code> if this node has children.
     * @return code>true</code> if this node has children.
     */
    public boolean hasChildren() {
        return children.size() > 0;
    }
    
    /**
     * Tests whether the parameter is equal to this Tree. In order to be
     * considered equal, the parameter must be a Tree, the value in that
     * Tree node must equal the value in this Tree node, and both Trees
     * must have the same number of children, where the children are
     * pairwise equal.
     * 
     * @param object The object (presumably a Tree) against which to
     * compare this Tree.
     * @return <code>true</code> if the two trees are equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tree)) return false;
        Tree that = (Tree)object;
        if (!(this.value.equals(that.value))) return false;
        if (this.children.size() != that.children.size()) return false;
        for (int i = 0; i < children.size(); i++) {
            if (!(this.child(i).equals(that.child(i)))) return false;
        }
        return true;
    }
    
    /**
     * Returns a multiline representation of this Tree. Each line contains
     * the <code>toString()</code> representation of the value in that node,
     * terminated with a newline (<code>\n</code>). Each child is indented
     * two spaces under its parent. 
     * 
     * @return A String representation of the Tree rooted at this Tree node.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return toString(this, "");
    }

    private String toString(Tree<V> node, String indent) {
        String result = indent + node.value.toString() + '\n';
        Iterator<Tree<V>> iter = node.children.iterator();
        while (iter.hasNext()) {
            result += toString(iter.next(), indent + "  ");
        }
        return result;
    }
    
    /**
     * Tests whether the given node is in the given tree.
     * 
     * @param node The node to search for.
     * @param tree the tree to be searched.
     * @return <code>true</code> if the node is found, else <code>false</code>.
     */
    private boolean search(Tree<V> node, Tree<V> tree) {
        if (node == tree) return true;
        Iterator<Tree<V>> iter = tree.children();
            while (iter.hasNext()) {
                if (search(node, iter.next())) {
                    return true;
                }
            }
        return false;
    }
}
