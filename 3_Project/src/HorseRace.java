public class HorseRace {
	private Horse horse1, horse2, horse3, horse4, horse5, horse6;
	
	private Thread horse1_t, horse2_t, horse3_t, horse4_t, horse5_t, horse6_t;
	
	public HorseRace() {
		horse1 = new Horse();
		horse2 = new Horse();
		horse3 = new Horse();
		horse4 = new Horse();
		horse5 = new Horse();
		horse6 = new Horse();
		
		horse1_t = new Thread(horse1);
		horse2_t = new Thread(horse2);
		horse3_t = new Thread(horse3);
		horse4_t = new Thread(horse4);
		horse5_t = new Thread(horse5);
		horse6_t = new Thread(horse6);
	}
	
	public void StartRace() {
		horse1_t.start();
		horse2_t.start();
		horse3_t.start();
		horse4_t.start();
		horse5_t.start();
		horse6_t.start();
	}
	
	public Horse[] GetHorses() {
		Horse[] horses = {horse1, horse2, horse3, horse4, horse5, horse6};
		return horses;
	}
}
