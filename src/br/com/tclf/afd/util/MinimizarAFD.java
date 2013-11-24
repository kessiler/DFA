package br.com.tclf.afd.util;

import br.com.tclf.afd.model.AFD;
import br.com.tclf.afd.model.State;
import br.com.tclf.afd.model.Transition;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: KESSILER
 * Date: 10/11/13
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public class MinimizarAFD {
    public MinimizarAFD() {}

    public AFD minimizar(AFD afd) {
       // Elimina estados inacessíveis e suas devidas transições
       removeUnreachable(afd.getStates(), afd.getTransitions());
       // Se δ não é total, adiciona um estado T sumidouro e acrescenta transições ao mesmo
       verifytotalTransitions(afd);
       // Verifica estados não equivalentes
       Set<StatesTable> tableStates = new LinkedHashSet<StatesTable>();
       for(State states : afd.getStates()) {

           System.out.println(states.getName());
          //  tableStates.add(new StatesTable())
       }
       return afd;
    }

    private void removeUnreachable(Set<State> states, Set<Transition> transitions) {}

    private void verifytotalTransitions(AFD afd) {}

    static class StatesTable {
        State stateLine, stateColumn;
        String isMarked;

        StatesTable(State stateLine, State stateColumn, String isMarked) {
            this.stateLine = stateLine;
            this.stateColumn = stateColumn;
            this.isMarked    = isMarked;
        }
    }
}
