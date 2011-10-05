package ch.ntb.inf.deep.runtime.mpc555.test;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.Task;
import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI1;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI2;

/* CHANGES:
 * 07.09.2011	NTB/MZ	Created (based on HLC1395Demo)
 */

/**
 * Test application for the HLC1395 experimental module.
 * The application reads periodically the values of four sensors
 * and print them every second to the SCI2.
 * Connecting diagram:
 * <pre>Trigger Pin    -- MPIOB5
 * Address Pin A  -- MPIOB6
 * Address Pin B  -- MPIOB7
 * Sensor out pin -- AN59</pre>
 * 
 * @author Martin Zueger
 */
public class HLC1395Test extends Task {
	
	public void action() {
		for(int i = 0; i < 4; i++) {
			System.out.print(HLC1395Pulsed.read(i));
			System.out.print('\t');
		}
		System.out.println();
	}
	
	static {
		// Initialize HLC1395Pulsed driver for 4 sensors and start reading values
		HLC1395Pulsed.init(4, 0x50076, 59); // initialize 4 sensors (addrPin0 = MPIOB6, addrPin1 = MPIOB7, trgPin = MPIOB5, analogInPin = AN59)
		HLC1395Pulsed.start();
		
		// Initialize SCI1 and set stdout to SCI1
		SCI2.start(9600, SCI1.NO_PARITY, (short)8);
		System.out = new PrintStream(SCI2.out);
		
		System.out.println("HLC1295-Test");
		System.out.println();
		System.out.println("  Connections:");
		System.out.println("  Trigger Pin    -- MPIOB5");
		System.out.println("  Address Pin A  -- MPIOB6");
		System.out.println("  Address Pin B  -- MPIOB7");
		System.out.println("  Sensor out pin -- AN59");
		System.out.println();
		System.out.println("1:\t2:\t3:\t4:");
		
		// Create and install demo task
		Task demoTask = new HLC1395Test();
		demoTask.period = 1000;
		Task.install(demoTask);
	}
}