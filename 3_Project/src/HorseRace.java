public class HorseRace {
	private Horse horse1, horse2, horse3, horse4, horse5, horse6;
	
	public HorseRace() {
		horse1 = new Horse(this);
		horse2 = new Horse(this);
		horse3 = new Horse(this);
		horse4 = new Horse(this);
		horse5 = new Horse(this);
		horse6 = new Horse(this);
	}
	
	public void StartRace() {
		horse1.AnimateHorse();
		horse2.AnimateHorse();
		horse3.AnimateHorse();
		horse4.AnimateHorse();
		horse5.AnimateHorse();
		horse6.AnimateHorse();
	}
	
	public void ResetRace() {
		horse1.ResetHorse();
		horse2.ResetHorse();
		horse3.ResetHorse();
		horse4.ResetHorse();
		horse5.ResetHorse();
		horse6.ResetHorse();
	}
	
	public Horse[] GetHorses() {
		Horse[] horses = {horse1, horse2, horse3, horse4, horse5, horse6};
		return horses;
	}
}
