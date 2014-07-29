DFA minimization algorithm
=========

## What is DFA?

In automata theory, a branch of theoretical computer science, a deterministic finite automaton (DFA) - also known as deterministic finite state machine—is a finite state machine that accepts/rejects finite strings of symbols and only produces a unique computation (or run) of the automaton for each input string.

![DFA](https://raw.githubusercontent.com/kessiler/DFA/master/resources/dfaExample.png)

## Formal Definition

A deterministic finite automaton M is a 5-tuple, (Q, Σ, δ, q0, F), consisting of:

  * a finite set of states (Q)
  * a finite set of input symbols called the alphabet (Σ)
  * a transition function (δ : Q × Σ → Q)
  * a start state (q0 ∈ Q)
  * a set of accept states (F ⊆ Q)

Let w = a1a2 ... an be a string over the alphabet Σ. The automaton M accepts the string w if a sequence of states, r0,r1, ..., rn, exists in Q with the following conditions:

  1. r0 = q0
  2. ri+1 = δ(ri, ai+1), for i = 0, ..., n−1
  3. rn ∈ F.

In words, the first condition says that the machine starts in the start state q0. The second condition says that given each character of string w, the machine will transition from state to state according to the transition function δ. The last condition says that the machine accepts w if the last input of w causes the machine to halt in one of the accepting states. Otherwise, it is said that the automaton rejects the string. The set of strings M accepts is the language recognized by M and this language is denoted by L(M).


## Application


A program for minimizing DFA through the marking table approach. The table of tags is described below:

a) Table - Build a table listing the different states, in which each pair of states occurs only once in the following format:

![Table](https://raw.githubusercontent.com/kessiler/DFA/master/resources/tagTable.png)

b) Marking of course not equivalent states. Mark entries formed by a final state and a non-final state.

c) Marking of non-equivalent states. For each pair {q, q '} and for each symbol a Σ Î, assume that δ (q, a) = p and δ (q ', a) = v and:
  1.  if p = q ', then q is equivalent aq' for ae should not be marked.; 
  2. Whether p ≠ q 'and the pair {q, q'} is not marked, then {q, q '} is included in a list to further analysis; 
  3.  if p ≠ q 'and the pair {q, q'} is marked: 
    3. {q, q '} is not equivalent and should be marked; 
    3. if {q, q '} is the head node of a list of pairs, then you should mark all pairs from the list.

d) Unification of equivalent states. The couple marked states are not equivalent and can be unified as follows: 
  1. equivalence is transitive; 
  2. Pairs of equivalent non-final states can be unified as a single state not the end; 
  3. Pairs of equivalent final states can be unified as a single state end. 
  4. If any of the initial states is equivalent, then the corresponding unified state is initial.

e) Exclusion of useless states. The useless states should be excluded. A state q is useless if it is not final and from it you can not reach a final state.


## Dependencies


* [JGraphx](https://github.com/jgraph/jgraphx) - is a Java Swing diagramming (graph visualisation) library licensed under the BSD license.


## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request

## License

[MIT License](http://opensource.org/licenses/MIT)
