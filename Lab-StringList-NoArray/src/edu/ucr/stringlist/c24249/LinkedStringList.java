package edu.ucr.stringlist.c24249;

import org.w3c.dom.Node;

import java.util.Objects;

public class LinkedStringList implements StringList{

    private static class Node {
        String value;
        Node next;

        Node(String value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public LinkedStringList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void add(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        if(exists(value)) {
            return;
        }

        Node newNode = new Node(value);
        if(head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void remove(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        if(head == null) {
            return;
        }

        if(Objects.equals(head.value, value)) {
            head = head.next;
            size--;
            return;
        }
        Node current = head;
        while (current.next != null && !Objects.equals(current.next.value, value)) {
            current = current.next;
        }

        if(current.next != null) {
            current.next = current.next.next;
            size--;
        }

    }

    @Override
    public Integer size() {
        return size;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node current = head;

        while(current != null) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(current.value);
            current = current.next;
        }
        return sb.toString();
    }

    @Override
    public Boolean exists(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        Node current = head;
        while (current != null) {
            if (Objects.equals(current.value, value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
