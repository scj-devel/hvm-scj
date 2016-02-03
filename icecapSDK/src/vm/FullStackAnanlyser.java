package vm;

import icecaptools.IcecapCompileMe;
import util.StringUtil;

public class FullStackAnanlyser implements StackAnalyser {

	private int numberOfStacksCreated;

	static int[][] stacks;

	static {
		stacks = new int[10][];
	}

	@Override
	public void addStack(int[] stack) {
		addStack(stack, true);
	}

	private void addStack(int[] stack, boolean clear) {
		if (stack != null) {
			if (numberOfStacksCreated < stacks.length) {
				if (clear) {
					for (short index = 0; index < stack.length; index++) {
						stack[index] = index;
					}
				}
				stacks[numberOfStacksCreated++] = stack;
			}
		}
	}

	@Override
	public void reportStackUsage() {
		Memory.executeInTrackingArea(new Runnable() {

			@Override
			@IcecapCompileMe
			public void run() {
				int[] mainStack = get_java_stack_array();
				addStack(mainStack, false);
				devices.Console.print(StringUtil.constructString("Created ", numberOfStacksCreated));
				devices.Console.println(" stacks");
				for (byte index = 0; index < stacks.length; index++) {
					if (stacks[index] != null) {
						analyseStack(stacks[index]);
						devices.Console.print(StringUtil.constructString("stack ", index));
						devices.Console.print(StringUtil.constructString("[", best_start_of_unused_area));
						devices.Console.print(StringUtil.constructString(", ", best_end_of_unused_area));
						devices.Console.print(StringUtil.constructString("][", stacks[index].length));
						devices.Console.println("]");
					}
				}
			}
		});
	}

	private static native int[] get_java_stack_array();

	private static final int USEDSTACKCELL = 10;
	private static final int UNUSEDSTACKCELL = 11;

	private int state;

	private int best_start_of_unused_area;
	private int best_end_of_unused_area;

	private void analyseStack(int[] stack) {
		state = USEDSTACKCELL;
		int index = 0;
		int start_of_unused_area = 0;
		int end_of_unused_area = 0;

		best_start_of_unused_area = 0;
		best_end_of_unused_area = 0;

		while (index < stack.length) {
			switch (state) {
			case USEDSTACKCELL:
				if (stack[index] == index) {
					start_of_unused_area = index;
					state = UNUSEDSTACKCELL;
				}
				break;
			case UNUSEDSTACKCELL:
				if (stack[index] != index) {
					end_of_unused_area = index;
					state = USEDSTACKCELL;
					updateBest(start_of_unused_area, end_of_unused_area);
				}
				break;
			}
			index++;
		}
		if (state == UNUSEDSTACKCELL) {
			updateBest(start_of_unused_area, end_of_unused_area);
		}
	}

	private void updateBest(int start_of_unused_area, int end_of_unused_area) {
		if (end_of_unused_area - start_of_unused_area > best_end_of_unused_area - best_start_of_unused_area) {
			best_end_of_unused_area = end_of_unused_area;
			best_start_of_unused_area = start_of_unused_area;
		}
	}
}
