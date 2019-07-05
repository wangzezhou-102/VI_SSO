package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.secusoft.web.core.util.ExcelUtil;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.model.PictureBean;
import com.secusoft.web.model.TrackBean;
import com.secusoft.web.tusouapi.model.SearchResponseData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huanghao
 * @date 2019-06-15
 */
@RestController
public class ExcelController {
    @Resource
    private PictureMapper pictureMapper;

    private static Logger log = LoggerFactory.getLogger(ExcelController.class);
    /**
     * 导出图片
     * @param response
     * @param, @RequestBody List<PictureBean> pictureBeans
     */
    @RequestMapping("trackExcel")
    public void trackExecl(HttpServletResponse response, @RequestBody JSONObject jsonObject){
        ArrayList<SearchResponseData> searchResponseDatas = JSON.parseObject(jsonObject.get("pictureList").toString(), new TypeReference<ArrayList<SearchResponseData>>() {
        });

        log.info(searchResponseDatas.toString());

        List<PictureBean> pictureBeans = new ArrayList<>();
        for (SearchResponseData searchResponseData:searchResponseDatas) {
            pictureBeans.add(PictureBean.toPictureBean(searchResponseData));
        }
        List<Object[]> contentList = new ArrayList<>();
        String[] headArray = {"序号","点位位置","设备ID","设备名称","抓拍时间","图片"};

        if(!CollectionUtils.isEmpty (pictureBeans)){
            Integer i=1;
            for (PictureBean pictureBean : pictureBeans){
                String longitude = pictureBean.getDeviceBean().getLongitude();
                String latitude = pictureBean.getDeviceBean().getLatitude();

                StringBuffer point=new StringBuffer();
                point.append(longitude);
                point.append(",");
                point.append(latitude);
                Long time=pictureBean.getPictureTime();
                Date date = new Date(time);
                byte[] byUrl=null;
                //区分本地图片以及 网上图片
                if(pictureBean.getPicType()==null){
                    byUrl = getImageFromNetByUrl(pictureBean.getCropImageSignedUrl());
                }else{
                    System.out.println(System.getProperty("user.dir") + "pic" + pictureBean.getCropImageSignedUrl());
                    byUrl = imagetoBytes((System.getProperty("user.dir") +"/"+ "pic" + pictureBean.getCropImageSignedUrl()));
                }

                Object[] o={
                        i,
                        point,
                        pictureBean.getDeviceBean().getDeviceId(),
                        pictureBean.getDeviceBean().getDeviceName(),
                        date,
                        byUrl
                };
                contentList.add(o);
                i++;
            }
        }

        try {
            ExcelUtil.ExportExcel(response, headArray, contentList, "Task.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     *https连接转换成byte数组
     */
    public static byte[] getImageFromNetByUrl(String strUrl){
        try {
            //要放到try/catch里面
            URL url = new URL(strUrl);
            // 打开连接
            URLConnection con = url.openConnection();
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            InputStream is = con.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len=is.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            is.close();
            byte[] bytes = outStream.toByteArray();
            is.close();
            return  bytes;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *本地地址转换成byte数组
     */
    public static byte[] imagetoBytes(String imgSrc){
        FileInputStream fin;
        byte[] bytes = null;
        try {
            fin = new FileInputStream(new File(imgSrc));
            bytes  = new byte[fin.available()];
            //将文件内容写入字节数组
            fin.read(bytes);
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }

}
