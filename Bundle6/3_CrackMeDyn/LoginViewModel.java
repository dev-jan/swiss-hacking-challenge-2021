package org.bfe.crackme.p004ui;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.InputStream;
import org.bfe.crackme.C0658R;
import org.bfe.crackme.data.LoggedInUser;
import org.bfe.crackme.util.AESUtil;

/* renamed from: org.bfe.crackme.ui.LoginViewModel */
public class LoginViewModel extends ViewModel {
    private Context context;
    protected DexClassLoader dexClassLoader = null;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    /* access modifiers changed from: package-private */
    public LiveData<LoginFormState> getLoginFormState() {
        return this.loginFormState;
    }

    /* access modifiers changed from: package-private */
    public LiveData<LoginResult> getLoginResult() {
        return this.loginResult;
    }

    public LoginViewModel(Context context2) {
        this.context = context2;
        loadVerifier();
    }

    private void loadVerifier() {
        try {
            InputStream openRawResource = this.context.getResources().openRawResource(C0658R.raw.elib);
            File createTempFile = File.createTempFile("code", "tmp", this.context.getCacheDir());
            AESUtil.writeDecryptedFile(openRawResource, createTempFile);
            this.dexClassLoader = new DexClassLoader(createTempFile.getPath(), createTempFile.getParent(), (String) null, this.context.getClassLoader());
            createTempFile.delete();
        } catch (Exception unused) {
            Log.e("Main", "Failed to load Verifyer.");
        }
    }

    public void login(String str) {
        try {
            Class loadClass = this.dexClassLoader.loadClass("org.bfe.bootstrap.HLLoginCheck");
            String str2 = (String) loadClass.getDeclaredMethod("checkPin", new Class[]{String.class}).invoke(loadClass, new Object[]{str});
            if (str2 == null || str2.length() <= 1) {
                this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.wrong_password)));
                return;
            }
            this.loginResult.setValue(new LoginResult(new LoggedInUser(str2, "Well done you did it.")));
        } catch (Exception unused) {
            this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.error_logging_in)));
        }
    }

    public void loginDataChanged(String str) {
        if (!isPasswordValid(str)) {
            this.loginFormState.setValue(new LoginFormState((Integer) null, Integer.valueOf(C0658R.string.invalid_password)));
        } else {
            this.loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isPasswordValid(String str) {
        return str != null && str.trim().length() == 6;
    }
}
