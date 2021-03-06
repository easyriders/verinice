/*******************************************************************************
 * Copyright (c) 2010 Alexander Koderman <ak@sernet.de>.
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,    
 * but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *     You should have received a copy of the GNU General Public 
 * License along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Alexander Koderman <ak@sernet.de> - initial API and implementation
 ******************************************************************************/
package sernet.gs.ui.rcp.main.bsi.views.chart;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import sernet.gs.ui.rcp.main.ExceptionUtil;
import sernet.verinice.interfaces.CommandException;
import sernet.verinice.model.common.CnATreeElement;
import sernet.verinice.model.iso27k.ControlGroup;
import sernet.verinice.service.commands.stats.MassnahmenSummaryHome;

/**
 * @author koderman@sernet.de
 */
public class SamtProgressChart extends MaturitySpiderChart {

    private static final float CHART_FOREGROUND_ALPHA = 0.6f;

    /*
     * @see sernet.gs.ui.rcp.main.bsi.views.chart.ISelectionChartGenerator#
     * createChart (sernet.gs.ui.rcp.main.common.model.CnATreeElement)
     */
    @Override
    public JFreeChart createChart(CnATreeElement elmt) {
        if (!(elmt instanceof ControlGroup)) {
            return null;
        }
        super.setElmt((ControlGroup) elmt);
        try {
            return createBarChart(createBarDataset());
        } catch (CommandException e) {
            ExceptionUtil.log(e, Messages.SamtProgressChart_0);
            return null;
        }
    }

    protected JFreeChart createBarChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart3D(null, Messages.SamtProgressChart_1,
                Messages.SamtProgressChart_2, dataset, PlotOrientation.HORIZONTAL, true, true,
                false);

        chart.setBackgroundPaint(Color.white);
        chart.getPlot().setForegroundAlpha(CHART_FOREGROUND_ALPHA);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        plot.getRenderer().setSeriesPaint(0, ChartColor.LIGHT_RED);
        plot.getRenderer().setSeriesPaint(1, ChartColor.LIGHT_GREEN);

        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

        int numUnanswered = dataset.getValue(0, 0).intValue();
        int numAnswered = dataset.getValue(1, 0).intValue();

        int sum = numAnswered + numUnanswered;
        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        if (sum == 0) {
            axis.setTickLabelsVisible(false);
        } else {
            // calculate next multiple of 5
            int upperBound = sum + (5 - sum % 5);
            axis.setUpperBound(upperBound);
        }
        return chart;
    }

    protected CategoryDataset createBarDataset() throws CommandException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        MassnahmenSummaryHome dao = new MassnahmenSummaryHome();
        Map<String, Integer> items = dao.getSamtTopicsProgress(getElmt());
        Set<Entry<String, Integer>> entrySet = items.entrySet();
        for (Entry<String, Integer> entry : entrySet) {
            dataset.addValue(entry.getValue(), entry.getKey(), ""); //$NON-NLS-1$
        }

        return dataset;
    }
}
