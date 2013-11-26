package br.com.tclf.afd.util;

import br.com.tclf.afd.model.AFD;
import br.com.tclf.afd.model.State;
import br.com.tclf.afd.model.TableStates;
import br.com.tclf.afd.model.Transition;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: KESSILER
 * Date: 10/11/13
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public class MinimizarAFD {
    public MinimizarAFD() {
    }

    public boolean minimizar(AFD afd) {
        boolean minimized = false;
        // Se δ não é total, adiciona um estado T sumidouro e acrescenta transições ao mesmo
//        verifytotalTransitions(afd);
        // Elimina estados inacessíveis e suas devidas transições
//        removeUnreachable(afd);
        // Verifica estados não equivalentes
        // Monta a tabela do AFD
        Set<TableStates> tableStates = new LinkedHashSet<TableStates>();
        Object[] arrStates = afd.getStates().toArray();
        Arrays.sort(arrStates);
        for (int i = 0; i < arrStates.length; i++) {
            for (int j = i + 1; j < arrStates.length; j++) {
                if (equalsStates((State) arrStates[i], (State) arrStates[j])) {
                    tableStates.add(new TableStates(((State) arrStates[i]).getName() + ";" + ((State) arrStates[j]).getName(), "X"));
                } else {
                    tableStates.add(new TableStates(((State) arrStates[i]).getName() + ";" + ((State) arrStates[j]).getName(), ""));
                }
            }
        }
        Map<String, Set<HashSet<State>>> listStates = new HashMap<String, Set<HashSet<State>>>();
        // Análise agora cada estado não marcado.
        for (Iterator<TableStates> tableStateIterator = tableStates.iterator(); tableStateIterator.hasNext();) {
            TableStates entry  = tableStateIterator.next();
            if (entry.getValue().isEmpty()) {
                // faz um split pegando o nome de cada estado para fazer a análise das transições
                String[] nameStates = entry.getKey().split(";");
                for (String character : afd.getAlphabet()) {
                    Transition transitionA = searchTransition(afd, nameStates[0], character);
                    Transition transitionB = searchTransition(afd, nameStates[1], character);
                    if(!transitionA.getStateDestination().getName().equals(transitionB.getStateDestination().getName())) {
                        String keyHash = transitionA.getStateDestination().getName() + ";" + transitionB.getStateDestination().getName();
                        if(verifyStateMarked(tableStates, keyHash)) {
                            markState(tableStates, transitionA.getStateSource().getName() + ";" + transitionB.getStateSource().getName());
                            if(listStates.containsKey(transitionA.getStateSource().getName() + ";" + transitionB.getStateSource().getName())) {
                                for(Iterator<HashSet<State>> iteratorState = listStates.get(transitionA.getStateSource().getName() + ";" + transitionB.getStateSource().getName()).iterator(); iteratorState.hasNext();) {
                                    HashSet<State> states = iteratorState.next();
                                    Object[] statesArray = states.toArray();
                                    Arrays.sort(statesArray);
                                    markState(tableStates, ((State) statesArray[0]).getName() + ";" + ((State) statesArray[1]).getName());
                                }
                            }
                        } else {
                            if (listStates.containsKey(keyHash)) {
                                HashSet<State> states = new HashSet<State>();
                                states.add(transitionA.getStateSource());
                                states.add(transitionB.getStateSource());
                                listStates.get(keyHash).add(states);
                            } else {
                                Set<HashSet<State>> list = new HashSet<HashSet<State>>();
                                HashSet<State> states = new HashSet<State>();
                                states.add(transitionA.getStateSource());
                                states.add(transitionB.getStateSource());
                                list.add(states);
                                listStates.put(keyHash, list);
                            }
                        }
                    }
                }
            }
        }
        // Análise quem não foi marcado
        for (Iterator<TableStates> tableStateIterator = tableStates.iterator(); tableStateIterator.hasNext();) {
            TableStates entry  = tableStateIterator.next();
            if (entry.getValue().isEmpty()) {
                String[] nameStates = entry.getKey().split(";");
                State stateA   = searchState(afd, nameStates[0]);
                State stateB   = searchState(afd, nameStates[1]);
                if(stateA != null && stateB != null) {
                    State newState = new State(nameStates[0] + nameStates[1]);
                    if(stateA.isStateEnd() || stateB.isStateEnd()) {
                        newState.setStateEnd(Boolean.TRUE);
                    }
                    // Remove os estados antigos e recria as novas transições
                    afd.removeState(stateA);
                    afd.removeState(stateB);
                    afd.addState(newState);
                    List<Transition> transitions = new ArrayList<Transition>();
                    for (String character : afd.getAlphabet()) {
                        for(int i = 0; i < nameStates.length; i++) {
                            transitions.addAll(listTransitions(afd, nameStates[i], character));
                        }
                    }
                    for(Iterator <Transition> transitionIterator = transitions.iterator(); transitionIterator.hasNext();) {
                        Transition transition = transitionIterator.next();
                        if(transition.getStateSource().getName().equals(nameStates[0]) || transition.getStateSource().getName().equals(nameStates[1])) {
                            Transition newTransition = new Transition(newState, transition.getCharacter(), transition.getStateDestination());
                            if (!existsTransition(afd, newTransition)) {
                                afd.addTransition(newTransition);
                            }
                        } else {
                            Transition newTransition = new Transition(transition.getStateSource(), transition.getCharacter(), newState);
                            if (!existsTransition(afd, newTransition)) {
                                afd.addTransition(newTransition);
                            }
                        }
                        afd.removeTransition(transition);
                    }
                    for (String character : afd.getAlphabet()) {
                        for(Transition transition: listTransitions(afd,stateA.getName(), character)) {
                            afd.removeTransition(transition);
                        }
                        for(Transition transition: listTransitions(afd,stateB.getName(), character)) {
                            afd.removeTransition(transition);
                        }
                    }
                    minimized = true;
                }
            }
        }
        return minimized;
    }

    private Transition searchTransition(AFD afd, String nameState, String alphabet) {
        for (Iterator<Transition> entry = afd.getTransitions().iterator(); entry.hasNext();) {
            Transition transition = entry.next();
            if (transition.getStateSource().getName().equals(nameState) && transition.getCharacter().equals(alphabet)) {
                return transition;
            }
        }
        return null;
    }

    private boolean existsTransition(AFD afd, Transition transition) {
        for (Iterator<Transition> entry = afd.getTransitions().iterator(); entry.hasNext();) {
            Transition transitions = entry.next();
            if (transitions.getStateSource().getName().equals(transition.getStateSource().getName()) &&
                transitions.getCharacter().equals(transition.getCharacter()) &&
                transitions.getStateDestination().getName().equals(transition.getStateDestination().getName())) {
                return true;
            }
        }
        return false;
    }

    private List<Transition> listTransitions(AFD afd, String nameState, String alphabet) {
        List<Transition> listTransitions = new ArrayList<Transition>();
        for (Iterator<Transition> entry = afd.getTransitions().iterator(); entry.hasNext();) {
            Transition transition = entry.next();
            if ((transition.getStateSource().getName().equals(nameState) || transition.getStateDestination().getName().equals(nameState)) && transition.getCharacter().equals(alphabet)) {
                listTransitions.add(transition);
            }
        }
        return listTransitions;
    }

    private boolean equalsStates(State stateOne, State stateEnd) {
        return ((stateOne.isStateBegin() || stateOne.isStateEnd()) && !(stateEnd.isStateBegin() || stateEnd.isStateEnd())) ||
                !(stateOne.isStateBegin() || stateOne.isStateEnd()) && (stateEnd.isStateBegin() || stateEnd.isStateEnd())  ||
                (stateOne.isStateBegin() != stateEnd.isStateBegin() && stateEnd.isStateEnd() != stateOne.isStateEnd());
    }

    private State searchState(AFD afd, String nameState) {
        for (State state : afd.getStates()) {
            if (state.getName().equals(nameState)) {
                return state;
            }
        }
        return null;
    }

    private boolean verifyStateMarked(Set<TableStates> table, String keyState) {
        for(TableStates stateTable : table) {
            if(stateTable.getKey().equals(keyState) && stateTable.getValue().equals("X")) {
                return true;
            }
        }
        return false;
    }

    private void markState(Set<TableStates> table, String keyState) {
        for(TableStates stateTable : table) {
            if(stateTable.getKey().equals(keyState)) {
               stateTable.setValue("+");
               return;
            }
        }
    }

    private void removeUnreachable(AFD afd) {}

    private void verifytotalTransitions(AFD afd) {
        for (String character : afd.getAlphabet()) {
            for (Iterator<State> entry = afd.getStates().iterator(); entry.hasNext();) {
                State state = entry.next();
                if(searchTransition(afd, state.getName(), character) == null) {
                    afd.addTransition(new Transition(state, character, new State("ERRO")));
                }
            }
        }
    }
}
