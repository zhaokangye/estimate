package com.kang.estimate.utils;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

public class SSH {

    public static Session createSession(String role, String host, int port, String password){
        JSch jSch = new JSch();
        Session session;
        try {
            session = jSch.getSession(role, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setTimeout(30000);
            session.connect();
        }catch (JSchException jsex){
            System.out.println(jsex.getMessage());
            return null;
        }
        return session;
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
                if (i < 0)
                    break;
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
    
    public static void upload(Session session,String src,String dst) throws JSchException, SftpException { ;
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        //channelSftp.put(src,dst,new MyProgressMonitor(),ChannelSftp.OVERWRITE);
        channelSftp.put("C:\\Users\\hasee\\Desktop\\测试传送文件.txt", "/usr/local",new MyProgressMonitor(),ChannelSftp.OVERWRITE);
        channelSftp.quit();
        channel.disconnect();
        session.disconnect();
    }

}
