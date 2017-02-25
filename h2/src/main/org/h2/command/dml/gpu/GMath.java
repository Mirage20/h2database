package org.h2.command.dml.gpu;

/**
 * Created by mirage on 1/16/17.
 */
public class GMath {

    static {
        System.loadLibrary("h2cuda");
    }

    private static native float cudaSum(float[] array, int length);

    //private static native void cudaMemAlloc(long size);

    //private static native void cudaMemFree();

    private static boolean isAllocated = false;

    public static float sum(float[] array) {
        return cudaSum(array, array.length);
    }

//    public static void gpuAlloc() {
//        if (!isAllocated) {
//            cudaMemAlloc(60000000);
//            isAllocated = true;
//        }
//    }
//
//    public static void gpuFree() {
//        if (isAllocated) {
//            cudaMemFree();
//            isAllocated = false;
//        }
//    }
}
