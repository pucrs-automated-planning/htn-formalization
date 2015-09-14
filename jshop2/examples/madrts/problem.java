import java.util.LinkedList;
import JSHOP2.*;

public class problem
{
	private static String[] defineConstants()
	{
		String[] problemConstants = new String[1];

		problemConstants[0] = "goal";

		return problemConstants;
	}

	private static void createState0(State s)	{
		s.add(new Predicate(1, 0, new TermList(new TermNumber(0.0), TermList.NIL)));
		s.add(new Predicate(1, 0, new TermList(new TermNumber(1.0), TermList.NIL)));
		s.add(new Predicate(1, 0, new TermList(new TermNumber(2.0), TermList.NIL)));
		s.add(new Predicate(2, 0, new TermList(new TermNumber(0.0), new TermList(new TermNumber(1.0), TermList.NIL))));
		s.add(new Predicate(2, 0, new TermList(new TermNumber(1.0), new TermList(new TermNumber(1.0), TermList.NIL))));
		s.add(new Predicate(2, 0, new TermList(new TermNumber(2.0), new TermList(new TermNumber(1.0), TermList.NIL))));
		s.add(new Predicate(0, 0, new TermList(new TermNumber(0.0), new TermList(new TermNumber(0.5), new TermList(new TermNumber(0.5), new TermList(new TermNumber(0.0), TermList.NIL))))));
		s.add(new Predicate(0, 0, new TermList(new TermNumber(1.0), new TermList(new TermNumber(0.5), new TermList(new TermNumber(0.5), new TermList(new TermNumber(0.0), TermList.NIL))))));
		s.add(new Predicate(0, 0, new TermList(new TermNumber(2.0), new TermList(new TermNumber(0.5), new TermList(new TermNumber(0.5), new TermList(new TermNumber(0.0), TermList.NIL))))));
	}

	public static LinkedList<Plan> getPlans()
	{
		LinkedList<Plan> returnedPlans = new LinkedList<Plan>();
		TermConstant.initialize(4);

		Domain d = new madrts();

		d.setProblemConstants(defineConstants());

		State s = new State(3, d.getAxioms());

		JSHOP2.initialize(d, s);

		TaskList tl;
		SolverThread thread;

		createState0(s);

		tl = new TaskList(1, true);
		tl.subtasks[0] = new TaskList(new TaskAtom(new Predicate(1, 0, new TermList(new TermList(new TermNumber(0.0), new TermList(new TermNumber(1.0), new TermList(new TermNumber(2.0), TermList.NIL))), new TermList(new TermNumber(0.1), new TermList(new TermNumber(0.1), TermList.NIL)))), false, false));

		thread = new SolverThread(tl, 2147483647);
		thread.start();

		try {
			while (thread.isAlive())
				Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		returnedPlans.addAll( thread.getPlans() );

		return returnedPlans;
	}

	public static LinkedList<Predicate> getFirstPlanOps() {
		return getPlans().getFirst().getOps();
	}
}