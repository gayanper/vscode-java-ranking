package org.gap.vscode.javaranking.ext.internal.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.gap.vscode.javaranking.ext.internal.ExtensionActivator;

public final class MethodModel {
    private Map<String, Integer> scores = new HashMap<>();
    
    private static MethodModel model = new MethodModel();
    
    private MethodModel() {
    }
    
    public void load(List<URL> urls) {
        urls.forEach(this::loadModel);
    }
    
    public static MethodModel getModel() {
        return model;
    }
    
    private void loadModel(URL url) {
        try (var fbr = new BufferedReader(new InputStreamReader(url.toURI().toURL().openStream()))) {
            fbr.lines().forEach(line -> {
                final String[] pair = line.split("=");
                if (pair.length == 2) {
                    scores.put(pair[0], Integer.parseInt(pair[1]));
                }
            });
        } catch (IOException | URISyntaxException e) {
            ExtensionActivator.logError(e.getMessage(), e);
        }
    }
    
    public Optional<Integer> findScore(String typeSig, String methodSig, String methodName, String scope) {
        String key = scoreKey(typeSig, methodSig, methodName, scope);
        return Optional.ofNullable(scores.get(key));
    }
    
    private static String scoreKey(String typeSig, String methodSig, String methodName, String scope) {
        return typeSig.concat("/").concat(scope).concat("/").concat(methodName).concat("/").concat(methodSig);
    }
}
