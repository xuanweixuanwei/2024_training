package com.dejavu.utopia.ui.activity;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.Editable;
import android.util.Base64;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.dejavu.utopia.R;
import com.dejavu.utopia.base.BaseActivity;
import com.dejavu.utopia.databinding.ActivityRegisterBinding;
import com.dejavu.utopia.room.AppDatabase;
import com.dejavu.utopia.room.dao.AccountDao;
import com.dejavu.utopia.room.entity.Account;
import com.dejavu.utopia.view.ClearEditText;
import com.dejavu.utopia.view.PasswordEditText;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {
    ClearEditText et_email;
    ClearEditText et_question;
    ClearEditText et_answer;
    PasswordEditText et_password;
    PasswordEditText et_password_again;
    View.OnClickListener listener;
    Button bt_submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    AppDatabase appDatabase;
    AccountDao accountDao;
    private HandlerThread databaseThread;
    private Handler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initListener();
        // 创建 HandlerThread 和相应的 Handler
        databaseThread = new HandlerThread("DatabaseThread");
        databaseThread.start();
        databaseHandler = new Handler(databaseThread.getLooper());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在Activity销毁时释放 HandlerThread
        if (databaseThread != null) {
            databaseThread.quit();
        }
    }

    private void initLayout() {
        et_email = binding.etRegisterEmail;
        et_question = binding.etRegisterQuestion;
        et_answer = binding.etRegisterAnswer;
        et_password = binding.etRegisterPassword1;
        et_password_again = binding.etRegisterPassword2;
        bt_submit = binding.btnRegisterCommit;
    }

    private void initListener() {
        listener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (v == bt_submit) {
                    databaseHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            registerCheck();
                        }
                    });

                }
            }
        };
        bt_submit.setOnClickListener(listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerCheck() {
        Editable emailText = et_email.getText();
        Editable passwordText = et_password.getText();
        Editable passwordAgainText = et_password_again.getText();

        if (emailText == null || passwordText == null || passwordAgainText == null) {
            Toast.makeText(this, "邮箱号、密码、确认密码不可为空", Toast.LENGTH_SHORT).show();
        } else {
            String email = emailText.toString().trim();
            String password = passwordText.toString().trim();
            String passwordAgain = passwordAgainText.toString().trim();
            if (email.length() > 0 && password.length() == passwordAgain.length() && (password.length() >= 8 && password.length() <= 16)) {
                if (email.matches(emailPattern)) {
                    if (password.equals(passwordAgain)) {
                        Editable questionText = et_question.getText();
                        if (questionText == null) {
                            register(email, password, null, null);
                        } else {
                            String question = questionText.toString().trim();
                            if (question.length() == 0) {
                                register(email, password, null, null);
                            } else {
                                if (et_answer.getText() == null || et_answer.getText().toString().isEmpty()) {
//                                    show error
                                    et_answer.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                                            R.anim.shake_anim));
                                    Toast.makeText(this, "请填入密保答案或清空密保问题", Toast.LENGTH_SHORT).show();
                                } else {
                                    String answer = et_answer.getText().toString().trim();
                                    register(email, password, question, answer);
                                }
                            }
                        }
                    } else {
                        et_password.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.shake_anim));
                        et_password_again.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.shake_anim));
                        Toast.makeText(this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    et_email.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.shake_anim));
                    Toast.makeText(this, "邮箱号输入格式有误", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "邮箱号、密码、确认密码的长度不符合要求", Toast.LENGTH_SHORT).show();
            }
        }

        String email;
        String password;
        String question;
        String answer;


    }

    private boolean checkEmail(String email) {
        if (appDatabase == null) {
            appDatabase = AppDatabase.getInstance(getApplicationContext());
            accountDao = appDatabase.accountDao();
        }
        return accountDao.checkEmail(email) == null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerByEmail_QA(String email, String password, String question, String answer) {
        String passwordMD5 = md5(password);
        String answerMD5 = md5(answer);
        accountDao.insert(new Account(email, passwordMD5, question, answerMD5));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerByEmail(String email, String password) {
        String passwordMD5 = md5(password);

        accountDao.insert(new Account(email, passwordMD5));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void register(String email, String password, String question, String answer) {
        if (checkEmail(email)) {
            if (question == null) {
                registerByEmail(email, password);
            } else {
                registerByEmail_QA(email, password, question, answer);
            }
            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
            toLogin(email, password);
        } else {
            et_email.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.shake_anim));
            Toast.makeText(this, "当前邮箱已经被注册！", Toast.LENGTH_SHORT).show();
        }
    }

    private void toLogin(String email, String password) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("password", password);
        intent.putExtras(bundle);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,600);
    }

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(str.getBytes(StandardCharsets.UTF_8));
            // 可选：将其转换为Base64编码，便于传输和存储
            str = Base64.encodeToString(messageDigest, Base64.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return str;
    }
}