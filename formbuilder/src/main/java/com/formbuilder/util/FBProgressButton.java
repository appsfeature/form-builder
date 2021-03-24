package com.formbuilder.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;

import com.formbuilder.R;


/**
 * @author Created by Abhijit Rao on 8/28/2017.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class FBProgressButton {

    //================================================================================
    // Properties
    //================================================================================
    private static final long BUTTON_PROGRESS_TIME = 500;
    private final ProgressBar pBar;
    private final Button btnAction;
    private final ImageView ivStatus;
    private final Context context;
    private int mOriginalWidth, mOriginalHeight;
    private String text;

    //================================================================================
    // Constructors
    //================================================================================
    public interface Listener {
        void onAnimationCompleted();
    }

    public static FBProgressButton newInstance(Activity activity) {
        return new FBProgressButton(activity, activity.getWindow().getDecorView().getRootView());
    }

    public static FBProgressButton newInstance(Context context, View v) {
        return new FBProgressButton(context, v);
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    private FBProgressButton(Context context, View view) {
        this.context = context;
        ivStatus = view.findViewById(R.id.iv_status);
        pBar = view.findViewById(R.id.progressBar);
        btnAction = view.findViewById(R.id.btn_action);
        btnAction.post(new Runnable() {
            @Override
            public void run() {
                mOriginalWidth = btnAction.getWidth();
                mOriginalHeight = btnAction.getHeight();
            }
        });
    }

    public static View getActivityRootView(Activity activity) {
        return activity.getWindow().getDecorView().getRootView();
    }

    //================================================================================
    // Accessors
    //================================================================================
    public FBProgressButton setOnClickListener(View.OnClickListener click) {
        if (btnAction != null) {
            btnAction.setOnClickListener(click);
        }
        return this;
    }


    public FBProgressButton setOnEditorActionListener(EditText editText) {
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    // Handle pressing "Enter" key here
                    executeTask();
                    handled = true;
                }
                return handled;
            }
        });
        return this;
    }

    public FBProgressButton setOnEditorActionListener(EditText editText, String btnLabel) {
        editText.setImeActionLabel(btnLabel, EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    // Handle pressing "Enter" key here
                    executeTask();
                    handled = true;
                }
                return handled;
            }
        });
        return this;
    }


    public FBProgressButton setText(String text) {
        this.text = text;
        if (btnAction != null) {
            btnAction.setText(text != null ? text : "");
        }
        return this;
    }

    public String getText() {
        if (btnAction != null) {
            return btnAction.getText().toString();
        } else {
            return "";
        }
    }

    public FBProgressButton setBackground(int drawable) {
        if (btnAction != null) {
            btnAction.setBackgroundResource(drawable);
            ivStatus.setBackgroundResource(drawable);
            pBar.setBackgroundResource(drawable);
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FBProgressButton setBackgroundTint(int colorRes) {
        if (btnAction != null) {
            btnAction.setBackgroundTintList(ColorStateList.valueOf(getColorRes(btnAction.getContext(), colorRes)));
        }
        return this;
    }




    //================================================================================
    // Action
    //================================================================================
    public void startProgress() {
        startProgress(null);
    }
    public void startProgress(final Listener listener) {
        startHideButtonAction(listener);
    }

    public void revertSuccessProgress(final Listener listener) {
        Handler handler = new Handler();
        Runnable runnableRevert = new Runnable() {
            @Override
            public void run() {
                listener.onAnimationCompleted();
            }
        };
        revertSuccessProgress();
        handler.postDelayed(runnableRevert, BUTTON_PROGRESS_TIME);
    }

    public void revertProgress() {
        revertProgress(null);
    }

    public void revertProgress(String buttonText) {
        if (!TextUtils.isEmpty(buttonText)) {
            setText(buttonText);
        }
        startShowButtonAction();
    }

    public void revertSuccessProgress() {
        revertSuccessProgress(false);
    }

    public void revertSuccessProgress(Boolean btnVisibility) {
        hideProgressBar();
        if (btnVisibility) {
            startShowButtonAction();
        } else {
            showSuccessView();
        }
    }


    public void performClick() {
        btnAction.performClick();
    }

    public void executeTask() {
        btnAction.performClick();
    }

    //================================================================================
    // Utility
    //================================================================================


    private void showSuccessView() {
        if (ivStatus != null) {
            startAlphaAnimation(ivStatus, View.VISIBLE);
        }
    }

    private void showProgressBar() {
        if (pBar != null) {
            startAlphaAnimation(pBar, View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (pBar != null) {
            startAlphaAnimation(pBar, View.INVISIBLE);
        }
    }

    private void startShowButtonAction() {
        if (btnAction != null) {
            int startPoint, endPoint;
            startPoint = mOriginalHeight;
            endPoint = mOriginalWidth;

            ValueAnimator heightAnimation = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight);
            heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.height = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });

            ValueAnimator widthAnimation = ValueAnimator.ofInt(startPoint, endPoint);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.width = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(3000);
            animatorSet.playTogether(
                    heightAnimation, widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    hideProgressBar();
                    btnAction.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    btnAction.setText(text);
                    btnAction.setVisibility(View.VISIBLE);
//                    ivStatus.setVisibility(View.GONE);
                    startAlphaAnimation(ivStatus, View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            hideProgressBar();
            btnAction.setVisibility(View.VISIBLE);
//            ivStatus.setVisibility(View.GONE);
            startAlphaAnimation(ivStatus, View.INVISIBLE);

            widthAnimation.start();
        }
    }

    private void startHideButtonAction(final Listener listener) {

        if (btnAction != null) {
            int startPoint, endPoint;
            startPoint = mOriginalWidth;
            endPoint = mOriginalHeight;

            ValueAnimator heightAnimation = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight);
            heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.height = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });

            ValueAnimator widthAnimation = ValueAnimator.ofInt(startPoint, endPoint);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.width = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(3000);
            animatorSet.playTogether(
                     widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    btnAction.setText("");
                    showProgressBar();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    btnAction.setVisibility(View.INVISIBLE);
                    listener.onAnimationCompleted();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            widthAnimation.start();
        }
    }

    public FBProgressButton setVisibility(int visibility) {
        if (btnAction != null) {
            btnAction.setVisibility(visibility);
        }
        return this;
    }


    private Drawable getDrawableRes(Context context, int resource) {
        return ContextCompat.getDrawable(context, resource);
    }

    private int getColorRes(Context context, int resource) {
        return ContextCompat.getColor(context, resource);
    }


    public static void startAlphaAnimation(View v, int visibility) {
        startAlphaAnimation(v, 10, visibility);
    }

    public static void startAlphaAnimation(final View v, long duration, final int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (visibility == View.VISIBLE) {
                    v.setVisibility(visibility);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(visibility);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
