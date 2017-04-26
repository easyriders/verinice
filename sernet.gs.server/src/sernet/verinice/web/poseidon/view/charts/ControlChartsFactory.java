/*******************************************************************************
 * Copyright (c) 2017 Benjamin Weißenfels.
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *     @author Benjamin Weißenfels <bw[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package sernet.verinice.web.poseidon.view.charts;

import static sernet.gs.web.Util.getMessage;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import sernet.verinice.web.poseidon.services.CompareByTitle;

/**
 * @author Benjamin Weißenfels <bw[at]sernet[dot]de>
 *
 */
public class ControlChartsFactory {

    private static final String MESSAGES = "sernet.verinice.web.WebMessages";

    private Map<String, Number> data;

    public ControlChartsFactory(Map<String, Number> data){
        this.data = sortDataKeys(data);
    }

    private Map<String, Number> sortDataKeys(Map<String, Number> unsorted) {
        SortedMap<String, Number> sortedData = new TreeMap<>(new CompareByTitle());
        sortedData.putAll(unsorted);
        return sortedData;
    }

    public PieChartModel getPieChartModel(){
        PieChartModel model = new PieChartModel();
        model.setData(ChartUtils.translateMapKeyLabel(data));
        model.setExtender("verinicePie");
        model.setSeriesColors(ChartUtils.getColors(data.keySet()));
        return model;
    }

    public BarChartModel getBarChart(){

        BarChartModel barChartModel = new BarChartModel();

        ChartSeries series = new ChartSeries();
        for (Map.Entry<String, Number> entry : ChartUtils.translateMapKeyLabel(data).entrySet()) {
            series.set(entry.getKey(), entry.getValue());
        }

        barChartModel.addSeries(series);
        barChartModel.setLegendPlacement(LegendPlacement.OUTSIDE);

        Axis yAxis = barChartModel.getAxis(AxisType.Y);
        yAxis.setMax(ChartUtils.getMax(data.values()));
        yAxis.setLabel(getMessage(MESSAGES, "chart.legend.safeguard"));
        yAxis.setTickCount(5);

        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setLabel(getMessage(MESSAGES, "chart.legend.states"));

        barChartModel.setExtender("veriniceVerticalBar");
        barChartModel.setSeriesColors(ChartUtils.getColors(data.keySet()));

        return barChartModel;
    }


    public HorizontalBarChartModel getHorizontalBarModel() {

        HorizontalBarChartModel horizontalBarModel = new HorizontalBarChartModel();

        ChartSeries series = new ChartSeries();
        for (Map.Entry<String, Number> entry : ChartUtils.translateMapKeyLabel(data).entrySet()) {
            series.set(entry.getKey(), entry.getValue());
        }

        horizontalBarModel.addSeries(series);
        horizontalBarModel.setLegendPlacement(LegendPlacement.OUTSIDE);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setMax(ChartUtils.getMax(data.values()));
        xAxis.setLabel(getMessage(MESSAGES, "chart.legend.safeguard"));
        xAxis.setTickCount(5);

        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel(getMessage(MESSAGES, "chart.legend.states"));

        horizontalBarModel.setExtender("veriniceHorizontalBar");
        horizontalBarModel.setSeriesColors(ChartUtils.getColors(data.keySet()));
        horizontalBarModel.setShadow(false);

        return horizontalBarModel;
    }

}
