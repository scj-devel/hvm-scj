package util;

import java.util.StringTokenizer;

public class URL {
	private String raw;

	private String scheme;
	private String schemeSpecificPart;
	private byte[] target;

	private KeyValueMap parameters;

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

	public byte[] getTarget() throws URLSyntaxException {
		if (target == null) {
			getSchemeSpecificPart();
			if (schemeSpecificPart == null) {
				throw new URLSyntaxException();
			}

			int idx = schemeSpecificPart.indexOf(";");
			if (idx == -1) {
				target = StringUtil.getBytes(schemeSpecificPart, true);
			} else {
				target = StringUtil.getBytes(schemeSpecificPart.substring(0, idx), true);
			}
		}
		return target;
	}

	public byte[] getParameter(String key) throws URLSyntaxException {
		parseParameters();

		return (byte[]) parameters.get(key);
	}

	private void parseParameters() throws URLSyntaxException {
		if (parameters == null) {
			parameters = new KeyValueMap();
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
				
				byte[] value = StringUtil.getBytes(nextToken.substring(idx + 1), true);
				this.parameters.put(key, value);
			}
		}
	}
}
