package com.kang.estimate.util;

import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.util.design_pattern.singleton.EmumSingleton;

import java.net.InetAddress;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kang
 */
public class Common {

    static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  3000 ;
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static int binarySearch(int target,int[] array){
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] > target) {
                high = mid - 1;
            }
            else if (array[mid] < target){
                low = mid + 1;
            } else{
                return mid;
            }
        }
        return low;
    }

    public static int[] bubbleSort(int[] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-i-1;j++){
                if(array[j+1]<array[j]){
                    int temp=array[j+1];
                    array[j+1]=array[j];
                    array[j]=temp;
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array={1,8,2,1,7,3,2,6,7,5,8};
        for(int i:bubbleSort(array)){
            System.out.print(i);
        }
    }
}
