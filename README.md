# htn-formalization

:suspect: HTN Planning Assignment Reference

**Felipe Meneguzzi**  
**Mauricio Magnaguagno (PhD Student)**  
**Ramon Fraga Pereira (PhD Student)**

Assigned: Wednesday, June 2  
Due: Wednesday, June 9, 9h50am (final deadline Friday, June 11, with discounts)  

Domain adapted from the [2016 The Fifth International Competition on Knowledge Engineering for Planning and Scheduling (ICKEPS)](https://helios.hud.ac.uk/scommv/ICKEPS/Scenarios.pdf), credits to [Dr. Lukas Chrpa](https://helios.hud.ac.uk/scomlc/) for the scenario.

## RPG Domain

<!-- You must work on this project **individually**. You are free to discuss high-level design issues with the people in your class, but every aspect of your actual formalisation must be entirely your own work. Furthermore, there can be no textual similarities in the reports generated by each student. Plagiarism, no matter the degree, will result in forfeiture of the entire grade of this assignment. -->

For this assignment, you can choose between two open source implementation of HTN planning: HyperTensioN available at [GitHub](https://github.com/Maumagnaguagno/HyperTensioN "This is the planner developed at our lab, winner of the 2020 IPC"), or [PANDA](https://www.uni-ulm.de/en/in/ki/research/software/panda/). You can obtain a copy of this software from their respective project sites. Your deliverables must be handed in via Moodle using the correspondingly named upload rooms. At the end of this assignment, you will upload **one zip file** containing the domain and problem specification files in the **.hddl** format, specifically:

- One file named `rpg.hddl` containing the domain encoding;
- three files containing the problems you formalized, named `pbX.hddl`, where `X` is the sequential number of your problem, e.g. `pb1.hddl`, `pb2.hddl`, `pb3.hddl`;
- the same number of files (with similar names) showing the traces created for the problems you created (e.g. `pb1.hddl` and `pb1.plan`); and
- one report following the guidelines detailed below.

## Overview

In this assignment you will formalize HTN methods for the RPG domain.

> [Recall the RPG Domain description you formalized in PDDL](https://github.com/pucrs-automated-planning/pddl-formalization).

For this project, you will model the domain knowledge encoded as methods in an HTN domain specification. The hero has four operations available to him/her:

- The hero can **move** to an adjacent room (connected by a corridor) that has not been destroyed (i.e., the hero has not yet visited the room);
- The hero can **pickup** the sword if it is present in the room the hero is currently in and the hero is empty-handed;
- S/He can also **destroy** the sword the hero currently holds. However, this can have unpleasant effects if done in a room with a trap or a monster;
- Finally, the hero can **disarm** a trap, if there is a trap in the room the hero is in and the hero is empty-handed (does not hold a sword), then the hero can disarm it.

You can (and we encourage you to) use the operators (action templates) as defined below.
<!-- If you choose to use [PANDA](https://www.uni-ulm.de/en/in/ki/research/software/panda/), the actions should work exactly the same as in PDDL. -->

```LISP
    ; Move from one room to another with sword
  (:action move-with-sword
    :parameters (?room1 ?room2 - object)
    :precondition (and
      (connected ?room1 ?room2)
      (at ?room1)
      (not (destroyed ?room2))
      (not (trap-in ?room1))
      (not (handempty))
    )
    :effect (and
      (destroyed ?room1)
      (not (at ?room1))
      (at ?room2)
    )
  )

  ; Move from one room to another
  (:action move
    :parameters (?room1 ?room2 - object)
    :precondition (and
      (connected ?room1 ?room2)
      (at ?room1)
      (not (destroyed ?room2))
      (not (trap-in ?room1))
      (not (monster-in ?room2))
    )
    :effect (and
      (not (at ?room1))
      (at ?room2)
      (destroyed ?room1)
    )
  )

  ; Pick sword present in room
  (:action pickup
    :parameters (?room - object)
    :precondition (and
      (handempty)
      (sword-in ?room)
      (at ?room)
    )
    :effect (and
      (not (sword-in ?room))
      (not (handempty))
    )
  )

  ; Destroy sword to obtain empty_hands
  (:action destroy
    :parameters (?room - object)
    :precondition (and
      (at ?room)
      (not (trap-in ?room))
      (not (monster-in ?room))
    )
    :effect (and
      (handempty)
    )
  )

  ; Disarm trap before leaving the room
  (:action disarm
    :parameters (?room - object)
    :precondition (and
      (handempty)
      (at ?room)
      (trap-in ?room)
    )
    :effect (and
      (not (trap-in ?room))
    )
  )
```

Your assignment is to develop a domain file using the specification above, and encode domain knowledge that will help the HTN planner to finish the task. The following hints may be useful, but you are welcome to use your creativity as long as you adhere to the specification above:

- You do not need extra actions (in fact, they are already prepared for you in this package under [rpg.hddl](hddl/rpg.hddl)), but you may need to create “bookkeeping” actions to keep track of the locations you have tried to decompose methods to;
<!-- - In JSHOP2, bookkeeping actions are encoded using double exclamation marks ```(:operator (!!mark ?room) ...)``` -->
- You will need a recursive method to make the agent travel all the way to a particular location;
- There are domains that do search over grid space in the examples included in HyperTensioN, you may want to look at the [search]([jshop2/examples/rover](https://github.com/Maumagnaguagno/HyperTensioN/tree/master/examples/search)) domain; and
- You may need specific methods to travel that use the operator above.

## Problem Instances

We illustrate the problem instances that you need to model in HTN in the pictures below, once you are done encoding your domain file. The text that accompanies each image is self-explanatory; remember, you must model corridors between rooms, rooms for the hero to move through, and you can only move between connected rooms and visit each room only **once**.
We specify the problems so that cells stand for rooms and edges between them represent corridors.
“I” is the hero's initial position, “G” is hero's desired goal position, “S” indicates a sword, “M” is a monster, and “T” stands for trap.
Notice that these problems **are not** the same as in the PDDL assignment.

### Problem 1

![Problem 1](pb4.png "Problem 1")

### Problem 2

![Problem 2](pb5.png "Problem 2")

### Problem 3

![Problem 3](pb6.png "Problem 3")

## Grading

In order to properly evaluate your work and thought process, you will write a short ```README.md``` file explaining your encoding and experiments.
The report must have the following sections:

- An introduction with your understanding of the problem domain, outlining the remainder of the file;

- Two domain formalization sections explaining your approach to formalizing the problems from Section [Problem Instances](#problem-instances).

- One experimentation section where you measure the performance of the planner using your action formalization for each of the domains, on multiple problems.

- One conclusion section, where you will summarize your experience in encoding planning domains and discuss the performance of the planner, and any limitations encountered in solving the problems you encoded.

<!-- In order to properly evaluate your work and thought process, you will write a 2-page report in the AAAI conference format explaining your encoding and experiments. These guidelines are to be followed **exactly**. **Reports that are less than two pages of actual content, or not in format will receive 0 marks for the report criterion.** This report will be included in the deliverables of **Part B** of the assignment. [The formatting instructions are available at ShareLatex (AAAI Press)](https://pt.sharelatex.com/templates/journals/aaai-press). The report must have the following sections:

-   An introduction with your understanding of the problem domain, outlining the remainder of the paper;

-   Two domain formalisation sections explaining your approach to formalising the problems from the problems instance section 

-   One experimentation section where the performance of the HTN planner is measured using your domain knowledge for each of the domains, on multiple problems.

-   One conclusion section, where you will summarise your experience in encoding planning domains and discuss the performance of <span style="font-variant:small-caps;">JShop2</span>, and any limitations encountered in solving the problems you encoded. -->

Grading will take into consideration elements of your encoding, experimentation and reporting of the work done. The criteria, as well as their weight in the final grade is as follows:

- Domain Encoding (30%) — correctness of the domain knowledge encoding, in relation to the domain specification above;
- Problem specifications (20%) — correctness of the problem specifications used for the experiments, particularly the initial state specification, as missing predicates here will jeopardize the planner’s ability to solve your problem;

- Overall report readability (20%) — how accessible and coherent your explanation of your encoding is;

- Experiments (30%) — how coherent the proposed experiments are in measuring the performance of the planner you chose, notice that this criterion is complementary to the one regarding problem specification.

## Important Information

**Corrections:** From time to time, students or staff find errors (e.g., typos, unclear instructions, etc.) in the assignment specification. In that case, a corrected version of this file will be produced, announced, and distributed for you to commit and push into your repository. Because of that, you are NOT to modify this file in any way to avoid conflicts.

**Late submissions & extensions:** You have a 24-hour grace period with a penalty of 10% of the maximum mark, which increases to 50% until 48 hours after the due date, and 100% penalty thereafter. Extensions will only be permitted in _exceptional_ circumstances.

**About this repo:** You must ALWAYS keep your fork private and never share it with anybody in or outside the course, even after the course is completed. You are not allowed to make another repository copy outside the provided GitHub Classroom without the written permission of the teaching staff.

> **_Please do not distribute or post solutions to any of the projects and notebooks._**

**Collaboration Policy:** You must work on this project **individually**.
You are free to discuss high-level design issues with the people in your class, but every aspect of your actual formalization must be entirely your own work.
Furthermore, there can be no textual similarities in the reports generated by each group.
Plagiarism, no matter the degree, will result in forfeiture of the entire grade of this assignment.

**We are here to help!:** We are here to help you! But we don't know you need help unless you tell us. We expect reasonable effort from you side, but if you get stuck or have doubts, please seek help by creating an issue in the repository and assigning it to the instructor. Always keep the most updated version of your code pushed to Git so when you create an issue, the teaching staff can look into your code to help.

**Silence Policy:** A silence policy will take effect **48 hours** before this assignment is due. This means that no question about this assignment will be answered, whether it is asked on Moodle, by email, or in person. Use the last 48 hours to wrap up and finish your project quietly as well as possible if you have not done so already. Remember it is not mandatory to do all perfect, try to cover as much as possible. By having some silence we reduce anxiety, last minute mistakes, and unreasonable expectations on others.

Please remember to follow all the submission steps as per project specification.

## Miscellaneous Advice

Here are some lessons we learned in creating our own solution and writing papers/reports:

<!-- - As a first step, you should at least look at the sample domain and problem files included in the <span style="font-variant:small-caps;">JShop2</span> package. -->
- As a first step, you should at least look at the sample domain and problem files included in the [HyperTensioN](https://github.com/Maumagnaguagno/HyperTensioN/tree/master/examples) repository.
- The best way to figure out how to model a domain and associated problems is to look at these examples. If you feel the need for documentation, there is a complete manual included in the package from Sourceforge as well as the one from moodle.
<!-- - Tables and graphs are a useful tool to show runtime performance of software. GnuPlot is an excellent (and free) graph making software, available at [Gnuplot](http://www.gnuplot.info/); -->
- In order to evaluate the performance of a planning encoding, you need to specify problems with most of the parameters locked in, and measure runtime as one parameter increases (e.g. number of locations, number of containers, etc);
- Pasting your entire domain specification (or problems) into the paper does not count as content (now you cannot say you were not warned);
- Overly large figures used to simply fill space in the report are also not a good idea;
- PANDA's parser can be finicky, be aware of that if you choose to use PANDA;
<!-- We aren't using LaTeX for this anymore -->
<!-- - Reviewers have a more pleasant reading experience when papers are generated using <span style="font-variant:small-caps;">LaTeX</span>, it is very easy to spot the difference. -->
