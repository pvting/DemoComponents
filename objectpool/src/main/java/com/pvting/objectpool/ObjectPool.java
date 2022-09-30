package com.pvting.objectpool;

import android.util.SparseArray;

public class ObjectPool {
    private SparseArray idleArray = new SparseArray<>();
    private SparseArray lentArray = new SparseArray<>();
}
