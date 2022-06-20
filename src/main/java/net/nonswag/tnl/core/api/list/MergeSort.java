package net.nonswag.tnl.core.api.list;

import javax.annotation.Nonnull;

public class MergeSort {

    public static void sort(@Nonnull Number[] array) {
        int length = array.length;
        if (length <= 1) return;
        int mid = length / 2;
        Number[] left = new Number[mid];
        Number[] right = new Number[length - mid];
        System.arraycopy(array, 0, left, 0, mid);
        if (length - mid >= 0) System.arraycopy(array, mid, right, 0, length - mid);
        sort(left);
        sort(right);
        merge(array, left, right);
    }

    public static void merge(@Nonnull Number[] array, @Nonnull Number[] left, @Nonnull Number[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i].doubleValue() <= right[j].doubleValue()) array[k++] = left[i++];
            else array[k++] = right[j++];
        }
        while (i < left.length) array[k++] = left[i++];
        while (j < right.length) array[k++] = right[j++];
    }
}
