package com.woniukeji.jianmerchant.utils;

import android.view.View;

public class ViewFindUtils
{

	/**
	 * 替代findviewById方法
	 */
	public static <T extends View> T find(View view, int id)
	{
		return (T) view.findViewById(id);
	}
}
