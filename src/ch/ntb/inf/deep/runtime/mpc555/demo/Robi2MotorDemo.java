package ch.ntb.inf.deep.runtime.mpc555.demo;
import ch.ntb.inf.deep.runtime.mpc555.Task;
import ch.ntb.inf.deep.runtime.mpc555.driver.Robi2;


/*
 * Diese Demo zeigt, wie die Motoren des Roboters angesprochen werden k�nnen.
 * 
 * Beschreibung:
 * 
 * Diese Klasse beinhaltet verschiedene Methoden, welche von Aussen aufgerufen werden k�nnen:
 * 		motorForward(): L�sst beide Motoren vorw�rts laufen.
 * 		motorBack(): L�sst beide Motoren r�ckw�rts laufen.
 * 		turnRight(): Der Roboter dreht sich im Uhrzeigersinn.
 * 		motorStop(): Der Roboter wird angehalten.
 * 
 * Die Klasse erweitert Task. Somit ist es m�glich die action() Methode periodisch aufzurufen. 
 * Das Installieren und Deinstallieren des Tasks wird nun �ber externe Aufrufe get�tigt.
 * 		startTask(): Installiert den Task. Die Methode action() wird periodisch aufgerufen.
 * 		stopTask(): Deinstalliert den Task. Die Methode action() wird nicht mehr periodisch aufgerufen.
 */
public class Robi2MotorDemo extends Task {

	static final int Forward = 0, Stop1 = 1, Backward = 2, Stop2 = 3;	// die unterschiedlichen Zust�nde des Tasks

	static Robi2MotorDemo task;	// das Task-Objekt

	int state;	// der Zustand des Tasks

	public static void motorForward() {
		Robi2.setDrivesSpeedEqual(100);
	}

	public static void motorBack() {
		Robi2.setDrivesSpeedEqual(-100);
	}
	
	public static void turnRight() {
		Robi2.setDrivesSpeedAntidormic(100);
	}

	public static void motorStop() {
		Robi2.stopDrives() ;
	}
	
	public static void startTask() {
		task.state = 0;		// Initialisierung des Task-Zustandes
		Task.install(task);	// Installieren des Tasks
	}

	public static void stopTask() {
		Task.remove(task);
		motorStop();
	}
	
	public void action() {	
		switch (state) {
			case Forward:
				motorForward();
				state = Stop1;
			break;
			case Stop1:
				motorStop();
				state = Backward;
			break;
			case Backward:
				motorBack();
				state = Stop2;
			break;
			case Stop2:
				motorStop();
				state = Forward;
			break;
		}
	}
	
	static {
		task = new Robi2MotorDemo();	// erstellen des Task-Objekts
		task.period = 1000;	// Periodenl�nge des Tasks in ms
	}
}
