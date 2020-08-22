package tech.v2c.minecraft.plugins.lode.tools;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

interface Fifo<T> extends List<T>, Deque<T>, Cloneable, Serializable{
    void push(T entity);
}

public class FifoList<T> extends LinkedList<T> implements Fifo<T> {
    private int maxSize = Integer.MAX_VALUE;
    private final Object syncObj = new Object();

    public FifoList(){
        super();
    }

    public FifoList(int size){
        super();
        this.maxSize = size;
    }

    public void push(T entity){
        synchronized(syncObj){
            while (size() >= maxSize){
                poll();
            }

            addLast(entity);
        }
    }
}
