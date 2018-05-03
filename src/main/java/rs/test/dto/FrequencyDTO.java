package rs.test.dto;

public class FrequencyDTO {

	private Double collectionFrequency;
	private Double uploadFrequency;

	public FrequencyDTO() {
	}

	public FrequencyDTO(Double collectionFrequency, Double uploadFrequency) {
		super();
		this.collectionFrequency = collectionFrequency;
		this.uploadFrequency = uploadFrequency;
	}

	public Double getCollectionFrequency() {
		return collectionFrequency;
	}

	public void setCollectionFrequency(Double collectionFrequency) {
		this.collectionFrequency = collectionFrequency;
	}

	public Double getUploadFrequency() {
		return uploadFrequency;
	}

	public void setUploadFrequency(Double uploadFrequency) {
		this.uploadFrequency = uploadFrequency;
	}

}
