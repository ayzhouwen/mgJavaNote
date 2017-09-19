package com.meConcurrent.cas;
//使用(treiber,1986) 算法(非阻塞栈)实现的栈
//其实类似还有个并发链表,但是书上是残码,所以就不贴了
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack<E> {
	AtomicReference<Node<E>> top=new AtomicReference<Node<E>> ();
	
	public void push(E item){
		Node<E> newHead= new Node<E>(item);
		Node<E> oldHead;
		do {
			oldHead=top.get();
			if (oldHead==null) {
				newHead.next=oldHead;
			}
		} while (!top.compareAndSet(oldHead, newHead));
	}
	
	public E pop(){
		Node<E> newHead;
		Node<E> oldHead;
		do {
			oldHead=top.get();
			if (oldHead==null) {
				return null;
			}
			newHead =oldHead.next;
			oldHead=null;
		} while (!top.compareAndSet(oldHead, newHead));
		return oldHead.item;
	}
	private static class Node<E> {
		public final E item;
		public Node<E> next;

		public Node(E item) {
			this.item = item;
		}

	}

}