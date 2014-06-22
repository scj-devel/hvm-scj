package vm;

public interface Scheduler {
	public vm.Process getNextProcess();

	void wait(Object target);

	void notify(Object target);

	/* TODO: void terminated(); */
}
