package dk.visualdesign.shell;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ShellCommand {

	/**
	 * @param command Complete shellcommand with all the needed switches included
	 * @return An Arraylist with two String Vectors, index 0 is std output lines,
	 *         index 1 is the std error lines.
	 */
	protected List<Vector<String>> executeCommand(String command) {

		List<Vector<String>> output = new ArrayList<Vector<String>>(2);

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();

			String s = "";

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			while ((s = stdInput.readLine()) != null) {
				output.get(0).add(s);
			}

			while ((s = stdError.readLine()) != null) {
				output.get(1).add(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}

}