package com.cts.menumodule.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class ImageUploadRequest {
	@NotNull(message = "Restaurant image must not be empty")
	private MultipartFile image;

	/**
	 * @return the image
	 */
	public MultipartFile getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(MultipartFile image) {
		this.image = image;
	}
}
