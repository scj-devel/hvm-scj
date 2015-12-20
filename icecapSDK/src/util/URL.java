package util;

import java.util.HashMap;
import java.util.StringTokenizer;

public class URL {
	private String raw;

	private String scheme;
	private String schemeSpecificPart;
	private String target;

	private HashMap<String, String> parameters;

	public URL(String name) {
		this.raw = name;
	}

	public String getScheme() throws URLSyntaxException {
		if (scheme == null) {
			int idx = raw.indexOf(":");
			if (idx == -1) {
				scheme = raw;
			} else if (idx == 0) {
				throw new URLSyntaxException();
			} else {
				scheme = raw.substring(0, idx);
			}
		}
		return scheme;
	}

	public String getSchemeSpecificPart() throws URLSyntaxException {
		if (schemeSpecificPart == null) {
			int idx = raw.indexOf(":");
			if (idx == -1) {
				schemeSpecificPart = null;
			} else {
				schemeSpecificPart = raw.substring(idx + 1, raw.length());
			}
		}
		return schemeSpecificPart;
	}

	public String getTarget() throws URLSyntaxException {
		if (target == null) {
			getSchemeSpecificPart();
			if (schemeSpecificPart == null) {
				throw new URLSyntaxException();
			}

			int idx = schemeSpecificPart.indexOf(";");
			if (idx == -1) {
				target = schemeSpecificPart;
			} else {
				target = schemeSpecificPart.substring(0, idx);
			}
		}
		return target;
	}

	public String getParameter(String key) throws URLSyntaxException {
		parseParameters();

		return parameters.get(key);
	}

	private void parseParameters() throws URLSyntaxException {
		if (parameters == null) {
			parameters = new HashMap<String, String>();
			getSchemeSpecificPart();
			int idx = schemeSpecificPart.indexOf(';');
			String parameters = schemeSpecificPart.substring(idx);
			StringTokenizer tokenizer = new StringTokenizer(parameters, ";");
			while (tokenizer.hasMoreElements()) {
				String nextToken = tokenizer.nextToken();
				idx = nextToken.indexOf('=');
				if (idx == -1) {
					throw new URLSyntaxException();
				}
				String key = nextToken.substring(0, idx);
				String value = nextToken.substring(idx + 1);
				this.parameters.put(key, value);
			}
		}
	}
}
