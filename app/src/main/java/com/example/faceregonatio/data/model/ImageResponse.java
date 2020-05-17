package com.example.faceregonatio.data.model;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {

	@SerializedName("image")
	private String image;

	@SerializedName("path")
	private String path;

	@SerializedName("success")
	private boolean success;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	@Override
 	public String toString(){
		return 
			"ImageResponse{" +
			"image = '" + image + '\'' + 
			",path = '" + path + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}