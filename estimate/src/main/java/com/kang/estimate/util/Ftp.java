package com.kang.estimate.util;

import com.jcraft.jsch.*;
import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.module.management.entity.Server;

import java.io.IOException;
import java.io.InputStream;

public class Jsch {

    private static ThreadLocal<Jsch> jschLocal=new ThreadLocal<Jsch>();

    private Session session;

    private Jsch(String username, String host, int port, String password){
        JSch jSch = new JSch();
        try {
            session = jSch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setTimeout(30000);
            session.connect();
        }catch (JSchException jsex){
            System.out.println(jsex.getMessage());
        }
    }

    private boolean isSession(){
        return session!=null&&session.isConnected();
    }

//    public static Session createSession(String username, String host, int port, String password){
//        JSch jSch = new JSch();
//        Session session;
//        try {
//            session = jSch.getSession(username, host, port);
//            session.setPassword(password);
//            session.setConfig("StrictHostKeyChecking", "no");
//            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
//            session.setTimeout(30000);
//            session.connect();
//        }catch (JSchException jsex){
//            System.out.println(jsex.getMessage());
//            return null;
//        }
//        return session;
//    }

    public Jsch getJsch(String username, String host, int port, String password){
        if(!isSession()){
            jschLocal.set(new Jsch(username,host,port,password));
        }
        return jschLocal.get();
    }

    public static StringBuilder execCommad(Session session,String commad)throws JSchException, IOException, InterruptedException{
        Channel channel = session.openChannel("exec");
        ChannelExec channelExec = (ChannelExec) channel;
        channelExec.setCommand(commad);
        channelExec.setErrStream(System.err);
        channelExec.setInputStream(null);
        channelExec.setOutputStream(System.out);
        InputStream in = channelExec.getInputStream();
        channelExec.connect();
        StringBuilder executeResultString = new StringBuilder();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0){
                    break;
                }
                executeResultString.append(new String(tmp, 0, i));
            }
            if (channelExec.isClosed()) {
                System.out.println("exit code:" + channelExec.getExitStatus());
                break;
            }
            Thread.sleep(1000);
        }
        channelExec.disconnect();
        session.disconnect();
        return executeResultString;
    }

    public static void upload(Session session,String src,String dst){
        try {
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp) channel;
//            channelSftp.put(src, dst,new MyProgressMonitor(),ChannelSftp.OVERWRITE);
            channelSftp.put(src, dst);
            channelSftp.quit();
            channel.disconnect();
            session.disconnect();
        }catch (Exception e){
            e.printStackTrace();
            throw new BussinessException(EmBussinessError.UNKNOW_ERROR);
        }
    }

    public static String findWebappsPath(Server server){
        Session session=createSession(server.getRole(),server.getHost(),server.getPort(),server.getPassword());
        StringBuilder path= null;
        try {
            path = execCommad(session, Const.FIND_COMMAD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussinessException(EmBussinessError.UNKNOW_ERROR);
        }
        if(path.indexOf(".")==0){
            path=path.replace(0,1,"/usr");
        }
        return Common.replaceBlank(path.toString());
    }

}
