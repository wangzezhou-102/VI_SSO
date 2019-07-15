package com.secusoft.web.core.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
*  生成PDF格式文档
* */
public class PdfUtil {
   /* *//**
     * 抠模板
     * @throws Exception
     *//*
    public void createAllPdf() throws Exception {
        //填充创建pdf
        PdfReader reader = null;
        PdfStamper stamp = null;
        try {
            reader = new PdfReader("E:/module.pdf");
            SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
            String times = simp.format(new Date()).trim();
            //创建生成报告名称
            String root = ServletActionContext.getRequest().getRealPath("/upload") + File.separator;
            if (!new File(root).exists())
                new File(root).mkdirs();
            File deskFile = new File(root, times + ".pdf");
            stamp = new PdfStamper(reader, new FileOutputStream(deskFile));
            //取出报表模板中的所有字段
            AcroFields form = stamp.getAcroFields();
            // 填充数据
            form.setField("name", "zhangsan");
            form.setField("sex", "男");
            form.setField("age", "15");

            //报告生成日期
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            String generationdate = dateformat.format(new Date());
            form.setField("generationdate", generationdate);
            stamp.setFormFlattening(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stamp != null) {
                stamp.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }*/
    public void turnToPdf() throws Exception{
        //创建文本对象
        Document document = new Document();
        //建立书写器,与文档对象关联
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F://巡逻任务报告.PDF"));
        writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
        writer.setEncryption("123".getBytes(),"1234".getBytes(),1,1);
        //设置文档标题
        document.addTitle("巡逻任务报告");
        //设置文档主题
        document.addSubject("巡逻报告");
        //设置关键字
        document.addKeywords("报告");
        //设置作者
        document.addAuthor("视在数科");
        //设置创建者
        document.addCreator("wangzezhou");
        //设置生产者
        document.addProducer();
        //设置创建日期
        document.addCreationDate();
        //打开文档
        document.open();
        //向文档中添加内容
        document.add(new Paragraph("巡逻任务内容"));
        //关闭文档
        document.close();
    }


    public  void printPayment(HttpServletResponse response) throws Exception {
        // 创建文本对象
        Document document = new Document();
        //页面大小
        Rectangle pageSize = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        //横向
        //pageSize.rotate();
        //页面背景色
        //pageSize.setBackgroundColor(BaseColor.ORANGE);
        document.setPageSize(pageSize);
        //创建书写器（Writer）对象
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, ba);
//	     下面response.getOutputStream()不行，会出现类似乱码
//		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
        document.open();
        // 设置PDF ,即确定了表格的列数
        //设置宽度
        float[] widths = { 200, 200, 300, 500, 300, 300, 300, 300, 300, 300, 300,300,400,300,500,200,500,400,300};
        //表格处理   创建表格时必须指定表格的列数
        PdfPTable table = new PdfPTable(widths);
        table.setLockedWidth(true);
        table.setTotalWidth(800);//550
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        System.out.println("生成pdf");
        // 设置PDF表格标题
        pdfTitle(table);
       /* //制表单位等信息设置
        pdfTitle1(table,financeBatchId);
        //表头
        pdfTitle2(table);
        // 设置PDF表格内容
        pdfContent(table, financeBatchId);
        // 设置PDF合计
        pdfSubtotal(table, countDetails);
        //底部四个人名
        pdfDown(table,financeBatchId);*/
        // pdf文档中加入table
        document.add(table);
        document.close();
        response.setHeader("content-Type", "application/pdf");
        ServletOutputStream out = response.getOutputStream();
        ba.writeTo(out);//将字节数组输出流中的数据写入指定的OutputStream输出流中.
        out.flush();
    }

    /**
     * 设置标题
     * @param table
     * @throws Exception
     */
    private void pdfTitle(PdfPTable table) throws Exception {
        String titleContent1= "巡逻任务报告";
        // 表格的单元格
        PdfPCell cell = new PdfPCell();
        // 向单元格中插入数据
        // new Paragraph()是段落的处理，可以设置段落的对齐方式，缩进和间距。
        cell.setPhrase(new Paragraph(titleContent1));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);//设置表格行高
        cell.setBorderWidth(0f);//去除表格的边框
        cell.setColspan(19);
        //cell.setRowspan(19);
        table.addCell(cell);
    }

}
