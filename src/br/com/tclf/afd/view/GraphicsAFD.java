package br.com.tclf.afd.view;

import br.com.tclf.afd.model.AFD;
import br.com.tclf.afd.model.State;
import br.com.tclf.afd.model.Transition;
import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: KESSILER
 * Date: 24/11/13
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public class GraphicsAFD {
    private mxGraph graph;
    private mxGraphComponent graphComponent;
    private mxParallelEdgeLayout layout;
    private AFD afd;
    private int nextInt = 1;
    private final int CELL_RADIUS = 80;

    public GraphicsAFD() {}

    public void setAFD(AFD afd) {
        this.afd = afd;
    }

    private void setNextInt(int nextInt) {
        this.nextInt = nextInt;
    }

    public JComponent drawAFD() {

        createGraph();
        showAFD();
        return graphComponent;
    }

    public void redrawAFD() {
        graph.getModel().beginUpdate();
        ((mxGraphModel) graph.getModel()).clear();
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setAntiAlias(true);
        graphComponent.setEnabled(false);
        graphComponent.getGraph().setCellsEditable(false);
        graph.getModel().endUpdate();
        setNextInt(1);
        showAFD();

    }

    private void showAFD() {
        for(State state : afd.getStates()) {
            addState(state);
        }
        Set<String> loops = new HashSet<String>();
        for(Transition transition : afd.getTransitions()) {
            if(transition.getStateSource().getName().equals(transition.getStateDestination().getName())) {
                loops.add(transition.getStateSource().getName());
            } else {
                Object source = ((mxGraphModel)graph.getModel()).getCell(transition.getStateSource().getName());
                Object target = ((mxGraphModel)graph.getModel()).getCell(transition.getStateDestination().getName());
                addTransition(transition.getCharacter(), source, target);
            }
            layout.execute(graph.getDefaultParent());
        }
        if(loops.size() > 0) {
            for(String nameState : loops) {
                String transitionsName = "";
                for(Transition transition : afd.getTransitions()) {
                    if(transition.getStateSource().getName().equals(nameState) && transition.getStateDestination().getName().equals(nameState)) {
                        transitionsName += transition.getCharacter() + ", ";
                    }
                }
                Object source = ((mxGraphModel)graph.getModel()).getCell(nameState);
                transitionsName = transitionsName.substring(0, transitionsName.length()-2);
                addTransition(transitionsName, source, source);
            }
        }
    }

    public void createGraph() {
        graph = new mxGraph();
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setAntiAlias(true);
        graphComponent.setEnabled(false);
        graphComponent.getGraph().setCellsEditable(false);
        mxStylesheet styleSheet = graph.getStylesheet();
        Map<String, Object> edgeStyle = styleSheet.getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_ROUNDED, true);
        styleSheet.setDefaultEdgeStyle(edgeStyle);
        graph.setStylesheet(styleSheet);
        layout = new mxParallelEdgeLayout(graph);
        mxGraphics2DCanvas.putShape("stateInitial", new StateInitialView());
        mxGraphics2DCanvas.putShape("stateInitialAndFinal", new StateInitialAndFinalView());
    }


    public int getNextInt() {
        return nextInt++;
    }

    private void addState(State state) {
        int valueCalcXandY = this.getNextInt();
        int columns = 2 + graphComponent.getBounds().width / (3 * CELL_RADIUS);
        int x = CELL_RADIUS + 2 * CELL_RADIUS * ((valueCalcXandY - 1) % columns);
        int y = CELL_RADIUS + 2 * CELL_RADIUS * ((valueCalcXandY - 1) / columns);
        graph.getModel().beginUpdate();
        try
        {
            if (state.isStateBegin()) {
                graph.insertVertex(graph.getDefaultParent(), state.getName(), state.getName(), x, y, CELL_RADIUS, CELL_RADIUS, "shape=stateInitial;perimeter=ellipsePerimeter");
            } else if (state.isStateEnd()) {
                graph.insertVertex(graph.getDefaultParent(), state.getName(), state.getName(), x, y, CELL_RADIUS, CELL_RADIUS, "shape=doubleEllipse;perimeter=ellipsePerimeter");
            } else if (state.isStateBegin() && state.isStateEnd()){
                graph.insertVertex(graph.getDefaultParent(), state.getName(), state.getName(), x, y, CELL_RADIUS, CELL_RADIUS, "shape=stateInitialAndFinal;perimeter=ellipsePerimeter");
            } else {
                graph.insertVertex(graph.getDefaultParent(), state.getName(), state.getName(), x, y, CELL_RADIUS, CELL_RADIUS, "shape=ellipse;perimeter=ellipsePerimeter");
            }
        }
        finally
        {
            graph.getModel().endUpdate();
        }
    }

    public void addTransition(String name, Object source, Object target) {
        graph.getModel().beginUpdate();
        try
        {
            graph.insertEdge(graph.getDefaultParent(), null, name, source, target);
        }
        finally
        {
            graph.getModel().endUpdate();
        }
    }
}
