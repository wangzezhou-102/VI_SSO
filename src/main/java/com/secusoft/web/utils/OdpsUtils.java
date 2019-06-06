package com.secusoft.web.utils;

import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.TableSchema;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordReader;
import com.aliyun.odps.task.SQLTask;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TableTunnel.DownloadSession;
import com.aliyun.odps.tunnel.TunnelException;

import java.io.IOException;
import java.util.UUID;

public class OdpsUtils {
//    public static final String accessId ;
    public static String accessId ;
    public static String accessKey;
    public static String endPoint;
    public static String project;
    public static String sql ;
    public static String table = "Tmp_" + UUID.randomUUID().toString().replace("-", "_");//其实也就是随便找了个随机字符串作为临时表的名字
    public static Odps odps = getOdps();

//    public static void main(String[] args) {
//        System.out.println(table);
//        runSql();
//        tunnel();
//    }

    /*
     * 把SQLTask的结果下载过来
     * */
    private static void tunnel() {
        TableTunnel tunnel = new TableTunnel(odps);
        try {
            DownloadSession downloadSession = tunnel.createDownloadSession(
                    project, table);
            System.out.println("Session Status is : "
                    + downloadSession.getStatus().toString());
            long count = downloadSession.getRecordCount();
            System.out.println("RecordCount is: " + count);
            RecordReader recordReader = downloadSession.openRecordReader(0,
                    count);
            Record record;
            while ((record = recordReader.read()) != null) {
                consumeRecord(record, downloadSession.getSchema());
            }
            recordReader.close();
        } catch (TunnelException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /*
     * 保存这条数据
     * 数据量少的话直接打印后拷贝走也是一种取巧的方法。实际场景可以用Java.io写到本地文件，或者写到远端数据等各种目标保存起来。
     * */
    private static void consumeRecord(Record record, TableSchema schema) {
        System.out.println(record.getString("username")+","+record.getBigint("cnt"));


    }

    /*
     * 运行SQL，把查询结果保存成临时表，方便后面用Tunnel下载
     * 这里保存数据的lifecycle为1天，所以哪怕删除步骤出了问题，也不会太浪费存储空间
     * */
    private static void runSql() {
        Instance i;
        StringBuilder sb = new StringBuilder("Create Table ").append(table)
                .append(" lifecycle 1 as ").append(sql);
        try {
            System.out.println(sb.toString());
            i = SQLTask.run(getOdps(), sb.toString());
            i.waitForSuccess();

        } catch (OdpsException e) {
            e.printStackTrace();
        }
    }
    /*
     * 初始化MaxCompute(原ODPS)的连接信息
     * */
    private static Odps getOdps() {
//        Account account = new AliyunAccount(accessId, accessKey);
//        Odps odps = new Odps(account);
//        odps.setEndpoint(endPoint);
//        odps.setDefaultProject(project);
        return null;
    }
}
