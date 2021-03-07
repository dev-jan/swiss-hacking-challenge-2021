package org.bfe.crackmesimple.p004ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dalvik.system.DexClassLoader;
import org.bfe.crackmesimple.C0658R;
import org.bfe.crackmesimple.data.LoggedInUser;
import org.bfe.crackmesimple.util.AESUtil;

/* renamed from: org.bfe.crackmesimple.ui.LoginViewModel */
public class LoginViewModel extends ViewModel {
    private static byte[] exs = {-28, 73, 79, 78, 113, 73, 101, 98, 115, 6, 27, -35, 111, -55, -114, -11, -29, 0, -73, 91, 115, -24, -4, -94, -59, 43, -57, 112, 11, -54, -115, 2};
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

    public void login(String str) {
        try {
            String str2 = new String(AESUtil.decrypt(exs));
            if (str.equals(str2)) {
                this.loginResult.setValue(new LoginResult(new LoggedInUser(str2, "Well done you did it.")));
                return;
            }
            this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.wrong_password)));
        } catch (Exception unused) {
            this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.error_logging_in)));
        }
    }

    public void loginDataChanged(String str) {
        if (!isPasswordValid(str)) {
            this.loginFormState.setValue(new LoginFormState((Integer) null, Integer.valueOf(C0658R.string.invalid_password)));
        } else if (!str.startsWith("HL")) {
            this.loginFormState.setValue(new LoginFormState((Integer) null, Integer.valueOf(C0658R.string.has_to_start_with)));
        } else {
            this.loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isPasswordValid(String str) {
        return str != null && str.trim().length() > 6;
    }
}
