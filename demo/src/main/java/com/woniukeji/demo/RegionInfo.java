package com.woniukeji.demo;

import java.util.jar.Attributes.Name;

import android.R.integer;

public class RegionInfo {
	
	private String name;
	private String adcode;
	public RegionInfo() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public RegionInfo(int id, int parent, String name) {
		super();

		this.name = name;
	}



	@Override
	public String toString()
	{
		return "RegionInfo [name=" + name + "]";
	}

	
	  //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
		return name;
    }
	
	

}
