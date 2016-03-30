package image.compressor.matrix.common.image.converter;

public enum ImageFormat {
	PGM("pgm"), JPEG("JPEG"), GIF("GIF"), PNG("PNG");
	private String format;

	private ImageFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}
}
