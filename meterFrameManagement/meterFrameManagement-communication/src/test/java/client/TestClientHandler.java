package client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.whut.meterFrameManagement.aes256.AES;
import org.whut.meterFrameManagement.util.date.DateUtil;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zmz
 * Date: 16-9-2
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class TestClientHandler extends IoHandlerAdapter {
    @Override
    public void exceptionCaught(IoSession arg0, Throwable arg1)
            throws Exception {
        arg1.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession arg0, Object message) throws Exception {
        byte[] receiveBytes = ((IoBuffer)message).array();
        System.out.print("客户端收到：");
        for (int i=0;i<receiveBytes.length;i++){
            System.out.print(Byte.toUnsignedInt(receiveBytes[i])+" ");
        }
        System.out.println();
        if(receiveBytes[0] == 0x68 && receiveBytes[receiveBytes.length-1] == 0x16){
            int command = Byte.toUnsignedInt(receiveBytes[1]);
            if(command == 0xA1){
                byte[] bytes = Arrays.copyOfRange(receiveBytes,2,6);
                String hex = Hex.BytesToHexString(bytes);
                long sub = Long.parseLong(hex,16);
                Date date = DateUtil.createDate("2000-1-1 00:00:00");
                long end = sub*1000 + date.getTime();
                Date date1 = new Date(end);
                System.out.println("系统时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));
            }
            if(command == 0xA2&&receiveBytes[2]>0){
                byte[] bytes = Arrays.copyOfRange(receiveBytes,3,receiveBytes.length-1);
                String key = "77DD9400EEB5A0DADA40120151212163"+"77DD9400EEB5A0DADA40120151212163";
                byte[] keyBytes = Hex.hexStringToBytes(key,key.length()/2);
                byte[] result = AES.decrypt(bytes,keyBytes);
                byte[] meterBytes = Arrays.copyOfRange(result,2,15);
                StringBuffer meterID = new StringBuffer();
                for(int i=0;i<meterBytes.length;i++)
                    meterID.append((char)meterBytes[i]);
                String resultString = "h"+Hex.BytesToHexString(result)+"16";
                System.out.println("客户端解密后："+resultString);
                System.out.println("表号："+meterID);
            }
        }
    }

    @Override
    public void messageSent(IoSession arg0, Object message) throws Exception {
        System.out.println("客户端发送信息："+message.toString());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("客户端与:"+session.getRemoteAddress().toString()+"断开连接");
        System.exit(0);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("客户端与:"+session.getRemoteAddress().toString()+"建立连接");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }

    @Override
    public void sessionOpened(IoSession arg0) throws Exception {
        System.out.println("打开连接");
    }
}
