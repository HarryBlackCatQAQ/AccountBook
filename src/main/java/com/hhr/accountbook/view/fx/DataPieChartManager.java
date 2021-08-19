package com.hhr.accountbook.view.fx;

import com.hhr.accountbook.vo.BillSpendingTypeVo;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/16 22:27
 * @Version 1.0
 */
@Data
public class DataPieChartManager {
    private PieChart dataPieChart;
    private Label caption;

    public DataPieChartManager(PieChart dataPieChart,Label caption){
        this.dataPieChart = dataPieChart;
        initDataPieChart();
        this.caption = caption;
        initCaption();
    }

    public void setPieChartData(List<BillSpendingTypeVo> billSpendingTypeVoList){
        // piechart data
        PieChart.Data data[] = new PieChart.Data[billSpendingTypeVoList.size()];

        // string and integer data
        double sum = 0;
        for(BillSpendingTypeVo billSpendIngTypeVo : billSpendingTypeVoList){
            sum += billSpendIngTypeVo.getTypeCount();
        }

        for (int i = 0; i < billSpendingTypeVoList.size(); i++) {
            data[i] = new PieChart.Data(billSpendingTypeVoList.get(i).getType(),
                    changDouble(billSpendingTypeVoList.get(i).getTypeCount() * 1.0 / sum * 100));
        }

        if(!this.dataPieChart.getData().isEmpty()){
            this.dataPieChart.getData().clear();
        }
        this.dataPieChart.setData(FXCollections.observableArrayList(data));


        for (final PieChart.Data datas : this.dataPieChart.getData()) {
            datas.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY() - 90);
                            caption.setText(datas.getPieValue() + "%");
                        }
                    });
        }
    }



    public void addCaption(Pane pane){
        if(!pane.getChildren().contains(caption)){
            pane.getChildren().add(caption);
        }
    }

    private void initCaption(){
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 24 arial;");
    }

    private void initDataPieChart(){
        // set line length of label
        this.dataPieChart.setLabelLineLength(10.0f);

        // make labels visible
        this.dataPieChart.setLabelsVisible(true);

        // set start angle
        this.dataPieChart.setStartAngle(20.0f);

        // set anticlockwise
        this.dataPieChart.setClockwise(false);

        this.dataPieChart.setLabelLineLength(30);

    }

    //保留两位小数
    private double changDouble(double x){
        return new BigDecimal(x).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
