package org.xiangbalao.camera;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;

public class SilentCamera {

	static Camera camera;

	/**
	 * 初始化设置Camera.打开摄像头 默认是先打开前置摄像头，如果没有前置摄像头的话就打开后置摄像头
	 * 
	 * @param camera
	 * @param mContext
	 * @param autoFocusCallback
	 */
	public static void openCamera(Context mContext,
			AutoFocusCallback autoFocusCallback) {

		Camera.CameraInfo cameraInfo = new CameraInfo();
		// 获得设备上的硬件camera数量
		int count = Camera.getNumberOfCameras();

		for (int i = 0; i < count; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				try {
					camera = Camera.open(i); // 尝试打开前置摄像头
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (camera == null) {
			for (int i = 0; i < count; i++) {
				Camera.getCameraInfo(i, cameraInfo);
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
					try {
						camera = Camera.open(i); // 尝试打开后置摄像头
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		try {
			if (camera != null) {
				camera.startPreview(); // 打开预览画面
				camera.autoFocus(new SilentAutoFocusCallback()); // 自动聚焦
			} else {
				// Toast.makeText(mContext, "没有前置摄像头",
				// Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
