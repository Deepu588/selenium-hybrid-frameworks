package com.scart.utilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Map;

public class ChartGenerator {

    public static String generateBarChart(Map<String, Long> testDurations, Map<String, Integer> testStatuses, String outputPath) throws Exception {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Long> entry : testDurations.entrySet()) {
            String testName = entry.getKey();
            Long duration = entry.getValue();
            int status = testStatuses.get(testName);
            
            String category = testName + " (" + getStatusString(status) + ")";
            dataset.addValue(duration, getStatusString(status), category);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Test Execution Results", "Test Method Names", "Execution Time (ms)", dataset);

        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Set colors based on status
        renderer.setSeriesPaint(0, Color.GREEN);  // Pass
        renderer.setSeriesPaint(1, Color.RED);    // Fail
        renderer.setSeriesPaint(2, Color.ORANGE); // Skip
        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setMaximumCategoryLabelLines(8);  
        xAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 15));  

        File chartFile = new File(outputPath);
        ChartUtils.saveChartAsPNG(chartFile, barChart, 1700, 1000);

        return chartFile.getAbsolutePath();
    }

    private static String getStatusString(int status) {
        switch (status) {
            case 1: return "Pass";
            case 2: return "Fail";
            case 3: return "Skip";
            default: return "Unknown";
        }
    }
}