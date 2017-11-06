package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cn.ml_tech.mx.mlservice.DAO.DetectionDetail;
import cn.ml_tech.mx.mlservice.DAO.DetectionReport;
import cn.ml_tech.mx.mlservice.DAO.DevUuid;
import cn.ml_tech.mx.mlservice.DAO.DrugContainer;
import cn.ml_tech.mx.mlservice.DAO.DrugControls;
import cn.ml_tech.mx.mlservice.DAO.DrugInfo;
import cn.ml_tech.mx.mlservice.DAO.Factory;
import cn.ml_tech.mx.mlservice.R;


/**
 * 创建时间: 2017/7/12
 * 创建人: zhongwang
 * 功能描述:导出pdf工具类
 */

public class PdfUtil {
    public static final int SUCESS = 88;//成功时handler输出的what
    public static final int FAILURE = 99;//异常时handler输出的what
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Context context;
    private Document document;
    private Font yaHeiFont;
    private Font redFont;
    private Paragraph paragraph;
    private Paragraph emptyParagraph;
    private PdfPCell pdfPCell;
    private Resources r;
    AssetManager assetManager;
    private String path;
    private DevUuid devUuid;
    private DrugControls drugControls;
    private DetectionReport report;
    private java.util.List<String> reports;
    private java.util.List<DetectionDetail> allDetailList;
    private java.util.List<DetectionDetail> posList, negList;
    private JSONObject jsonObject;
    private String redFontName;
    private int num;
    private static PdfUtil pdfUtil;
    private Handler handler;
    private Handler outHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            report.setIspdfdown(true);
            report.update(report.getId());
            num--;
            outputPdf();
        }
    };

    private PdfUtil() {
    }

    private PdfUtil(Context context) {
        this.context = context;
        assetManager = context.getAssets();
    }

    public static PdfUtil getInstance(Context context) {
        if (pdfUtil == null)
            pdfUtil = new PdfUtil(context);
        return pdfUtil;

    }

    public void startOutput(java.util.List<String> reports, Handler handler) {
        this.reports = reports;
        this.handler = handler;
        num = reports.size();
        outputPdf();
    }

    private void outputPdf() {
        if (num == 0) {
            handler.sendEmptyMessage(SUCESS);
            return;
        } else {
            report = DataSupport.where("id = ?", reports.get(num - 1)).find(DetectionReport.class).get(0);
            allDetailList = DataSupport.where("detectionreport_id = ?", reports.get(num - 1)).find(DetectionDetail.class);
            if (drugControls == null) {
                drugControls = new DrugControls();
            }
            initDrugControls(report.getDruginfo_id());
            createPdf();
        }
    }

    private synchronized void createPdf() {
        if (devUuid == null)
            devUuid = DataSupport.findAll(DevUuid.class).get(0);
        new Thread() {
            @Override
            public void run() {
                super.run();
                path = context.getFilesDir().getPath() + "/" + report.getDetectionSn() + ".pdf";
                posList = new ArrayList<>();
                negList = new ArrayList<>();
                if (allDetailList != null) {
                    for (DetectionDetail detail :
                            allDetailList) {
                        if (detail.getRepIndex() == 0) {
                            posList.add(detail);
                        } else {
                            negList.add(detail);
                        }
                    }
                }
                try {
                    // 上、下、左、右间距
                    document = new Document(PageSize.A4, 20, 20, 20, 20);
                    File file = new File(path);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    OutputStream outputStream = new FileOutputStream(file);
                    PdfWriter.getInstance(document, outputStream);
                    r = context.getResources();
                    emptyParagraph = new Paragraph(" ");
                    document.open();
                    setFont();
                    addImage();
                    addHead();
                    addInfo();
                    addTable();
                    document.newPage();
                    addDetailTitle();
                    redFont = new Font(BaseFont.createFont(redFontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
                    redFont.setColor(BaseColor.RED);
                    redFont.setSize(10);
                    addDetailInfo();
                    document.close();
                    outputStream.close();
                    outHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void setFont() {
        String yaHeiFontName = context.getResources().getString(R.raw.simsun);
        yaHeiFontName += ",1";
        redFontName = context.getResources().getString(R.raw.simsun);
        redFontName += ",1";
        try {
            yaHeiFont = new Font(BaseFont.createFont(yaHeiFontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            yaHeiFont.setSize(20);
            redFont = new Font(BaseFont.createFont(redFontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            redFont.setColor(BaseColor.RED);
            redFont.setSize(13);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addImage() throws IOException, DocumentException {
        File file = new File(context.getFilesDir().getAbsolutePath() + "/reporthead.jpg");
        if (file.exists()) {
            Image image = Image.getInstance(context.getFilesDir().getAbsolutePath() + "/reporthead.jpg");
            image.scaleAbsolute(118, 35);
            image.scalePercent(50);
            document.add(image);
        } else {
            createFile(context.getFilesDir().getAbsolutePath() + "/reporthead.jpg");
            Image image = Image.getInstance(context.getFilesDir().getAbsolutePath() + "/reporthead.jpg");
            image.scaleAbsolute(118, 35);
            image.scalePercent(50);
            document.add(image);
        }
    }

    private void createFile(String s) {
        int bytesum = 0;
        int byteread = 0;
        try {
            InputStream inStream =
                    assetManager.open("reporthead.jpg");
            FileOutputStream fs = new FileOutputStream(s);
            byte[] buffer = new byte[1444];

            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("zw", "false");
        }
    }

    private void addDetailInfo() throws DocumentException {
        yaHeiFont.setSize(10);
        int i = 0;
        for (DetectionDetail detail :
                allDetailList) {
            if (i % 4 == 0 && i != 0) {
                document.newPage();
                document.add(emptyParagraph);
                document.add(emptyParagraph);
            }
            try {
                jsonObject = new JSONObject(detail.getNodeInfo());
                PdfPTable pdfPTable = new PdfPTable(5);
                pdfPTable.setWidthPercentage(70); // Width 100%
                float[] columnWidths = {2f, 1f, 1f, 1f, 0.8f};
                pdfPTable.setWidths(columnWidths);
                if (detail.getRepIndex() == 0) {
                    pdfPCell = new PdfPCell(new Paragraph("初检" + detail.getDetIndex() + "号样品", yaHeiFont));
                } else {
                    pdfPCell = new PdfPCell(new Paragraph("复检" + detail.getRepIndex() + "号样品", yaHeiFont));

                }
                pdfPCell.setRowspan(1);
                pdfPCell.setColspan(5);

                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("floatdta").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("floatdta").getInt("data") + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("floatdta").getString("result"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph("色差系数", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(detail.getColorFactor() + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("glassprecent").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("glassprecent").getInt("data") + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("glassprecent").getString("result"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setRowspan(2);
                pdfPCell.setColspan(1);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph("旋瓶时间", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(detail.getScrTime() + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("glasstime").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("glasstime").getDouble("data") + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph("停瓶时间", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(detail.getStpTime() + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("statistics40").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("statistics40").getDouble("data") + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("statistics40").getString("result"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setRowspan(4);
                pdfPCell.setColspan(1);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph("旋瓶状态", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(detail.getScrTimeText(), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("statistics50").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("statistics50").getDouble("data") + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph("停瓶状态", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(detail.getStpTimeText(), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("statistics60").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("statistics60").getDouble("data") + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph("综合结果", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setRowspan(5);
                pdfPCell.setColspan(1);
                pdfPTable.addCell(pdfPCell);
                if (detail.isPositive()) {
                    pdfPCell = new PdfPCell(new Paragraph("阳性", redFont));
                } else {
                    pdfPCell = new PdfPCell(new Paragraph("阴性", yaHeiFont));

                }
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setRowspan(5);
                pdfPCell.setColspan(1);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("statistics70").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("statistics70").getDouble("data") + "", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("min").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("min").getDouble("data") + "颗", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("min").getString("result"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setRowspan(2);
                pdfPCell.setColspan(1);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("max").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("max").getDouble("data") + "颗", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("super").getString("name"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("super").getDouble("data") + "颗", yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                pdfPCell = new PdfPCell(new Paragraph(jsonObject.getJSONObject("super").getString("result"), yaHeiFont));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
                document.add(pdfPTable);
                document.add(emptyParagraph);
            } catch (JSONException e) {
                e.printStackTrace();
                break;
            }
            i++;
        }

    }

    private void addDetailTitle() throws DocumentException {
        yaHeiFont.setSize(50);
        paragraph = new Paragraph("详细结果", yaHeiFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(emptyParagraph);
        document.add(emptyParagraph);


    }

    private void addTable() throws DocumentException {
        PdfPTable table = new PdfPTable(4); // 3 columns.
        table.setWidthPercentage(70); // Width 100%
        table.setSpacingBefore(10f); // Space before table
        table.setSpacingAfter(10f); // Space after table
        // Set Column widths
        float[] columnWidths = {1f, 1f, 1f, 1f};
        table.setWidths(columnWidths);
        PdfPCell c1 = new PdfPCell(new Phrase("初检序号", yaHeiFont));
        c1.setMinimumHeight(15);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("综合结果", yaHeiFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setMinimumHeight(15);
        c1.setVerticalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("复检序号", yaHeiFont));
        c1.setMinimumHeight(15);
        c1.setVerticalAlignment(Element.ALIGN_CENTER);

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("综合结果", yaHeiFont));
        c1.setMinimumHeight(15);


        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);
        int a = posList.size();
        int b = negList.size();
        for (int i = 0; i < 20; i++) {

            c1 = new PdfPCell(new Phrase((i + 1) + "", yaHeiFont));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            if (i < a) {
                if (posList.get(i).isPositive()) {
                    yaHeiFont.setColor(BaseColor.RED);
                    c1 = new PdfPCell(new Phrase("阳性 ×", redFont));
                    c1.setMinimumHeight(15);
                    c1.setVerticalAlignment(Element.ALIGN_CENTER);

                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    yaHeiFont.setColor(BaseColor.BLACK);
                    c1.setBackgroundColor(BaseColor.BLACK);

                } else {
                    c1 = new PdfPCell(new Phrase("阴性 √", yaHeiFont));
                    c1.setMinimumHeight(15);
                    c1.setVerticalAlignment(Element.ALIGN_CENTER);

                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                }
            } else {
                c1 = new PdfPCell(new Phrase(" ", yaHeiFont));
                c1.setMinimumHeight(15);
                c1.setVerticalAlignment(Element.ALIGN_CENTER);

                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            c1 = new PdfPCell(new Phrase((i + 1) + "", yaHeiFont));
            c1.setMinimumHeight(15);
            c1.setVerticalAlignment(Element.ALIGN_CENTER);

            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            if (i < b) {
                if (negList.get(i).isPositive()) {
                    c1 = new PdfPCell(new Phrase("阳性 ×", redFont));
                    c1.setMinimumHeight(15);
                    c1.setVerticalAlignment(Element.ALIGN_CENTER);

                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);

                    table.addCell(c1);


                } else {
                    c1 = new PdfPCell(new Phrase("阴性 √", yaHeiFont));
                    c1.setMinimumHeight(15);
                    c1.setVerticalAlignment(Element.ALIGN_CENTER);

                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                }
            } else {
                c1 = new PdfPCell(new Phrase(" ", yaHeiFont));
                c1.setMinimumHeight(15);
                c1.setVerticalAlignment(Element.ALIGN_CENTER);

                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }
        }
        document.add(table);
        document.add(emptyParagraph);
    }

    private void addInfo() {
        paragraph.add(emptyParagraph);
        yaHeiFont.setSize(13);
        List orderedList = new List(List.UNORDERED);
        orderedList.add(new ListItem("仪器编号 " + devUuid.getDevID(), yaHeiFont));
        orderedList.add(new ListItem("药品名称 " + drugControls.getDrugName(), yaHeiFont));
        orderedList.add(new ListItem("检测编号 " + report.getDetectionBatch(), yaHeiFont));
        orderedList.add(new ListItem("药品批号 " + report.getDetectionNumber(), yaHeiFont));
        orderedList.add(new ListItem("拼音简写 " + drugControls.getPinyin(), yaHeiFont));
        orderedList.add(new ListItem("药品英文 " + drugControls.getEnname(), yaHeiFont));
        orderedList.add(new ListItem("阈值通道 " + "50um通道", yaHeiFont));
        orderedList.add(new ListItem("药品规格 " + drugControls.getDrugBottleType(), yaHeiFont));
        orderedList.add(new ListItem("药品厂家 " + report.getFactoryName(), yaHeiFont));
        orderedList.add(new ListItem("检测人员 " + report.getUserName(), yaHeiFont));
        orderedList.add(new ListItem("检测日期 " + dateFormat.format(report.getDate()), yaHeiFont));
        try {
            document.add(orderedList);

            document.add(emptyParagraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public void initDrugControls(long id) {
        DrugInfo drugInfo = DataSupport.find(DrugInfo.class, id);
        java.util.List<DrugContainer> list = DataSupport.select(new String[]{"id", "name"}).where("id=?", String.valueOf(drugInfo.getDrugcontainer_id())).find(DrugContainer.class);
        String drugBottleType = list.get(0).getName();
        java.util.List<Factory> lists = DataSupport.select(new String[]{"*"}).where("id=?", String.valueOf(drugInfo.getFactory_id())).find(Factory.class);
        String factory_name = lists.get(0).getName();
        if (drugControls == null) {
            drugControls = new DrugControls();
        }
        drugControls.setPinyin(drugInfo.getPinyin());
        drugControls.setEnname(drugInfo.getEnname());
        drugControls.setDrugName(drugInfo.getName());
        drugControls.setDrugBottleType(drugBottleType);
        drugControls.setDrugFactory(factory_name);
    }


    private void addHead() {
        paragraph = new Paragraph("光散射法全自动可见异物检测仪(ML-AMIIXH-2)", yaHeiFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        try {
            document.add(paragraph);
            paragraph.add(emptyParagraph);
            yaHeiFont.setSize(30);
            paragraph = new Paragraph("检测报告", yaHeiFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.add(emptyParagraph);
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
