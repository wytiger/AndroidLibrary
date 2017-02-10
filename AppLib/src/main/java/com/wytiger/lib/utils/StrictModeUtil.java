    public class StrictModeUtil{
	
	/**
     * 严格模式，检测违例情况。
     */
    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll() //disk,network,resourceMismatches等
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll() //acitivity,broadcast,sql,io等
                .penaltyLog()
                .build());
    }
	}