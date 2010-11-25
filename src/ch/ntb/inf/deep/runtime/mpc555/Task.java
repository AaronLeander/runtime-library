package ch.ntb.inf.deep.runtime.mpc555;

/*changes:
 * 11.11.10	NTB/GRAU	creation
 */

public class Task {
	public static boolean Done;

	/**
	 * Enth�lt die Nummer des ersten registrierten Fehlers. <br>
	 * <i> 1</i>: Fehler bei der Installation, z.B. negative Periodenl�nge
	 * (period) <br>
	 * <i> 2</i>: Zu viele Task <br>
	 * <i> 3</i>: task ist bereits - oder noch immer - installiert
	 */
	public static short firstErr;

	// ---- Instanzvariablen
	/**
	 * Task-Startverz�gerung [ms].
	 */
	public int time;

	/**
	 * Task-Periode [ms]. Wird die Periode w�hrend des Betriebs ge�ndert, muss nach der 
	* �nderung von <code>Task.period</code> <code>Task.install(Task task)</code> aufgerufen werden. 
	* Ansonsten werden die �nderungen erst nach der n�chsten Ausf�hrung des Tasks �bernommen.
	 */
	public int period;

	/**
	 * Anzahl Aktivierungen dieses Tasks. <br>
	 * wird jedesmal erh�ht, wenn die Methode <i>Do()</i> vom Tasking
	 * aufgerufen wurde.
	 */
	public int nofActivations;

	public int minExecTimeInMicro;

	public int maxExecTimeInMicro;

	public boolean safe, getTime, coalesce;

	public Task() {
	}

	/**
	 * action to be performed by the task
	 */
	public void action() {
	}

	/**
	 * returns system time in milliseconds, time starts at powerup
	 */
	public static int time() {
		return 0;
	}

	/**
	 * Installiert (registriert) einen <i>Task</i>. <br>
	 * Wurde der Task durch einen anderen Task gestoppt, so kann er erst wieder
	 * installiert weren, nachdem der vom Taksking System aus der Liste der
	 * registrierten entfernt wurde (siehe auch <i>setStop()</i> ).
	 */
	public static void install(Task task) {
	}

	/**
	 * Entfernt einen bereits installierten <i>Task</i>.
	 * 
	 * @param task
	 *            Der zu entfernende Task.
	 */
	public static void remove(Task task) {
	}
}
