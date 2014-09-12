package dto;

public class VocaDTO {

	public boolean checked;
	public String english;
	public String korean;
	public boolean isChecked() {
		return checked;
	}
	public String getEnglish() {
		return english;
	}
	public String getKorean() {
		return korean;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public void setKorean(String korean) {
		this.korean = korean;
	}
}
