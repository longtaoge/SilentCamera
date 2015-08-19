package org.xiangbalao.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.text.format.DateFormat;

public class SilentAutoFocusCallback implements AutoFocusCallback {
	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		// 自动对焦
		processPoto(success, camera);
	}

	private void processPoto(boolean success, Camera camera) {
		if (success && camera != null) {
			savePoto(camera);
		} else {
			savePoto(camera);
		}
	}

	private void savePoto(Camera camera) {
		// 设置回调，参数（快门，源数据，JPEG数据）
		camera.takePicture(null, null, new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// 使用当前的时间拼凑图片的名称
				String name = DateFormat.format("yyyy_MM_dd_hhmmss",
						Calendar.getInstance(Locale.CHINA))
						+ ".jpg";
				File file = new File(Config4Camera.POTOPATH);
				file.mkdirs(); // 创建文件夹保存照片
				String filename = file.getPath() + File.separator + name;
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				try {
					FileOutputStream fileOutputStream = new FileOutputStream(
							filename);
					boolean b = bitmap.compress(CompressFormat.JPEG, 100,
							fileOutputStream);
					fileOutputStream.flush();
					fileOutputStream.close();
					
					Config4Camera.silentPotoList.add(filename);
					/*
					 * if (b) { Toast.makeText(mContext, "照片保存成功",
					 * Toast.LENGTH_LONG) .show(); } else {
					 * Toast.makeText(mContext, "照片保存失败", Toast.LENGTH_LONG)
					 * .show(); }
					 */

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					releaseCamera(camera);// 释放Camera
				}
			}
		});
	};

	/**
	 * 释放摄像头资源
	 */
	private void releaseCamera(Camera camera) {
		if (camera != null) {
			try {
				camera.setPreviewDisplay(null);
				camera.stopPreview();
				camera.release();
				camera = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
