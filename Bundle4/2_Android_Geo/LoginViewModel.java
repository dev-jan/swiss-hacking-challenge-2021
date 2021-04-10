package org.bfe.geoblocking.p004ui.login;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bfe.geoblocking.C0658R;
import org.bfe.geoblocking.data.model.LoggedInUser;
import org.json.JSONObject;

/* renamed from: org.bfe.geoblocking.ui.login.LoginViewModel */
public class LoginViewModel extends ViewModel {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String checkURL = "https://ja3er.com/img";
    private String ipCheckUrl = "https://ipinfo.io/json";
    /* access modifiers changed from: private */
    public String ipCountryLocation = null;
    /* access modifiers changed from: private */
    public String ipRegionLocation = null;
    /* access modifiers changed from: private */
    public MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    /* access modifiers changed from: private */
    public MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    /* access modifiers changed from: package-private */
    public LiveData<LoginFormState> getLoginFormState() {
        return this.loginFormState;
    }

    /* access modifiers changed from: package-private */
    public LiveData<LoginResult> getLoginResult() {
        return this.loginResult;
    }

    public void login(Activity activity, String str) {
        if (!hasInternet(activity)) {
            this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.not_connected)));
        } else if (this.ipCountryLocation != null) {
            try {
                String format = String.format("%s.%s.%s", new Object[]{((TextView) activity.findViewById(C0658R.C0661id.country)).getText(), ((TextView) activity.findViewById(C0658R.C0661id.region)).getText(), str});
                new LoginTask().execute(new String[]{String.format("%s/%s", new Object[]{checkURL, getMD5(format)})});
            } catch (Exception unused) {
                this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.error_logging_in)));
            }
        } else {
            this.loginResult.setValue(new LoginResult(Integer.valueOf(C0658R.string.wrong_geoip)));
        }
    }

    public void loginDataChanged(Activity activity, String str) {
        if (!hasInternet(activity)) {
            this.loginFormState.setValue(new LoginFormState(this.ipCountryLocation, this.ipRegionLocation, Integer.valueOf(C0658R.string.not_connected), (Integer) null));
        } else if (this.ipCountryLocation == null) {
            new JsonTask().execute(new String[]{this.ipCheckUrl});
        } else if (!isPasswordValid(str)) {
            this.loginFormState.setValue(new LoginFormState(this.ipCountryLocation, this.ipRegionLocation, (Integer) null, Integer.valueOf(C0658R.string.invalid_password)));
        } else {
            String str2 = this.ipCountryLocation;
            if (str2 != null && !str2.contains("CH")) {
                this.loginFormState.setValue(new LoginFormState(this.ipCountryLocation, this.ipRegionLocation, Integer.valueOf(C0658R.string.invalid_country), (Integer) null));
            } else if (this.ipCountryLocation == null || this.ipRegionLocation == null) {
                this.loginFormState.setValue(new LoginFormState(true));
            } else {
                this.loginFormState.setValue(new LoginFormState(this.ipCountryLocation, this.ipRegionLocation, Integer.valueOf(C0658R.string.invalid_region), (Integer) null));
            }
        }
    }

    private boolean hasInternet(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getApplicationContext().getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        if (networkInfo2 != null && networkInfo2.isConnected()) {
            return true;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public static String getMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(str.getBytes());
        return bytesToHex(instance.digest());
    }

    public static String bytesToHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i] & 255;
            int i2 = i * 2;
            char[] cArr2 = HEX_ARRAY;
            cArr[i2] = cArr2[b >>> 4];
            cArr[i2 + 1] = cArr2[b & 15];
        }
        return new String(cArr).toLowerCase();
    }

    private boolean isPasswordValid(String str) {
        return str != null && str.trim().length() == 4;
    }

    /* renamed from: org.bfe.geoblocking.ui.login.LoginViewModel$JsonTask */
    private class JsonTask extends AsyncTask<String, Void, String> {
        private JsonTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            LoginViewModel.this.loginFormState.setValue(new LoginFormState(LoginViewModel.this.ipCountryLocation, LoginViewModel.this.ipRegionLocation, Integer.valueOf(C0658R.string.checking_internet), (Integer) null));
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x0083  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x0088 A[SYNTHETIC, Splitter:B:35:0x0088] */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x0094  */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x0099 A[SYNTHETIC, Splitter:B:44:0x0099] */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x00a5  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x00aa A[SYNTHETIC, Splitter:B:53:0x00aa] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:30:0x007e=Splitter:B:30:0x007e, B:39:0x008f=Splitter:B:39:0x008f} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String doInBackground(java.lang.String... r8) {
            /*
                r7 = this;
                r0 = 0
                java.net.URL r1 = new java.net.URL     // Catch:{ MalformedURLException -> 0x008c, IOException -> 0x007b, all -> 0x0076 }
                r2 = 0
                r8 = r8[r2]     // Catch:{ MalformedURLException -> 0x008c, IOException -> 0x007b, all -> 0x0076 }
                r1.<init>(r8)     // Catch:{ MalformedURLException -> 0x008c, IOException -> 0x007b, all -> 0x0076 }
                java.net.URLConnection r8 = r1.openConnection()     // Catch:{ MalformedURLException -> 0x008c, IOException -> 0x007b, all -> 0x0076 }
                java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch:{ MalformedURLException -> 0x008c, IOException -> 0x007b, all -> 0x0076 }
                r8.connect()     // Catch:{ MalformedURLException -> 0x0073, IOException -> 0x0070, all -> 0x006c }
                java.io.InputStream r1 = r8.getInputStream()     // Catch:{ MalformedURLException -> 0x0073, IOException -> 0x0070, all -> 0x006c }
                java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ MalformedURLException -> 0x0073, IOException -> 0x0070, all -> 0x006c }
                java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ MalformedURLException -> 0x0073, IOException -> 0x0070, all -> 0x006c }
                r3.<init>(r1)     // Catch:{ MalformedURLException -> 0x0073, IOException -> 0x0070, all -> 0x006c }
                r2.<init>(r3)     // Catch:{ MalformedURLException -> 0x0073, IOException -> 0x0070, all -> 0x006c }
                java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                r1.<init>()     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
            L_0x0025:
                java.lang.String r3 = r2.readLine()     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                if (r3 == 0) goto L_0x0056
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                r4.<init>()     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                r4.append(r3)     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                java.lang.String r5 = "\n"
                r4.append(r5)     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                java.lang.String r4 = r4.toString()     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                r1.append(r4)     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                java.lang.String r4 = "Response: "
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                r5.<init>()     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                java.lang.String r6 = "> "
                r5.append(r6)     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                r5.append(r3)     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                java.lang.String r3 = r5.toString()     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                android.util.Log.d(r4, r3)     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                goto L_0x0025
            L_0x0056:
                java.lang.String r0 = r1.toString()     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
                if (r8 == 0) goto L_0x005f
                r8.disconnect()
            L_0x005f:
                r2.close()     // Catch:{ IOException -> 0x0063 }
                goto L_0x0067
            L_0x0063:
                r8 = move-exception
                r8.printStackTrace()
            L_0x0067:
                return r0
            L_0x0068:
                r1 = move-exception
                goto L_0x007e
            L_0x006a:
                r1 = move-exception
                goto L_0x008f
            L_0x006c:
                r1 = move-exception
                r2 = r0
                r0 = r1
                goto L_0x00a3
            L_0x0070:
                r1 = move-exception
                r2 = r0
                goto L_0x007e
            L_0x0073:
                r1 = move-exception
                r2 = r0
                goto L_0x008f
            L_0x0076:
                r8 = move-exception
                r2 = r0
                r0 = r8
                r8 = r2
                goto L_0x00a3
            L_0x007b:
                r1 = move-exception
                r8 = r0
                r2 = r8
            L_0x007e:
                r1.printStackTrace()     // Catch:{ all -> 0x00a2 }
                if (r8 == 0) goto L_0x0086
                r8.disconnect()
            L_0x0086:
                if (r2 == 0) goto L_0x00a1
                r2.close()     // Catch:{ IOException -> 0x009d }
                goto L_0x00a1
            L_0x008c:
                r1 = move-exception
                r8 = r0
                r2 = r8
            L_0x008f:
                r1.printStackTrace()     // Catch:{ all -> 0x00a2 }
                if (r8 == 0) goto L_0x0097
                r8.disconnect()
            L_0x0097:
                if (r2 == 0) goto L_0x00a1
                r2.close()     // Catch:{ IOException -> 0x009d }
                goto L_0x00a1
            L_0x009d:
                r8 = move-exception
                r8.printStackTrace()
            L_0x00a1:
                return r0
            L_0x00a2:
                r0 = move-exception
            L_0x00a3:
                if (r8 == 0) goto L_0x00a8
                r8.disconnect()
            L_0x00a8:
                if (r2 == 0) goto L_0x00b2
                r2.close()     // Catch:{ IOException -> 0x00ae }
                goto L_0x00b2
            L_0x00ae:
                r8 = move-exception
                r8.printStackTrace()
            L_0x00b2:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.bfe.geoblocking.p004ui.login.LoginViewModel.JsonTask.doInBackground(java.lang.String[]):java.lang.String");
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            parseJSON(str);
        }

        private void parseJSON(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getString("country") != null) {
                    String unused = LoginViewModel.this.ipCountryLocation = jSONObject.getString("country");
                }
                if (jSONObject.getString("region") != null) {
                    String unused2 = LoginViewModel.this.ipRegionLocation = jSONObject.getString("region");
                }
                LoginViewModel.this.loginFormState.setValue(new LoginFormState(LoginViewModel.this.ipCountryLocation, LoginViewModel.this.ipRegionLocation, Integer.valueOf(C0658R.string.ip_check_done), (Integer) null));
            } catch (Exception e) {
                Log.i("App", "Error parsing data" + e.getMessage());
            }
        }
    }

    /* renamed from: org.bfe.geoblocking.ui.login.LoginViewModel$LoginTask */
    private class LoginTask extends AsyncTask<String, Void, String> {
        private LoginTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
            if (r5 != null) goto L_0x0030;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0030, code lost:
            r5.disconnect();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0039, code lost:
            if (r5 != null) goto L_0x0030;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
            return null;
         */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0040  */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:20:0x0036=Splitter:B:20:0x0036, B:14:0x002b=Splitter:B:14:0x002b} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String doInBackground(java.lang.String... r5) {
            /*
                r4 = this;
                r0 = 0
                java.net.URL r1 = new java.net.URL     // Catch:{ MalformedURLException -> 0x0034, IOException -> 0x0029, all -> 0x0024 }
                r2 = 0
                r5 = r5[r2]     // Catch:{ MalformedURLException -> 0x0034, IOException -> 0x0029, all -> 0x0024 }
                r1.<init>(r5)     // Catch:{ MalformedURLException -> 0x0034, IOException -> 0x0029, all -> 0x0024 }
                java.net.URLConnection r5 = r1.openConnection()     // Catch:{ MalformedURLException -> 0x0034, IOException -> 0x0029, all -> 0x0024 }
                java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ MalformedURLException -> 0x0034, IOException -> 0x0029, all -> 0x0024 }
                r5.connect()     // Catch:{ MalformedURLException -> 0x0022, IOException -> 0x0020 }
                java.io.InputStream r1 = r5.getInputStream()     // Catch:{ MalformedURLException -> 0x0022, IOException -> 0x0020 }
                java.lang.String r0 = r4.getStringFromStream(r1)     // Catch:{ MalformedURLException -> 0x0022, IOException -> 0x0020 }
                if (r5 == 0) goto L_0x001f
                r5.disconnect()
            L_0x001f:
                return r0
            L_0x0020:
                r1 = move-exception
                goto L_0x002b
            L_0x0022:
                r1 = move-exception
                goto L_0x0036
            L_0x0024:
                r5 = move-exception
                r3 = r0
                r0 = r5
                r5 = r3
                goto L_0x003e
            L_0x0029:
                r1 = move-exception
                r5 = r0
            L_0x002b:
                r1.printStackTrace()     // Catch:{ all -> 0x003d }
                if (r5 == 0) goto L_0x003c
            L_0x0030:
                r5.disconnect()
                goto L_0x003c
            L_0x0034:
                r1 = move-exception
                r5 = r0
            L_0x0036:
                r1.printStackTrace()     // Catch:{ all -> 0x003d }
                if (r5 == 0) goto L_0x003c
                goto L_0x0030
            L_0x003c:
                return r0
            L_0x003d:
                r0 = move-exception
            L_0x003e:
                if (r5 == 0) goto L_0x0043
                r5.disconnect()
            L_0x0043:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.bfe.geoblocking.p004ui.login.LoginViewModel.LoginTask.doInBackground(java.lang.String[]):java.lang.String");
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            Integer valueOf = Integer.valueOf(C0658R.string.login_failed);
            if (str == null) {
                LoginViewModel.this.loginResult.setValue(new LoginResult(valueOf));
            } else if (str.length() > 0) {
                LoginViewModel.this.loginResult.setValue(new LoginResult(new LoggedInUser(str, "Well done you did it.")));
            } else {
                LoginViewModel.this.loginResult.setValue(new LoginResult(valueOf));
            }
        }

        private String getStringFromStream(InputStream inputStream) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb.append(readLine);
                        sb.append(10);
                    } else {
                        try {
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                    inputStream.close();
                } catch (Throwable th) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    throw th;
                }
            }
            inputStream.close();
            return sb.toString();
        }
    }
}
