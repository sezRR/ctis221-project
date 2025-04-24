package dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.auth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CustomUriSchemeHandler {

    private static final String SCHEME_NAME = "oauthapp";
    
    /**
     * Uygulama için özel bir URI şeması kaydeder.
     * @param executablePath Uygulamanın çalıştırılabilir dosyasının tam yolu
     * @return Kayıt başarılı olduysa true, değilse false
     */
    public static boolean registerUriScheme(String executablePath) {
        String os = System.getProperty("os.name").toLowerCase();
        
        try {
            if (os.contains("win")) {
                return registerWindowsUriScheme(executablePath);
            } else if (os.contains("mac")) {
                return registerMacUriScheme(executablePath);
            } else if (os.contains("nix") || os.contains("nux")) {
                return registerLinuxUriScheme(executablePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Windows'ta URI şeması kaydeder (registry kullanır)
     */
    private static boolean registerWindowsUriScheme(String executablePath) throws IOException, InterruptedException {
        String command = String.format(
                "reg add \"HKCU\\Software\\Classes\\%s\" /ve /d \"URL:Custom Protocol\" /f & " +
                "reg add \"HKCU\\Software\\Classes\\%s\" /v \"URL Protocol\" /d \"\" /f & " +
                "reg add \"HKCU\\Software\\Classes\\%s\\shell\\open\\command\" /ve /d \"\\\"%s\\\" \\\"%%1\\\"\" /f",
                SCHEME_NAME, SCHEME_NAME, SCHEME_NAME, executablePath.replace("/", "\\"));
        
        Process process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", command});
        return process.waitFor() == 0;
    }
    
    /**
     * macOS'ta URI şeması kaydeder (Info.plist kullanır)
     */
    private static boolean registerMacUriScheme(String executablePath) {
        // Not: macOS için genellikle Info.plist dosyasını güncelleme gerekiyor
        // Bu örnekte basitleştirilmiş bir yaklaşım kullanıyoruz
        System.out.println("macOS URI şema kaydı genellikle Info.plist dosyasında yapılmalıdır.");
        return false;
    }
    
    /**
     * Linux'ta URI şeması kaydeder (desktop dosyaları kullanır)
     */
    private static boolean registerLinuxUriScheme(String executablePath) throws IOException {
        String home = System.getProperty("user.home");
        Path desktopFile = Paths.get(home, ".local", "share", "applications", SCHEME_NAME + "-handler.desktop");
        
        // Klasör yapılandırmasını kontrol et
        Files.createDirectories(desktopFile.getParent());
        
        List<String> lines = List.of(
                "[Desktop Entry]",
                "Name=OAuth Application",
                "Exec=" + executablePath + " %u",
                "Type=Application",
                "NoDisplay=true",
                "MimeType=x-scheme-handler/" + SCHEME_NAME + ";"
        );
        
        Files.write(desktopFile, lines);
        
        // xdg-mime ile kaydet
        ProcessBuilder pb = new ProcessBuilder(
                "xdg-mime", "default", 
                SCHEME_NAME + "-handler.desktop", 
                "x-scheme-handler/" + SCHEME_NAME
        );
        
        Process process = pb.start();
        try {
            return process.waitFor() == 0;
        } catch (InterruptedException e) {
            return false;
        }
    }
    
    /**
     * Uygulama için URI şemasını döndürür
     */
    public static String getUriScheme() {
        return SCHEME_NAME;
    }
    
    /**
     * Callback URI'sini oluşturur
     */
    public static String getCallbackUri() {
        return SCHEME_NAME + "://oauth/callback";
    }
    
    /**
     * URI'den parametreleri çıkarır
     * @param uri İşlenecek URI
     * @return URI parametrelerinin bir haritası
     */
    public static java.util.Map<String, String> parseUriParameters(String uri) {
        java.util.Map<String, String> params = new java.util.HashMap<>();
        
        try {
            // Temel URI yapısı: scheme://path?query
            int queryIndex = uri.indexOf('?');
            if (queryIndex != -1 && queryIndex < uri.length() - 1) {
                String query = uri.substring(queryIndex + 1);
                for (String param : query.split("&")) {
                    String[] pair = param.split("=", 2);
                    String key = java.net.URLDecoder.decode(pair[0], "UTF-8");
                    String value = pair.length > 1 ? java.net.URLDecoder.decode(pair[1], "UTF-8") : "";
                    params.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return params;
    }
}
