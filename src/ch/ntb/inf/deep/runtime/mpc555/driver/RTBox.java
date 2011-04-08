package ch.ntb.inf.deep.runtime.mpc555.driver;

/*changes:
 * 18.05.06	NTB/HS	chn => channel
 * 24.3.05,ED	Emulationsanweisungen gel�scht
 */

/**
 * Dieser Treiber wird f�r die Regelungstechnik-Box verwendet.<br>
 * Diese "Black-Box" beinhaltet im Wesentlichen einen MPC555 als Prozessor.
 * Davon werden je 8 analoge und digitale Ein- und Ausg�nge auf Buchsen
 * herausgef�hrt.<br>
 * Ebenso gibt es zwei Anschl�sse f�r serielle Schnittestellen (RS232) sowie
 * eine parallele Schnittstelle f�r das Debugging (BDM).<br>
 * Weitere Informationen sind auf dem Infoportal erh�ltlich.
 */
public class RTBox {
	
	static final int FQDChannel=6;

	
	

	/**
	 * Ausgabe eines TTL-Signals auf dem digitalen Ausgang.<br>
	 * Es wird auf dem gew�hlten Kanal <code>channel</code> der in
	 * <code>level</code> �bergebene Wert ausgegeben. Die Kan�le sind mit 0..7
	 * bezeichnet. Dabei entspricht der Wert <code>true</code> einem logischen
	 * Signal <code>1</code>.
	 * 
	 * @param channel
	 *            Kanal, auf welchem das TTL Signal ausgegeben werden soll.
	 * @param level
	 *            TTL-Signal, welches ausgegeben werden soll. <code>true</code>
	 *            entspricht einem logischen Signal <code>1</code>.
	 */
	public static void dioOut(int channel, boolean level) {
		TPU_DIO.set(true,channel+8,level);
	}

	/**
	 * Gibt das TTL-Signal am gew�htlen digitalen Eingang zur�ck.<br>
	 * Das digitale Signal wird vom gegebenen Kanal <code>channel</code>
	 * eingelesen. Die Kan�le sind mit 0..7 bezeichnet. Dabei entspricht der
	 * Wert <code>true</code> einem logischen Signal <code>1</code>.
	 * 
	 * @param channel
	 *            Kanal, von welchem das TTL-Signal eingelesen werden soll.
	 * @return Digitales Signal, welches vom gegebenen Kanal <code>channel</code>
	 *         eingelesen wird.
	 */
	public static boolean dioIn(int channel) {
		return TPU_DIO.get(true,channel);
	}

	/**
	 * Ausgabe eines analogen Signals.<br>
	 * Es wird ein analoges Signal auf den gegebenen Kanal <code>channel</code>
	 * ausgegeben. Die Kan�le sind mit 0..7 bezeichnet. Der Wertebereich f�r
	 * <code>val</code> betr�gt -2048..2047. Dabei entspricht -2048 einem Wert
	 * von -10 V und 2047 einem Wert von +10 V.
	 * 
	 * @param channel
	 *            Kanal, auf welchem das analoge Signal ausgegeben werden soll.
	 * @param val
	 *            Wert, welcher auf dem analogen Kanal ausgegeben werden soll.
	 */
	public static void analogOut(int channel, int val) {
		DAC7614.write(channel,val+2048);
	}

	/**
	 * Gibt den analogen Wert eines Eingangs zur�ck.<br>
	 * Das analoge Signal wird vom gegebenen Kanal <code>channel</code>
	 * eingelesen. Die Kan�le sind mit 0..7 bezeichnet. Der Wertebereich f�r den
	 * zur�ckgegebenen Wert betr�gt -512..511. Dabei entspricht -512 einem
	 * Wert von -10 V und 511 einem Wert von +10 V.
	 * 
	 * @param channel
	 *            Kanal, von welchem das analoge Signal eingelesen werden soll.
	 * @return Analoges Signal, welches eingelesen wurde.
	 */
	public static int analogIn(int channel) {
		return QADC_AIN.read(true,channel+8)-512;
	}

	/**
	 * Gibt den digitalen Z�hlerwert f�r einen Encoder zur�ck.<br>
	 * Die digitalen Kan�le 6 und 7 haben eine spezielle Verwendung. Sie k�nnen
	 * als Z�hlereing�nge f�r einen Encoder verwendet werden.
	 * 
	 * @return Ausgelesener Z�hlerwert.
	 */
	public static int getEncCount() {
		return TPU_FQD.getPosition(true,FQDChannel);
	}

	/**
	 * Setzt den digitalen Z�hlerwert f�r einen Encoder.<br>
	 * Die digitalen Kan�le 6 und 7 haben eine spezielle Verwendung. Sie k�nnen
	 * als Z�hlereing�nge f�r einen Encoder verwendet werden.<br>
	 * Der Z�hlerwert wird mit dem Wert <code>pos</code> gesetzt.
	 * 
	 * @param pos
	 *            Wert, mit welchem der Encoder-Z�hlerwert gesetzt werden soll.
	 */
	public static void setEncCount(int pos) {
		TPU_FQD.setPosition(true,FQDChannel,pos);
	}
	
	static{
		DAC7614.init();
		for(int i=0; i<8; i++){
			TPU_DIO.init(true,i,false);
			TPU_DIO.init(true,i+8,true);
		}
		TPU_FQD.init(true,FQDChannel);
		TPU_FQD.setPosition(true,FQDChannel,0);
		QADC_AIN.init(true);
	}
}
