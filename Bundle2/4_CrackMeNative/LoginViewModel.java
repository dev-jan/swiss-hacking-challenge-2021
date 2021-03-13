package org.bfe.crackmenative.p004ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import org.bfe.crackmenative.C0658R;
import org.bfe.crackmenative.data.LoggedInUser;

/* renamed from: org.bfe.crackmenative.ui.LoginViewModel */
public class LoginViewModel extends ViewModel {

    /* renamed from: x0 */
    protected static int[] f121x0 = {121, 134, 239, 213, 16, 28, 184, 101, 150, 60, 170, 49, 159, 189, 241, 146, 141, 22, 205, 223, 218, 210, 99, 219, 34, 84, 156, 237, 26, 94, 178, 230, 27, 180, 72, 32, 102, 192, 178, 234, 228, 38, 37, 142, 242, 142, 133, 159, 142, 33};
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    public native boolean checkHooking();

    public native int[] checkPw(int[] iArr);

    static {
        System.loadLibrary("native-lib");
    }

    /* access modifiers changed from: package-private */
    public LiveData<LoginFormState> getLoginFormState() {
        return this.loginFormState;
    }

    /* access modifiers changed from: package-private */
    public LiveData<LoginResult> getLoginResult() {
        return this.loginResult;
    }

    public void login(String str) {
        if (checkHooking()) {
            this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.must_not_hook)));
            return;
        }
        try {
            int[] checkPw = checkPw(getCode(str));
            if (checkPw.length > 0) {
                this.loginResult.setValue(new LoginResult(new LoggedInUser(getStringFromCode(checkPw), "Well done you did it.")));
                return;
            }
            this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.login_failed)));
        } catch (Exception unused) {
            this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.error_logging_in)));
        }
    }

    public void loginDataChanged(String str) {
        if (!isPasswordValid(str)) {
            this.loginFormState.setValue(new LoginFormState((Integer) null, Integer.valueOf(C0658R.string.invalid_password)));
        } else if (str.indexOf("HL") < 0) {
            this.loginFormState.setValue(new LoginFormState((Integer) null, Integer.valueOf(C0658R.string.must_contain_HL)));
        } else if (checkHooking()) {
            this.loginFormState.setValue(new LoginFormState((Integer) null, Integer.valueOf(C0658R.string.must_not_hook)));
        } else if (str.indexOf(123) < 0) {
            this.loginFormState.setValue(new LoginFormState((Integer) null, Integer.valueOf(C0658R.string.is_of_format)));
        } else if (str.indexOf(125) < 0) {
            this.loginFormState.setValue(new LoginFormState((Integer) null, Integer.valueOf(C0658R.string.is_of_format)));
        } else {
            this.loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isPasswordValid(String str) {
        return str != null && str.trim().length() > 0;
    }

    /* access modifiers changed from: protected */
    public int[] getCode(String str) {
        byte[] bytes = str.getBytes();
        int[] iArr = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            iArr[i] = bytes[i] ^ f121x0[i];
        }
        return iArr;
    }

    /* access modifiers changed from: protected */
    public String getStringFromCode(int[] iArr) {
        byte[] bArr = new byte[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            bArr[i] = (byte) (iArr[i] ^ f121x0[i]);
        }
        return new String(bArr);
    }
}
