package com.bilibili.syringa.core.service;

import com.bilibili.syringa.core.dto.KafkaTopicDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);

    public static List<KafkaTopicDTO> readExcel(String filePath) {
        List<KafkaTopicDTO> kafkaTopicDTOS = new ArrayList<>();

        try {
            boolean xlsx = filePath.endsWith(".xlsx");
            Workbook book;
            Sheet sheet = null;
            InputStream inputStream = new FileInputStream(new File(filePath));
            if (xlsx) {
                // 直接通过流获取整个excel
                book = new XSSFWorkbook(inputStream);
                // 获取第一个表单sheet
                sheet = book.getSheetAt(0);
            }

            if (sheet != null) {

                // 获取第一行
                int firstRow = sheet.getFirstRowNum();
                // 获取最后一行
                int lastRow = sheet.getLastRowNum();

                // 循环行数依次获取列数
                for (int i = firstRow; i < lastRow + 1; i++) {
                    // 获取第 i 行
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        // 获取此行的第一列
                        int firstCell = 0;
                        /*
                         *获取此行的存在数据的第一列
                         * int firstCell = row.getFirstCellNum();
                         * */
                        // 获取此行的存在数据的最后一列
                        int lastCell = row.getLastCellNum();
                        // 创建集合，保存每一行的每一列
                        List<String> list = new ArrayList<>();
                        for (int j = firstCell; j < lastCell; j++) {
                            // 获取第 j 列
                            Cell cell = row.getCell(j);
                            if (cell != null) {
                                list.add(cell.toString());

                            } else {
                                list.add("");
                            }
                        }
                        LOGGER.info("the line :{}",list);
                        KafkaTopicDTO kafkaTopicDTO = new KafkaTopicDTO();
                        kafkaTopicDTO.setCluster(list.get(0));
                        kafkaTopicDTO.setTopic(list.get(1));
                        kafkaTopicDTO.setType(list.get(2));
                        kafkaTopicDTO.setUsername(list.get(3));
                        kafkaTopicDTO.setDepartmentName(list.get(4));
                        kafkaTopicDTOS.add(kafkaTopicDTO);
                    }
                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kafkaTopicDTOS;

    }

}
