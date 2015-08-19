package org.xiangbalao;

import org.xiangbalao.camera.Config4Camera;
import org.xiangbalao.camera.LogUtil;
import org.xiangbalao.camera.SilentAutoFocusCallback;
import org.xiangbalao.camera.SilentCamera;
import org.xiangbalao.silentCamera.R;
import org.xiangbalao.ui.FixedGridLayout;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 描述：不用预览直接静默拍照
 * 
 * @author longtaoge
 * 
 */
public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	ViewGroup container = null;

	// private boolean isPreviewing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		//初始化
		intiView();
		//定时器
		timer();
	}

	private void intiView() {
		ViewGroup parent = (ViewGroup) findViewById(R.id.parent);
		container = new FixedGridLayout(this);
		container.setClipChildren(false);
		((FixedGridLayout) container).setCellHeight(300);
		((FixedGridLayout) container).setCellWidth(300);
		final LayoutTransition transitioner = new LayoutTransition();
		container.setLayoutTransition(transitioner);
		parent.addView(container);
		parent.setClipChildren(false);
	}

	@Override
	protected void onResume() {

		super.onResume();
		LogUtil.i("SilentCamera", "Onresume");
		// SilentCamera.openCamera(MainActivity.this,
		// new SilentAutoFocusCallback());
	}

	public void addView(Uri uri) {
		ImageView mImageView = new ImageView(MainActivity.this);
		
		
		
		mImageView.setImageURI(uri);
		LayoutParams mLayoutParams =new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);	
		
		mImageView.setPadding(10, 10, 10, 10);
		mImageView.setLayoutParams(mLayoutParams);
		
		mImageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				container.removeView(v);
			}
		});
		container.addView(mImageView, Math.min(1, container.getChildCount()));

	}

	public void timer() {

		CountDownTimer mTimer = new CountDownTimer(10000, 2000) {
			@Override
			public void onTick(long millisUntilFinished) {
				SilentCamera.openCamera(MainActivity.this,
						new SilentAutoFocusCallback());

				LogUtil.i("mill", millisUntilFinished + "倒计时");
				if (Config4Camera.silentPotoList.size() > 0) {
					String mUri = Config4Camera.silentPotoList
							.get(Config4Camera.silentPotoList.size() - 1);

					LogUtil.i(MainActivity.this.getClass().getSimpleName(),
							mUri);

					addView(Uri.parse(mUri));
				}

			}

			@Override
			public void onFinish() {

				// mTextView.setText("倒计时：结束");
			}
		};

		mTimer.start();
	}

}
