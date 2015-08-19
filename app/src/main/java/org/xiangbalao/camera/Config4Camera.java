package org.xiangbalao.camera;

import java.util.ArrayList;
import java.util.List;

import android.os.Environment;


public class Config4Camera {
    //存储路径
	public static String POTOPATH =   Environment.getExternalStorageDirectory()+"/silent";
	public static List<String> silentPotoList= new ArrayList<String>();

}
