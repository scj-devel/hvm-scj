package util;

public class URL {
	private String raw;

	private String scheme;
	private String schemeSpecificPart;

	private String target;

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
			}
			else
			{
				target = schemeSpecificPart.substring(0, idx);
			}
		}
		return target;
	}
}
