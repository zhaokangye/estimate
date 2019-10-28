package com.kang.estimate.utils;

import com.kang.estimate.controller.ViewModel.ServerVO;
import com.kang.estimate.controller.ViewModel.UserVO;
import com.kang.estimate.module.Management.entity.Server;
import com.kang.estimate.module.Management.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.Scanner;

public class Convert {
    public static ServerVO convertFromServer(Server server){
        if(server==null){
            return null;
        }
        ServerVO serverVO=new ServerVO();
        BeanUtils.copyProperties(server,serverVO);
        return serverVO;
    }
    public static UserVO convertFromUser(User user){
        if(user==null){
            return null;
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    public static void main(String[] args) {
        String str="Linux 3.10.0-514.26.2.el7.x86_64 (iZ2ze329z39hnu9r307anaZ)      08/11/2019      _x86_64_        (1 CPU)\n" +
                "\n" +
                "04:57:48 PM     CPU     %user     %nice   %system   %iowait    %steal     %idle\n" +
                "04:57:58 PM     all      0.50      0.00      0.10      0.00      0.00     99.40\n" +
                "04:58:08 PM     all      0.50      0.00      0.20      0.00      0.00     99.30\n" +
                "04:58:18 PM     all      0.30      0.00      0.20      0.00      0.00     99.50\n" +
                "Average:        all      0.43      0.00      0.17      0.00      0.00     99.40\n";
//        String[] s0=str.split("\n");
//        ArrayList<CPU> cpus=new ArrayList<>();
//        for(int i=3;i<s0.length-1;i++){
//            //strings.add(str.split("\n")[i].split(" "));
//            String[] strings=str.split("\n")[i].split(" ");
//            ArrayList<String> arrayList=new ArrayList<String>();
//            for(int j=0;j<strings.length;j++){
//                if(!strings[j].isEmpty()){
//                    arrayList.add(strings[j]);
//                }
//            }
//            CPU cpu=new CPU();
//            cpu.setTime(arrayList.get(0)+arrayList.get(1));
//            cpu.setUser(arrayList.get(3));
//            cpu.setNice(arrayList.get(4));
//            cpu.setSystem(arrayList.get(5));
//            cpu.setIowait(arrayList.get(6));
//            cpu.setSteal(arrayList.get(7));
//            cpu.setIdle(arrayList.get(8));
//            cpus.add(cpu);
//        }
        StringBuilder currentPath=new StringBuilder();
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()) {
            //利用nextXXX()方法输出内容
            currentPath.append("/"+scanner.next());
            System.out.println(currentPath);
        }
    }
}
