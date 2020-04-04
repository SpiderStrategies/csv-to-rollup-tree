package com.spider;

import java.util.SortedSet;
import java.util.TreeSet;

public class TreeNode implements Comparable<TreeNode> {
	String label;
	String key;
	SortedSet<TreeNode> children = new TreeSet<TreeNode>();

	public String getLabel() { return this.label; }
	public void setLabel(String label) {
		this.label = label;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public SortedSet<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(SortedSet<TreeNode> children) {
		this.children = children;
	}

	public int hashCode() {
		return this.key.hashCode();
	}

	public boolean equals(TreeNode tn) {
		return this.key.equals(tn.getKey());
	}

	public int compareTo(TreeNode tn) {
		return (this.label + this.key).compareTo(tn.getLabel() + tn.getKey());
	}
}
