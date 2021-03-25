package com.reader.utils;

/**
 * @Author Flagship
 * @Date 2021/3/22 22:37
 * @Description
 */
public class FilterUtils {
    public static String XSSFilter(String value) {
        if (value == null) {
            return null;
        }
        value = value.replaceAll("\\{", "｛");
        value = value.replaceAll("<", "&lt;");
        value = value.replaceAll(">", "&gt;");
        value = value.replaceAll("\t", "    ");
        value = value.replaceAll("\r\n", "\n");
        value = value.replaceAll("\n", "<br/>");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("\\\\", "&#92;");
        value = value.replaceAll("\"", "&quot;");
        value = value.replaceAll("\\}", "﹜").trim();
        return value;
    }
}
